<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 17.04.2017
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Schedule Admin</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<div class="container-fluid">
    <form action="/scheduleAdmin" method="get">
        <h3 style="margin-top: 0px; margin-bottom: 0px;">Schedule</h3>
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <input type="hidden" name="staffID" value="${staff.roleID}"/>
        <div class="form-group">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="height: 363px;">
                <tr>
                    <td valign="top" width="80%" style="min-width: 900px;">
                        <fieldset class="form-group" valign="top" style="width: 100%;">
                            <legend style="margin-bottom: 0px;">Doctor Info</legend>
                            <table border="0" width="100%">
                                <tr>
                                    <td width="300px">
                                        First / Last Name
                                    </td>
                                    <td>
                                        <label style="font-weight: bold; font-size: x-large;">${staff.firstName} ${staff.lastName}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Category
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${staff.category}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Notes
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${staff.roleNote} ${staff.humanNote}</label>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset valign="top" style="width: 100%;">
                            <legend style="margin-bottom: 0px;">Schedule rows</legend>
                            <div class="navbar-form" style="padding-left: 0px; padding-right: 0px;">
                                <input type="submit" class="btn btn-default" value="Query schedule rows" style="margin-right: 10px;"/>
                                <input class="form-control" type="datetime-local" name="eventBegin" value="${eventBegin}" style="margin-right: 10px;"/>
                                <input class="form-control" type="datetime-local" name="eventEnd" value="${eventEnd}" style="margin-right: 10px;"/>
                                <a class="btn btn-default" style="float: right; margin-left: 5px; width: 70px" href="${pageContext.request.contextPath}/scheduleClear?staffID=${staff.roleID}">Clear</a>
                                <a class="btn btn-default" style="float: right; margin-left: 5px; width: 70px;" href="${pageContext.request.contextPath}/scheduleBuild?staffID=${staff.roleID}">Build</a>
                            </div>

                            <table class="table table-hover table-condensed" width="100%">
                                <thead>
                                    <tr class="bg-primary">
                                        <th style="width: 60px; text-align: center;">ID</th>
                                        <th style="width: 60px; text-align: center;">Building</th>
                                        <th style="width: 60px; text-align: center;">Floor</th>
                                        <th style="width: 60px; text-align: center;">Room</th>
                                        <th style="width: 250px">Patient</th>
                                        <th style="width: 150px; text-align: center;">Event begin</th>
                                        <th style="width: 150px; text-align: center;">Event end</th>
                                        <th style="width: 70px; text-align: center;">Status</th>
                                        <th>Note</th>
                                        <th style="width: 90px; text-align: center;">Actions</th>
                                    </tr>
                                </thead>
                                <c:forEach items="${scheduleInfos}" var="row" >
                                    <tr class="<c:choose><c:when test="${row.patientID > 0}">bg-danger</c:when><c:otherwise>bg-warning</c:otherwise></c:choose>">
                                        <td style="text-align: center;">${row.id}</td>
                                        <td style="text-align: center;">${row.building}</td>
                                        <td style="text-align: center;">${row.floor}</td>
                                        <td style="text-align: center;">${row.room}</td>
                                        <td>${row.getPatientName()}</td>
                                        <td style="width: 120px; text-align: center;">${row.getEventBeginText()}</td>
                                        <td style="width: 120px; text-align: center;">${row.getEventEndText()}</td>
                                        <td style="width: 60px; text-align: center;">${row.status}</td>
                                        <td>${row.scheduleNote}</td>
                                        <td style="text-align: left; padding-left: 20px;" colspan="2">
                                            <a href="${pageContext.request.contextPath}/scheduleManage?scheduleID=${row.id}" data-toggle="tooltip" data-placement="auto" title="Manage Schedule record"><i class="material-icons table-icon">border_color</i></a>
                                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Schedule Detail" onclick="deleteRequest('scheduleDelete?scheduleID=${row.id}');"><i class="material-icons table-icon">cancel</i></button>
                                            <c:choose>
                                                <c:when test="${row.patientID > 0}">
                                                    <a href="${pageContext.request.contextPath}/records?patientID=${row.patientID}&scheduleID=${row.id}" data-toggle="tooltip" data-placement="auto" title="Manage Patient Records"><i class="material-icons table-icon">info</i></a>
                                                </c:when>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </fieldset>
                    </td>
                    <td>

                    </td>
                    <td valign="top" width="200px">
                        <fieldset style="height: 267px;">
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
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
