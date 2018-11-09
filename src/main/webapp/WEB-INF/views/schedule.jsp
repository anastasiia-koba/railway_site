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

<div class="container-fluid bg-light ">
    <div class="row align-items-center justify-content-center">
        <form method="POST" action="${contextPath}/schedule">
            <div class="col-md-2 pt-3">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label>Station </label>
                    <select id="comboboxStation" name="station" path="station">
                        <option></option>
                        <c:forEach items="${stations}" var="station">
                            <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                        </c:forEach>
                    </select>
                    <span>${error}</span>
                </div>
            </div>
            <div class="col-md-2 pt-3">
                <div class="form-group" ${status.error ? 'has-error' : ''}>
                    <label>Date: </label>
                    <input type="date" name="date" class="form-control" value="<%=new java.util.Date()%>"
                           max="2020-06-04" min="2018-10-25">
                    <span>${error}</span>
                </div>
            </div>
            <input type="submit" value="Show schedule">
        </form>
    </div>
</div>

<div class="container">
    <div class="list">
        <h3>Through the station ${station.stationName.toString()} ${trains.size()} Trains founded</h3>
        <table class="table" id="myTable">
            <thead>
            <tr>
                <th>Rout</th>
                <th>From</th>
                <th>To</th>
                <th>Arrival time</th>
                <th>Departure time</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${trains}" var="train">
                <tr>
                    <td>${train.rout.routName.toString()}</td>
                    <td>${train.rout.startStation.stationName.toString()}</td>
                    <td>${train.rout.endStation.stationName.toString()}</td>
                    <td>${arrivals[train.id]}</td>
                    <td>${departures[train.id]}</td>
                </tr>
            </c:forEach>
            </tbody>
    </div>
</div>
</body>
</html>
