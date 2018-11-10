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
                <li class="${selectedTab == 'rout-tab' ? 'active' : '' }"><a data-target="#rout-tab" data-toggle="tab">Routs</a>
                <li class="${selectedTab == 'section-tab' ? 'active' : '' }"><a data-target="#section-tab"
                                                                                data-toggle="tab">Sections</a>
                </li>
            </ul>
        </nav>
    </div>
    <!-- tab content -->
    <div class="tab-content" id="containerContainingTabs">
        <%--STATIONS--%>
        <div class="tab-pane ${selectedTab == 'station-tab' ? 'active' : ''} text-style" id="station-tab">
            <form:form method="POST" modelAttribute="stationForm" action="${contextPath}/admin/stations" class="form-group">
                <form:input path="id" type="hidden"></form:input>
                <spring:bind path="stationName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
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

        <%--ROUTS--%>
        <div class="tab-pane ${selectedTab == 'rout-tab' ? 'active' : ''} text-style" id="rout-tab">
            <dix class="container">
                <form:form method="POST" modelAttribute="routForm" action="${contextPath}/admin/routs">
                    <form:input path="id" type="hidden"></form:input>
                    <spring:bind path="routName">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Rout name</label>
                            <form:input type="text" id="routName" placeholder="New Rout" class="form-control"
                                        path="routName"></form:input>
                            <form:errors path="routName"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="startStation">
                        <div class="col-md-2 pt-3">
                            <div class="form-group ">
                                <label>Select start </label>
                                <select id="comboboxStart" placeholder="From where?" name="startStation"
                                        class="form-control">
                                    <option value="${routForm.startStation.stationName.toString()}">${routForm.startStation.stationName.toString()}</option>
                                    <c:forEach items="${stationsFrom}" var="station">
                                        <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                                    </c:forEach>
                                </select>
                                <form:errors path="startStation"></form:errors>
                            </div>
                        </div>
                    </spring:bind>

                    <spring:bind path="endStation">
                        <div class="col-md-2 pt-3">
                            <div class="form-group">
                                <label>Select end </label>
                                <select id="comboboxEnd" placeholder="To where?" name="endStation" class="form-control">
                                    <option value="${routForm.endStation.stationName.toString()}">${routForm.endStation.stationName.toString()}</option>
                                    <c:forEach items="${stationsTo}" var="station">
                                        <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                                    </c:forEach>
                                </select>
                                <form:errors path="endStation"></form:errors>
                            </div>
                        </div>
                    </spring:bind>

                    <button type="submit" name="save">Save</button>
                </form:form>
            </dix>

            <div class="container routs-table">
                <div class="list">
                    <h3>Routs</h3>
                    <table class="table" id="myTableRouts">
                        <thead>
                        <tr>
                            <th>Rout name</th>
                            <th>Station from</th>
                            <th>Station to</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${routs}" var="rout">
                            <tr>
                                <td value="${rout.routName.toString()}">${rout.routName.toString()}</td>
                                <td value="${rout.startStation.stationName.toString()}">${rout.startStation.stationName.toString()}</td>
                                <td value="${rout.endStation.stationName.toString()}">${rout.endStation.stationName.toString()}</td>
                                <form method="POST" action="${contextPath}/admin/routs">
                                    <td>
                                        <input type="hidden" name="id" value="${rout.id}">
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

        <%--SECTIONS--%>
        <div class="tab-pane ${selectedTab == 'section-tab' ? 'active' : '' } text-style" id="section-tab">
            <form name="change_rout" method="POST" action="${contextPath}/admin/sections"
                  class="container-fluid bg-light ">
                <div class="row align-items-center justify-content-center">
                    <div class="col-md-2 pt-3">
                        <div class="form-group ">
                            <label>Select start </label>
                            <select id="comboboxFrom" placeholder="From where?" name="stationFrom">
                                <option></option>
                                <c:forEach items="${stationsFrom}" var="station">
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
                                <c:forEach items="${stationsTo}" var="station">
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

            <h3>Rout section</h3>
            <form:form method="POST" modelAttribute="routSectionForm" action="${contextPath}/admin/routs">
                <form:input path="id" type="hidden"></form:input>
                <input type="hidden" name="routId" value="${routForSection.id}">
                <%--<form:input path="rout" type="hidden" name="rout"></form:input>--%>
                <spring:bind path="departure">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label>Select from where </label>
                        <select id="comboboxSecFrom" name="departure" path="departure">
                            <option value="${routSectionForm.departure.id}">${routSectionForm.departure.stationName.toString()}</option>
                            <c:forEach items="${stationsFrom}" var="station">
                                <option value="${station.id}">${station.stationName.toString()}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="departure"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="destination">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label>Select to where </label>
                        <select id="comboboxSecTo" placeholder="To where?" name="destination" path="destination">
                            <option value="${routSectionForm.destination.id}">${routSectionForm.destination.stationName.toString()}</option>
                            <c:forEach items="${stationsTo}" var="station">
                                <option value="${station.id}">${station.stationName.toString()}</option>
                            </c:forEach>
                        </select>
                        <form:errors path="destination"></form:errors>
                    </div>
                </spring:bind>
                <form:input type="text" id="timeDeparture" placeholder="Departure time" class="form-control"
                            path="departureTime"></form:input>
                <form:errors path="departureTime"></form:errors>
                <form:input type="text" id="timeArrival" placeholder="Arrival time" class="form-control"
                            path="arrivalTime"></form:input>
                <form:errors path="arrivalTime"></form:errors>
                <form:input type="text" id="distance" placeholder="Distance" class="form-control"
                            path="distance"></form:input>
                <form:errors path="distance"></form:errors>
                <form:input type="text" id="price" placeholder="Price" class="form-control"
                            path="price"></form:input>
                <form:errors path="price"></form:errors>

                <button type="submit" name="save">Save</button>
            </form:form>

            <div class="container rout-table">
                <div class="list">
                    <h3>Build rout ${stationFrom.stationName.toString()} - ${stationTo.stationName.toString()}</h3>
                    <table class="table" id="myTableSections">
                        <thead>
                        <tr>
                            <th>From</th>
                            <th>To</th>
                            <th>Departure time</th>
                            <th>Arrival time</th>
                            <th>Distance</th>
                            <th>Price</th>
                            <th></th>
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
                                <td>
                                    <form method="POST" action="${contextPath}/admin/routs">
                                        <input type="hidden" name="id" value="${section.id}">
                                        <input type="hidden" name="routId" value="${routForSection.id}">
                                        <input type="submit" name="change" value="Change">
                                        <input type="submit" name="delete" value="Delete">
                                    </form>
                                </td>
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
