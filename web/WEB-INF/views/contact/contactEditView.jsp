<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 13.04.2017
  Time: 21:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Contact</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form method="POST" action="contactEdit">
        <input type="hidden" name="id" value="${contact.id}"/>
        <input type="hidden" name="humanID" value="${human.id}"/>
        <fieldset>
            <legend>Contact of ${human.getFirstName().toString()} ${human.getLastName().toString()}</legend>
            <table border="0">
                <tr>
                    <td>Type</td>
                    <td><select class="form-control" name="type" />
                        <c:forEach items="${contactType}" var="type">
                            <option ${contact.type == type ? "selected" : ""} value=${type.toString()}>${type.toString()}</option>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>Value</td>
                    <td><input class="form-control" type="text" name="value" value="${contact.value}" /></td>
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