package Servlets.Workplace;

import Beans.Workplace;
import Factories.SessionFactory;
import Factories.WorkplaceFactory;
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
import java.util.List;

/**
 * Created by Andrew on 12.04.2017.
 */
@WebServlet(urlPatterns = { "/workplaceList" })
public class WorkplaceListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WorkplaceListServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        List<Workplace> list = null;
        String building = request.getParameter("building");
        String room = request.getParameter("room");
        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            list = WorkplaceFactory.query(conn, building, room);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        // Store info in request attribute, before forward to views
        request.setAttribute("errorString", HttpServletUtil.prepareErrorString(errorString));
        request.setAttribute("workplaceList", list);
        request.setAttribute("building", building);
        request.setAttribute("room", room);
        // Forward to /WEB-INF/views/diagnosisListView.jsp
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/workplace/workplaceListView.jsp");
        dispatcher.forward(request, response);
    }
}
