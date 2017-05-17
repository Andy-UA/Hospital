package Servlets.Diagnosis;

import Beans.Diagnosis;
import Factories.SessionFactory;
import Factories.DiagnosisFactory;
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
 * Created by Andrew on 10.04.2017.
 */
@WebServlet(urlPatterns = { "/diagnosisList" })
public class DiagnosisListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DiagnosisListServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        List<Diagnosis> list = null;
        String code = request.getParameter("code");
        String name = request.getParameter("name");

        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            list = DiagnosisFactory.query(conn, code, name);
        } catch (SQLException e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        // Store info in request attribute, before forward to views
        request.setAttribute("errorString", HttpServletUtil.prepareErrorString(errorString));
        request.setAttribute("diagnosisList", list);
        request.setAttribute("code", code);
        request.setAttribute("name", name);

        // Forward to /WEB-INF/views/diagnosisListView.jsp
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/diagnosis/diagnosisListView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
