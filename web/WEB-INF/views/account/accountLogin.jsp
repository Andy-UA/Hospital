<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 10.04.2017
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <h3>Login</h3>
    <form method="POST" action="login">
        <table border="0">
            <tr>
                <td width="100px">
                    Login
                </td>
                <td width=100px">
                    <input class="form-control" type="text" name="login" value= "${account.login}" />
                </td>
            </tr>
            <tr>
                <td>
                    Password
                </td>
                <td>
                    <input class="form-control" type="password" name="password" value= "" />
                </td>
            </tr>
            <tr>
                <td>
                    Login as role
                </td>
                <td>
                    <select class="form-control" name="role" />
                    <c:forEach items="${roleScope}" var="r">
                        <option ${role == r ? "selected" : ""} value=${r.toString()}>${r.toString()}</option>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td>
                    Remember me
                </td>
                <td>
                    <input class="form-control" type="checkbox" name="rememberMe" value= "Y" style="width: 20px;" />
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <label style="color: red;">${errorString}</label>
                </td>
            </tr>
            <tr>
                <td colspan ="2">
                    <input class="btn btn-success" type="submit" value= "Login" style="padding-right: 20px;" />
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/home">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>