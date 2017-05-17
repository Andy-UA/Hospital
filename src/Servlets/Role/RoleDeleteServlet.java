package Servlets.Role;

import Factories.RecordFactory;
import Factories.RoleFactory;
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
@WebServlet(urlPatterns = {"/roleDelete"})
public class RoleDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Long roleID = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            RoleFactory.delete(conn, roleID);
        }catch (Exception e){
            HttpServletUtil.setResponseException(response, e);
        }
    }
}
