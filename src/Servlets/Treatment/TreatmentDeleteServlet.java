package Servlets.Treatment;

import Factories.SessionFactory;
import Factories.TreatmentFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by andre on 24.04.2017.
 */
@WebServlet(urlPatterns = "/treatmentDelete")
public class TreatmentDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Connection conn = SessionFactory.getStoredConnection(request);
            Long treatmentID = HttpServletUtil.getLongParam(request, "treatmentID", 0L);
            TreatmentFactory.delete(conn, treatmentID);
        }catch (Exception e){
            HttpServletUtil.setResponseException(response, e);
        }
    }
}
