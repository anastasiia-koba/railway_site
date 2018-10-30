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
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-datetimepicker.min.css" rel="stylesheet"
          id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap-datetimepicker.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

    <title>Railway Home</title>
</head>
<body>

<jsp:include page="navbar.jsp"></jsp:include>

<div class="container-fluid bg-light ">
    <div class="row align-items-center justify-content-center">
        <div class="col-md-2 pt-3">
            <div class="form-group ">
                <label>From where? </label>
                <select id="comboboxFrom" placeholder="From where?">
                    <option></option>
                    <c:forEach items="${stationFrom}" var="station">
                        <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-md-2 pt-3">
            <div class="form-group">
                <label>To where? </label>
                <select id="comboboxTo" placeholder="To where?">
                    <option></option>
                    <c:forEach items="${stationTo}" var="station">
                        <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-md-2 pt-3">
            <div class="form-group">
                <label>Date: </label>
                <input type="date" name="calendar" class="form-control" value="2018-10-25"
                       max="2020-06-04" min="2018-10-25">
            </div>
        </div>
        <div class="col-md-2">
            <button type="button" class="btn btn-primary btn-block">Search</button>
        </div>
    </div>
</div>

<div class="container search-table">
    <div class="search-list">
        <h3>"number" Records Found</h3>
        <table class="table" id="myTable">
            <thead>
            <tr>
                <th>Train number</th>
                <th>Time of departure</th>
                <th>Time of arrival</th>
                <th>Travel time</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>1</td>
                <td>10:30</td>
                <td>15:30</td>
                <td>5:30</td>
            </tr>
            <tr>
                <td>1</td>
                <td>10:30</td>
                <td>15:30</td>
                <td>5:30</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
