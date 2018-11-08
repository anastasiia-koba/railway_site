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

    <title>Train's rout</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container">
    <form:form method="POST" modelAttribute="finalRoutForm" action="${contextPath}/admin/finalrouts">
        <form:input path="id" type="hidden"></form:input>
        <spring:bind path="train">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label>Select train </label>
                <select id="comboboxTrain" name="train" path="train">
                    <option value="${finalRoutForm.train.id}">${finalRoutForm.train.trainName.toString()}</option>
                    <c:forEach items="${trains}" var="train">
                        <option value="${train.id}">${train.trainName.toString()}</option>
                    </c:forEach>
                </select>
                <form:errors path="train"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="rout">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label>Select rout </label>
                <select id="comboboxRout" name="rout" path="rout">
                    <option value="${finalRoutForm.rout.id}">${finalRoutForm.rout.startStation.stationName.toString()}
                        - ${finalRoutForm.rout.endStation.stationName.toString()}</option>
                    <c:forEach items="${routs}" var="rout">
                        <option value="${rout.id}">${rout.startStation.stationName.toString()}
                            - ${rout.endStation.stationName.toString()}</option>
                    </c:forEach>
                </select>
                <form:errors path="rout"></form:errors>
            </div>
        </spring:bind>

        <form:input type="date" name="date" path="date" class="form-group" value="${finalRoutForm.date}"
                    max="2020-06-04" min="2018-10-25"></form:input>

        <button type="submit" name="save" class="btn btn-primary site-btn">Save</button>
    </form:form>
</div>

<div class="container finalRout-table">
    <div class="list">
        <h3>Train's routs</h3>
        <table class="table" id="myTableFinalRouts">
            <thead>
            <tr>
                <th>Train name</th>
                <th>From station</th>
                <th>To station</th>
                <th>Time of departure</th>
                <th>Time of arrival</th>
                <th>date</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${finalRouts}" var="rout">
                <tr>
                    <td>${rout.train.trainName.toString()}</td>
                    <td>${rout.rout.startStation.stationName.toString()}</td>
                    <td>${rout.rout.endStation.stationName.toString()}</td>
                    <td>${departures[rout.id]}</td>
                    <td>${arrivals[rout.id]}</td>
                    <td>${rout.date}</td>

                    <form method="POST" action="${contextPath}/admin/finalrouts">
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

</body>
</html>
