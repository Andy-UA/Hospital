<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 15.04.2017
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Load Image</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form method="POST" action="image" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${id}"/>
        <input type="hidden" name="humanID" value="${human.getId().toString()}"/>

        <fieldset style="border: solid thin silver; padding: 10px;">
            <legend>Image of ${human.getFirstName().toString()} ${human.getLastName().toString()} (${human.getId().toString()})</legend>
            <table border="0">
                <tr>
                    <td width="150px">
                        Image scope
                    </td>
                    <td><select class="form-control" name="scope" />
                        <c:forEach items="${imageScope}" var="v">
                            <option ${scope == v ? "selected" : ""} value=${v.toString()}>${v.toString()}</option>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        Content-Type
                    </td>
                    <td>
                        <input class="form-control" type="text" name="contentType" value="${contentType}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Note
                    </td>
                    <td>
                        <input class="form-control" type="text" name="note" value="${note}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        Image
                    </td>
                    <td>
                        <input class="form-control" type="file" name="file" />
                    </td>
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
