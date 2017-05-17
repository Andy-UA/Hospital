package Servlets.Image;

import Beans.Human;
import Types.ImageScope;
import Factories.SessionFactory;
import Factories.HumanFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Andrew on 15.04.2017.
 */
@WebServlet(urlPatterns = { "/imageLoad" })
public class ImageLoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long humanID = 0L;
        try {
            String scope = request.getParameter("scope");
            humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);

            if (humanID < 1L)
                throw new Exception("Undefined value for humanID");
            String contentType = getServletContext().getMimeType("Photo.jpg");
            Connection conn = SessionFactory.getStoredConnection(request);
            try {
                Human human = HumanFactory.select(conn, humanID);
                request.setAttribute("human", human);
                request.setAttribute("imageScope", ImageScope.values());
                request.setAttribute("contentType", contentType);
                request.setAttribute("scope", scope == null ? "Photo" : scope);
                request.setAttribute("id", "0");
                request.setAttribute("note", "(untitled image)");
            } catch (SQLException e) {
                errorString = HttpServletUtil.getExeptionMessage(e);
            }
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/Image/imageLoadView.jsp");
            dispatcher.forward(request, response);
        }catch (Exception e){
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/humanEdit?id=" + humanID);
        }
    }
}
