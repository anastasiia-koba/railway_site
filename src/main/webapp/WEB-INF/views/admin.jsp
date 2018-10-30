<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

    <title>Admin</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<form name="change_rout" method="POST" action="${contextPath}/admin" class="container-fluid bg-light ">
    <div class="row align-items-center justify-content-center">
        <div class="col-md-2 pt-3">
            <div class="form-group ">
                <label>Select start </label>
                <select id="comboboxFrom" placeholder="From where?" name="stationFrom">
                    <option></option>
                    <c:forEach items="${stationFrom}" var="station">
                        <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="col-md-2 pt-3">
            <div class="form-group">
                <label>Select end </label>
                <select id="comboboxTo" placeholder="To where?" name="stationTo">
                    <option></option>
                    <c:forEach items="${stationTo}" var="station">
                        <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-md-2" ${error ? 'has-error' : ''}>
            <!-- <input type="submit" value="Search" class="btn btn-primary btn-block"/>-->
            <button class="btn btn-lg btn-primary btn-block" type="submit">Search</button>
            <span>${error}</span>
        </div>
    </div>
</form>

<div class="container rout-table">
    <div class="list">
        <h3>Build rout</h3>
        <table class="table" id="myTable">
            <thead>
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Departure time</th>
                <th>Arrival time</th>
                <th>Distance</th>
                <th>Price</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sections}" var="section">
                <tr>
                    <td value="${section.departure.stationName.toString()}">${section.departure.stationName.toString()}</td>
                    <td value="${section.destination.stationName.toString()}">${section.destination.stationName.toString()}</td>
                    <td value="${section.departureTime.toString()}">${section.departureTime.toString()}</td>
                    <td value="${section.arrivalTime.toString()}">${section.arrivalTime.toString()}</td>
                    <td value="${section.distance.toString()}">${section.distance.toString()}</td>
                    <td value="${section.price.toString()}">${section.price.toString()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
