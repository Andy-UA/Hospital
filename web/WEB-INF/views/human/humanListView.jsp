<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 12.04.2017
  Time: 20:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
    <meta charset="UTF-8">
    <title>Human List</title>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form action="/humanList" method="get">
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <div class="navbar-form request-bar text-left">
            <button class="btn btn-primary request-control" type="submit"><i class="material-icons table-icon" style="font-size: 20px; vertical-align: bottom;">sync</i>&nbsp;Humans</button>
            <input class="form-control request-control" type="text" name="firstName" value="${firstName}" placeholder="first name ..."/>
            <input class="form-control request-control" type="text" name="lastName" value="${lastName}" placeholder="last name ..."/>
            <a class="btn btn-success request-control" href="${pageContext.request.contextPath}/humanEdit?id=0" ><i class="material-icons table-icon" style="font-size: 20px; vertical-align: bottom;">person_add</i>&nbsp;Create Human</a>
            <div class="col-sm-2 text-right" style="float: right; margin: 0px; padding: 0px;">
                <ul class="pagination pagination-sm" style="margin: 0px; padding: 0px; vertical-align: bottom">
                    <li ${page == "1" ? "class=\"disabled\"" : ""}><a href="/humanList?page=1" type="submit">&laquo;</a></li>
                    <li ${page == "1" ? "class=\"active\"" : ""}><a href="/humanList?page=1" type="submit">1</a></li>
                    <li ${page == "2" ? "class=\"active\"" : ""}><a href="/humanList?page=2" type="submit">2</a></li>
                    <li ${page == "3" ? "class=\"active\"" : ""}><a href="/humanList?page=3" type="submit">3</a></li>
                    <li ${page == "4" ? "class=\"active\"" : ""}><a href="/humanList?page=4" type="submit">4</a></li>
                    <li ${page == "5" ? "class=\"active\"" : ""}><a href="/humanList?page=5" type="submit">5</a></li>
                    <li ${page == "5" ? "class=\"disabled\"" : ""}><a href="/humanList?page=5" type="submit">&raquo;</a></li>
                </ul>
            </div>
        </div>
        <table class="table table-striped table-condensed" width="100%">
            <thead>
                <tr class="bg-primary">
                    <th style="text-align: center; width: 120px;">ID</th>
                    <th style="text-align: left; width: 170px;">Last Name</th>
                    <th style="text-align: left; width: 170px;">First Name</th>
                    <th style="text-align: center; width: 120px;">Birthday</th>
                    <th style="text-align: center; width: 70px;">Gender</th>
                    <th style="text-align: center; width: 70px;">Enabled</th>
                    <th style="text-align: left;">Note</th>
                    <th style="text-align: left; width: 250px;">Roles</th>
                    <th width="70px" style="text-align: center;">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${humanList}" var="human" >
                    <tr>
                        <td style="text-align: center;">${human.id}</td>
                        <td>${human.lastName}</td>
                        <td>${human.firstName}</td>
                        <td style="text-align: center;">${human.getBirthdayText()}</td>
                        <td style="text-align: center;">${human.sex}</td>
                        <td style="text-align: center;">${human.enabled}</td>
                        <td>${human.note}</td>
                        <td>${human.getRolesText()}</td>
                        <td style="text-align: center;">
                            <a href="${pageContext.request.contextPath}/humanEdit?id=${human.id}"><i class="material-icons table-icon">border_color</i></a>
                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Record Detail" onclick="deleteRequest('humanDelete?id=${human.id}');"><i class="material-icons table-icon">cancel</i></button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
