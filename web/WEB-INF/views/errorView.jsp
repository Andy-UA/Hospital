<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 10.04.2017
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <jsp:include page="main/_bootstrap.jsp"></jsp:include>
</head>

<body>

<jsp:include page="main/_header.jsp"></jsp:include>
<jsp:include page="main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <h3>${title}</h3>
    <fieldset style="border: solid thin silver; margin-bottom: 15px;">
        <legend style="padding-left: 10px; color: #286090;">message:</legend>
        <h3 style="padding-left: 15px; padding-top: 0px; font-size: 150%; color: darkred;">${errorString}</h3>
    </fieldset>
    <a class="btn btn-default" href="${pageContext.request.contextPath}${returnRef}">${returnText}</a>
</div>
<jsp:include page="main/_footer.jsp"></jsp:include>

</body>
</html>
