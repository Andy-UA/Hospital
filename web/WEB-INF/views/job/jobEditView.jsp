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
    <title>Human: Job of Role</title>
</head>
<body>
<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <form method="POST" action="jobEdit">
        <input type="hidden" name="id" value="${job.id}"/>
        <input type="hidden" name="roleID" value="${job.roleID}"/>
        <fieldset>
            <legend>${role.category} ${human.getFirstName().toString()} ${human.getLastName().toString()}: Job of Role ${role.scope}</legend>
            <table border="0">
                <tr>
                    <td>
                        Scope
                    </td>
                    <td>
                        <select class="form-control" name="scope" />
                        <c:forEach items="${jobScope}" var="scope">
                            <option ${job.scope == scope ? "selected" : ""} value=${scope}>${scope}</option>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>
                        Note
                    </td>
                    <td>
                        <input class="form-control" type="text" name="note" value="${job.note}" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <label style="color: red;">${errorString}&nbsp;</label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <a class="btn btn-default" href="${pageContext.request.contextPath}/roleEdit?id=${job.roleID}">Go to Role ${role.scope} (${role.category}) edit page ...</a>
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