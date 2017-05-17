<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 10.04.2017
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Diagnosis</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>


<div class="container-fluid">
    <h3>Edit Diagnosis</h3>
    <form method="POST" action="diagnosisEdit">
        <input type="hidden" name="id" value="${diagnosis.id}"/>
        <p style="color: red;">${errorString}</p>
        <table border="0">
            <tr>
                <td width="100px">ID</td>
                <td>
                    <label style="font-weight: bold;"><c:choose><c:when test="${diagnosis.id == 0}">(new diagnosis)</c:when><c:otherwise>${diagnosis.id}</c:otherwise></c:choose>
                </td>
            </tr>
            <tr>
                <td>Code</td>
                <td><input class="form-control" type="text" name="code" value="${diagnosis.code}" /></td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input class="form-control" type="text" name="name" value="${diagnosis.name}" /></td>
            </tr>
            <tr>
                <td>Note</td>
                <td><input class="form-control" type="text" name="note" value="${diagnosis.note}" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <label style="color: red;">${errorString}&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/diagnosisList">Cancel</a>
                    <input class="btn btn-success" type="submit" value="Submit" />
                </td>
            </tr>
        </table>
    </form>
</div>

<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
