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

/**
 * Created by Andrew on 12.04.2017.
 */
@WebServlet(urlPatterns = { "/workplaceEdit" })
public class WorkplaceEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WorkplaceEditServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            String building = (String) request.getParameter("building");
            String floor = (String) request.getParameter("floor");
            String room = (String) request.getParameter("room");
            String note = (String) request.getParameter("note");
            Workplace workplace = new Workplace(id, building, floor, room, note);
            workplace.validate();
            if (workplace.getId() < 1)
                WorkplaceFactory.insert(conn, workplace);
            else
                WorkplaceFactory.update(conn, workplace);
            response.sendRedirect(request.getContextPath() + "/workplaceList");
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        HttpServletUtil.showError(request, response, errorString, "/workplaceList");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            request.setAttribute("workplace", WorkplaceFactory.select(conn, id));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/workplace/workplaceEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/workplaceList");
        }
    }
}
