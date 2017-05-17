<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 12.04.2017
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Workplace List</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form action="/workplaceList" method="get">
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <div class="navbar-form request-bar">
            <button class="btn btn-primary request-control" type="submit"><i class="material-icons table-icon" style="font-size:20px; vertical-align: bottom;">sync</i>&nbsp;Workplaces</button>
            <input class="form-control request-control" type="text" name="building" value="${building}" placeholder="building ..."/>
            <input class="form-control request-control" type="text" name="room" value="${room}" placeholder="room ..."/>
            <a class="btn btn-success request-control" href="workplaceEdit?id=0"><img src="${pageContext.request.contextPath}/images/hospital-building.png"/>&nbsp;Create Workplace</a>
        </div>
        <table class="table table-striped table-condensed" width="100%">
            <thead>
                <tr class="bg-primary">
                    <th width="70px">ID</th>
                    <th width="90px">Building</th>
                    <th width="90px">Floor</th>
                    <th width="90px">Room</th>
                    <th>Note</th>
                    <th width="70px" style="text-align: center;">Actions</th>
                </tr>
            </thead>
            <c:forEach items="${workplaceList}" var="workplace" >
                <tr>
                    <td>${workplace.id}</td>
                    <td>${workplace.building}</td>
                    <td>${workplace.floor}</td>
                    <td>${workplace.room}</td>
                    <td>${workplace.note}</td>
                    <td style="text-align: center;">
                        <a href="${pageContext.request.contextPath}/workplaceEdit?id=${workplace.id}"><i class="material-icons table-icon">border_color</i></a>
                        <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Workplace" onclick="deleteRequest('workplaceDelete?id=${workplace.id}');"><i class="material-icons table-icon">cancel</i></button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
