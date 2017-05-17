package Servlets.Diagnosis;

import Factories.SessionFactory;
import Factories.DiagnosisFactory;
import Servlets.Common.HttpServletUtil;

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
@WebServlet(urlPatterns = { "/diagnosisDelete" })
public class DiagnosisDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DiagnosisDeleteServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            DiagnosisFactory.delete(conn, id);
        } catch (Exception e) {
            HttpServletUtil.setResponseException(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
