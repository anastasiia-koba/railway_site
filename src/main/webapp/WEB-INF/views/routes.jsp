<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.min.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/js/routs.js"></script>
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/common.css">
    <title>Routes</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'rout-tab' ? 'active' : ''} text-style" id="rout-tab">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <form:form id="routForm" name="routForm" modelAttribute="routForm">
                    <div class="col-md-3">
                        <form:input path="id" id="idForm" type="hidden"></form:input>
                        <spring:bind path="routName">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label>Route name</label>
                                <form:input type="text" id="routName" placeholder="New Route" class="form-control"
                                            path="routName" required="required"></form:input>
                                <form:errors path="routName"></form:errors>
                            </div>
                        </spring:bind>
                    </div>
                    <spring:bind path="startStation">
                        <div class="col-md-2 pt-3">
                            <div class="form-group ">
                                <label>Select start </label>
                                <form:select path="startStation" id="comboboxStart" name="startStation"
                                             class="form-control" required="required">
                                    <option></option>
                                    <c:forEach items="${stationsFrom}" var="station">
                                        <option value="${station.stationName}">${station.stationName}</option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                    </spring:bind>

                    <spring:bind path="endStation">
                        <div class="col-md-2 pt-3">
                            <div class="form-group">
                                <label>Select end </label>
                                <form:select path="endStation" id="comboboxEnd" name="endStation" class="form-control"
                                             required="required">
                                    <option></option>
                                    <c:forEach items="${stationsTo}" var="station">
                                        <option value="${station.stationName}">${station.stationName}</option>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="endStation"></form:errors>
                            </div>
                        </div>
                    </spring:bind>

                    <div class="col-md-3">
                        <button type="submit" id="btnAddRout">Save</button>
                        <button type="button" id="btnClearRout">Clear</button>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="container routs-table">
            <div class="list">
                <h3>Routes</h3>
                <div id="routMessage"></div>
                <table class="table" id="myTableRouts">
                    <thead>
                    <tr>
                        <th>Route name</th>
                        <th>Station from</th>
                        <th>Station to</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <script id="template" type="text/x-handlebars-template">
                        {{#each routs}}
                        <tr>
                            <td>{{routName}}</td>
                            <td>{{startStation.stationName}}</td>
                            <td>{{endStation.stationName}}</td>
                            <td>
                                <input type="hidden" id="idRout-{{@index}}" value="{{id}}">
                                <button id="btnChangeRout-{{@index}}"
                                        onclick="routEdit('{{@index}}')">Change
                                </button>
                                <button id="btnDeleteRout-{{@index}}"
                                        onclick="routDelete('{{@index}}')">Delete
                                </button>
                            </td>
                        </tr>
                        {{/each}}
                    </script>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${contextPath}/resources/js/routs.js"></script>
<script>
    var contextPath = '${contextPath}';
</script>
</body>
</html>
