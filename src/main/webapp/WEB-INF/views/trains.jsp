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

    <title>Trains</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container-fluid bg-light ">
    <div class="row align-items-center justify-content-center">
        <form method="POST" action="${contextPath}/admin/trains/all">
            <div class="col-md-2 pt-3">
                <div class="form-group ">
                    <label>Date: </label>
                    <input type="date" name="calendar" class="form-control" value="<%=new java.util.Date()%>"
                           max="2020-06-04" min="2018-10-25">
                </div>
            </div>
            <div class="col-md-2">
                <button type="button" class="btn btn-primary btn-block">Search</button>
            </div>
        </form>
    </div>
</div>


<div class="container search-table">
    <div class="search-list">
        <h3>${finalRouts.size()} Trains Found</h3>
        <table class="table" id="myTableTrains">
            <thead>
            <tr>
                <th>Train number</th>
                <th>Number of seats</th>
                <th>Departure</th>
                <th>Destination</th>
                <th>Time of departure</th>
                <th>Time of arrival</th>
                <th>Travel time</th>
                <th>Date</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${finalRouts}" var="rout">
                <td value="${rout.train.id}">${rout.train.trainName.toString()}</td>
                <td value="${rout.train.placesNumber}">${rout.train.placesNumber.toString()}</td>
                <td value="${rout.rout.startStation.id}">${rout.rout.startStation.stationName.toString()}</td>
                <td value="${rout.rout.endStation.id}">${rout.rout.endStation.stationName.toString()}</td>
                <td></td>
                <td></td>
                <td></td>
                <td>${rout.date.toString()}</td>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
