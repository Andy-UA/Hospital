package Servlets.Appointment;

import Beans.Appointment;
import Types.AppointmentType;
import Factories.AppointmentFactory;
import Factories.SessionFactory;
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
@WebServlet(urlPatterns = { "/appointmentEdit" })
public class AppointmentEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AppointmentEditServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);

            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            AppointmentType type = AppointmentType.valueOf(request.getParameter("type"));
            String description = (String) request.getParameter("description");
            String unit = (String) request.getParameter("unit");
            String note = (String) request.getParameter("note");

            Appointment appointment = new Appointment(id, type, description, unit, note);
            appointment.validate();

            if (appointment.getId() < 1)
                AppointmentFactory.insert(conn, appointment);
            else
                AppointmentFactory.update(conn, appointment);
            response.sendRedirect(request.getContextPath() + "/appointmentList");
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        // If error, forward to Edit page.
        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/appointmentList");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            request.setAttribute("appointment", AppointmentFactory.select(conn, id));
            request.setAttribute("appointmentType", AppointmentType.values());
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/appointment/appointmentEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/appointmentList");
        }
    }
}
