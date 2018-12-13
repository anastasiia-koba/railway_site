<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.min.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/js/finalrouts.js"></script>
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/common.css">
    <title>Train's rout</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'finalrout-tab' ? 'active' : ''} text-style" id="finalrout-tab">
        <div class="container">
            <form:form id="finalRoutForm" name="finalRoutForm" modelAttribute="finalRoutForm">
                <form:input path="id" id="idForm" type="hidden"></form:input>
                <spring:bind path="train">
                    <div class="col-md-2 pt-3">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Select train </label>
                            <form:select path="train" id="comboboxTrain" name="train" class="form-control"
                                         required="required">
                                <option></option>
                                <c:forEach items="${trains}" var="train">
                                    <option value="${train.trainName}">${train.trainName}</option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="train"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="rout">
                    <div class="col-md-2 pt-3">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Select route </label>
                            <form:select path="rout" id="comboboxRout" name="rout" class="form-control"
                                         required="required">
                                <option></option>
                                <c:forEach items="${routs}" var="rout">
                                    <option value="${rout.routName}">${rout.routName}
                                        : ${rout.startStation.stationName}
                                        - ${rout.endStation.stationName}</option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="rout"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="date">
                    <div class="col-md-2 pt-3">
                        <label>Select date </label>
                        <form:input type="date" id="date" path="date" class="form-control" value="${finalRoutForm.date}"
                                    max="2020-06-04" min="2018-10-25" placeholder="Date" required="required"></form:input>
                    </div>
                </spring:bind>

                <div class="col-md-3">
                    <button type="submit" id="btnSaveFinal">Save</button>
                    <button type="button" id="btnClearFinal">Clear</button>
                </div>
            </form:form>
        </div>

        <div class="container finalRout-table">
            <div class="list">
                <h3>Train's routs</h3>
                <div id="finalRoutMessage"></div>
                <table class="table" id="myTableFinalRouts">
                    <thead>
                    <tr>
                        <th>Route name</th>
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
                    <script id="template" type="text/x-handlebars-template">
                        {{#each finalrouts}}
                        <tr>
                            <td>{{routName}}</td>
                            <td>{{trainName}}</td>
                            <td>{{startStationName}}</td>
                            <td>{{endStationName}}</td>
                            <td>{{departure}}</td>
                            <td>{{arrival}}</td>
                            <td>{{date}}</td>

                            <td>
                                <input type="hidden" id="idFinal-{{@index}}" value="{{id}}">
                                <button id="btnChangeFinal-{{@index}}"
                                        onclick="finalEdit('{{@index}}')">Change
                                </button>
                                <button id="btnDeleteFinal-{{@index}}"
                                        onclick="finalDelete('{{@index}}')">Delete
                                </button>
                            </td>
                        </tr>
                        {{/each}}
                    </script>
                    </tbody>
                </table>
                <ul class="pagination pagination-sm">
                    <c:forEach begin="${startpage}" end="${endpage}" var="p">
                        <li class="page-item"><a href="#" id="page-${p}" onclick="getFinalList(${p})">${p}</a>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${contextPath}/resources/js/finalrouts.js"></script>
<script>
    var contextPath = '${contextPath}';
</script>
</body>
</html>
