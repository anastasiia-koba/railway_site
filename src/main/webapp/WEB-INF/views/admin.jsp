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

    <title>Admin</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container">
    <div class="col-sm-2">
        <nav class="nav-sidebar">
            <ul id="myTab" class="nav tabs">
                <li class="${selectedTab == 'station-tab' ? 'active' : ''}"><a data-target="#station-tab"
                                                                               data-toggle="tab">Station</a>
                </li>
                <li class="${selectedTab == 'train-tab' ? 'active' : ''}"><a data-target="#train-tab"
                                                                             data-toggle="tab">Trains</a></li>
                <li class="${selectedTab == 'rout-tab' ? 'active' : '' }"><a data-target="#rout-tab" data-toggle="tab">Routs</a>
                </li>
            </ul>
        </nav>
    </div>
    <!-- tab content -->
    <div class="tab-content" id="containerContainingTabs">
        <%--STATIONS--%>
        <div class="tab-pane ${selectedTab == 'station-tab' ? 'active' : ''} text-style" id="station-tab">
            <form:form method="POST" modelAttribute="stationForm" action="${contextPath}/admin/stations">
                <spring:bind path="stationName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="id" type="hidden"></form:input>
                        <form:input type="text" id="stationName" placeholder="New Station" class="form-control"
                                    path="stationName"></form:input>
                        <form:errors path="stationName"></form:errors>
                    </div>
                </spring:bind>
                <button type="submit" name="save">Save</button>
            </form:form>

            <div class="container station-table">
                <div class="list">
                    <h3>Stations</h3>
                    <table class="table" id="myTableStations">
                        <thead>
                        <tr>
                            <th>Station name</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${stations}" var="station">
                            <tr>
                                <form method="POST" action="${contextPath}/admin/stations">

                                    <td value="${station.stationName.toString()}">${station.stationName.toString()}</td>
                                    <td>
                                        <input type="hidden" name="id" value="${station.id}">
                                        <input type="submit" name="change" value="Change">
                                        <input type="submit" name="delete" value="Delete">
                                    </td>
                                </form>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <%--TRAINS--%>
        <div class="tab-pane ${selectedTab == 'train-tab' ? 'active' : ''} text-style" id="train-tab">
            <form:form method="POST" modelAttribute="trainForm" action="${contextPath}/admin/trains">
                <spring:bind path="trainName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="id" type="hidden"></form:input>
                        <form:input type="text" id="trainName" placeholder="New Train" class="form-control"
                                    path="trainName"></form:input>
                        <form:errors path="trainName"></form:errors>

                        <form:input type="text" id="placesNumber" placeholder="Count" class="form-control"
                                    path="placesNumber"></form:input>
                        <form:errors path="placesNumber"></form:errors>
                    </div>
                </spring:bind>
                <button type="submit" name="save">Save</button>
            </form:form>

            <div class="container train-table">
                <div class="list">
                    <h3>Trains</h3>
                    <table class="table" id="myTableTrains">
                        <thead>
                        <tr>
                            <th>Train name</th>
                            <th>Places</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${trains}" var="train">
                            <tr>
                                <form method="POST" action="${contextPath}/admin/trains">

                                    <td value="${train.trainName.toString()}">${train.trainName.toString()}</td>
                                    <td value="${train.placesNumber.toString()}">${train.placesNumber.toString()}</td>
                                    <td>
                                        <input type="hidden" name="id" value="${train.id}">
                                        <input type="submit" name="change" value="Change">
                                        <input type="submit" name="delete" value="Delete">
                                    </td>
                                </form>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <%--ROUTS--%>
        <div class="tab-pane ${selectedTab == 'rout-tab' ? 'active' : '' } text-style" id="rout-tab">
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
        </div>
    </div>
</div>
</body>
</html>
