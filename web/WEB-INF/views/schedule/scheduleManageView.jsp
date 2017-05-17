<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 19.04.2017
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Schedule Manage</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <h3 style="margin-top: 0px; margin-bottom: 0px;">Schedule Manage</h3>
    <label class="label-danger">${errorString}</label>
    <form method="POST" action="scheduleManage">
        <input type="hidden" name="scheduleID" value="${schedule.id}"/>
        <fieldset class="form-group" valign="top" style="width: 100%; border: thin;">
            <legend>Doctor Info</legend>
            <table border="0" width="100%">
                <tr>
                    <td width="300px">
                        First / Last Name
                    </td>
                    <td>
                        <label style="font-weight: bold; font-size: x-large;">${staff.firstName} ${staff.lastName}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        Category
                    </td>
                    <td>
                        <label style="font-weight: bold;">${staff.category}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        Notes
                    </td>
                    <td>
                        <label style="font-weight: bold;">${staff.roleNote} ${staff.humanNote}</label>
                    </td>
                </tr>
            </table>
        </fieldset>
        <fieldset class="form-group" valign="top" style="width: 100%; border: thin;">
            <legend>Schedule info</legend>
            <table border="0" width="100%">
                <tr>
                    <td width="300px">
                        Appointment location
                    </td>
                    <td>
                        <label style="font-weight: bold;">${workplace.toString()}</label>
                    </td>
                </tr>
                <tr>
                    <td>
                        Appointment hours
                    </td>
                    <td>
                        <label style="font-weight: bold;">${schedule.getEventBeginText()} to ${schedule.getEventEndText()}</label>
                    </td>
                </tr>
            </table>
        </fieldset>
        <fieldset class="form-group" valign="top" style="width: 100%; border: thin;">
            <legend>Schedule assign patient</legend>
            <table border="0" width="100%">
                <tr>
                    <td width="300px">
                        Patient
                    </td>
                    <td>
                        <select class="form-control" name="patientID">
                            <c:forEach items="${patientList}" var="w">
                                <option ${patientID == w.getRoleID() ? "selected" : ""} value=${w.getRoleID().toString()}>${w.toString()}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        Appointment notes
                    </td>
                    <td>
                        <input class="form-control" type="text" name="note" value="${note}"/>
                    </td>
                </tr>
            </table>
        </fieldset>
        <div class="nav-divider">
            <a class="btn btn-default" href="${pageContext.request.contextPath}/scheduleAdmin?staffID=${staff.roleID}">Go back to Schedule admin</a>
            <input type="submit" class="btn btn-success" value="Assign patient"/>
        </div>
    </form>
<div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
