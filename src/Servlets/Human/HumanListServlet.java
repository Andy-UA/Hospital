package Servlets.Human;

import Beans.Human;
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
import java.util.List;

/**
 * Created by Andrew on 12.04.2017.
 */
@WebServlet(urlPatterns = { "/humanList" })
public class HumanListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HumanListServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected Boolean isEmpty(String v)
    {
        return v == null || v.isEmpty();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            List<Human> list = null;
            Long page = HttpServletUtil.getLongParam(request, "page", 1L);
            if (page == 0)
                page = 1L;
            Connection conn = SessionFactory.getStoredConnection(request);
            list = HumanFactory.query(conn, request.getParameter("firstName"), request.getParameter("lastName"));
            request.setAttribute("firstName", isEmpty(request.getParameter("firstName")) ? "%" : request.getParameter("firstName"));
            request.setAttribute("lastName", isEmpty(request.getParameter("lastName")) ? "%" : request.getParameter("lastName"));
            request.setAttribute("errorString", HttpServletUtil.prepareErrorString(errorString));
            request.setAttribute("page", page);
            request.setAttribute("humanList", list);
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/human/humanListView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/humanList");
        }
    }
}
