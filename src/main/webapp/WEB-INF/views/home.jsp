<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!--<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<html>
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <title>Railway Home</title>
</head>
<body>

<jsp:include page="navbar.jsp"></jsp:include>

<div id="top-filter" class="top-filter tfilter-box hidden-xs" data-spy="affix" data-offset-top="197">
    <div class="container">
        <div class="row">
            <form method="POST" id="searchForm" action="${contextPath}/">
                <div class="col-sm-2">
                    <div class="form-group" ${status.error ? 'has-error' : ''}>
                        <label>From where? </label>
                        <select id="comboboxFrom" name="stationsFrom" placeholder="From where?" class="form-control">
                            <option></option>
                            <c:forEach items="${stationsFrom}" var="station">
                                <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="form-group" ${status.error ? 'has-error' : ''}>
                        <label>To where? </label>
                        <select id="comboboxTo" name="stationsTo" placeholder="To where?" class="form-control">
                            <option></option>
                            <c:forEach items="${stationsTo}" var="station">
                                <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-sm-2">
                    <div class="form-group">
                        <label>Date: </label>
                        <input type="date" name="date" id="date" class="form-control" value="<%=LocalDate.now()%>"
                               max="2020-06-04" min="2018-10-25">
                    </div>
                </div>
                <div class="col-sm-1">
                    <input type="submit" id="search" class="btn btn-primary site-btn" value="Search"/>
                </div>
            </form>
        </div>
    </div>
</div>

<c:if test="${routs != null}">
    <div class="container search-table">
        <div class="search-list">
            <h3>${stationFrom.stationName} -> ${stationTo.stationName}</h3>
            <h3>${routs.size()} Routs Found</h3>
            <table class="table" id="myTableRouts">
                <thead>
                <tr>
                    <th>Rout number</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Time of departure</th>
                    <th>Time of arrival</th>
                    <th>Travel time</th>
                    <th>Price</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${routs}" var="rout">
                    <tr>
                        <td>${rout.rout.routName.toString()}</td>
                        <td>${rout.rout.startStation.stationName.toString()}</td>
                        <td>${rout.rout.endStation.stationName.toString()}</td>
                        <td>${departures[rout.id]}</td>
                        <td>${arrivals[rout.id]}</td>
                        <td>${times[rout.id]}</td>
                        <td>${prices[rout.id]}</td>
                        <td>
                            <form method="POST" action="${contextPath}/buy" ${error != null ? 'has-error' : ''}>
                                <input type="hidden" name="routId" value="${rout.id}">
                                <input type="hidden" name="stationFrom" value="${stationFrom.id}">
                                <input type="hidden" name="stationTo" value="${stationTo.id}">
                                <c:if test="${freePlaces[rout.id] != 0}">
                                    <input type="submit" id="btnBuy" name="buy" value="Buy">
                                </c:if>
                            <br/>
                                <span>${error}</span>
                            </form>
                                ${freePlaces[rout.id]} places
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>
</body>
</html>
