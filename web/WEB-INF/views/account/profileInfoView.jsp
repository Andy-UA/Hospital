<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 10.04.2017
  Time: 22:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile Info</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form method="post" action="profile">
        <fieldset style="width: 100%; font-size: small;">
            <legend>User profile</legend>
            <table border="0" width="100%">
                <tr style="vertical-align: top;">
                    <td width="150px">
                        First / Last Name
                    </td>
                    <td>
                        <label>${profile.human.firstName} ${profile.human.lastName}</label>
                    </td>
                </tr>
                <tr style="vertical-align: top;">
                    <td>
                        Birthday
                    </td>
                    <td>
                        <label>${profile.human.getBirthdayText()}</label>
                    </td>
                <tr/>
                <tr style="vertical-align: top;">
                    <td>
                        Gender
                    </td>
                    <td>
                        <label>${profile.human.sex}</label>
                    </td>
                </tr>
                <tr style="vertical-align: top;">
                    <td>
                        Locations
                    </td>
                    <td>
                        <c:forEach items="${profile.locations}" var="v" >
                            <label>${v.toString()}</label><br/>
                        </c:forEach>
                    </td>
                </tr>
                <tr style="vertical-align: top;">
                    <td>
                        Contacts
                    </td>
                    <td>
                        <c:forEach items="${profile.contacts}" var="v" >
                            <label>${v.toString()}</label><br/>
                        </c:forEach>
                    </td>
                </tr>
                <tr style="vertical-align: top;">
                    <td>
                        Documents
                    </td>
                    <td>
                        <c:forEach items="${profile.documents}" var="v" >
                            <label>${v.toString()}</label><br/>
                        </c:forEach>
                    </td>
                </tr>
                <tr style="vertical-align: top;">
                    <td>
                        Human note
                    </td>
                    <td>
                        <label>${profile.human.note}&nbsp;</label>
                    </td>
                </tr>
                <tr style="vertical-align: top;">
                    <td>
                        Account Login
                    </td>
                    <td>
                        <label>${profile.account.login}&nbsp;</label>
                    </td>
                </tr>
                <tr style="vertical-align: top;">
                    <td>
                        Account note
                    </td>
                    <td>
                        <label>${profile.account.note}&nbsp;</label>
                    </td>
                </tr>
                <tr style="vertical-align: top;">
                    <td>
                        Roles
                    </td>
                    <td>
                        <c:forEach items="${profile.roles}" var="v" >
                            <label>${v.toString()}&nbsp;</label>
                        </c:forEach>
                    </td>
                </tr>
            </table>
        </fieldset>
        <div style="margin-top: 20px;">
            <a class="btn btn-default" href="${pageContext.request.contextPath}/home">&laquo; Go home</a>
            <input class="btn btn-success" type="submit" value= "Go job of role" style="padding-right: 20px;" />
        </div>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>
</body>
</html>
