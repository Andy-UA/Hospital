<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 19.04.2017
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
    <title>Patient Records</title>
</head>
<body>
<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form action="/records" method="get">
        <h2 style="margin-top: 0px; margin-bottom: 0px;">Patient Records <label class="recordid-label"><c:choose><c:when test="${recordID == 0}">(new patient)</c:when><c:otherwise>(patient ID: ${patient.roleID})</c:otherwise></c:choose></label></h2>
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <input type="hidden" name="patientID" value="${patient.roleID}"/>
        <input type="hidden" name="scheduleID" value="${schedule != null ? schedule.id : 0}"/>
        <div class="form-group">
            <table border="0" width="100%" cellspacing="0" cellpadding="0" style="height: 363px;">
                <tr>
                    <td valign="top" width="85%" style="min-width: 900px;">
                        <fieldset class="form-group" valign="top" style="width: 100%; padding-top: 0px; padding-bottom: 0px;">
                            <div>
                                <label style="font-weight: bold; font-size: xx-large">${patient.getNameText()}</label>
                                <br>
                                <label style="font-weight: bold; font-size: large; color: #265a88;">gender: ${patient.sex}, birthday: ${patient.birthday}</label>
                                <br>
                                <label style="font-style: italic; font-size: medium; color: #444444;">${patient.roleNote} ${patient.humanNote}</label>
                            </div>
                        </fieldset>

                        <ul class="nav nav-tabs">
                            <li role="presentation" <c:if test="${section == 0}">class="active"</c:if> >
                                <button class="btn btn-link <c:if test="${section == 0}">btn-primary</c:if>" style="<c:if test="${section == 0}">color: whitesmoke</c:if>" type="submit" name="section" value="0">Schedule</button>
                            </li>
                            <li role="presentation" <c:if test="${section == 1}">class="active"</c:if> >
                                <button class="btn btn-link <c:if test="${section == 1}">btn-primary</c:if>" style="<c:if test="${section == 1}">color: whitesmoke</c:if>" type="submit" name="section" value="1">Records</button>
                            </li>
                        </ul>

                        <div class="navbar-form" style="padding-left: 0px; padding-right: 0px; margin-right: 0px; width: 100%;">
                            <input class="form-control" type="datetime-local" name="eventBegin" value="${eventBegin}" style="margin-right: 10px;"/>
                            <input class="form-control" type="datetime-local" name="eventEnd" value="${eventEnd}" style="margin-right: 10px;"/>
                            &nbsp;&nbsp;&nbsp;
                            schedule status
                            &nbsp;
                            <select class="form-control request-control" name="scheduleStatus" data-toggle="tooltip" data-placement="auto" title="Request schedule records on Status ...">
                                <c:forEach items="${scheduleStatusList}" var="c">
                                    <option ${scheduleStatus == c ? "selected" : ""} value=${c}>${c}</option>
                                </c:forEach>
                            </select>

                            <a class="btn btn-default" style="float: right; margin-left: 30px;" href="${pageContext.request.contextPath}/recordsPrint?patientID=${patient.roleID}&section=${section}"/><span class="glyphicon glyphicon-print"/>&nbsp;Print</a>
                            <div class="col-sm-1" style="float: right;">
                            <c:choose>
                                <c:when test="${schedule != null}">
                                    <a class="btn btn-success" href="${pageContext.request.contextPath}/recordManage?patientID=${patient.roleID}&scheduleID=${schedule.id}"><span class="glyphicon glyphicon-plus"/>&nbsp;New Record</a>
                                </c:when>
                                <c:when test="${schedule == null && section == 0}">
                                    <a class="btn btn-success" href="${pageContext.request.contextPath}/scheduleFind?patientID=${patient.roleID}"><span class="glyphicon glyphicon-search"/>&nbsp;Find Schedule</a>
                                </c:when>
                            </c:choose>
                            </div>
                        </div>

                        <c:if test="${section == 0}">
                            <fieldset class="form-group" valign="top" style="width: 100%; padding-top: 0px; padding-bottom: 0px;">
                                <table class="table table-bordered table-condensed" width="100%" border="1" style="border: 1px solid silver;">
                                    <tr class="bg-primary" style="border-bottom: 1px dotted whitesmoke; font-size: x-small;">
                                        <th class="cell-dotted" style="width: 70px; text-align: center; vertical-align: middle" rowspan="2">Schedule ID</th>
                                        <th class="cell-dotted" style="text-align: center;" colspan="4">Schedule</th>
                                        <th class="cell-dotted" style="text-align: center;" colspan="5">Staff</th>
                                        <th class="cell-dotted" style="text-align: center;" colspan="3">Place</th>
                                        <th class="cell-dotted" style="width: 70px; text-align: center; vertical-align: middle" rowspan="2">Action</th>
                                    </tr>
                                    <tr class="bg-primary" style="border-bottom: 1px dotted whitesmoke; font-size: x-small;">
                                        <th class="cell-dotted font-normal" style="width: 150px; text-align: center;">Event begin</th>
                                        <th class="cell-dotted font-normal" style="width: 150px; text-align: center;">Event end</th>
                                        <th class="cell-dotted font-normal" style="width: 70px; text-align: center;">Status</th>
                                        <th class="cell-dotted font-normal">Note</th>


                                        <th class="cell-dotted font-normal" class="cell-dotted" style="width: 90px; text-align: center;">Category</th>
                                        <th class="cell-dotted font-normal" style="width: 200px ">Name</th>
                                        <th class="cell-dotted font-normal" style="max-width: 200px;">Note</th>
                                        <th class="cell-dotted font-normal" style="width: 90px; text-align: center;">Gender</th>
                                        <th class="cell-dotted font-normal" style="width: 90px; text-align: center;">Birthday</th>

                                        <th class="cell-dotted font-normal" style="width: 60px; text-align: center;">Building</th>
                                        <th class="cell-dotted font-normal" style="width: 60px; text-align: center;">Floor</th>
                                        <th class="cell-dotted font-normal" style="width: 60px; text-align: center;">Room</th>
                                    </tr>
                                    <c:forEach items="${scheduleRecords}" var="row" >
                                        <tr>
                                            <td style="text-align: center;"><a href="${pageContext.request.contextPath}/scheduleManage?scheduleID=${row.id}">${row.id}</a></td>
                                            <td style="text-align: center;">${row.getEventBeginText()}</td>
                                            <td style="text-align: center;">${row.getEventEndText()}</td>
                                            <td style="text-align: center;">${row.status}</td>
                                            <td>${row.scheduleNote}</td>

                                            <td style="text-align: center; border-left: 1px dotted slategrey;">${row.staffCategory}</td>
                                            <td>${row.staffName}</td>
                                            <td>${row.staffNote}</td>
                                            <td style="text-align: center;">${row.staffSex}</td>
                                            <td style="text-align: center;">${row.staffBirthday}</td>

                                            <td style="text-align: center; border-left: 1px dotted slategrey;">${row.building}</td>
                                            <td style="text-align: center;">${row.floor}</td>
                                            <td style="text-align: center;">${row.room}</td>

                                            <td style="text-align: center; border-left: 1px dotted slategrey;">
                                                <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Clear Schedule" onclick="scheduleCancelRequest('scheduleAccept?scheduleID=${row.id}&patientID=0');"><span class="glyphicon glyphicon-remove"/></button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </fieldset>
                        </c:if>
                        <c:if test="${section == 1}">
                            <fieldset class="form-group" valign="top" style="width: 100%; padding-top: 0px; padding-bottom: 0px;">
                                <c:forEach items="${patientRecords}" var="row" >
                                    <div class="col-md-12" style="width: 100%; padding: 0px; margin: 0px 0px 10px 0px;">
                                        <div class="col-md-12 request-bar" style="padding: 0px; margin: 0px; height: 23px; width: 100%; border-top: 1px solid silver; border-left: 1px solid silver; border-right: 1px solid silver; background-color: #337ab7; color: whitesmoke;">
                                            <div class="col-md-1" style="float: right; margin: 0px; padding: 0px; height: 23px; background-color: #eea236; text-align: center;">
                                            <c:choose>
                                                <c:when test="${schedule != null}">
                                                    <a class="btn btn-sm btn-link table-button" href="${pageContext.request.contextPath}/recordManage?patientID=${patient.roleID}&scheduleID=${schedule.id}&recordID=${row.id}" data-toggle="tooltip" data-placement="auto" title="Manage Patient Record"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span>&nbsp;Edit&nbsp;</a>
                                                    <button class="btn btn-sm btn-link table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Record Detail" onclick="deleteRequest('recordDelete?recordID=${row.id}');"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;Clear&nbsp;</button>
                                                </c:when>
                                                <c:otherwise>
                                                    (preview only)
                                                </c:otherwise>
                                            </c:choose>
                                            </div>
                                            <div class="col-md-11" style="padding-left: 1em; background-color: transparent; vertical-align: text-bottom">
                                                <label>record #${row.id}&nbsp;</label>
                                                <label>${row.getEventDateText()}&nbsp;</label>
                                                &nbsp;&nbsp;diagnosis:&nbsp;
                                                <label>${row.diagnosisCode}&nbsp;</label>
                                                <label>${row.diagnosisName}&nbsp;</label>
                                                &nbsp;&nbsp;notes:&nbsp;
                                                <label>${row.recordNote}&nbsp;</label>
                                            </div>
                                        </div>
                                        <div style="width: 100%">
                                            <table class="table table-bordered table-condensed" width="100%" border="1" style="border: 1px solid silver;">
                                                <tr style="background-color: whitesmoke; color: gray; font-size: x-small;">
                                                    <th style="width: 300px; text-align: left;">Appointment Description</th>
                                                    <th style="width: 200px; text-align: left;">Appointment Notes</th>
                                                    <th style="width: 70px; text-align: center;">Type</th>
                                                    <th style="width: 70px; text-align: center;">Unit</th>
                                                    <th style="width: 70px; text-align: center;">Amount</th>
                                                    <th style="width: 90px; text-align: center;">Status</th>
                                                    <th style="width: 150px; text-align: left;">Record Detail Notes</th>
                                                    <th style="width: 70px; text-align: center;">Actions</th>
                                                </tr>
                                                <c:forEach items="${row.details}" var="detail" >
                                                    <tr style="background-color: mintcream; color: slategray">
                                                        <td style="width: 150px; text-align: left;">${detail.appointmentDescription}</td>
                                                        <td style="width: 150px; text-align: left;">${detail.appointmentNote}</td>
                                                        <td style="width: 70px; text-align: center;">${detail.appointmentType}</td>
                                                        <td style="width: 70px; text-align: center;">${detail.appointmentUnit}</td>
                                                        <td style="width: 90px; text-align: center;">${detail.amount}</td>
                                                        <td style="width: 90px; text-align: center;">${detail.status}</td>
                                                        <td style="width: 150px; text-align: left;">${detail.detailNote}</td>
                                                        <td style="text-align: center">
                                                            <a href="${pageContext.request.contextPath}/recordDetailsManage?recordID=${detail.recordID}&detailID=${detail.id}" data-toggle="tooltip" data-placement="auto" title="Manage Record Detail"><i class="material-icons table-icon">border_color</i></a>
                                                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Record Detail" onclick="deleteRequest('recordDetailsDelete?recordID=${detail.recordID}&detailID=${detail.id}');"><i class="material-icons table-icon">cancel</i></button>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </div>
                                    </div>
                                </c:forEach>
                            </fieldset>
                        </c:if>
                    </td>
                    <td>

                    </td>
                    <td valign="top" width="250px">
                        <fieldset style="height: 333px;">
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
        <div class="navbar-form" style="padding-left: 0px; margin-top: 5px; margin-bottom: 2px;" ${schedule == null ? "hidden" : ""}>
            <a class="btn btn-default" href="${pageContext.request.contextPath}/scheduleAdmin?staffID=${schedule.staffID}">&laquo; Go Schedule Admin</a>
        </div>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>

