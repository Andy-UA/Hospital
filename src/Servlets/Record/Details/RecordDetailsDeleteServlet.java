package Servlets.Record.Details;

import Factories.SessionFactory;
import Factories.RecordDetailFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by andre on 22.04.2017.
 */
@WebServlet(urlPatterns = { "/recordDetailsDelete" })
public class RecordDetailsDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Long detailID = HttpServletUtil.getLongParam(request, "detailID", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            RecordDetailFactory.delete(conn, detailID);
        }catch (Exception e){
            HttpServletUtil.setResponseException(response, e);
        }
    }
}
