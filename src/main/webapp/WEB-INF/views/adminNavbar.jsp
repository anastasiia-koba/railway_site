<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/adminNavbar.css">

<div class="sidenav">
    <nav>
        <ul class="nav nav-pills nav-stacked">
            <li role="presentation" class="${selectedTab == 'station-tab' ? 'active' : '' }">
                <a href="${contextPath}/admin/stations">Stations</a>
            </li>
            <li role="presentation" class="${selectedTab == 'rout-tab' ? 'active' : '' }">
                <a href="${contextPath}/admin/routs">Routs</a>
            </li>
            <li role="presentation" class="${selectedTab == 'section-tab' ? 'active' : '' }">
                <a href="${contextPath}/admin/sections">Sections in routs</a>
            </li>
            <li role="presentation" class="${selectedTab == 'train-tab' ? 'active' : '' }">
                <a href="${contextPath}/admin/trains">Trains</a>
            </li>
            <li role="presentation" class="${selectedTab == 'finalrout-tab' ? 'active' : '' }">
                <a href="${contextPath}/admin/finalrouts">Train's rout</a>
            </li>
        </ul>
    </nav>
</div>