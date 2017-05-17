<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 14.04.2017
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
    <title>Human Role</title>
</head>
<body>
<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form method="POST" action="roleEdit">
        <input type="hidden" name="id" value="${role.id}"/>
        <input type="hidden" name="humanID" value="${role.humanID}"/>
        <fieldset style="width: 700px;">
            <legend>Role of ${human.getFirstName().toString()} ${human.getLastName().toString()}</legend>
            <table border="0">
                <tr>
                    <td width="120px">
                        Scope
                    </td>
                    <td width="600px">
                        <select class="form-control" name="scope" />
                        <c:forEach items="${roleScope}" var="scope">
                            <option ${role.scope == scope ? "selected" : ""} value=${scope.toString()}>${scope.toString()}</option>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        Category
                    </td>
                    <td>
                        <select class="form-control" name="category" />
                        <c:forEach items="${roleCategory}" var="category">
                            <option ${role.category == category ? "selected" : ""} value=${category.toString()}>${category.toString()}</option>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        Note
                    </td>
                    <td>
                        <input class="form-control" type="text" name="note" value="${role.note}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td>
                        Enabled
                    </td>
                    <td>
                        <input class="checkbox" type="checkbox" name="enabled" value="Y" style="width: 20px;" ${role.enabled ? "checked" : ""} />
                    </td>
                </tr>
                <tr>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        <c:if test="${role.id > 0 && role.scope == 'Staff'}">
                            <fieldset style="width: 300px; float: right; border: 1px dotted slategrey; padding-bottom: 20px;">
                                <legend style="font-size: medium; font-weight: bold; background-color: whitesmoke; border: 1px dotted slategrey; margin-left: 10px; padding-left: 20px; width: 70%;">Jobs of Role</legend>
                                <c:forEach items="${jobScope}" var="job" >
                                    <c:if test="${job != 'Unknown'}">
                                        <div class="col-md-10" style="width: 100%;">
                                            <div class="col-md-9">
                                                <label style="font-weight: 100;">
                                                    <c:if test="${jobs.stream().filter(value->value.getScope() == job).count() > 0}">
                                                        <a href="${pageContext.request.contextPath}/jobEdit?id=${jobs.stream().filter(value->value.getScope() == job).findFirst().get().getId()}">${job}</a>
                                                    </c:if>
                                                    <c:if test="${jobs.stream().filter(value->value.getScope() == job).count() < 1}">
                                                        ${job}
                                                    </c:if>
                                                </label>
                                            </div>
                                            <div class="col-md-1">
                                                <input type="checkbox" name="jobChecks", value="${job}" ${jobs.stream().filter(value->value.getScope() == job).count() > 0 ? "checked" : ""}>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </fieldset>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label style="color: red;">${errorString}&nbsp;</label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <a class="btn btn-default" href="${pageContext.request.contextPath}/humanEdit?id=${role.id}">Go to ${human.getFirstName().toString()}'s edit page ...</a>
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