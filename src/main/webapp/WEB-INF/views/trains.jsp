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

<div class="container">
    <div class="col-sm-2">
        <nav class="nav-sidebar">
            <ul id="myTab" class="nav tabs">
                <li class="${selectedTab == 'oneTrain-tab' ? 'active' : ''}"><a data-target="#train-tab"
                                                                                data-toggle="tab">Trains</a>
                </li>
                <li class="${selectedTab == 'allTrain-tab' ? 'active' : ''}"><a data-target="#train-rout-tab"
                                                                                data-toggle="tab">Train's Routs</a>
                </li>
            </ul>
        </nav>
    </div>
    <!-- tab content -->
    <div class="tab-content">
        <div class="tab-pane ${selectedTab == 'oneTrain-tab' ? 'active' : ''} text-style" id="train-tab">
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
                                <td value="${train.trainName.toString()}">${train.trainName.toString()}</td>
                                <td value="${train.placesNumber.toString()}">${train.placesNumber.toString()}</td>
                                <td>
                                    <form method="POST" action="${contextPath}/admin/trains">
                                        <input type="hidden" name="id" value="${train.id}">
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

        <div class="tab-pane ${selectedTab == 'allTrains-tab' ? 'active' : ''} text-style" id="train-rout-tab">
            <div class="container-fluid bg-light ">
                <div class="row align-items-center justify-content-center">
                    <form method="POST" action="${contextPath}/admin/trains/date">
                        <div class="col-md-2 pt-3" ${error ? 'has-error' : ''}>
                            <div class="form-group ">
                                <label>Date: </label>
                                <input type="date" name="calendar" class="form-control"
                                       value="<%=new java.util.Date()%>"
                                       max="2020-06-04" min="2018-10-25">
                            </div>
                            <span>${error}</span>
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary btn-block">Search</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="container search-table">
                <div class="search-list">
                    <h3>${finalRouts.size()} Trains Found</h3>
                    <table class="table" id="myTableRoutsTrains">
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
        </div>
    </div>
</div>
</body>
</html>
