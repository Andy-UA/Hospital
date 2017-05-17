<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 24.04.2017
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
    <title>Treatment detail</title>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form id="recordsPost" action="/treatmentManage" method="post">
        <h2 style="margin-top: 0px; margin-bottom: 0px;">Treatment detail <label class="recordid-label"><c:choose><c:when test="${treatmentID == 0}">(new treatment)</c:when><c:otherwise>(treatment ID: ${treatmentID})</c:otherwise></c:choose></label></h2>
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <input type="hidden" name="treatmentID" value="${treatmentID}"/>
        <input type="hidden" name="detailID" value="${detailID}"/>
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
                                        <label style="font-weight: bold;">${staff.firstName} ${staff.lastName}</label>
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
                            <legend style="margin-bottom: 0px;">Patient Record Detail Info</legend>
                            <table border="0" width="100%">
                                <tr>
                                    <td width="300px">
                                        Appointment
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${appointment.toString()}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Total Amount
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${detail.amount}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Status
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${detail.status}</label>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Detail notes
                                    </td>
                                    <td>
                                        <label style="font-weight: bold;">${detail.note}</label>
                                    </td>
                                </tr>
                            </table>
                        </fieldset>
                        <fieldset class="form-group" valign="top" style="width: 100%; margin-bottom: 0px;">
                            <legend style="margin-bottom: 5px;">Treatment Detail Info</legend>
                            <table border="0" width="100%">
                                <tr>
                                    <td width="300px">
                                        Date & Time
                                    </td>
                                    <td>
                                        <input class="form-control" type="datetime-local" name="eventDate" value="${eventDate}"/>
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
                                        <c:forEach items="${treatmentStatus}" var="s">
                                            <option ${status == s ? "selected" : ""} value=${s.toString()}>${s.toString()}</option>
                                        </c:forEach>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Treatment notes
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
            <a class="btn btn-default" href="${pageContext.request.contextPath}/recordDetailsManage?detailID=${detail.id}&recordID=${record.id}">&laquo; Go Patient Record Detail</a>
            <button class="btn btn-success" type="submit">Store Treatment Detail</button>
        </div>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
