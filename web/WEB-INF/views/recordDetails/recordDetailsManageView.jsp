<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 22.04.2017
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
    <title>Patient Record Detail</title>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form id="recordsPost" action="/recordDetailsManage" method="post">
        <h2 style="margin-top: 0px; margin-bottom: 0px;">Patient Record Detail <label class="recordid-label"><c:choose><c:when test="${detailID == 0}">(new detail)</c:when><c:otherwise>(detail ID: ${detailID})</c:otherwise></c:choose></label></h2>
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <input type="hidden" name="detailID" value="${detailID}"/>
        <input type="hidden" name="patientID" value="${patient.roleID}"/>
        <input type="hidden" name="scheduleID" value="${schedule.id}"/>
        <input type="hidden" name="recordID" value="${recordID}"/>
        <div class="form-group" style="margin-top: 0px;">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="height: 350px;">
                <tr>
                    <td valign="top" width="80%">
                        <fieldset class="form-group" valign="top" style="width: 100%; margin-bottom: 0px; padding-top: 0px;">
                            <table border="0" width="100%">
                                <tr>
                                    <td colspan="2">
                                        <label style="font-weight: bold; font-size: xx-large">${patient.getNameText()}</label>
                                        <br>
                                        <label style="font-weight: bold; font-size: large; color: #265a88;">gender: ${patient.sex}, birthday: ${patient.birthday}</label>
                                        <br>
                                        <label style="font-style: italic; font-size: medium; color: #444444;">${patient.roleNote} ${patient.humanNote}</label>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset class="form-group" valign="top" style="width: 100%; margin-bottom: 0px;">
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
                        <fieldset class="form-group" valign="top" style="width: 100%; margin-bottom: 0px;">
                            <legend style="margin-bottom: 0px;">Schedule Info</legend>
                            <table border="0" width="100%">
                                <tr>
                                    <td width="300px">
                                        Appointment location
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${workplace.toString()}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Appointment hours
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${schedule.getEventBeginText()} to ${schedule.getEventEndText()}</label>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset class="form-group" valign="top" style="width: 100%; margin-bottom: 0px;">
                            <legend style="margin-bottom: 0px;">Patient Record Info</legend>
                            <table border="0" width="100%">
                                <tr>
                                    <td width="300px">
                                        Date and Time
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${record.getEventDateText()}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Diagnosis
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${record.diagnosisCode} ${record.diagnosisName}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Note
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${record.recordNote}</label>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset class="form-group" valign="top" style="width: 100%; margin-bottom: 0px;">
                            <legend style="margin-bottom: 5px;">Patient Record Detail Info</legend>
                            <table border="0" width="100%">
                                <tr>
                                    <td width="300px">
                                        Appointment
                                    </td>
                                    <td>
                                        <select class="form-control" name="appointmentID">
                                            <c:forEach items="${appointmentsList}" var="appointment">
                                                <option ${appointmentID == appointment.id ? "selected" : ""} value=${appointment.id}>${appointment.description}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Amount
                                    </td>
                                    <td>
                                        <input class="form-control" type="number" name="amount" step="0.01" min="0.00" max="9999.00" value="${amount}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Status
                                    </td>
                                    <td>
                                        <select class="form-control" name="status" />
                                        <c:forEach items="${recordDetailStatus}" var="s">
                                            <option ${status == s ? "selected" : ""} value=${s.toString()}>${s.toString()}</option>
                                        </c:forEach>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Detail notes
                                    </td>
                                    <td>
                                        <input class="form-control" type="text" name="note" value="${note}"/>
                                    </td>
                                </tr>
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
        <div class="navbar-form" style="padding-left: 0px; margin-top: 5px; margin-bottom: 2px;">
            <a class="btn btn-default" href="${pageContext.request.contextPath}/recordManage?recordID=${recordID}">&laquo; Go patient record manage</a>
            <button class="btn btn-success" type="submit">Store Record Detail</button>
        </div>
        <fieldset valign="top" style="width: 100%; margin-bottom: 0px;">
            <legend style="margin-bottom: 0px;">Treatments</legend>
            <div class="navbar-form" style="padding-left: 0px; padding-right: 0px;">
                <input class="btn btn-default" type="submit" value="Query records"/>
                <c:choose>
                    <c:when test="${detailID != 0}">
                        <a class="btn btn-success" style="float: right; margin-left: 5px;" href="${pageContext.request.contextPath}/treatmentManage?detailID=${detailID}&treatmentID=0" data-toggle="tooltip" data-placement="auto" title="Append New Treatment"><span class="glyphicon glyphicon-plus"/>&nbsp;New Treatment</a>
                    </c:when>
                </c:choose>
                <a class="btn btn-default" style="float: right; margin-left: 5px;" href="${pageContext.request.contextPath}/treatmentPrint?detailID=${detailID}" data-toggle="tooltip" data-placement="auto" title="Print Treatments"><span class="glyphicon glyphicon-print"/>&nbsp;Print</a>
            </div>
            <table class="table table-striped table-hover table-condensed" width="100%">
                <thead>
                <tr class="bg-primary">
                    <th style="width: 60px; text-align: center;">ID</th>
                    <th style="width: 120px; text-align: center;">Date & Time</th>
                    <th style="width: 90px; text-align: center;">Amount</th>
                    <th style="width: 90px; text-align: center;">Status</th>
                    <th style="width: 150px; text-align: left;">Notes</th>
                    <th style="width: 75px; text-align: center;">Actions</th>
                </tr>
                </thead>
                <c:forEach items="${treatmentList}" var="row" >
                    <tr>
                        <td style="text-align: center;">${row.id}</td>
                        <td style="text-align: center;">${row.getEventDateText()}</td>
                        <td style="width: 90px; text-align: center;">${row.amount}</td>
                        <td style="width: 90px; text-align: center;">${row.status}</td>
                        <td style="width: 150px; text-align: left;">${row.note}</td>
                        <td style="text-align: center;">
                            <a href="${pageContext.request.contextPath}/treatmentManage?detailID=${detailID}&treatmentID=${row.id}" data-toggle="tooltip" data-placement="auto" title="Manage Treatment"><i class="material-icons table-icon">border_color</i></a>
                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Treatment" onclick="deleteRequest('treatmentDelete?detailID=${detailID}&treatmentID=${row.id}');"><i class="material-icons table-icon">cancel</i></button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
