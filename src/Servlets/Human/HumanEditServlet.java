package Servlets.Human;

import Beans.Human;
import Beans.Role;
import Types.ImageScope;
import Types.RoleCategory;
import Types.RoleScope;
import Factories.*;
import Factories.SessionFactory;
import Servlets.Common.HttpServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;

/**
 * Created by Andrew on 12.04.2017.
 */
@WebServlet(urlPatterns = { "/humanEdit" })
public class HumanEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HumanEditServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        Human human = null;
        try{
            human = new Human(HttpServletUtil.getLongParam(request, "id", 0L),
                    request.getParameter("firstName"),
                    request.getParameter("lastName"),
                    HttpServletUtil.getDateParam(request, "birthday", LocalDate.now(), "uuuu-MM-dd"),
                    request.getParameter("sex"),
                    "Y".equals(request.getParameter("enabled")),
                    request.getParameter("note"));
            human.validate();
            Connection conn = SessionFactory.getStoredConnection(request);
            if (human.getId() < 1)
                HumanFactory.insert(conn, human);
            else
                HumanFactory.update(conn, human);
            response.sendRedirect(request.getContextPath() + "/humanEdit?id=" + human.getId());
        }catch (Exception e){
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null) {
            if (human.getId() < 1)
                HttpServletUtil.showError(request, response, errorString, "/humanList");
            else
                HttpServletUtil.showError(request, response, errorString, "/humanEdit?humanID=" + human.getId());
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            Human human = HumanFactory.select(conn, id);
            if (human == null)
                human = new Human(0L, null, null, LocalDate.now(), "M", true, null);
            request.setAttribute("human", human);
            request.setAttribute("images", ImageFactory.queryDescriptions(conn, id, ImageScope.Photo, null));
            request.setAttribute("locations", LocationFactory.query(conn, id));
            request.setAttribute("contacts", ContactFactory.query(conn, id));
            request.setAttribute("accounts", AccountFactory.query(conn, id));
            request.setAttribute("roles", RoleFactory.query(conn, id, RoleScope.Unknown, true));
            request.setAttribute("documents", DocumentFactory.query(conn, id));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/human/humanEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        HttpServletUtil.showError(request, response, errorString, "/humanList");
    }

    private void setRole(Connection conn, Human human, RoleScope scope, RoleCategory category, boolean enabled) throws Exception {
        if (enabled) {
            if (!RoleFactory.enabledRole(conn, human.getId(), scope, true, null)) {
                Role role = new Role(0L, scope, category, human.getId(), true, null, null);
                RoleFactory.insert(conn, role);
            }
        } else {
            RoleFactory.enabledRole(conn, human.getId(), scope, false, null);
        }
    }
}
