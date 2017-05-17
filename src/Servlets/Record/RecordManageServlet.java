package Servlets.Record;

import Beans.*;
import Types.ImageScope;
import Factories.*;
import Factories.SessionFactory;
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

/**
 * Created by andre on 20.04.2017.
 */
@WebServlet(urlPatterns = { "/recordManage" })
public class RecordManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Long scheduleID = HttpServletUtil.getLongParam(request, "scheduleID", 0L);
            Long diagnosisID = HttpServletUtil.getLongParam(request, "diagnosisID", 0L);
            Long recordID = HttpServletUtil.getLongParam(request, "recordID", 0L);
            LocalDateTime eventDate =  HttpServletUtil.getDateTimeParam(request, "eventDate", LocalDateTime.now().withSecond(0).withNano(0), "uuuu-MM-dd HH:mm");
            String note = request.getParameter("note");

            if (diagnosisID == 0L)
                throw new Exception("Undefined value for diagnosisID");
            if (scheduleID == 0L)
                throw new Exception("Undefined value for scheduleID");
            Connection conn = SessionFactory.getStoredConnection(request);

            //find and get patient record (if != 0 and exists)
            Record record = null;
            if (recordID > 0) {
                record = RecordFactory.select(conn, recordID);
                if (record != null) {
                    scheduleID = record.getScheduleID();
                }
            }
            //insert or update patient record
            if (record == null){
                record = new Record(0L, eventDate, diagnosisID, scheduleID, note);
                RecordFactory.insert (conn, record);
                recordID = record.getId();
            }
            else{
                record.setEventDate(eventDate);
                record.setDiagnosisID(diagnosisID);
                record.setNote(note);
                RecordFactory.update (conn, record);
            }
            response.sendRedirect(request.getContextPath() + "/recordManage?recordID=" + recordID);
        } catch (Exception e) {
            errorString = e.getMessage() != null ? e.getMessage() : e.toString();
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/patientList");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            HttpSession session = request.getSession(true);
            Long recordID = HttpServletUtil.getLongParam(request, "recordID", 0L);
            Long patientID = HttpServletUtil.getLongParam(request, "patientID", 0L);
            Long scheduleID = HttpServletUtil.getLongParam(request, "scheduleID", 0L);
            Long diagnosisID = HttpServletUtil.getLongParam(request, "diagnosisID", 0L);
            String note =  request.getParameter("note");
            LocalDateTime eventDate = HttpServletUtil.getDateTimeParam(request, "eventDate", LocalDateTime.now().withSecond(0).withNano(0), "uuuu-MM-dd HH:mm");

            Patient patient = null;
            Staff staff = null;
            Schedule schedule = null;
            Workplace workplace = null;
            Record record =  null;

            //find and get patient record (if != 0 and exists)
            if (recordID > 0){
                record = RecordFactory.select(conn, recordID);
                if (record != null){
                    scheduleID = record.getScheduleID();
                    diagnosisID = record.getDiagnosisID();
                    eventDate =  record.getEventDate();
                    note = record.getNote();
                }
            }
            //get schedule instance
            schedule = ScheduleFactory.select(conn, scheduleID);
            if (schedule != null){
                patientID = schedule.getPatientID();
                staff = StaffFactory.select(conn, schedule.getStaffID());
                workplace = WorkplaceFactory.select(conn, schedule.getWorkplaceID());
                if (record == null)
                    eventDate = HttpServletUtil.getDateTimeParam(request, "eventDate", LocalDateTime.now().withSecond(0).withNano(0), "uuuu-MM-dd HH:mm");
            }
            else
                throw new Exception("Requested schedule isn't exists");

            //get patient instance
            if (patientID == 0L)
                throw new Exception("Undefined value for patientID");
            patient = PatientFactory.select(conn, patientID);
            if(patient == null)
                throw new Exception("Requested patient role isn't exists");

            request.setAttribute("schedule", schedule);
            request.setAttribute("workplace", workplace);
            request.setAttribute("patient", patient);
            request.setAttribute("staff", staff);

            request.setAttribute("recordID", recordID);
            request.setAttribute("eventDate", eventDate);
            request.setAttribute("note", note);
            request.setAttribute("diagnosisID", diagnosisID);
            request.setAttribute("diagnosisList", DiagnosisFactory.query(conn, "%", "%"));
            request.setAttribute("recordDetails", RecordDetailInfoFactory.query(conn, recordID));
            request.setAttribute("images", ImageFactory.queryDescriptions(conn, patient.getHumanID(), ImageScope.Photo, null));

            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/record/recordManageView.jsp");
            dispatcher.forward(request, response);
        }catch (Exception e){
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }
}
