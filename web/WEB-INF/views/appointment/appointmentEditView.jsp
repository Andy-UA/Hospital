<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 12.04.2017
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Appointment</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <h3>Edit Appointment</h3>
    <form method="POST" action="appointmentEdit">
        <input type="hidden" name="id" value="${appointment.id}"/>
        <p style="color: red;">${errorString}</p>
        <table border="0">
            <tr>
                <td width="100px">ID</td>
                <td>
                    <label style="font-weight: bold;"><c:choose><c:when test="${appointment.id == 0}">(new appointment)</c:when><c:otherwise>${appointment.id}</c:otherwise></c:choose></label>
                </td>
            </tr>
            <tr>
                <td>
                    Type
                </td>
                <td>
                    <select class="form-control" name="type" />
                    <c:forEach items="${appointmentType}" var="type">
                        <option ${appointment.type == type ? "selected" : ""} value=${type.toString()}>${type.toString()}</option>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td>Description</td>
                <td><input class="form-control" type="text" name="description" value="${appointment.description}" /></td>
            </tr>
            <tr>
                <td>Unit</td>
                <td><input class="form-control" type="text" name="unit" value="${appointment.unit}" /></td>
            </tr>
            <tr>
                <td>Note</td>
                <td><input class="form-control" type="text" name="note" value="${appointment.note}" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <label style="color: red;">${errorString}&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/appointmentList">Cancel</a>
                    <input class="btn btn-success" type="submit" value="Submit" />
                </td>
            </tr>
        </table>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
