<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/common.css">
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
                        <select id="comboboxStation" name="station" path="station" class="form-control"
                                required="required">
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
                        <input type="date" id="date" name="date" class="form-control" value="<%=LocalDate.now()%>"
                               required="required" max="2020-06-04" min="2018-10-25">
                        <span>${error}</span>
                    </div>
                </div>
                <div class="col-sm-2">
                    <button onclick="searchRout()">Show schedule</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="container" hidden>
    <div class="list">
        <h3 id="countTrains"></h3>
        <h3 id="message"></h3>
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
            <script id="templateRouts" type="text/x-handlebars-template">
                {{#each routs}}
                <tr>
                    <td>{{routName}}</td>
                    <td>{{startStation}}</td>
                    <td>{{endStation}}</td>
                    <td>{{arrivalTime}}</td>
                    <td>{{departureTime}}</td>
                    <td>
                        <input type="hidden" id="idRout-{{@index}}" value="{{id}}">
                        <button onclick="ontimeSend('{{@index}}')">On time</button>
                        <button onclick="delaySend('{{@index}}')">Delayed</button>
                        <button onclick="cancelSend('{{@index}}')">Canceled</button>
                    </td>
                </tr>
                {{/each}}
            </script>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="${contextPath}/resources/js/realSchedule.js"></script>
<script>
    var contextPath = '${contextPath}';
</script>
</body>
</html>
