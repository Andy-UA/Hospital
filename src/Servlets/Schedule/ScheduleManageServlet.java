package Servlets.Schedule;

import Beans.*;
import Types.RoleCategory;
import Factories.SessionFactory;
import Factories.PatientFactory;
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
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Andrew on 14.04.2017.
 */
@WebServlet(urlPatterns = { "/scheduleManage" })
public class ScheduleManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long scheduleID = Long.parseLong(request.getParameter("scheduleID"));
        Long patientID = Long.parseLong(request.getParameter("patientID"));
        Schedule schedule = null;
        String note = request.getParameter("note");
        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            schedule = ScheduleFactory.select(conn, scheduleID);
            if (patientID > 0)
                schedule.setPatientID(patientID);
            else
            if (patientID == -1)
                schedule.setPatientID(null);
            else
                throw new Exception("Please, select patient for assigning to current schedule!");
            schedule.setNote(note);
            ScheduleFactory.update(conn, schedule);
        } catch (Exception e) {
            errorString = e.getMessage();
        }
        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
        else
            response.sendRedirect(request.getContextPath() + "/scheduleAdmin?staffID=" + schedule.getStaffID());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long scheduleID = Long.parseLong(request.getParameter("scheduleID"));
        Long staffID = 0l;
        if(scheduleID == null)
            errorString = "Undefined value for scheduleID";
        else {
            try {
                Connection conn = SessionFactory.getStoredConnection(request);
                Schedule schedule = ScheduleFactory.select(conn, scheduleID);
                if(schedule == null )
                    throw new Exception("Requested schedule record isn't exists");
                staffID = schedule.getStaffID();

                Staff staff = StaffFactory.select(conn, schedule.getStaffID());
                if(staff == null)
                    throw new Exception("Requested staff role isn't exists");

                Workplace workplace = WorkplaceFactory.select(conn, schedule.getWorkplaceID());
                Patient patient = PatientFactory.select(conn, schedule.getPatientID());
                List<Patient> patientList = PatientFactory.query(conn, "%", "%", false, RoleCategory.People);
                Patient removePatient = new Patient(-1L, RoleCategory.People, true, "", "Clear reference into patient ...", "", LocalDate.now(), true, "M", -1L, null);
                patientList.add(0, removePatient);

                request.setAttribute("workplace", workplace);
                request.setAttribute("schedule", schedule);
                request.setAttribute("staff", staff);
                request.setAttribute("note", schedule.getNote());
                request.setAttribute("patient", patient);
                request.setAttribute("patientList", patientList);

                if(patient != null)
                    request.setAttribute("patientID", patient.getRoleID());
                else
                if(patientList.size() > 0)
                    request.setAttribute("patientID", patientList.get(0).getRoleID());
                else
                    request.setAttribute("patientID", -1L);

            }catch (Exception e) {
                errorString = HttpServletUtil.getExeptionMessage(e);
            }
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/schedule/scheduleManageView.jsp");
            dispatcher.forward(request, response);
        }
        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/staffList");
        }
    }
}
