<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 15.04.2017
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Staff List</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form action="/staffList" method="get">
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <div class="navbar-form request-bar" style="padding-left: 0px; padding-top: 0px; margin-top: 0px; margin-bottom: 5px;">
            <button class="btn btn-primary" type="submit"><i class="material-icons" style="font-size: 20px; vertical-align: bottom;">sync</i>&nbsp;Doctors</button>
            <input class="form-control" type="text" name="firstName" value="${firstName}" data-toggle="tooltip" data-placement="bottom" title="first name, % - any part of value" placeholder="first name ..." style="margin-right: 20px; margin-left: 20px;"/>
            <input class="form-control" type="text" name="lastName" value="${lastName}" data-toggle="tooltip" data-placement="bottom" title="last name, % - any part of value" placeholder="last name ..." style="margin-right: 20px;"/>
            <select class="form-control" name="category" style="padding-right: 20px;">
                <c:forEach items="${roleCategory}" var="c">
                    <c:choose>
                        <c:when test="${c.toString() != 'People' && c.toString() != 'Unknown'}">
                            <option ${category == c ? "selected" : ""} value=${c.toString()}>${c.toString()}</option>
                        </c:when>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
        <table class="table table-striped" width="100%">
            <thead>
                <tr class="bg-primary">
                    <th>ID</th>
                    <th style="text-align: center;">Category</th>
                    <th style="text-align: center;">Role Enabled</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th style="text-align: center;">Birthday</th>
                    <th style="text-align: center;">Human Enabled</th>
                    <th style="text-align: center;">Sex</th>
                    <th>Role Note</th>
                    <th>Human Note</th>
                    <th style="padding-left: 5px;">Actions</th>
                </tr>
            </thead>
            <c:forEach items="${staffList}" var="staff" >
                <tr>
                    <td>${staff.roleID}</td>
                    <td style="text-align: center;">${staff.category}</td>
                    <td style="text-align: center;">${staff.roleEnabled}</td>
                    <td>${staff.firstName}</td>
                    <td>${staff.lastName}</td>
                    <td style="text-align: center;">${staff.birthday.toString()}</td>
                    <td style="text-align: center;">${staff.humanEnabled}</td>
                    <td style="text-align: center;">${staff.sex}</td>
                    <td>${staff.roleNote}</td>
                    <td>${staff.humanNote}</td>
                    <td colspan="2" style="padding-left: 5px;">
                        <a href="${pageContext.request.contextPath}/scheduleAdmin?staffID=${staff.roleID}"><i class="material-icons" style="font-size: 20px;">schedule</i>&nbsp;schedule</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>

<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
