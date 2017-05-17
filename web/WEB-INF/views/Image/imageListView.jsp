<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 23.04.2017
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Image List</title>
    <jsp:include page="../main/_bootstrap.jsp"></jsp:include>
</head>
<body>

<jsp:include page="../main/_header.jsp"></jsp:include>
<jsp:include page="../main/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <form action="/imageList" method="get">
        <label style="color: red;" ${errorString == null ? "hidden" : ""}>error:${errorString}</label>
        <div class="navbar-form" style="padding-left: 0px;">
            <button class="btn btn-primary" type="submit" style="margin-right: 20px;"><i class="material-icons" style="font-size: 20px; vertical-align: bottom;">sync</i>&nbsp;Images</button>
            <label>content type</label>
            <input class="form-control" type="text" name="contentType" value="${contentType}" placeholder="content type ..." style="margin-right: 20px;"/>
            <label>human ID</label>
            <input class="form-control" type="numeric" name="humanID" value="${humanID}" step="1" placeholder="human ID ..." style="width: 5em; margin-right: 20px;"/>
            <label>scope</label>
            <select class="form-control" name="scope" style="margin-right: 20px;">
                <c:forEach items="${imageScope}" var="c">
                    <option ${scope == c ? "selected" : ""} value=${c.toString()}>${c.toString()}</option>
                </c:forEach>
            </select>
        </div>
        <table class="table table-striped" width="100%">
            <thead>
                <tr class="bg-primary">
                    <th style="text-align: center; width: 70px;">ID</th>
                    <th style="text-align: center; width: 100px;">Human ID</th>
                    <th style="text-align: center; width: 70px;">Enabled</th>
                    <th style="text-align: center; width: 70px;">Scope</th>
                    <th style="text-align: center; width: 170px;">Content Type</th>
                    <th>Note</th>
                    <th style="width: 67px;">Image</th>
                    <th style="width: 100px; padding-left: 5px;">Actions</th>
                </tr>
            </thead>
            <c:forEach items="${images}" var="image" >
                <tr>
                    <td style="text-align: center;">${image.id}</td>
                    <td style="text-align: center;">${image.humanID}</td>
                    <td style="text-align: center;">${image.enabled}</td>
                    <td style="text-align: center;">${image.scope}</td>
                    <td style="text-align: center;">${image.contentType}</td>
                    <td>${image.note}</td>
                    <td style="height: 100px;"><div style="padding: 5px;"><img src="/image?id=${image.id}" alt="${image.note}" style="width: 150px; height: 150px; alignment: center;"></div></td>
                    <td colspan="2" style="padding-left: 5px;">
                        <a href="${pageContext.request.contextPath}/imageEdit?id=${image.id}&humanID=${humanID}&contentType=${contentType}&scope=${scope}"><i class="material-icons" style="font-size: 20px">border_color</i></a>
                        <button class="btn btn-link btn-sm" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Image" onclick="deleteRequest('imageDelete?id=${image.id}');"><i class="material-icons" style="font-size: 20px">cancel</i></button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p/>
        <div class="row">
            <c:forEach items="${images}" var="image" >
            <div class="col-sm-2 col-md-2">
                <div class="thumbnail">
                    <img src="/image?id=${image.id}" alt="${image.note}">
                    <div class="caption">
                        <p>${image.note}</p>
                        <table class="table table-condensed" style="font-size: x-small;">
                            <tr>
                                <td width="100px">Row ID</td>
                                <td>${image.id}</td>
                            </tr>
                            <tr>
                                <td>Human ID</td>
                                <td>${image.humanID}</td>
                            </tr>
                            <tr>
                                <td>Enabled</td>
                                <td>${image.enabled}</td>
                            </tr>
                            <tr>
                                <td>Scope</td>
                                <td>${image.scope}</td>
                            </tr>
                            <tr>
                                <td>Content Type</td>
                                <td>${image.contentType}</td>
                            </tr>
                        </table>
                        <div style="border-top: 1px solid silver;">
                            <a href="${pageContext.request.contextPath}/imageEdit?id=${image.id}&humanID=${humanID}&contentType=${contentType}&scope=${scope}"><i class="material-icons" style="font-size: 15px;">border_color</i></a>
                            <button class="btn btn-link btn-sm" type="button" data-toggle="tooltip" data-placement="auto" title="Delete Image" onclick="deleteRequest('imageDelete?id=${image.id}');"><i class="material-icons" style="font-size:  15px;">cancel</i></button>
                        </div>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </form>
</div>

<jsp:include page="../main/_footer.jsp"></jsp:include>

</body>
</html>
