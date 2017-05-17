package Servlets.Staff;

import Beans.Account;
import Beans.Role;
import Beans.Staff;
import Factories.RoleFactory;
import Types.RoleCategory;
import Factories.SessionFactory;
import Factories.StaffFactory;
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
 * Created by Andrew on 15.04.2017.
 */
@WebServlet(urlPatterns = { "/staffList" })
public class StaffListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
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

            List<Staff> list = null;
            RoleCategory roleCategory;
            String x = request.getParameter("category");
            if (x != null && !x.isEmpty())
                roleCategory = RoleCategory.valueOf(x);
            else
                roleCategory = RoleCategory.Doctors;
            Connection conn = SessionFactory.getStoredConnection(request);
            list = StaffFactory.query(conn, request.getParameter("firstName"), request.getParameter("lastName"), x == null, roleCategory);
            // Store info in request attribute, before forward to views
            request.setAttribute("staffList", list);
            request.setAttribute("category", roleCategory);
            request.setAttribute("firstName", HttpServletUtil.isEmpty(request.getParameter("firstName")) ? "%" : request.getParameter("firstName"));
            request.setAttribute("lastName", HttpServletUtil.isEmpty(request.getParameter("lastName")) ? "%" : request.getParameter("lastName"));
            request.setAttribute("roleCategory", RoleCategory.values());
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/staff/staffListView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/home");
        }
    }
}
