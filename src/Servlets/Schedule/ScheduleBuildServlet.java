package Servlets.Schedule;

import Beans.ScheduleBuild;
import Beans.Staff;
import Beans.Workplace;
import Factories.SessionFactory;
import Factories.ScheduleFactory;
import Factories.StaffFactory;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;

/**
 * Created by Andrew on 17.04.2017.
 */
@WebServlet(urlPatterns = { "/scheduleBuild" })
public class ScheduleBuildServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long staffID = 0L;
        try {
            String sDate = request.getParameter("eventDate");
            String sBegin = sDate + " " + request.getParameter("eventBegin");
            String sEnd = sDate + " " + request.getParameter("eventEnd");
            LocalDateTime beginDate = HttpServletUtil.getDateTimeValue(sBegin, LocalDateTime.now(), "uuuu-MM-dd HH:mm").withSecond(0).withNano(0);
            LocalDateTime endDate = HttpServletUtil.getDateTimeValue(sEnd, LocalDateTime.now(), "uuuu-MM-dd HH:mm").withSecond(0).withNano(0);

            staffID = HttpServletUtil.getLongParam(request,"staffID", 0L);
            if (staffID == 0L)
                throw new Exception("Undefined value for staffID");
            ScheduleBuild scheduleBuild = new ScheduleBuild(staffID,
                    Long.parseLong(request.getParameter("workplaceID")),
                    beginDate,
                    endDate,
                    Integer.parseInt(request.getParameter("interval")),
                    request.getParameter("note"));
            Connection conn = SessionFactory.getStoredConnection(request);
            ScheduleFactory.build(conn, scheduleBuild);
            response.sendRedirect(request.getContextPath() + "/scheduleAdmin?staffID=" + staffID);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long staffID = 0L;
        try {
            staffID = HttpServletUtil.getLongParam(request, "staffID", 0L);
            if (staffID == 0L)
                throw new Exception("Undefined value for staffID");
            Connection conn = SessionFactory.getStoredConnection(request);
            Staff staff = StaffFactory.select(conn, staffID);
            if (staff == null)
                throw new Exception("Requested staff role isn't exists");
            request.setAttribute("staff", staff);
            List<Workplace> workplaceList = WorkplaceFactory.query(conn, "%", "%");
            request.setAttribute("workplaceList", workplaceList);
            if (workplaceList.size() > 0)
                request.setAttribute("workplaceID", workplaceList.get(0).getId());
            else
                request.setAttribute("workplaceID", "0");
            request.setAttribute("interval", "20");
            LocalDateTime today = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0).plus(1, ChronoUnit.HOURS);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            request.setAttribute("eventDate", today.format(dateFormatter));
            request.setAttribute("eventBegin", today.format(timeFormatter));
            today = today.plus(8, ChronoUnit.HOURS);
            request.setAttribute("eventEnd", today.format(timeFormatter));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/schedule/scheduleBuildView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = e.getMessage();
        }
        if (errorString != null) {
            request.setAttribute("errorString", HttpServletUtil.prepareErrorString(errorString));
            if (staffID > 0L)
                response.sendRedirect(request.getContextPath() + "/scheduleAdmin?staffID=" + staffID);
            else {
                HttpServletUtil.showError(request, response, errorString, "/staffList");
            }
        }
    }
}
