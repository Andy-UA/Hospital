package Servlets.Common;

import Factories.SessionFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Created by andre on 21.04.2017.
 */
public class HttpServletUtil {
    public static Long getLongParam(HttpServletRequest request, String name, Long defaultValue) throws Exception {
        String x = request.getParameter(name);
        if (x != null && !x.isEmpty()) {
            try {
                return Long.parseLong(x);
            }
            catch (Exception e){
                throw new Exception("Invalid value '" + x + "' for parameter " + name);
            }
        }
        return defaultValue;
    }

    public static Boolean isEmpty(String v) {
        return v == null || v.isEmpty();
    }

    public static Double getDoubleParam(HttpServletRequest request, String name, double defaultValue) throws Exception {
        String x = request.getParameter(name);
        if (x != null) {
            try {
                return Double.parseDouble(x);
            }
            catch (Exception e){
                throw new Exception("Invalid value '" + x + "' for parameter " + name);
            }
        }
        return defaultValue;
    }

    public static LocalDateTime getDateTimeParam(HttpServletRequest request, String name, LocalDateTime defaultValue, String dateFormat) throws Exception {
        return getDateTimeValue(request.getParameter(name), defaultValue, dateFormat);
    }

    public static LocalDateTime getDateTimeValue(String value, LocalDateTime defaultValue, String dateFormat) throws Exception {
        if (value != null) {
            try {
                value = value.replace("T", " ");
                if (dateFormat == null || dateFormat.isEmpty())
                    return LocalDateTime.parse(value);
                else{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                    return  LocalDateTime.parse(value, formatter);
                }
            }
            catch (Exception e){
                throw new Exception("Invalid DateTime value '" + value + "'");
            }
        }
        return defaultValue;
    }

    public static LocalDate getDateParam(HttpServletRequest request, String name, LocalDate defaultValue, String dateFormat) throws Exception {
        return getDateValue(request.getParameter(name), defaultValue, dateFormat);
    }

    public static LocalDate getDateValue(String value, LocalDate defaultValue, String dateFormat) throws Exception {
        if (value != null) {
            try {
                value = value.replace("T", " ");
                if (dateFormat == null || dateFormat.isEmpty())
                    return LocalDate.parse(value);
                else{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
                    return  LocalDate.parse(value, formatter);
                }
            }
            catch (Exception e){
                throw new Exception("Invalid Date value '" + value + "'");
            }
        }
        return defaultValue;
    }

    public static String prepareErrorString(String value) {
        String output = null;
        if (value != null) {
            output = value.replace("<", "&lt").replace(">", "&gt").replace(" ", "&nbsp;");
            output = output.replace("\r\n", "<br>");
        }
        return output;
    }

    public static String getExeptionMessage(Exception e) {
        return e.getMessage() != null ? e.getMessage() : e.toString();
    }

    public static void setResponseException(HttpServletResponse response, Exception e) {
        setResponseText(response, getExeptionMessage(e));
    }

    public static void setResponseText(HttpServletResponse response, String text) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/html;charset=utf-8");
            response.getOutputStream().write(Charset.forName("UTF-8").encode(HttpServletUtil.prepareErrorString(text)).array());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Font getFont(float size, int style, BaseColor color) throws Exception {
        String FONT_LOCATION = "c:/Windows/Fonts/tahoma.ttf";
        BaseFont baseFont = BaseFont.createFont(FONT_LOCATION, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        return new Font(baseFont, size, style, color);
    }
    public static String getPDFContentDisposition(String parent){
        StringBuffer sbFilename = new StringBuffer();
        sbFilename.append("hospital_");
        if (parent != null && !parent.isEmpty())
            sbFilename.append(parent);
        else
            sbFilename.append("Untitled");
        sbFilename.append(System.currentTimeMillis());
        sbFilename.append(".pdf");
        StringBuffer sbContentDisposition = new StringBuffer();
        sbContentDisposition.append("inline");
        sbContentDisposition.append("; filename=");
        sbContentDisposition.append(sbFilename);

        return sbContentDisposition.toString();
    }


    public static void prepareEventPeriod(HttpServletRequest request, HttpServletResponse response) {
        LocalDateTime eventBegin;
        LocalDateTime eventEnd;
        HttpSession session = request.getSession(true);

        String sBegin = request.getParameter("eventBegin");
        String sEnd = request.getParameter("eventEnd");
        if (sBegin == null)
            sBegin = SessionFactory.getCookieValue(request, SessionFactory.EVENT_BEGIN);
        if (sEnd == null)
            sEnd = SessionFactory.getCookieValue(request, SessionFactory.EVENT_END);
        if (sBegin == null) {
            eventBegin = SessionFactory.getSessionEventBegin(session);
            if (eventBegin == null)
                eventBegin = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        } else
            eventBegin = LocalDateTime.parse(sBegin).withSecond(0).withNano(0);

        if (sEnd == null) {
            eventEnd = SessionFactory.getSessionEventEnd(session);
            if (eventEnd == null)
                eventEnd = LocalDateTime.now().withHour(23).withMinute(0).withSecond(0).withNano(0);
        } else
            eventEnd = LocalDateTime.parse(sEnd).withSecond(0).withNano(0);

        request.setAttribute("eventBegin", eventBegin);
        request.setAttribute("eventEnd", eventEnd);

        SessionFactory.setSessionEventBegin(session, eventBegin);
        SessionFactory.setSessionEventEnd(session, eventEnd);
        SessionFactory.setCookieValue(response, SessionFactory.EVENT_BEGIN, eventBegin.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        SessionFactory.setCookieValue(response, SessionFactory.EVENT_END, eventEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public static void showError(HttpServletRequest request, HttpServletResponse response, String errorString, String returnRef) throws ServletException, IOException {
        if (errorString != null) {
            request.setAttribute("errorString", prepareErrorString(errorString));
            request.setAttribute("title", "Hospital IS");
            request.setAttribute("returnRef", request.getContextPath() + returnRef);
            request.setAttribute("returnText", "Continue work ...");
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/WEB-INF/views/errorView.jsp");
            dispatcher.forward(request, response);
        }
    }

    public  static <T extends Enum<T>> T getEnumParam(Class<T> enumType, HttpServletRequest request, String name, Object defaultValue) throws Exception{
        String x = request.getParameter(name);
        if (x != null && !x.isEmpty()) {
            T value = Enum.valueOf(enumType, x);
            return value;
        }
        return (T)defaultValue;
    }

    public static Enum<?> findEnumValue(Class<? extends Enum<?>> enumType, String value) {
        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.name().equals(value))
                .findFirst()
                .orElse(null);
    }

    public static Enum<?> findEnumDefault(Class<? extends Enum<?>> enumType) {
        return Arrays.stream(enumType.getEnumConstants())
                .findFirst().get();
    }
}
