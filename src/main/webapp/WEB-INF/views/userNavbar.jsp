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
            <li role="presentation" class="${selectedTab == 'profile-tab' ? 'active' : '' }">
                <a href="${contextPath}/user/profile">User profile</a>
            </li>
            <li role="presentation" class="${selectedTab == 'ticket-tab' ? 'active' : '' }">
                <a href="${contextPath}/user/tickets">Tickets</a>
            </li>
        </ul>
    </nav>
</div>
