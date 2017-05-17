package Servlets.Image;

import Beans.ImageDescriptor;
import Types.ImageScope;
import Factories.SessionFactory;
import Factories.ImageFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by andre on 23.04.2017.
 */
@WebServlet(urlPatterns = { "/imageList" })
public class ImageListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try{
            Long humanID = HttpServletUtil.getLongParam(request, "humanID", -1L);
            ImageScope scope = ImageScope.Unknown;
            String x = request.getParameter("scope");
            try{
                scope = ImageScope.valueOf(x);
            }catch (Exception r){
                scope = ImageScope.Unknown;
            }
            String contentType = request.getParameter("contentType");
            if(contentType == null || contentType.isEmpty())
                contentType = "%";

            Connection conn = SessionFactory.getStoredConnection(request);
            List<ImageDescriptor> list = ImageFactory.queryDescriptions(conn, humanID, scope, contentType);
            request.setAttribute("scope", scope);
            request.setAttribute("imageScope", ImageScope.values());
            request.setAttribute("humanID", humanID);
            request.setAttribute("contentType", contentType);
            request.setAttribute("images", list);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/Image/imageListView.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/home");
        }
    }
}
