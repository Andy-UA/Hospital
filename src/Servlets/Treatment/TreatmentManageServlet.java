package Servlets.Treatment;

import Beans.*;
import Types.AppointmentType;
import Types.ImageScope;
import Types.TreatmentStatus;
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
 * Created by andre on 24.04.2017.
 */
@WebServlet(urlPatterns = "/treatmentManage")
public class TreatmentManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try{
            HttpSession session = request.getSession(true);
            Connection conn = SessionFactory.getStoredConnection(request);

            Long treatmentID = HttpServletUtil.getLongParam(request, "treatmentID", 0L);
            Long detailID = HttpServletUtil.getLongParam(request, "detailID", 0L);
            Double amount = HttpServletUtil.getDoubleParam(request, "amount", 0.0);
            LocalDateTime eventDate = HttpServletUtil.getDateTimeParam(request, "eventDate", LocalDateTime.now().withSecond(0).withNano(0), "uuuu-MM-dd HH:mm");
            String note = request.getParameter("note");
            TreatmentStatus status = TreatmentStatus.Unknown;
            String statusString = request.getParameter("status");
            try {
                status = (statusString == null || statusString.isEmpty()) ? TreatmentStatus.Open : TreatmentStatus.valueOf(statusString);
            } catch (Exception x) {
                status = TreatmentStatus.Open;
            }

            if (detailID < 1L)
                throw new Exception("Undefined value detailID");

            Treatment treatment = TreatmentFactory.select(conn, treatmentID);
            if (treatment != null){
                treatment.setDetailID(detailID);
                treatment.setEventDate(eventDate);
                treatment.setAmount(amount);
                treatment.setNote(note);
                treatment.setStatus(status);
            }
            else {
                treatment = new Treatment(0L, detailID, eventDate, amount, status, note);
            }
            if (treatment.getId() > 0)
                TreatmentFactory.update(conn, treatment);
            else
                TreatmentFactory.insert(conn, treatment);
            response.sendRedirect(request.getContextPath() + "/recordDetailsManage?detailID=" + detailID);
        }catch (Exception e){
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try{
            HttpSession session = request.getSession(true);
            Connection conn = SessionFactory.getStoredConnection(request);

            Long treatmentID = HttpServletUtil.getLongParam(request, "treatmentID", 0L);
            Long detailID = HttpServletUtil.getLongParam(request, "detailID", 0L);
            Double amount = HttpServletUtil.getDoubleParam(request, "amount", 0.0);
            LocalDateTime eventDate = HttpServletUtil.getDateTimeParam(request, "eventDate", LocalDateTime.now().withSecond(0).withNano(0), "uuuu-MM-dd HH:mm");
            String note = request.getParameter("note");
            TreatmentStatus status = TreatmentStatus.Unknown;
            Appointment appointment = null;
            RecordDetail detail = null;
            Treatment treatment = TreatmentFactory.select(conn, treatmentID);
            if (treatment != null){
                detailID = treatment.getDetailID();
                note = treatment.getNote();
                status = treatment.getStatus();
                eventDate = treatment.getEventDate();
                amount = treatment.getAmount();
                detail = RecordDetailFactory.select(conn, treatment.getDetailID());
                if (detail != null) {
                    appointment = AppointmentFactory.select(conn, detail.getAppointmentID());
                }
            }
            else {
                String statusString = request.getParameter("status");
                try {
                    status = (statusString == null || statusString.isEmpty()) ? TreatmentStatus.Open : TreatmentStatus.valueOf(statusString);
                } catch (Exception x) {
                    status = TreatmentStatus.Open;
                }
            }
            if (detail == null && detailID > 0L)
                detail = RecordDetailFactory.select(conn, detailID);
            if (detail == null)
                throw new Exception("Record Detail #" + detailID + " is not exists!");
            RecordInfo record = RecordInfoFactory.select(conn, detail.getRecordID());
            if (record == null)
                throw new Exception("Patient record #" + detail.getRecordID() + " is not exists!");
            Schedule schedule = ScheduleFactory.select(conn, record.getScheduleID());
            if (schedule == null)
                throw new Exception("Schedule #" + record.getScheduleID() + " is not exists!");
            Patient patient = PatientFactory.select(conn, schedule.getPatientID());
            if (patient == null)
                throw new Exception("Patient #" + schedule.getPatientID() + " is not exists!");
            Staff staff = StaffFactory.select(conn, schedule.getStaffID());
            if (staff == null)
                throw new Exception("Doctor #" + schedule.getStaffID() + " is not exists!");
            Workplace workplace = WorkplaceFactory.select(conn, schedule.getWorkplaceID());
            if (workplace == null)
                throw new Exception("Workplace #" + schedule.getWorkplaceID() + " is not exists!");

            request.setAttribute("treatmentID", treatmentID);
            request.setAttribute("detailID", detailID);
            request.setAttribute("detail", detail);
            request.setAttribute("record", record);
            request.setAttribute("schedule", schedule);
            request.setAttribute("staff", staff);
            request.setAttribute("workplace", workplace);
            request.setAttribute("patient", patient);
            request.setAttribute("note", note);
            request.setAttribute("appointment", appointment);
            request.setAttribute("eventDate", eventDate);
            request.setAttribute("amount", amount);
            request.setAttribute("status", status);
            request.setAttribute("treatmentStatus", TreatmentStatus.values());
            request.setAttribute("appointmentsList", AppointmentFactory.query(conn, "%", true, AppointmentType.Unknown));
            request.setAttribute("images", ImageFactory.queryDescriptions(conn, patient.getHumanID(), ImageScope.Photo, null));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/treatment/treatmentManageView.jsp");
            dispatcher.forward(request, response);

        }catch (Exception e){
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }
}
