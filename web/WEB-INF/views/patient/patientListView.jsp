<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 17.04.2017
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient List</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form action="/patientList" method="get">
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <div class="navbar-form request-bar">
            <button class="btn btn-primary" type="submit"><i class="material-icons" style="font-size: 20px; vertical-align: bottom;">sync</i>&nbsp;Patients</button>
            <input class="form-control" type="text" name="firstName" value="${firstName}" data-toggle="tooltip" data-placement="bottom" title="first name, % - any part of value" placeholder="first name ..." style=" margin-left: 20px; margin-right: 20px;"/>
            <input class="form-control" type="text" name="lastName" value="${lastName}" data-toggle="tooltip" data-placement="bottom" title="last name, % - any part of value" placeholder="last name ..." style="margin-right: 20px;"/>
            <input class="form-control" type="hidden" name="category" value="${category}"/>
        </div>
        <table class="table table-striped" width="100%">
            <thead>
                <tr class="bg-primary">
                    <th>ID</th>
                    <th style="width: 120px; text-align: center;">Role Enabled</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th style="width: 120px; text-align: center;">Birthday</th>
                    <th style="text-align: center;">Human Enabled</th>
                    <th style="text-align: center;">Sex</th>
                    <th>Role Note</th>
                    <th>Human Note</th>
                    <th style="padding-left: 5px;">Actions</th>
                </tr>
            </thead>
            <c:forEach items="${patientList}" var="patient" >
                <tr>
                    <td>${patient.roleID}</td>
                    <td style="text-align: center;">${patient.roleEnabled}</td>
                    <td>${patient.firstName}</td>
                    <td>${patient.lastName}</td>
                    <td style="text-align: center;">${patient.birthday.toString()}</td>
                    <td style="text-align: center;">${patient.humanEnabled}</td>
                    <td style="text-align: center;">${patient.sex}</td>
                    <td>${patient.roleNote}</td>
                    <td>${patient.humanNote}</td>
                    <td colspan="2" style="padding-left: 5px;">
                        <a href="${pageContext.request.contextPath}/records?patientID=${patient.roleID}"><i class="material-icons" style="font-size: 20px">info</i>records</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>

<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
