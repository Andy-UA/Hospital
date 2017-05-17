package Servlets.Record;

import Factories.SessionFactory;
import Factories.RecordFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by andre on 20.04.2017.
 */
@WebServlet(urlPatterns = { "/recordDelete" })
public class RecordDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Long recordID = HttpServletUtil.getLongParam(request, "recordID", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            RecordFactory.delete(conn, recordID);
        }catch (Exception e){
            HttpServletUtil.setResponseException(response, e);
        }
    }
}
