package Servlets.Account;

import Beans.Account;
import Beans.Role;
import Factories.ProfileFactory;
import Types.RoleScope;
import Factories.AccountFactory;
import Factories.SessionFactory;
import Factories.RoleFactory;
import Servlets.Common.HttpServletUtil;

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
@WebServlet(urlPatterns = { "/login" })
public class AccountLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AccountLoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            HttpSession session = request.getSession();
            Account account = SessionFactory.getLoginedAccount(session);
            if (account == null)
                account = new Account(0L, 0L, SessionFactory.getCookieValue(request, SessionFactory.LAST_LOGIN), null, true, null);
            RoleScope role = SessionFactory.getSessionRole(session);
            if (role == RoleScope.Unknown)
                role = RoleScope.Patient;
            request.setAttribute("role", role);
            request.setAttribute("roleScope", RoleScope.values());
            request.setAttribute("account", account);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/account/accountLogin.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        HttpServletUtil.showError(request, response, errorString, "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Account account = null;
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean remember = "Y".equals(request.getParameter("rememberMe"));
        RoleScope role = RoleScope.Unknown;
        try{
            String roleString = request.getParameter("role");
            if (roleString != null && !roleString.isEmpty())
                role = RoleScope.valueOf(roleString);
        }catch (Exception e){
            role = RoleScope.Patient;
        }
        try {
            HttpSession session = request.getSession();
            SessionFactory.setSessionRole(session, role);
            Connection conn = SessionFactory.getStoredConnection(request);
            if (login == null || login.isEmpty() || password == null || password.isEmpty())
                request.setAttribute("errorString", HttpServletUtil.prepareErrorString("Required username and password!"));
            else
                account = AccountFactory.select(conn, login, AccountFactory.hashValue(password));
            if (account == null) {
                account = new Account(0L, 0L, login, password, true, null);
            } else {
                //Check: is member of role ...
                List<Role> humanRoles = RoleFactory.query(conn, account.getHumanID(), role, true);
                if (humanRoles.size() == 0)
                    throw new Exception("You are not a member of role " + role.toString().toUpperCase());
                // If no error Store account information in Session And redirect to userInfo page.
                SessionFactory.setCookieValue(response, SessionFactory.LAST_LOGIN, account.getLogin());
                SessionFactory.setLoginedAccount(request.getSession(), account);
                SessionFactory.setLoginedProfile(request.getSession(), ProfileFactory.select(conn, account, SessionFactory.getSessionRole(session)));
                // If account checked "Remember me".
                if (remember) {
                    SessionFactory.setCookieLogin(response, account);
                }
                else {
                    SessionFactory.clearCookieLogin(response);
                }
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
                return;
            }
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (account != null){
            // Store information in request attribute, before forward.
            request.setAttribute("role", role);
            request.setAttribute("roleScope", RoleScope.values());
            request.setAttribute("errorString", HttpServletUtil.prepareErrorString(errorString));
            request.setAttribute("account", account);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/account/accountLogin.jsp");
            dispatcher.forward(request, response);
        }
        else if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/login");
        }
    }
}
