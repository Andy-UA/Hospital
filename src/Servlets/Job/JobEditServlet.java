package Servlets.Job;

import Beans.Job;
import Beans.Role;
import Factories.HumanFactory;
import Factories.JobFactory;
import Factories.RoleFactory;
import Factories.SessionFactory;
import Servlets.Common.HttpServletUtil;
import Types.JobScope;

import javax.servlet.RequestDispatcher;
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
@WebServlet(urlPatterns = {"/jobEdit"})
public class JobEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long roleID = 0L;
        try {
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            roleID = HttpServletUtil.getLongParam(request, "roleID", 0L);
            if (roleID < 1L)
                throw new Exception("Undefined Role ID");

            Connection conn = SessionFactory.getStoredConnection(request);

            Job job = new Job(id, roleID, JobScope.valueOf(request.getParameter("scope")), request.getParameter("note"));

            if (job.getId() < 1)
                JobFactory.insert(conn, job);
            else
                JobFactory.update(conn, job);
            response.sendRedirect(request.getContextPath() + "/roleEdit?id=" + roleID);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null) {
            if (roleID > 0L)
                HttpServletUtil.showError(request, response, errorString, "/roleEdit?id=" + roleID);
            else
                HttpServletUtil.showError(request, response, errorString, "/humanList");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long id = 0L;
        Long roleID = 0L;
        try {
            id = HttpServletUtil.getLongParam(request, "id", 0L);
            roleID = HttpServletUtil.getLongParam(request, "roleID", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            Job job = JobFactory.select(conn, id);
            if (job != null)
                roleID = job.getRoleID();
            else
                job = new Job(0L, roleID, JobScope.Therapist, null);
            if (roleID < 1L)
                throw new Exception("Undefined Role ID");
            Role role = RoleFactory.select(conn, roleID);
            request.setAttribute("jobScope", JobScope.values());
            request.setAttribute("job", job);
            request.setAttribute("role", role);
            request.setAttribute("human", HumanFactory.select(conn, role.getHumanID()));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/job/jobEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/roleEdit?id=" + roleID);
        }
    }
}
