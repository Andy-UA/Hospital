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
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Andrew on 12.04.2017.
 */
@WebServlet(urlPatterns = { "/appointmentList" })
public class AppointmentListServlet extends HttpServlet {

    protected Boolean isEmpty(String v)
    {
        return v == null || v.isEmpty();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = SessionFactory.getStoredConnection(request);

        String errorString = null;
        List<Appointment> list = null;
        AppointmentType appointmentType = AppointmentType.Unknown;
        String x = request.getParameter("type");
        String description = request.getParameter("description");
        try {
            if (x != null)
                appointmentType = AppointmentType.valueOf(x);
            list = AppointmentFactory.query(conn, description, x == null, appointmentType);
        } catch (SQLException e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        // Store info in request attribute, before forward to views
        request.setAttribute("errorString", HttpServletUtil.prepareErrorString(errorString));
        request.setAttribute("appointmentList", list);
        request.setAttribute("type", appointmentType);
        request.setAttribute("description", isEmpty(description) ? "%" : description);
        request.setAttribute("appointmentType", AppointmentType.values());
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/appointment/appointmentListView.jsp");
        dispatcher.forward(request, response);
    }
}
