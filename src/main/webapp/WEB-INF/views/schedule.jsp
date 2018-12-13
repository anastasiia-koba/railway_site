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
        <div class="row">
            <form method="POST" action="${contextPath}/schedule">
                <div class="col-sm-2">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label>Station </label>
                        <select id="comboboxStation" name="station" path="station" class="form-control">
                            <option></option>
                            <c:forEach items="${stations}" var="station">
                                <option value="${station.stationName}">${station.stationName.toString()}</option>
                            </c:forEach>
                        </select>
                        <span>${error}</span>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="form-group" ${status.error ? 'has-error' : ''}>
                        <label>Date: </label>
                        <input type="date" name="date" class="form-control" value="<%=LocalDate.now()%>"
                               max="2020-06-04" min="2018-10-25">
                        <span>${error}</span>
                    </div>
                </div>
                <div class="col-sm-1">
                    <input type="submit" class="btn btn-primary site-btn" value="Show schedule">
                </div>
            </form>
        </div>
    </div>
</div>

<div class="container">
    <div class="list">
        <h3>Through the station ${station.stationName.toString()} ${trains.size()} Trains founded</h3>
        <table class="table" id="myTable">
            <thead>
            <tr>
                <th>Route</th>
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
        </table>
    </div>
</div>
</body>
</html>
