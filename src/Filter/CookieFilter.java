package Filter;

import Beans.Account;
import Beans.Profile;
import Factories.AccountFactory;
import Factories.ProfileFactory;
import Factories.SessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * Created by Andrew on 10.04.2017.
 */
@WebFilter(filterName = "cookieFilter", urlPatterns = { "/*" })
public class CookieFilter implements Filter {

    public CookieFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Profile profile = SessionFactory.getLoginedProfile(session);
        if (profile != null){
            httpRequest.setAttribute("profile_", profile);
        }
        Account account = SessionFactory.getLoginedAccount(session);
        if (account != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            httpRequest.setAttribute("account_", account);
        }else {
            // Connection was created in JDBCFilter.
            Connection conn = SessionFactory.getStoredConnection(request);

            // Flag check cookie
            String checked = (String) session.getAttribute("COOKIE_CHECKED");
            if (checked == null && conn != null) {
                String userName = SessionFactory.getCookieLogin(httpRequest);
                if (userName != null && !userName.isEmpty()) {
                    try {
                        //init account (may be removed in future)
                        account = AccountFactory.select(conn, userName);
                        SessionFactory.setLoginedAccount(session, account);
                        httpRequest.setAttribute("account_", account);
                        //init profile (this is base in future)
                        profile = ProfileFactory.select(conn, account, SessionFactory.getSessionRole(session));
                        SessionFactory.setLoginedProfile(session, profile);
                        httpRequest.setAttribute("profile_", profile);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Mark checked.
                session.setAttribute("COOKIE_CHECKED", "CHECKED");
            }
        }
        chain.doFilter(request, response);
    }
}
