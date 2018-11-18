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
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'finalrout-tab' ? 'active' : ''} text-style" id="finalrout-tab">
        <div class="container">
            <form:form method="POST" modelAttribute="finalRoutForm" action="${contextPath}/admin/finalrouts">
                <form:input path="id" type="hidden"></form:input>
                <spring:bind path="train">
                    <div class="col-md-2 pt-3">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Select train </label>
                            <select id="comboboxTrain" name="train" class="form-control">
                                <option value="${finalRoutForm.train.trainName.toString()}">${finalRoutForm.train.trainName.toString()}</option>
                                <c:forEach items="${trains}" var="train">
                                    <option value="${train.trainName.toString()}">${train.trainName.toString()}</option>
                                </c:forEach>
                            </select>
                            <form:errors path="train"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="rout">
                    <div class="col-md-2 pt-3">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Select rout </label>
                            <select id="comboboxRout" name="rout" class="form-control">
                                <option value="${finalRoutForm.rout.routName.toString()}">${finalRoutForm.rout.routName}
                                    : ${finalRoutForm.rout.startStation.stationName.toString()}
                                    - ${finalRoutForm.rout.endStation.stationName.toString()}</option>
                                <c:forEach items="${routs}" var="rout">
                                    <option value="${rout.routName.toString()}">${rout.routName}
                                        : ${rout.startStation.stationName.toString()}
                                        - ${rout.endStation.stationName.toString()}</option>
                                </c:forEach>
                            </select>
                            <form:errors path="rout"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <div class="col-md-2 pt-3">
                    <form:input type="date" name="date" path="date" class="form-control" value="${finalRoutForm.date}"
                                max="2020-06-04" min="2018-10-25"></form:input>
                </div>

                <button type="submit" name="save" class="btn btn-primary site-btn">Save</button>
            </form:form>
        </div>

        <div class="container finalRout-table">
            <div class="list">
                <h3>Train's routs</h3>
                <table class="table" id="myTableFinalRouts">
                    <thead>
                    <tr>
                        <th>Rout name</th>
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
                            <td>${rout.rout.routName.toString()}</td>
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
    </div>
</div>
</body>
</html>
