package Factories;

import Beans.Account;
import Beans.Profile;
import Types.RoleScope;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Andrew on 10.04.2017.
 */
public class SessionFactory extends CommonFactory {

    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

    public static final String LAST_LOGIN = "LAST_LOGIN";

    public static final String EVENT_BEGIN = "EVENT_BEGIN";
    public static final String EVENT_END = "EVENT_END";

    public static final String ROLE = "ROLE";

    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }

    // Get the Connection object has been stored in one attribute of the request.
    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
        return conn;
    }

    //Get and store event begin .. end in Session
    public static LocalDateTime getSessionEventBegin(HttpSession session) {
        LocalDateTime value = (LocalDateTime) session.getAttribute(EVENT_BEGIN);
        return value;
    }

    public static void setSessionEventBegin(HttpSession session, LocalDateTime value) {
        session.setAttribute(EVENT_BEGIN, value);
    }

    public static LocalDateTime getSessionEventEnd(HttpSession session) {
        LocalDateTime value = (LocalDateTime) session.getAttribute(EVENT_END);
        return value;
    }

    public static void setSessionEventEnd(HttpSession session, LocalDateTime value) {
        session.setAttribute(EVENT_END, value);
    }


    //Get and store Role in Session
    public static RoleScope getSessionRole(HttpSession session) {
        Object v = session.getAttribute(ROLE);
        if (v != null)
            return (RoleScope) v;
        else
            return RoleScope.Unknown;
    }

    public static void setSessionRole(HttpSession session, RoleScope value) {
        session.setAttribute(ROLE, value);
    }

    // Get and Set the user account in the session.
    public static void setLoginedAccount(HttpSession session, Account account) {
        session.setAttribute("userAccount", account);
    }

    public static void clearLoginedAccount(HttpSession session) {
        if(session.getAttribute("userAccount") != null)
            session.removeAttribute("userAccount");
    }

    public static Account getLoginedAccount(HttpSession session) {
        return  (Account) session.getAttribute("userAccount");
    }

    // Get and Set the user profile in the session.
    public static void setLoginedProfile(HttpSession session, Profile profile) {
        session.setAttribute("userProfile", profile);
    }

    public static void clearLoginedProfile(HttpSession session) {
        if(session.getAttribute("userProfile") != null)
            session.removeAttribute("userProfile");
    }

    public static Profile getLoginedProfile(HttpSession session) {
        return  (Profile) session.getAttribute("userProfile");
    }

    // Store Login in Cookie
    public static void setCookieLogin(HttpServletResponse response, Account account) {
        System.out.println("Store user cookie");
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, account.getLogin());

        // 1 day (Convert to seconds)
        cookieUserName.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserName);
    }

    public static String getCookieLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void clearCookieLogin(HttpServletResponse response) {
        Cookie cookieLogin = new Cookie(ATT_NAME_USER_NAME, null);
        cookieLogin.setMaxAge(0);
        response.addCookie(cookieLogin);
    }

    // Store info in Cookie Value
    public static void setCookieValue(HttpServletResponse response, String cookieName,  String value) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void clearCookieValue(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName,null);
        // 0 seconds (Expires immediately)
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}