<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 07.05.2017
  Time: 8:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
    <style>
        .cell-dotted {
            border: 1px dotted whitesmoke;
        }
        .font-normal {
            font-style: normal;
        }
    </style>
    <meta charset="UTF-8">
    <title>Schedule Find</title>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>

<div class="container-fluid">
    <form action="/scheduleFind" method="get">
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <input type="hidden" name="patientID" value="${patient.roleID}"/>
        <c:if test="${patient != null}">
            <fieldset class="form-group" valign="top" style="width: 100%; padding-top: 0px; padding-bottom: 0px;">
                <div>
                    <label style="font-weight: bold; font-size: xx-large">${patient.getNameText()}</label>
                </div>
            </fieldset>
        </c:if>
        <h3 style="margin-top: 0px; margin-bottom: 0px;">Find a doctor</h3>
        <div class="col-sm-12" style="width: 100%; padding: 0px;">
            <div class="navbar-form request-bar">
                <a class="btn btn-default" href="${pageContext.request.contextPath}/records?patientID=${patient.roleID}">&laquo; Go patient records review</a>
                <button class="btn btn-primary request-control" type="submit" data-toggle="tooltip" data-placement="auto" title="Request records in the schedule according to the conditions"><i class="material-icons" style="font-size: 20px; vertical-align: bottom;">sync</i>&nbsp;Query</button>
                <input class="form-control request-control" type="datetime-local" name="eventBegin" value="${eventBegin}" data-toggle="tooltip" data-placement="auto" title="Request records after"/>
                <input class="form-control request-control" type="datetime-local" name="eventEnd" value="${eventEnd}" data-toggle="tooltip" data-placement="auto" title="Request records before"/>
                <select class="form-control request-control" name="roleCategory" data-toggle="tooltip" data-placement="auto" title="Request records on category" style="padding-right: 20px;">
                    <c:forEach items="${roleCategoryList}" var="c">
                        <c:choose>
                            <c:when test="${c.toString() != 'People'}">
                                <option ${roleCategory == c ? "selected" : ""} value=${c}>${c}</option>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </select>
                <select class="form-control request-control" name="jobScope" data-toggle="tooltip" data-placement="auto" title="Request records on scope of job-list">
                    <c:forEach items="${jobScopeList}" var="c">
                        <option ${jobScope == c ? "selected" : ""} value=${c}>${c}</option>
                    </c:forEach>
                </select>
            </div>
            <table class="table table-hover table-condensed" width="100%">
                <thead>
                <tr class="bg-primary" style="border-bottom: 1px dotted whitesmoke;">
                    <th class="cell-dotted" style="width: 70px; text-align: center; vertical-align: middle" rowspan="2">Schedule ID</th>
                    <th class="cell-dotted" style="text-align: center;" colspan="4">Schedule</th>
                    <th class="cell-dotted" style="text-align: center;" colspan="5">Staff</th>
                    <th class="cell-dotted" style="text-align: center;" colspan="3">Place</th>
                    <th class="cell-dotted" style="width: 70px; text-align: center; vertical-align: middle" rowspan="2">Action</th>
                </tr>
                <tr class="bg-primary" style="border-bottom: 1px dotted whitesmoke;">
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

                    <%--<th class="cell-dotted font-normal" style="width: 70px; text-align: center;">&naturals;</th>--%>
                </tr>
                </thead>
                <c:forEach items="${scheduleInfos}" var="row" >
                    <tr>
                        <td style="text-align: center;">${row.id}</td>
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
                            <button class="btn btn-link btn-sm table-button" type="button" data-toggle="tooltip" data-placement="auto" title="Accept Schedule" onclick="scheduleAcceptRequest('scheduleAccept?scheduleID=${row.id}&patientID=${patient.roleID}');"><span class="glyphicon glyphicon-thumbs-up"/></button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
</div>
<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>

<script>
    function scheduleAcceptRequest(requestBody) {
        BootstrapDialog.confirm({
            title: 'Confirm the action with the schedule ...',
            message: "Continue this operation?",
            type: BootstrapDialog.TYPE_PRIMARY, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
            closable: true, // <-- Default value is false
            draggable: true, // <-- Default value is false
            btnCancelLabel: 'Cancel', // <-- Default value is 'Cancel',
            btnOKLabel: 'Confirm', // <-- Default value is 'OK',
            btnOKClass: 'btn-default', // <-- If you didn't specify it, dialog type will be used,
            callback: function(result){
                if(result) {
                    var request = new XMLHttpRequest();
                    var context = '${pageContext.request.contextPath}';
                    if (context == 'NaN')
                        context = '';
                    request.open("GET", context + '/' + requestBody, true);
                    request.addEventListener("load", function() {
                        console.log(request.responseText);
                        if (request.responseText != ""){
                            BootstrapDialog.alert({
                                title: 'WARNING',
                                message: request.responseText,
                                type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
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
