package Servlets.Location;

import Beans.Location;
import Types.LocationType;
import Factories.SessionFactory;
import Factories.HumanFactory;
import Factories.LocationFactory;
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
@WebServlet(urlPatterns = { "/locationEdit" })
public class LocationEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try{
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Long humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);

            if (humanID < 1L)
                throw new Exception("Undefined value for human ID");

            Location location = new Location(id,
                    humanID,
                    LocationType.valueOf(request.getParameter("type")),
                    request.getParameter("postIndex"),
                    request.getParameter("country"),
                    request.getParameter("state"),
                    request.getParameter("area"),
                    request.getParameter("city"),
                    request.getParameter("street"),
                    request.getParameter("house"),
                    request.getParameter("apartment"),
                    request.getParameter("note"));
            location.validate();
            Connection conn = SessionFactory.getStoredConnection(request);
            if(location.getId() < 1)
                LocationFactory.insert(conn, location);
            else
                LocationFactory.update(conn, location);
            response.sendRedirect(request.getContextPath() + "/humanEdit?id=" + humanID );
            return;
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
            Long humanID = HttpServletUtil.getLongParam(request, "humanID", 0L);
            Long id = HttpServletUtil.getLongParam(request, "id", 0L);
            Connection conn = SessionFactory.getStoredConnection(request);
            Location location = LocationFactory.select(conn, id);
            if (location != null)
                humanID = location.getHumanID();
            else
                location = new Location(0L, humanID, LocationType.RegistrationPlace, null, "Ukraine", null, null, "Kyiv", null, null, null, null);
            if (humanID == 0L)
                throw new Exception("Undefined value for humanID");
            request.setAttribute("location", location);
            request.setAttribute("locationType", LocationType.values());
            request.setAttribute("human", HumanFactory.select(conn, humanID));
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/location/locationEditView.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }

        if (errorString != null) {
            HttpServletUtil.showError(request, response, errorString, "/humanList");
        }
    }
}
