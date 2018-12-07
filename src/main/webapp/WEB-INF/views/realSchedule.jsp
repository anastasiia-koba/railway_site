<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <title>Schedule</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div id="top-filter" class="top-filter tfilter-box hidden-xs" data-spy="affix" data-offset-top="197">
    <div class="container">
        <form method="POST" action="${contextPath}/admin/schedule">
            <input type="submit" class="btn btn-primary site-btn" value="Send schedule">
        </form>
    </div>
</div>
</body>
</html>
