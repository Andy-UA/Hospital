package Servlets.Account;

import Beans.Account;
import Factories.AccountFactory;
import Factories.SessionFactory;
import Factories.HumanFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by Andrew on 14.04.2017.
 */
@WebServlet(urlPatterns = { "/accountEdit" })
public class AccountEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            Long humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            if (humanID == 0L)
                throw new Exception("Undefined value for humanID");
            Account account = new Account(id, humanID, request.getParameter("login"), request.getParameter("password"), "Y".equals(request.getParameter("enabled")), request.getParameter("note"));
            account.validate();
            account.setPassword(AccountFactory.hashValue(account.getPassword()));
            if (account.getId() < 1)
                AccountFactory.insert(conn, account);
            else
                AccountFactory.update(conn, account);
            response.sendRedirect(request.getContextPath() + "/humanEdit?id=" + account.getHumanID());
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/humanList");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Long humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            Account account = AccountFactory.select(conn, id);
            if (account != null) {
                humanID = account.getHumanID();
                account.setPassword(null);
            }
            else
                account = new Account(0L, humanID, null, null, true, null);
            if (humanID == 0L)
                throw new Exception("Undefined value for humanID");
            request.setAttribute("account", account);
            request.setAttribute("human", HumanFactory.select(conn, humanID));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/account/accountEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        HttpServletUtil.showError(request, response, errorString, "/humanList");
    }
}
