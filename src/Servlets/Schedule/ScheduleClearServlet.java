package Servlets.Schedule;

import Beans.ScheduleBuild;
import Beans.Staff;
import Factories.SessionFactory;
import Factories.ScheduleFactory;
import Factories.StaffFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by Andrew on 17.04.2017.
 */
@WebServlet(urlPatterns = { "/scheduleClear" })
public class ScheduleClearServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Long staffID = HttpServletUtil.getLongParam(request, "staffID", 0L);
            String sDate = request.getParameter("eventDate");
            String sBegin = sDate + " " + request.getParameter("eventBegin");
            String sEnd = sDate + " " + request.getParameter("eventEnd");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
            LocalDateTime beginDate = LocalDateTime.parse(sBegin, formatter);
            LocalDateTime endDate = LocalDateTime.parse(sEnd, formatter);
            ScheduleBuild scheduleBuild = new ScheduleBuild(staffID, 0l, beginDate, endDate, 0, null);
            Connection conn = SessionFactory.getStoredConnection(request);
            ScheduleFactory.clear(conn, scheduleBuild);
            response.sendRedirect(request.getContextPath() + "/scheduleAdmin?staffID=" + staffID);
        } catch (Exception e) {
            errorString = e.getMessage();
        }
        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long staffID = 0L;
        try {
            HttpSession session = request.getSession(true);
            staffID = HttpServletUtil.getLongParam(request, "staffID", 0L);
            if (staffID == 0L)
                throw new Exception("Undefined value for staffID");
            Connection conn = SessionFactory.getStoredConnection(request);
            Staff staff = StaffFactory.select(conn, staffID);
            if (staff == null)
                throw new Exception("Requested staff role isn't exists");
            request.setAttribute("staff", staff);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDateTime today = SessionFactory.getSessionEventBegin(session);
            if (today == null)
                today = LocalDateTime.now().withSecond(0).withNano(0);
            request.setAttribute("eventDate", today.format(dateFormatter));
            request.setAttribute("eventBegin", today.format(timeFormatter));

            today = SessionFactory.getSessionEventEnd(session);
            if (today == null)
                today = LocalDateTime.now().withSecond(0).withNano(0).plus(8, ChronoUnit.HOURS);
            request.setAttribute("eventEnd", today.format(timeFormatter));

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/schedule/scheduleClearView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }
}
