<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 14.04.2017
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Location</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form method="POST" action="locationEdit">
        <input type="hidden" name="id" value="${location.id}"/>
        <input type="hidden" name="humanID" value="${human.id}"/>
        <fieldset>
            <legend>Location of ${human.getFirstName().toString()} ${human.getLastName().toString()}</legend>
            <table border="0">
                <tr>
                    <td>Type</td>
                    <td><select class="form-control" name="type" />
                        <c:forEach items="${locationType}" var="type">
                            <option ${location.type == type ? "selected" : ""} value=${type.toString()}>${type.toString()}</option>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>Post Index</td>
                    <td><input class="form-control" type="text" name="postIndex" value="${location.postIndex}" /></td>
                </tr>
                <tr>
                    <td>Country</td>
                    <td><input class="form-control" type="text" name="country" value="${location.country == null || location.country.isEmpty() ? "Ukraine" : location.country}" /></td>
                </tr>
                <tr>
                    <td>State-</td>
                    <td><input class="form-control" type="text" name="state" value="${location.state}" /></td>
                </tr>
                <tr>
                    <td>Area-</td>
                    <td><input class="form-control" type="text" name="area" value="${location.area}" /></td>
                </tr>
                <tr>
                    <td>City</td>
                    <td><input class="form-control" type="text" name="city" value="${location.city == null || location.city.isEmpty() ? "Kyiv" : location.city}"/></td>
                </tr>
                <tr>
                    <td>Street</td>
                    <td><input class="form-control" type="text" name="street" value="${location.street}" /></td>
                </tr>
                <tr>
                    <td>House</td>
                    <td><input class="form-control" type="text" name="house" value="${location.house}" /></td>
                </tr>
                <tr>
                    <td>Apartment-</td>
                    <td><input class="form-control" type="text" name="apartment" value="${location.apartment}" /></td>
                </tr>
                <tr>
                    <td>Note-</td>
                    <td><input class="form-control" type="text" name="note" value="${location.note}" /></td>
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