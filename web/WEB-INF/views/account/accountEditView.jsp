<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 14.04.2017
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Account</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
<form method="POST" action="accountEdit">
    <input type="hidden" name="id" value="${account.id}"/>
    <input type="hidden" name="humanID" value="${human.id}"/>
    <fieldset>
        <legend>Account of ${human.getFirstName().toString()} ${human.getLastName().toString()}</legend>
        <table border="0">
            <tr>
                <td>Login</td>
                <td><input class="form-control" type="text" name="login" value="${account.login}"/></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input class="form-control" type="password" name="password" value="${account.password}"/></td>
            </tr>
            <tr>
                <td>Enabled</td>
                <td><input class="checkbox" type="checkbox" name="enabled" value="Y" style="width: 20px;" ${account.enabled ? "checked" : ""} /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <label style="color: red;">${errorString}&nbsp;</label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <a class="btn btn-default" href="${pageContext.request.contextPath}/humanEdit?id=${human.id}">Go to ${human.getFirstName().toString()}'s edit page ...</a>
                    <input class="btn btn-success" type="submit" value="Submit"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>
</body>
</html>