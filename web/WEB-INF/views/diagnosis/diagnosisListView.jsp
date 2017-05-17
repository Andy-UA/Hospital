<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 10.04.2017
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Diagnosis List</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form action="/diagnosisList" method="get">
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <div class="navbar-form request-bar">
            <button class="btn btn-primary request-control" type="submit"><i class="material-icons table-icon" style=" font-size: 20px; vertical-align: bottom;">sync</i>&nbsp;Diagnosis</button>
            <input class="form-control request-control" type="text" name="code" value="${code}" placeholder="code ..."/>
            <input class="form-control request-control" type="text" name="name" value="${name}" placeholder="name ..."/>
            <a class="btn btn-success request-control" href="${pageContext.request.contextPath}/diagnosisEdit?id=0"><i class="material-icons table-icon" style="font-size: 20px; vertical-align: bottom;">local_hospital</i>&nbsp;Create Diagnosis</a>
        </div>
        <table class="table table-striped table-condensed" width="100%" >
            <thead>
                <tr class="bg-primary">
                    <th width="70px">ID</th>
                    <th width="100px">Code</th>
                    <th width="300px">Name</th>
                    <th>Note</th>
                    <th width="70px" style="text-align: center;">Actions</th>
                </tr>
            </thead>
            <c:forEach items="${diagnosisList}" var="diagnosis" >
                <tr>
                    <td>${diagnosis.id}</td>
                    <td>${diagnosis.code}</td>
                    <td>${diagnosis.name}</td>
                    <td>${diagnosis.note}</td>
                    <td style="text-align: center;">
                        <a href="${pageContext.request.contextPath}/diagnosisEdit?id=${diagnosis.id}"><i class="material-icons table-icon">border_color</i></a>
                        <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Diagnosis" onclick="deleteRequest('diagnosisDelete?id=${diagnosis.id}');"><i class="material-icons table-icon">cancel</i></button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
