<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Users in train</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container-fluid bg-light ">
    <div class="container">
        <div class="row">
            <form:form method="POST" modelAttribute="finalRoutForm" action="${contextPath}/admin/passengers">
                <spring:bind path="train.id">
                    <div class="col-sm-2">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Train name </label>
                            <select id="comboboxTrain" name="train.id" path="train.id" class="form-control">
                                <option></option>
                                <c:forEach items="${trains}" var="train">
                                    <option value="${train.trainName}">${train.trainName.toString()}</option>
                                </c:forEach>
                            </select>
                            <form:errors path="train"></form:errors>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="rout.id">
                    <div class="col-sm-2">
                        <div class="form-group" ${status.error ? 'has-error' : ''}>
                            <label>Rout </label>
                            <select id="comboboxRout" name="rout.id" path="rout.id" class="form-control">
                                <option></option>
                                <c:forEach items="${routs}" var="rout">
                                    <option value="${rout.routName}">${rout.routName} : ${rout.startStation.stationName.toString()} -
                                            ${rout.endStation.stationName.toString()}</option>
                                </c:forEach>
                            </select>
                            <form:errors path="rout"></form:errors>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="date">
                    <div class="col-sm-2">
                        <div class="form-group" ${status.error ? 'has-error' : ''}>
                            <label>Date: </label>
                            <input type="date" name="date" class="form-control" value="<%=LocalDate.now()%>"
                                   max="2020-06-04" min="2018-10-25">
                            <form:errors path="date"></form:errors>
                        </div>
                    </div>
                </spring:bind>
                <div class="col-sm-1">
                    <button type="submit" class="btn btn-primary site-btn">Show passengers</button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<div class="container rout-table">
    <div class="list">
        <h3>${tickets.size()} Passengers found</h3>
        <table class="table" id="myTable">
            <thead>
            <tr>
                <th>Name</th>
                <th>From</th>
                <th>To</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tickets}" var="ticket">
                <tr>
                    <td>${ticket.user.surname} ${ticket.user.firstname}</td>
                    <td>${ticket.startStation.stationName}</td>
                    <td>${ticket.endStation.stationName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
