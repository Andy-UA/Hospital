package Servlets.Record.Details;

import Beans.*;
import Types.AppointmentType;
import Types.ImageScope;
import Types.RecordDetailStatus;
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

/**
 * Created by andre on 22.04.2017.
 */
@WebServlet(urlPatterns = { "/recordDetailsManage" })
public class RecordDetailsManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try{
            HttpSession session = request.getSession(true);
            Long detailID = HttpServletUtil.getLongParam(request, "detailID", 0L);
            Long recordID = HttpServletUtil.getLongParam(request, "recordID", 0L);
            Long appointmentID = HttpServletUtil.getLongParam(request, "appointmentID", 0L);
            Double amount = HttpServletUtil.getDoubleParam(request, "amount", 0.0);
            RecordDetailStatus status;
            String statusString = request.getParameter("status");
            try {
                status = statusString == null || statusString.isEmpty() ? RecordDetailStatus.Unknown : RecordDetailStatus.valueOf(statusString);
            }catch (Exception x){
                status = RecordDetailStatus.Unknown;
            }
            String note = request.getParameter("note");
            if (recordID == 0L)
                throw new Exception("Undefined value for recordID");
            if (appointmentID == 0L)
                throw new Exception("Undefined value for appointmentID");

            Connection conn = SessionFactory.getStoredConnection(request);
            Record record = RecordFactory.select(conn, recordID);
            if (record == null)
                throw new Exception("Patient record #" + recordID + " is not exists!");
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
            RecordDetail detail = new RecordDetail(detailID, recordID, appointmentID, status, amount, note);
            if (detailID > 0)
                RecordDetailFactory.update(conn, detail);
            else{
                RecordDetailFactory.insert(conn, detail);
                detailID = detail.getId();
            }
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
            Connection conn = SessionFactory.getStoredConnection(request);

            Long detailID = HttpServletUtil.getLongParam(request, "detailID", 0L);
            Long recordID = HttpServletUtil.getLongParam(request, "recordID", 0L);
            Long appointmentID = HttpServletUtil.getLongParam(request, "appointmentID", 0L);
            Double amount = HttpServletUtil.getDoubleParam(request, "amount", 0.0);
            String note = request.getParameter("note");
            RecordDetailStatus status;
            RecordDetail detail = RecordDetailFactory.select(conn, detailID);
            if (detail != null){
                recordID = detail.getRecordID();
                status = detail.getStatus();
                appointmentID = detail.getAppointmentID();
                amount = detail.getAmount();
                note = detail.getNote();
            }
            else {
                String statusString = request.getParameter("status");
                try {
                    status = statusString == null || statusString.isEmpty() ? RecordDetailStatus.Open : RecordDetailStatus.valueOf(statusString);
                } catch (Exception x) {
                    status = RecordDetailStatus.Open;
                }
            }
            RecordInfo record = RecordInfoFactory.select(conn, recordID);
            if (record == null)
                throw new Exception("Patient record #" + recordID + " is not exists!");
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

            request.setAttribute("detailID", detailID);
            request.setAttribute("recordID", recordID);
            request.setAttribute("record", record);
            request.setAttribute("schedule", schedule);
            request.setAttribute("staff", staff);
            request.setAttribute("workplace", workplace);
            request.setAttribute("patient", patient);
            request.setAttribute("note", note);
            request.setAttribute("appointmentID", appointmentID);
            request.setAttribute("status", status);
            request.setAttribute("recordDetailStatus", RecordDetailStatus.values());
            request.setAttribute("amount", amount);
            request.setAttribute("appointmentsList", AppointmentFactory.query(conn, "%", true, AppointmentType.Unknown));
            request.setAttribute("images", ImageFactory.queryDescriptions(conn, patient.getHumanID(), ImageScope.Photo, null));
            request.setAttribute("treatmentList", TreatmentFactory.query(conn, detailID));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/recordDetails/recordDetailsManageView.jsp");
            dispatcher.forward(request, response);

        }catch (Exception e){
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }
}
