<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 12.04.2017
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Appointment List</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form action="/appointmentList" method="get">
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <div class="navbar-form request-bar">
            <button class="btn btn-primary request-control" type="submit"><i class="material-icons table-icon" style="font-size:20px; vertical-align: bottom;">sync</i>&nbsp;Appointments</button>
            <select class="form-control request-control" name="type">
                <c:forEach items="${appointmentType}" var="t">
                    <option ${type == t ? "selected" : ""} value=${t.toString()}>${t.toString()}</option>
                </c:forEach>
            </select>
            <input class="form-control request-control" type="text" name="description" value="${description}" placeholder="description ..."/>
            <a class="btn btn-success request-control" href="${pageContext.request.contextPath}/appointmentEdit?id=0"><img src="/images/pill.png"/>&nbsp;Create Appointment</a>
        </div>
        <table class="table table-striped table-condensed" width="100%" >
            <thead>
                <tr class="bg-primary">
                    <th width="70px">ID</th>
                    <th width="100px">Type</th>
                    <th width="300px">Description</th>
                    <th width="90px">Unit</th>
                    <th>Note</th>
                    <th width="70px" style="text-align: center;">Actions</th>
                </tr>
            </thead>
            <c:forEach items="${appointmentList}" var="appointment" >
                <tr>
                    <td>${appointment.id}</td>
                    <td>${appointment.type}</td>
                    <td>${appointment.description}</td>
                    <td>${appointment.unit}</td>
                    <td>${appointment.note}</td>
                    <td style="text-align: center;">
                        <a href="${pageContext.request.contextPath}/appointmentEdit?id=${appointment.id}"><i class="material-icons table-icon">border_color</i></a>
                        <button class="btn btn-link btn-sm table-button" type="button" style="margin: 0px; padding: 0px;" data-toggle="tooltip" data-placement="auto" title="Delete Appointment" onclick="deleteRequest('appointmentDelete?id=${appointment.id}');"><i class="material-icons table-icon">cancel</i></button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
