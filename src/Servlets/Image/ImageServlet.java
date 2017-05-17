package Servlets.Image;

import Beans.Human;
import Beans.Image;
import Types.ImageScope;
import Factories.SessionFactory;
import Factories.HumanFactory;
import Factories.ImageFactory;
import Servlets.Common.HttpServletUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Andrew on 15.04.2017.
 */
@WebServlet(urlPatterns = { "/image" })
public class ImageServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        String errorString = null;
        boolean isMultipart;
        int maxFileSize = 10000 * 1024;
        int maxMemSize = 4 * 1024;
        Long humanID = 0L;
        String note = null;
        String scope = null;
        String contentType = "image/jpeg";
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter( );
        if( !isMultipart ){
            errorString = "No file uploaded";
        }
        else {
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                // maximum size that will be stored in memory
                factory.setSizeThreshold(maxMemSize);
                // Location to save data that is larger than maxMemSize.
                String tempFilePath = getServletContext().getInitParameter("file-upload");
                if (tempFilePath == null || tempFilePath.isEmpty())
                    tempFilePath = "c:\\temp";
                factory.setRepository(new File(tempFilePath));

                // Create a new file upload handler
                ServletFileUpload upload = new ServletFileUpload(factory);
                // maximum file size to be uploaded.
                upload.setSizeMax(maxFileSize);
                // Parse the request to get file items.
                List fileItems = upload.parseRequest(request);

                // Process the uploaded file items
                Iterator i = fileItems.iterator();
                while (i.hasNext()) {
                    FileItem fi = (FileItem) i.next();
                    String fieldName = fi.getFieldName();
                    if (fi.isFormField()) {
                        if (fieldName.equalsIgnoreCase("humanID")) {
                            String x = fi.getString();
                            humanID = Long.parseLong(x);
                        }
                        if (fieldName.equalsIgnoreCase("scope")) {
                            scope = fi.getString();
                            if (scope == null)
                                scope = "Path";
                        }
                        if (fieldName.equalsIgnoreCase("note")) {
                            note = fi.getString();
                        }
                    }
                }

                if (humanID > 0 && scope != null) {
                    i = fileItems.iterator();
                    while (i.hasNext()) {
                        FileItem fi = (FileItem) i.next();
                        String fieldName = fi.getFieldName();
                        if (!fi.isFormField()) {
                            // Get the uploaded file parameters
                            String fileName = fi.getName();
                            if (fileName == null || fileName.isEmpty())
                                errorString = "File not selected";
                            else {
                                contentType = fi.getContentType();
                                // writing into database
                                Connection conn = SessionFactory.getStoredConnection(request);
                                try {
                                    Image image = new Image(0L, humanID, ImageScope.valueOf(scope), contentType, fi.get(), true, (note != null ? note : "") + " " + fileName);
                                    if (image.getData().length == 0)
                                        errorString = "Data in file is empty";
                                    else if (!ImageFactory.insert(conn, image))
                                        errorString = "Error on uploading file " + fileName + " into database";
                                } catch (SQLException e) {
                                    errorString = e.getMessage();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                errorString = HttpServletUtil.getExeptionMessage(e);
            }
        }

        // If error, forward to Edit page.
        if (errorString != null) {
            request.setAttribute("errorString", HttpServletUtil.prepareErrorString(errorString));
            request.setAttribute("id", "0");
            request.setAttribute("humanID", humanID);
            request.setAttribute("scope", scope);
            if (humanID > 0) {
                Connection conn = SessionFactory.getStoredConnection(request);
                try {
                    Human human = HumanFactory.select(conn, humanID);
                    request.setAttribute("human", human);
                    request.setAttribute("imageScope", ImageScope.values());
                    request.setAttribute("contentType", contentType);
                    request.setAttribute("scope", scope);
                    request.setAttribute("id", "0");
                    request.setAttribute("note", note);
                } catch (Exception e) {
                }
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/Image/imageLoadView.jsp");
                dispatcher.forward(request, response);
            }
            else{
                HttpServletUtil.showError(request, response, errorString, "/humanList");
            }
        }
        // If everything nice.
        // Redirect to the Human page.
        else {
            response.sendRedirect(request.getContextPath() + "/humanEdit?id=" + humanID);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Image image = null;
            String x = request.getParameter("id");
            if (x != null){
                image = ImageFactory.select(SessionFactory.getStoredConnection(request), Long.parseLong(x));
            }
            else{
                x = request.getParameter("humanID");
                if (x != null){
                    String scope = request.getParameter("scope");
                    if (scope == null)
                        scope = "Photo";
                    List<Image> list = ImageFactory.query(SessionFactory.getStoredConnection(request), Long.parseLong(x), ImageScope.valueOf(scope), null);
                    if (list.size() > 0){
                        image = list.get(0);
                    }
                }
            }
            if(image != null){
                response.setBufferSize(image.getData().length);
                response.setContentLength(image.getData().length);
                response.setContentType(image.getContentType());
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                try {
                    bos.write(image.getData());
                }finally {
                    bos.flush();
                    bos.close();
                }
            }
            else
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_OK, e.getMessage() != null ? e.getMessage() : e.toString());
        }
    }
}
