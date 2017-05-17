package Servlets.Account;

import Factories.SessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Andrew on 11.04.2017.
 */
@WebServlet(urlPatterns = { "/logout" })
public class AccountLogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        SessionFactory.clearLoginedAccount(session);
        SessionFactory.clearLoginedProfile(session);
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
