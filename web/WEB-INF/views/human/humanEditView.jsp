<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 12.04.2017
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Human</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form method="POST" action="humanEdit">
    <input type="hidden" name="id" value="${human.id}"/>
    <div class="form-group">
        <table border="0" width="100%" cellspacing="0" cellpadding="0" style="height: 350px;">
            <tr>
                <td valign="top" width="60%">
                    <fieldset valign="top">
                        <legend>Personal info</legend>
                        <table border="0" width="100%">
                            <tr>
                                <td width="150px">
                                    Human ID
                                </td>
                                <td>
                                    <label style="font-weight: bold;"><c:choose><c:when test="${human.id == 0}">(new Human)</c:when><c:otherwise>${human.id}</c:otherwise></c:choose></label>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    First Name
                                </td>
                                <td>
                                    <input class="form-control" type="text" width="100%" name="firstName" value="${human.firstName}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Last Name
                                </td>
                                <td>
                                    <input class="form-control" type="text" width="100%" name="lastName" value="${human.lastName}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Birthday
                                </td>
                                <td>
                                    <input class="form-control" type="date" width="100%" name="birthday" value="${human.birthday}" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Gender
                                </td>
                                <td>
                                    <select class="form-control" name="sex" width="100%">
                                        <option ${human.sex == "M" ? "selected" : ""} value="M">Man</option>
                                        <option ${human.sex == "W" ? "selected" : ""} value="W">Woman</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Note
                                </td>
                                <td>
                                    <input class="form-control" type="text" width="100%" name="note" value="${human.note}" />
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
                                    <input class="checkbox" style="width: 22px" type="checkbox" name="enabled" value="Y" ${human.enabled ? "checked" : ""} />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <label style="color: red;">${errorString}&nbsp;</label>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <a class="btn btn-default" href="${pageContext.request.contextPath}/humanList">Go humans list ...</a>
                                    <input class="btn btn-success" type="submit" value="Submit" />
                                    <div style="float: right">
                                        <c:choose><c:when test="${human.id != 0}"><a class="btn btn-default" style="float: right;" href="${pageContext.request.contextPath}/imageLoad?humanID=${human.id}&scope=Photo">Load photo ...</a></c:when></c:choose>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
                <td>

                </td>
                <td valign="top" width="180px">
                    <fieldset style="height: 227px;">
                        <legend>Photo</legend>
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width: 100%; height: 100%;">
                            <!-- Indicators -->
                            <ol class="carousel-indicators">
                                <c:forEach items="${images}" var="image" >
                                    <li data-target="#carousel-example-generic" data-slide-to="${image.index}" ${image.index == 0 ? "class=\"active\"" : ""}></li>
                                </c:forEach>
                            </ol>

                            <!-- Wrapper for slides -->
                            <div class="carousel-inner" role="listbox">
                                <c:forEach items="${images}" var="image">
                                    <div class="item ${image.index == 0 ? "active" : ""}">
                                        <img src="/image?id=${image.id}" alt="${image.note}" style="vertical-align: middle;">
                                        <div class="carousel-caption">
                                            ${image.note}
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- Controls -->
                            <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    </fieldset>
                </td>
            </tr>
        </table>
    </div>
    <c:choose>
        <c:when test="${human.id > 0}">
            <div class="form-group">
                <table border="0" width="100%" style="height: 200px;">
                    <td width="40%" valign="top">
                        <fieldset valign="top" style="height: 190px; padding-right: 15px;">
                            <legend>Locations</legend>
                            <div class="nav-divider" style="padding-bottom: 3px;">
                                <a class="btn btn-success btn-sm" href="${pageContext.request.contextPath}/locationEdit?id=0&humanID=${human.id}" data-toggle="tooltip" data-placement="auto" title="New Location"><i class="material-icons" style="font-size: 15px; vertical-align: text-bottom;">add</i>create</a>
                            </div>
                            <table class="table table-striped table-condensed" width="100%">
                                <thead>
                                    <tr class="bg-primary">
                                        <th>Type</th>
                                        <th>Index</th>
                                        <th>Country</th>
                                        <th>State</th>
                                        <th>Area</th>
                                        <th>City</th>
                                        <th>Street</th>
                                        <th>House</th>
                                        <th>Apartment</th>
                                        <th width="60px"></th>
                                    </tr>
                                </thead>
                                <c:forEach items="${locations}" var="location" >
                                    <tr>
                                        <td>${location.type}</td>
                                        <td>${location.postIndex}</td>
                                        <td>${location.country}</td>
                                        <td>${location.state}</td>
                                        <td>${location.area}</td>
                                        <td>${location.city}</td>
                                        <td>${location.street}</td>
                                        <td>${location.house}</td>
                                        <td>${location.apartment}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/locationEdit?id=${location.id}" data-toggle="tooltip" data-placement="auto" title="Edit"><i class="material-icons table-icon">border_color</i></a>
                                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete" onclick="deleteRequest('locationDelete?id=${location.id}');"><i class="material-icons  table-icon">cancel</i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                    </td>
                    <td width="15%" valign="top">
                        <fieldset valign="top" style="height: 190px; padding-right: 15px;">
                            <legend>Contacts</legend>
                            <div class="nav-divider" style="padding-bottom: 3px;">
                                <a class="btn btn-success btn-sm" href="${pageContext.request.contextPath}/contactEdit?id=0&humanID=${human.id}" data-toggle="tooltip" data-placement="auto" title="New Contact"><i class="material-icons" style="font-size: 15px; vertical-align: text-bottom;">add</i>create</a>
                            </div>
                            <table class="table table-striped table-condensed" width="100%">
                                <thead>
                                    <tr class="bg-primary">
                                        <th width="80px">Type</th>
                                        <th>Value</th>
                                        <th width="60px"></th>
                                    </tr>
                                </thead>
                                <c:forEach items="${contacts}" var="contact" >
                                    <tr>
                                        <td>${contact.type}</td>
                                        <td>${contact.value}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/contactEdit?id=${contact.id}" data-toggle="tooltip" data-placement="auto" title="Edit"><i class="material-icons table-icon" >border_color</i></a>
                                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete" onclick="deleteRequest('contactDelete?id=${contact.id}');"><i class="material-icons  table-icon">cancel</i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                    </td>
                    <td width="15%" valign="top">
                        <fieldset valign="top" style="height: 190px; padding-right: 15px;">
                            <legend>Documents</legend>
                            <div class="nav-divider" style="padding-bottom: 3px;">
                                <a class="btn btn-success btn-sm" href="${pageContext.request.contextPath}/documentEdit?id=0&humanID=${human.id}" data-toggle="tooltip" data-placement="auto" title="New Document"><i class="material-icons" style="font-size: 15px; vertical-align: text-bottom;">add</i>create</a>
                            </div>
                            <table class="table table-striped table-condensed" width="100%">
                                <thead>
                                    <tr class="bg-primary">
                                        <th width="80px">Type</th>
                                        <th>Number</th>
                                        <th width="60px"></th>
                                    </tr>
                                </thead>
                                <c:forEach items="${documents}" var="document" >
                                    <tr>
                                        <td>${document.type}</td>
                                        <td>${document.value}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/documentEdit?id=${document.id}"><i class="material-icons table-icon">border_color</i></a>
                                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete" onclick="deleteRequest('documentDelete?id=${document.id}');"><i class="material-icons table-icon">cancel</i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                    </td>
                    <td width="15%" valign="top">
                        <fieldset valign="top" style="height: 190px; padding-right: 15px;">
                            <legend>Accounts</legend>
                            <div class="nav-divider" style="padding-bottom: 3px;">
                                <a class="btn btn-success btn-sm" href="${pageContext.request.contextPath}/accountEdit?id=0&humanID=${human.id}" data-toggle="tooltip" data-placement="auto" title="Edit"><i class="material-icons" style="font-size: 15px; vertical-align: text-bottom;">add</i>create</a>
                            </div>
                            <table class="table table-striped table-condensed" width="100%">
                                <thead>
                                    <tr class="bg-primary">
                                        <th>Login</th>
                                        <th width="50px">Status</th>
                                        <th width="60px"></th>
                                    </tr>
                                </thead>
                                <c:forEach items="${accounts}" var="account" >
                                    <tr>
                                        <td>${account.login}</td>
                                        <td>${account.enabled}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/accountEdit?id=${account.id}" data-toggle="tooltip" data-placement="auto" title="Edit"><i class="material-icons table-icon">border_color</i></a>
                                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete" onclick="deleteRequest('accountDelete?id=${account.id}');"><i class="material-icons table-icon">cancel</i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                    </td>
                    <td width="15%" valign="top">
                        <fieldset valign="top" style="height: 190px; padding-right: 0px;">
                            <legend>Roles</legend>
                            <div class="nav-divider" style="padding-bottom: 3px;">
                                <a class="btn btn-success btn-sm" href="${pageContext.request.contextPath}/roleEdit?id=0&humanID=${human.id}" data-toggle="tooltip" data-placement="auto" title="New role"><i class="material-icons" style="font-size: 15px; vertical-align: text-bottom;">add</i>create</a>
                            </div>
                            <table class="table table-striped table-condensed" width="100%">
                                <thead>
                                <tr class="bg-primary">
                                    <th width="80px">Scope</th>
                                    <th>Category</th>
                                    <th width="60px"></th>
                                </tr>
                                </thead>
                                <c:forEach items="${roles}" var="role" >
                                    <tr>
                                        <td>${role.scope}</td>
                                        <td>${role.category}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/roleEdit?id=${role.id}"><i class="material-icons table-icon" data-toggle="tooltip" data-placement="auto" title="Edit">border_color</i></a>
                                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete" onclick="deleteRequest('roleDelete?id=${role.id}');"><i class="material-icons table-icon">cancel</i></button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                    </td>
                </table>
            </div>
        </c:when>
    </c:choose>
</form>
</div>

<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
