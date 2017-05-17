<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 17.04.2017
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Schedule Build</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <h3 style="margin-top: 0px; margin-bottom: 0px;">Schedule Build</h3>
    <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
    <form method="POST" action="scheduleBuild">
        <input type="hidden" name="staffID" value="${staff.roleID}"/>
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
            <legend>Schedule build options</legend>
            <table border="0" width="100%">
                <tr>
                    <td width="300px">
                        Workplace
                    </td>
                    <td>
                        <select class="form-control" name="workplaceID">
                            <c:forEach items="${workplaceList}" var="w">
                                <option ${workplaceID == w.getId() ? "selected" : ""} value=${w.getId().toString()}>${w.toString()}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        Shift Date
                    </td>
                    <td>
                        <input class="form-control" style="width: 200px;" type="date" name="eventDate" value="${eventDate}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Shift start time
                    </td>
                    <td>
                        <input class="form-control" style="width: 200px;" type="time" name="eventBegin" value="${eventBegin}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Shift end time
                    </td>
                    <td>
                        <input class="form-control" style="width: 200px;" type="time" name="eventEnd" value="${eventEnd}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Duration of admission by a doctor, minutes
                    </td>
                    <td>
                        <select class="form-control" style="width: 200px;" name="interval">
                            <option ${interval == "10" ? "selected" : ""} value="10">10 minutes</option>
                            <option ${interval == "20" ? "selected" : ""} value="20">20 minutes</option>
                            <option ${interval == "30" ? "selected" : ""} value="30">30 minutes</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        Schedule note
                    </td>
                    <td>
                        <input class="form-control" type="text" name="note" value="${note}"/>
                    </td>
                </tr>
            </table>
        </fieldset>
        <div class="nav-divider">
            <a class="btn btn-default" href="${pageContext.request.contextPath}/scheduleAdmin?staffID=${staff.roleID}">Go back to Schedule admin</a>
            <input class="btn btn-success" type="submit" value="Build"/>
        </div>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
