package Servlets.Patient;

import Beans.Account;
import Beans.Patient;
import Beans.Profile;
import Beans.Role;
import Factories.RoleFactory;
import Types.RoleCategory;
import Factories.SessionFactory;
import Factories.PatientFactory;
import Servlets.Common.HttpServletUtil;
import Types.RoleScope;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by Andrew on 17.04.2017.
 */
@WebServlet(urlPatterns = { "/patientList" })
public class PatientListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected Boolean isEmpty(String v) {
        return v == null || v.isEmpty();
   }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            HttpSession session = request.getSession(true);
            Account account = SessionFactory.getLoginedAccount(session);
            if (account != null) {
                List<Role> roles = RoleFactory.query(SessionFactory.getStoredConnection(request), account.getHumanID(), RoleScope.Admin, true);
                if (roles.size() == 0) {
                    response.sendRedirect(request.getContextPath() + "/home");
                    return;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            List<Patient> list = null;
            RoleCategory roleCategory = RoleCategory.People;
            String x = request.getParameter("category");
            if (x != null)
                roleCategory = RoleCategory.valueOf(x);
            Connection conn = SessionFactory.getStoredConnection(request);
            list = PatientFactory.query(conn, request.getParameter("firstName"), request.getParameter("lastName"), x == null, roleCategory);

            // Store info in request attribute, before forward to views
            request.setAttribute("patientList", list);
            request.setAttribute("category", roleCategory);
            request.setAttribute("firstName", isEmpty(request.getParameter("firstName")) ? "%" : request.getParameter("firstName"));
            request.setAttribute("lastName", isEmpty(request.getParameter("lastName")) ? "%" : request.getParameter("lastName"));

            // Forward to /WEB-INF/views/diagnosisListView.jsp
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/patient/patientListView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/home");
        }
    }
}
