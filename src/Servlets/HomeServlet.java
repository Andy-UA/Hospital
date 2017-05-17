package Servlets;

import Beans.Account;
import Beans.Role;
import Factories.AccountFactory;
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
@WebServlet(urlPatterns = { "/home"})
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Account account = null;
        try {
            HttpSession session = request.getSession();
            Connection conn = SessionFactory.getStoredConnection(request);

            account = SessionFactory.getLoginedAccount(session);
            if (account != null){
                //Check: is member of role ...
                RoleScope role = SessionFactory.getSessionRole(session);
                if (role != RoleScope.Unknown) {
                    List<Role> humanRoles = RoleFactory.query(conn, account.getHumanID(), role, true);
                    if (humanRoles.size() > 0) {
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
                }
            }
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/login");
        }
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
