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
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.min.js"></script>

    <title>Railway Home</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div id="top-filter" class="top-filter tfilter-box hidden-xs" data-spy="affix" data-offset-top="197">
    <div class="container">
        <div class="row">
            <div class="col-sm-2">
                <div class="form-group" ${status.error ? 'has-error' : ''}>
                    <label>From where? </label>
                    <select id="comboboxFrom" name="stationsFrom" placeholder="From where?" class="form-control">
                        <option></option>
                        <c:forEach items="${stationsFrom}" var="station">
                            <option value="${station.stationName}">${station.stationName}</option>
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
                            <option value="${station.stationName}">${station.stationName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-sm-2">
                <div class="form-group" ${status.error ? 'has-error' : ''}>
                    <label>Count passengers</label>
                    <input type="number" id="places" value="1" class="form-control"/>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group">
                    <label>Date: </label>
                    <input type="datetime-local" name="date" id="date" class="form-control" value="<%=LocalDate.now()%>T08:30"
                           min="2018-11-01T08:30" max="2020-06-30T16:30">
                </div>
            </div>
            <div class="col-md-2">
                <button onclick="searchRout()" id="search" class="btn btn-primary site-btn"> Search</button>
            </div>
        </div>
    </div>
</div>

<div class="container search-table" hidden>
    <div class="search-list">
        <h3 id="buildMessage"></h3>
        <h3 id="countRoutMessage"></h3>
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
            <script id="templateFinals" type="text/x-handlebars-template">
                {{#each routs}}
                <tr>
                    <td>{{routName}}</td>
                    <td>{{startStation}}</td>
                    <td>{{endStation}}</td>
                    <td>{{departureTime}}</td>
                    <td>{{arrivalTime}}</td>
                    <td>{{travelTime}}</td>
                    <td>{{price}}</td>
                    <td>
                        <form method="POST" action="${contextPath}/preorder"
                              class="form-group ${status.error != null ? 'has-error' : ''}">
                            <input type="hidden" id="idFinal-{{@index}}" name="routId" value="{{id}}">
                            <input type="hidden" name="stationFrom" id="from-{{@index}}">
                            <input type="hidden" name="stationTo" id="to-{{@index}}">

                            <input type="submit" id="btnBuy-{{@index}}" name="buy" value="Buy"/>
                            <br/>
                            <div id="error-{{@index}}"></div>
                            {{freePlace}} places
                        </form>
                    </td>
                </tr>
                {{/each}}
            </script>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="${contextPath}/resources/js/home.js"></script>
<script>
    var contextPath = '${contextPath}';
</script>
</body>
</html>
