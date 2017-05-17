package Servlets.Record;

import Beans.*;
import Factories.*;
import Types.ImageScope;
import Servlets.Common.HttpServletUtil;
import Types.JobScope;
import Types.RoleCategory;
import Types.ScheduleStatus;

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
import java.util.List;

/**
 * Created by andre on 19.04.2017.
 */
@WebServlet(urlPatterns = { "/records" })
public class RecordsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            HttpSession session = request.getSession(true);
            Long section = HttpServletUtil.getLongParam(request, "section", 0L);
            Long patientID = HttpServletUtil.getLongParam(request, "patientID", 0L);
            Long scheduleID = HttpServletUtil.getLongParam(request, "scheduleID", 0L);
            ScheduleStatus scheduleStatus = HttpServletUtil.getEnumParam(ScheduleStatus.class, request, "scheduleStatus", ScheduleStatus.Open);
            if (patientID == 0L)
                throw new Exception("Undefined value for patientID");

            HttpServletUtil.prepareEventPeriod(request, response);
            LocalDateTime eventBegin = (LocalDateTime) request.getAttribute("eventBegin");
            LocalDateTime eventEnd = (LocalDateTime) request.getAttribute("eventEnd");

            Connection conn = SessionFactory.getStoredConnection(request);
            Patient patient = PatientFactory.select(conn, patientID);
            if (patient == null)
                throw new Exception("Requested patient role isn't exists");
            Schedule schedule = ScheduleFactory.select(conn, scheduleID);
            RecordsRequest recordsRequest = new RecordsRequest();
            recordsRequest.setPatientID(patient.getRoleID());
            recordsRequest.setEventBegin(eventBegin);
            recordsRequest.setEventEnd(eventEnd);
            recordsRequest.setStaffID(schedule != null ? schedule.getStaffID() : 0L);

            ScheduleRequest scheduleRequest = new ScheduleRequest();
            scheduleRequest.setPatientID(patient.getRoleID());
            scheduleRequest.setEventBegin(eventBegin);
            scheduleRequest.setEventEnd(eventEnd);
            scheduleRequest.setJobScope(JobScope.Unknown);
            scheduleRequest.setRoleCategory(RoleCategory.Unknown);
            scheduleRequest.setScheduleStatus(scheduleStatus);

            request.setAttribute("section", section);
            request.setAttribute("patient", patient);
            request.setAttribute("schedule", schedule);
            request.setAttribute("scheduleStatus", scheduleStatus);
            request.setAttribute("scheduleStatusList", ScheduleStatus.values());
            request.setAttribute("scheduleRecords", ScheduleInfoFactory.query(conn, scheduleRequest));
            request.setAttribute("patientRecords", RecordInfoFactory.query(conn, recordsRequest));
            request.setAttribute("images", ImageFactory.queryDescriptions(conn, patient.getHumanID(), ImageScope.Photo, null));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/record/recordsView.jsp");
            dispatcher.forward(request, response);
        }catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }
}
