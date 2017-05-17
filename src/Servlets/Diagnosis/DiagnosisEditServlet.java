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

/**
 * Created by Andrew on 10.04.2017.
 */
@WebServlet(urlPatterns = { "/diagnosisEdit" })
public class DiagnosisEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DiagnosisEditServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            request.setAttribute("diagnosis", DiagnosisFactory.select(conn, id));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/diagnosis/diagnosisEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        HttpServletUtil.showError(request, response, errorString, "/diagnosisList");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);

            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            String code = (String) request.getParameter("code");
            String name = (String) request.getParameter("name");
            String note = (String) request.getParameter("note");
            Diagnosis diagnosis = new Diagnosis(id, code, name, note);
            diagnosis.validate();
            if (diagnosis.getId() < 1)
                DiagnosisFactory.insert(conn, diagnosis);
            else
                DiagnosisFactory.update(conn, diagnosis);
            response.sendRedirect(request.getContextPath() + "/diagnosisList");
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/diagnosisList");
        }
    }

}
