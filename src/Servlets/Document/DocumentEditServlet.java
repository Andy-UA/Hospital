package Servlets.Document;

import Beans.Document;
import Types.DocumentType;
import Factories.SessionFactory;
import Factories.DocumentFactory;
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
@WebServlet(urlPatterns = { "/documentEdit" })
public class DocumentEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Connection conn = SessionFactory.getStoredConnection(request);

            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Long humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);

            if (humanID < 1L)
                throw new Exception("Undefined value for human ID");

            Document document = new Document(id, humanID, DocumentType.valueOf(request.getParameter("type")), request.getParameter("value"), request.getParameter("note"));
            document.validate();

            if (document.getId() < 1)
                DocumentFactory.insert(conn, document);
            else
                DocumentFactory.update(conn, document);
            response.sendRedirect(request.getContextPath() + "/humanEdit?id=" + humanID);
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
            Document document = DocumentFactory.select(conn, id);
            if (document != null)
                humanID = document.getHumanID();
            else
                document = new Document(0L, humanID, DocumentType.Passport, null, null);
            request.setAttribute("document", document);
            request.setAttribute("documentType", DocumentType.values());
            request.setAttribute("human", HumanFactory.select(conn, humanID));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/document/documentEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/humanList");
        }
    }
}
