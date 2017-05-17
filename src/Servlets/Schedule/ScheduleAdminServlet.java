package Servlets.Schedule;

import Beans.ScheduleInfo;
import Beans.ScheduleRequest;
import Beans.Staff;
import Types.*;
import Factories.SessionFactory;
import Factories.ImageFactory;
import Factories.ScheduleInfoFactory;
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
import java.util.List;

/**
 * Created by Andrew on 14.04.2017.
 */
@WebServlet(urlPatterns = { "/scheduleAdmin" })
public class ScheduleAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long staffID = null;
        try {
            staffID = HttpServletUtil.getLongParam(request, "staffID", 0L);
            if (staffID == 0L)
                throw new Exception("Undefined value for staffID");
            HttpServletUtil.prepareEventPeriod(request, response);
            LocalDateTime eventBegin = (LocalDateTime) request.getAttribute("eventBegin");
            LocalDateTime eventEnd = (LocalDateTime) request.getAttribute("eventEnd");

            Connection conn = SessionFactory.getStoredConnection(request);
            Staff staff = StaffFactory.select(conn, staffID);
            if (staff == null)
                throw new Exception("Requested staff role isn't exists");
            ScheduleRequest scheduleRequest = new ScheduleRequest();
            scheduleRequest.setStaffID(staff.getRoleID());
            scheduleRequest.setEventBegin(eventBegin);
            scheduleRequest.setEventEnd(eventEnd);
            scheduleRequest.setJobScope(JobScope.Unknown);
            scheduleRequest.setRoleCategory(RoleCategory.Unknown);
            scheduleRequest.setOccupiedState(ScheduleOccupiedState.All);
            scheduleRequest.setScheduleStatus(ScheduleStatus.Unknown);
            request.setAttribute("scheduleInfos", ScheduleInfoFactory.query(conn, scheduleRequest));
            request.setAttribute("staff", staff);
            request.setAttribute("images", ImageFactory.queryDescriptions(conn, staff.getHumanID(), ImageScope.Photo, null));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/schedule/scheduleAdminView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }
}
