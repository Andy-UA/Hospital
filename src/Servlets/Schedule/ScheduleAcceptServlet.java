package Servlets.Schedule;

import Beans.Patient;
import Beans.Schedule;
import Factories.PatientFactory;
import Factories.ScheduleFactory;
import Factories.SessionFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by andre on 08.05.2017.
 */
@WebServlet(urlPatterns = {"/scheduleAccept"})
public class ScheduleAcceptServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = SessionFactory.getStoredConnection(request);
        try{
            Long scheduleID = HttpServletUtil.getLongParam(request, "scheduleID", 0L);
            Long patientID = HttpServletUtil.getLongParam(request, "patientID", 0L);
            Schedule schedule = ScheduleFactory.select(conn, scheduleID);
            Patient patient = PatientFactory.select(conn, patientID);
            if (schedule == null)
                throw new Exception("Schedule " + scheduleID + " - not found");
            if (patient != null)
                schedule.setPatientID(patientID);
            else
                schedule.setPatientID(null);
            ScheduleFactory.update(conn, schedule);
        }catch (Exception e){
            HttpServletUtil.setResponseException(response, e);
        }
    }
}
