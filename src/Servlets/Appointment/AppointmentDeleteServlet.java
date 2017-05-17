package Servlets.Appointment;

import Factories.AppointmentFactory;
import Factories.SessionFactory;
import Servlets.Common.HttpServletUtil;

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
@WebServlet(urlPatterns = { "/appointmentDelete" })
public class AppointmentDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AppointmentDeleteServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            AppointmentFactory.delete(conn, id);
        } catch (Exception e) {
            HttpServletUtil.setResponseException(response, e);
        }
    }
}