<script>
    function scheduleCancelRequest(requestBody) {
        var query = '${pageContext.request.contextPath}';
        if (query == 'NaN' || query == null)
            query = '';
        query = query + '/' + requestBody;
        BootstrapDialog.confirm( {
            title: 'Confirm the action with the schedule ...',
            message: "Continue this operation?",
            type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            btnCancelLabel: 'Cancel', // <-- Default value is 'Cancel',
            btnOKLabel: 'Confirm', // <-- Default value is 'OK',
            btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
            callback: function(result){
                if(result) {
                    var request = new XMLHttpRequest();
                    request.open("GET", query, true);
                    request.setRequestHeader('Content-Type', 'text/html;charset=utf-8');
                    request.addEventListener("load", function() {
                        console.log(request.getAllResponseHeaders());
                        console.log(request.responseText);
                        if (request.responseText != ""){
                            BootstrapDialog.alert({
                                title: 'WARNING',
                                message: request.responseText,
                                type: BootstrapDialog.TYPE_PRIMARY,
                                closable: true, // <-- Default value is false
                                draggable: true, // <-- Default value is false
                                buttonLabel: 'Ok', // <-- Default value is 'OK',
                                callback: function(result) {
                                    // result will be true if button was click, while it will be false if users close the dialog directly.
                                }
                            });
                        } else {
                            window.location.reload();
                        }
                    });
                    request.send(null);
                }
            }
        });
    };
</script>

</html>
