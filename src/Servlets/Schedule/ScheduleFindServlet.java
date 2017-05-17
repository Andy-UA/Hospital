package Servlets.Schedule;

import Beans.Patient;
import Beans.ScheduleRequest;
import Factories.PatientFactory;
import Factories.ScheduleInfoFactory;
import Factories.SessionFactory;
import Servlets.Common.HttpServletUtil;
import Types.JobScope;
import Types.RoleCategory;
import Types.ScheduleOccupiedState;
import Types.ScheduleStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;

/**
 * Created by andre on 07.05.2017.
 */
@WebServlet(urlPatterns = {"/scheduleFind"})
public class ScheduleFindServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long patientID = 0L;
        JobScope jobScope = JobScope.Therapist;
        RoleCategory roleCategory = RoleCategory.Doctors;
        try {
            patientID = HttpServletUtil.getLongParam(request, "patientID", 0L);
            jobScope = HttpServletUtil.getEnumParam(JobScope.class, request, "jobScope", JobScope.Unknown);
            roleCategory = HttpServletUtil.getEnumParam(RoleCategory.class, request, "roleCategory", RoleCategory.Unknown);

            Connection conn = SessionFactory.getStoredConnection(request);
            Patient patient = PatientFactory.select(conn, patientID);
            if (patient == null)
                throw new Exception("Requested patient role isn't exists");
            request.setAttribute("patient", patient);
            request.setAttribute("roleCategoryList", RoleCategory.values());
            request.setAttribute("jobScopeList", JobScope.values());

            request.setAttribute("roleCategory", roleCategory);
            request.setAttribute("jobScope", jobScope);

            HttpServletUtil.prepareEventPeriod(request, response);
            LocalDateTime eventBegin = (LocalDateTime) request.getAttribute("eventBegin");
            LocalDateTime eventEnd = (LocalDateTime) request.getAttribute("eventEnd");
            ScheduleRequest scheduleRequest = new ScheduleRequest();
            scheduleRequest.setEventBegin(eventBegin);
            scheduleRequest.setEventEnd(eventEnd);
            scheduleRequest.setJobScope(jobScope);
            scheduleRequest.setRoleCategory(roleCategory);
            scheduleRequest.setOccupiedState(ScheduleOccupiedState.Free);
            scheduleRequest.setScheduleStatus(ScheduleStatus.Open);
            request.setAttribute("scheduleInfos", ScheduleInfoFactory.query(conn, scheduleRequest));

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/schedule/scheduleFindView.jsp");
            dispatcher.forward(request, response);

        }catch (Exception e){
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        HttpServletUtil.showError(request, response, errorString, "/records");
    }
}
