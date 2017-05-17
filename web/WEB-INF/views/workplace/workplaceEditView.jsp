<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 12.04.2017
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Workplace</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form method="POST" action="workplaceEdit">
        <h3>Edit Workplace</h3>
        <input type="hidden" name="id" value="${workplace.id}"/>
        <table border="0">
            <tr>
                <td width="100px">ID</td>
                <td>
                    <label style="font-weight: bold;"><c:choose><c:when test="${workplace.id == 0}">(new workplace)</c:when><c:otherwise>${workplace.id}</c:otherwise></c:choose></label>
                </td>
            </tr>
            <tr>
                <td>Building</td>
                <td><input class="form-control" type="text" name="building" value="${workplace.building}" /></td>
            </tr>
            <tr>
                <td>Floor</td>
                <td><input class="form-control" type="text" name="floor" value="${workplace.floor}" /></td>
            </tr>
            <tr>
                <td>Room</td>
                <td><input class="form-control" type="text" name="room" value="${workplace.room}" /></td>
            </tr>
            <tr>
                <td>Note</td>
                <td><input class="form-control" type="text" name="note" value="${workplace.note}" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <label style="color: red;">${errorString}&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/workplaceList">Cancel</a>
                    <input class="btn btn-success" type="submit" value="Submit" />
                </td>
            </tr>
        </table>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
