package Servlets.Schedule;

import Factories.SessionFactory;
import Factories.ScheduleFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by Andrew on 14.04.2017.
 */
@WebServlet(urlPatterns = "/scheduleDelete")
public class ScheduleDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Long scheduleID = HttpServletUtil.getLongParam(request, "scheduleID", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            ScheduleFactory.delete(conn, scheduleID);
        }catch (Exception e){
            HttpServletUtil.setResponseException(response, e);
        }
    }
}
