<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 10.04.2017
  Time: 21:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <jsp:include page="main/_bootstrap.jsp"></jsp:include>
</head>
<body>
<jsp:include page="main/_header.jsp"></jsp:include>
<jsp:include page="main/_menu.jsp"></jsp:include>
<div class="container-fluid" style="height: 350px; width: 100%;">
    <div style="float: left; width: 600px; ">
        <h1 style="font-weight: bold; font-size: 500%; color: #245580;">Hospital IS</h1>
        <h8>the hospital information system ...</h8>
        <br/>
        <img src="${pageContext.request.contextPath}/images/worldclients.png"/>
        <br/>
        <hr>
        <br/>
        <label style="font-weight: bold; font-size: small; color: gray">Created by Andrew Stupnitsky KP-31<br>KPI Ukraine 2017</label>
    </div>
</div>
<jsp:include page="main/_footer.jsp"></jsp:include>

</body>
</html>
