package Servlets.Job;

import Factories.JobFactory;
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
 * Created by andre on 07.05.2017.
 */
@WebServlet(urlPatterns = {"/jobDelete"})
public class JobDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Long jobID = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            JobFactory.delete(conn, jobID);
        }catch (Exception e){
            HttpServletUtil.setResponseException(response, e);
        }
    }
}
