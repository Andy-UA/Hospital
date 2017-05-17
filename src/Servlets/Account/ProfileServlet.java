package Servlets.Account;

import Beans.Account;
import Beans.Role;
import Factories.AccountFactory;
import Factories.ProfileFactory;
import Factories.RoleFactory;
import Factories.SessionFactory;
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
 * Created by Andrew on 10.04.2017.
 */
@WebServlet(urlPatterns = { "/profile" })
public class ProfileServlet extends HttpServlet {
    public ProfileServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String errorString = null;
        try {
            Account account = SessionFactory.getLoginedAccount(session);
            if (account == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            } else if (AccountFactory.select(SessionFactory.getStoredConnection(request), account.getLogin(), account.getPassword()) == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            RoleScope role = SessionFactory.getSessionRole(session);
            if (role == RoleScope.Unknown){
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            // Store info in request attribute
            Connection conn = SessionFactory.getStoredConnection(request);
            request.setAttribute("profile", ProfileFactory.select(conn, account, role));
            // Logined, forward to /WEB-INF/views/profileInfoView.jsp
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/account/profileInfoView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        HttpServletUtil.showError(request, response, errorString, "/home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        HttpSession session = request.getSession();
        try {
            Account account = SessionFactory.getLoginedAccount(session);
            if (account == null) {
                // Redirect to login page.
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            } else if (AccountFactory.select(SessionFactory.getStoredConnection(request), account.getLogin(), account.getPassword()) == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            RoleScope role = SessionFactory.getSessionRole(session);
            if (role == RoleScope.Unknown){
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            List<Role> humanRoles = RoleFactory.query(SessionFactory.getStoredConnection(request), account.getHumanID(), role, true);
            // Redirect to role page.
            if (humanRoles.size() == 0)
                throw new Exception("You have not assigned any roles.");
            switch (role) {
                case Patient:
                    response.sendRedirect(request.getContextPath() + "/records?patientID=" + humanRoles.get(0).getId());
                    break;
                case Staff:
                    response.sendRedirect(request.getContextPath() + "/scheduleAdmin?staffID=" + humanRoles.get(0).getId());
                    break;
                case Admin:
                    response.sendRedirect(request.getContextPath() + "/humanList");
                    break;
            }
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        HttpServletUtil.showError(request, response, errorString, "/login");
    }
}
