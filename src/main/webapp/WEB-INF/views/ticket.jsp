<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <title>Buy ticket</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container">
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Train: </label>
            <label>${rout.train.trainName.toString()}</label>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Train's rout: </label>
            <label>${rout.rout.startStation.stationName.toString()} - ${rout.rout.endStation.stationName.toString()}</label>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Date: </label>
            <label>${rout.date.toString()}</label>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Your rout: </label>
            <label>${stationFrom.stationName.toString()} - ${stationTo.stationName.toString()}</label>
        </div>
    </div>
    <div class="col-md-2">
        <button type="submit" class="btn btn-primary btn-block">Purchase</button>
    </div>
</div>
</body>
</html>
