package Servlets.Contact;

import Beans.Contact;
import Types.ContactType;
import Factories.SessionFactory;
import Factories.ContactFactory;
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
 * Created by Andrew on 13.04.2017.
 */
@WebServlet(urlPatterns = { "/contactEdit" })
public class ContactEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);
            Long humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            if (humanID == 0L)
                throw new Exception("Undefined value for humanID");
            Contact contact = new Contact(id, humanID, ContactType.valueOf(request.getParameter("type")), request.getParameter("value"), request.getParameter("note"));
            contact.validate();
            if (contact.getId() < 1)
                ContactFactory.insert(conn, contact);
            else
                ContactFactory.update(conn, contact);
            response.sendRedirect(request.getContextPath() + "/humanEdit?id=" + humanID );
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        HttpServletUtil.showError(request, response, errorString, "/humanList");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Long humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            Contact contact = ContactFactory.select(conn, id);
            if (contact != null)
                humanID = contact.getHumanID();
            else
                contact = new Contact(0L, humanID, ContactType.Mobile, null, null);
            if (humanID == 0L)
                throw new Exception("Undefined value for humanID");
            request.setAttribute("contact", contact);
            request.setAttribute("contactType", ContactType.values());
            request.setAttribute("human", HumanFactory.select(conn, humanID));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/contact/contactEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/humanList");
        }
    }
}
