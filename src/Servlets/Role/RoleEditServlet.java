package Servlets.Role;

import Beans.Job;
import Beans.Role;
import Factories.HumanFactory;
import Factories.JobFactory;
import Factories.RoleFactory;
import Factories.SessionFactory;
import Servlets.Common.HttpServletUtil;
import Types.JobScope;
import Types.RoleCategory;
import Types.RoleScope;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 07.05.2017.
 */
@WebServlet(urlPatterns = { "/roleEdit" })
public class RoleEditServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long humanID = 0L;
        try {
            humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);

            if (humanID < 1L)
                throw new Exception("Undefined value for human ID");
            Role role = new Role(id, RoleScope.valueOf(request.getParameter("scope")), RoleCategory.valueOf(request.getParameter("category")), humanID, "Y".equals(request.getParameter("enabled")), request.getParameter("note"), null);
            role.validate();

            if (role.getId() < 1)
                RoleFactory.insert(conn, role);
            else
                RoleFactory.update(conn, role);
            String[] jobChecks = request.getParameterValues("jobChecks");
            if (jobChecks != null){
                //prepare ArrayList<>
                List<JobScope> jobSelection = new ArrayList<>();
                for (String jobItem: jobChecks)
                    jobSelection.add(JobScope.valueOf(jobItem));
                //query current jobs
                List<Job> jobs = JobFactory.query(conn, role.getId());
                //append new
                for (JobScope jobItem: jobSelection) {
                    if (jobs.stream().filter(value->value.getScope() == jobItem).count() == 0L)
                        JobFactory.insert(conn, new Job(0L, role.getId(), jobItem, null));
                }
                //delete if is not checked
                for (Job jobItem: jobs) {
                    if (!jobSelection.contains(jobItem.getScope()))
                        JobFactory.delete(conn, jobItem.getId());
                }
            }
            response.sendRedirect(request.getContextPath() + "/roleEdit?id=" + role.getId() + "&humanID=" + humanID);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null) {
            if (humanID > 0L)
                HttpServletUtil.showError(request, response, errorString, "/humanEdit?id=" + humanID);
            else
                HttpServletUtil.showError(request, response, errorString, "/humanList");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Long id = 0L;
        Long humanID = 0L;
        try {
            id = HttpServletUtil.getLongParam(request, "id", 0L);
            humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            Role role = RoleFactory.select(conn, id);
            if (role != null)
                humanID = role.getHumanID();
            else
                role = new Role(0L, RoleScope.Unknown, RoleCategory.People, humanID, true, null, null);

            List<Job> j = JobFactory.query(conn, role.getId());
            //j.stream().filter(value->value.getScope() == JobScope.Dentist).findFirst().get()

            request.setAttribute("role", role);
            request.setAttribute("roleScope", RoleScope.values());
            request.setAttribute("roleCategory", RoleCategory.values());
            request.setAttribute("human", HumanFactory.select(conn, humanID));
            request.setAttribute("jobScope", JobScope.values());
            request.setAttribute("jobs", JobFactory.query(conn, role.getId()));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/role/roleEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/humanEdit?id=" + humanID);
        }
    }
}
