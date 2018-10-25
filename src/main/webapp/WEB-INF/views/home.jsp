<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <title>Railway Home</title>
</head>
<body>

<div class="container-fluid">
    <nav class="navbar navbar-default">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse-2">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            <   a class="navbar-brand" href="${contextPath}/home">Some railway</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="navbar-collapse-2">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">Home</a></li>
                    <li><a href="#">News</a></li>
                    <li><a href="#">Contact</a></li>
                    <li>
                        <a class="btn btn-default btn-outline btn-circle"  data-toggle="collapse" href="${contextPath}/login">Sign in</a>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container -->
    </nav><!-- /.navbar -->

    <div class="containerFrom">
        <div class="row">
            <div class="ui-widget1">
                <label>From where? </label>
                <select id="comboboxFrom">
                    <option></option>
                    <c:forEach items="${stationFrom}" var="station">
                        <option value ="${station.stationName.toString()}">${station.stationName.toString()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="containerTo">
        <div class="row">
            <div class="ui-widget2">
                <label>To where? </label>
                <select id="comboboxTo">
                    <option></option>
                    <c:forEach items="${stationTo}" var="station">
                        <option value ="${station.stationName.toString()}">${station.stationName.toString()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>

</div>
</body>
</html>
