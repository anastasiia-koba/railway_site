<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.min.js"></script>
    <script type="text/javascript" src="${contextPath}/resources/js/sections.js"></script>
    <title>Sections in routs</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'section-tab' ? 'active' : '' } text-style" id="section-tab">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <div class="col-md-2 pt-3">
                    <div class="form-group ">
                        <label>Select rout </label>
                        <select id="comboboxRout" name="routForSearch" class="form-control">
                            <option></option>
                            <c:forEach items="${routs}" var="rout">
                                <option value="${rout.id}">${rout.routName.toString()}: ${rout.startStation.stationName.toString()}
                                    -
                                        ${rout.endStation.stationName.toString()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-2">
                    <button onclick="searchRout()">Search</button>
                </div>
            </div>
        </div>

        <div class="container" id="containerBackRout" hidden>
            <div class="row align-items-center justify-content-center">
                <div class="col-md-2 pt-3">
                    <div class="form-group ">
                        <label>Select rout </label>
                        <select id="comboboxBack" name="routBack" class="form-control">
                            <option></option>
                            <c:forEach items="${routs}" var="rout">
                                <option value="${rout.id}">${rout.routName.toString()}: ${rout.startStation.stationName.toString()}
                                    -
                                        ${rout.endStation.stationName.toString()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="col-md-2">
                    <button onclick="formBackRout()">Form back rout</button>
                </div>
                <br/>
                <div id="errorBackMessage"></div>
            </div>
        </div>

        <div class="container" id="containerExistSection" hidden>
            <h3>Choose rout section</h3>
            <div class="col-md-2 pt-3">
                <div class="form-group">
                    <label>Select start section </label>
                    <select id="comboboxSectionFrom" name="sectionFrom" class="form-control">
                        <option></option>
                        <c:forEach items="${stationsFrom}" var="station">
                            <option value="${station.stationName}">${station.stationName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-2 pt-3">
                <div class="form-group">
                    <label>Select end section </label>
                    <select id="comboboxSectionTo" name="sectionTo" class="form-control">
                        <option></option>
                        <c:forEach items="${stationsTo}" var="station">
                            <option value="${station.stationName}">${station.stationName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="col-md-2">
                <button type="submit" id="btnAllSections">Search sections</button>
            </div>

            <div class="container section-table">
                <div class="list">
                    <table class="table" id="myTableAllSections">
                        <thead>
                        <tr>
                            <th>From</th>
                            <th>To</th>
                            <th>Departure time</th>
                            <th>Arrival time</th>
                            <th>Distance</th>
                            <th>Price</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <script id="templateSearch" type="text/x-handlebars-template">
                            {{#each search}}
                            <tr>
                                <td>{{departure.stationName}}</td>
                                <td>{{destination.stationName}}</td>
                                <td>{{departureTime}}</td>
                                <td>{{arrivalTime}}</td>
                                <td>{{distance}}</td>
                                <td>{{price}}</td>
                                <td>
                                    <input type="hidden" id="idSearch-{{@index}}" value="{{id}}">
                                    <button onclick="sectionAdd('{{@index}}')">Add</button>
                                    <button onclick="sectionDeleteFromAll('{{@index}}')">Delete</button>
                                </td>
                            </tr>
                            {{/each}}
                        </script>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <input type="hidden" id="routId">

        <div class="container" id="newSection" hidden>
            <h3>Create rout section</h3>
            <form:form modelAttribute="sectionForm" id="sectionForm" name="sectionForm"
                       role="form" data-toggle="validator">
                <form:input path="id" id="idForm" type="hidden"></form:input>
                <spring:bind path="departure">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label>Select from where </label>
                        <form:select id="comboboxSecFrom" name="departure" path="departure">
                            <option></option>
                            <c:forEach items="${stationsFrom}" var="station">
                                <option value="${station.stationName}">${station.stationName}</option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="departure"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="destination">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label>Select to where </label>
                        <form:select id="comboboxSecTo" name="destination" path="destination">
                            <option></option>
                            <c:forEach items="${stationsTo}" var="station">
                                <option value="${station.stationName}">${station.stationName}</option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="destination"></form:errors>
                    </div>
                </spring:bind>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label>Departure time</label>
                    <form:input type="time" id="timeDeparture" placeholder="Departure time" class="form-control"
                                path="departureTime"></form:input>
                    <form:errors path="departureTime"></form:errors>
                </div>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label>Arrival time</label>
                    <form:input type="time" id="timeArrival" placeholder="Arrival time" class="form-control"
                                path="arrivalTime"></form:input>
                    <form:errors path="arrivalTime"></form:errors>
                </div>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label>Distance</label>
                    <form:input type="text" id="distance" placeholder="Distance" class="form-control"
                                path="distance"></form:input>
                    <form:errors path="distance"></form:errors>
                </div>
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label>Price</label>
                    <form:input type="text" id="price" placeholder="Price" class="form-control"
                                path="price"></form:input>
                    <form:errors path="price"></form:errors>
                </div>

                <div class="col-md-3">
                    <button type="submit" id="btnSaveSection">Save</button>
                    <button type="submit" id="btnClearSection">Clear</button>
                </div>
            </form:form>
        </div>

        <div class="container rout-table" hidden>
            <div class="list">
                <h3>Build rout</h3>
                <h3 id="buildMessage"></h3>
                <div id="sectionMessage"></div>
                <table class="table" id="myTableSections">
                    <thead>
                    <tr>
                        <th>From</th>
                        <th>To</th>
                        <th>Departure time</th>
                        <th>Arrival time</th>
                        <th>Distance</th>
                        <th>Price</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <script id="templateSections" type="text/x-handlebars-template">
                        {{#each sections}}
                        <tr>
                            <td>{{departure}}</td>
                            <td>{{destination}}</td>
                            <td>{{departureTime}}</td>
                            <td>{{arrivalTime}}</td>
                            <td>{{distance}}</td>
                            <td>{{price}}</td>
                            <td>
                                <input type="hidden" id="idSection-{{@index}}" value="{{id}}">
                                <button onclick="sectionEdit('{{@index}}')">Change</button>
                                <button onclick="sectionDelete('{{@index}}')">Delete</button>
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
<script type="text/javascript" src="${contextPath}/resources/js/sections.js"></script>
<script>
    var contextPath = '${contextPath}';
</script>
</body>
</html>
