<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Andrew
  Date: 10.04.2017
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../main/_bootstrap.jsp"></jsp:include>
<nav class="navbar navbar-inverse" style="margin-bottom: 4px;">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/home">Hospital</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <%--<li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>--%>
                <li><a href="${pageContext.request.contextPath}/patientList">Patients</a></li>
                <li><a href="${pageContext.request.contextPath}staffList">Doctors</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Base tables <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/diagnosisList">Diagnosis</a></li>
                        <li><a href="${pageContext.request.contextPath}/appointmentList">Appointments</a></li>
                        <li><a href="${pageContext.request.contextPath}/workplaceList">Workplaces</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/imageList">Images</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/humanList">Humans</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                        <c:choose>
                            <c:when test="${profile_ != null}">
                                <b>${profile_.human.getNameText().toUpperCase()}&nbsp;(${profile_.role.scope.toString().toUpperCase()})</b>
                            </c:when>
                            <c:otherwise>
                                Profile
                            </c:otherwise>
                        </c:choose>
                        <span class="caret"/>
                    </a>
                    <ul class="dropdown-menu">
                        <c:choose>
                            <c:when test="${account_ == null}">
                                <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                            </c:otherwise>
                        </c:choose>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">About</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>