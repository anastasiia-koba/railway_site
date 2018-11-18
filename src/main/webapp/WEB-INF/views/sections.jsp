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

    <title>Sections in routs</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'section-tab' ? 'active' : '' } text-style" id="section-tab">
        <form name="change_rout" id="searchRout" method="POST" action="${contextPath}/admin/sections"
              class="container-fluid bg-light ">
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
                    <button type="submit">Search</button>
                </div>
            </div>
        </form>

        <c:if test="${routForSection != null}">
            <div class="container">
                <h3>Choose rout section</h3>
                <form method="post" action="${contextPath}/admin/sections/all">
                    <input type="hidden" name="rout.id" value="${routForSection.id}">
                    <div class="col-md-2 pt-3">
                        <div class="form-group">
                            <label>Select start section </label>
                            <select id="comboboxSectionFrom" name="sectionFrom">
                                <option></option>
                                <c:forEach items="${stationsTo}" var="station">
                                    <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-2 pt-3">
                        <div class="form-group">
                            <label>Select end section </label>
                            <select id="comboboxSectionTo" name="sectionTo">
                                <option></option>
                                <c:forEach items="${stationsTo}" var="station">
                                    <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-2">
                        <button type="submit">Search sections</button>
                    </div>
                </form>

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
                            <c:forEach items="${searchSections}" var="section">
                                <tr>
                                    <td value="${section.departure.stationName.toString()}">${section.departure.stationName.toString()}</td>
                                    <td value="${section.destination.stationName.toString()}">${section.destination.stationName.toString()}</td>
                                    <td value="${section.departureTime.toString()}">${section.departureTime.toString()}</td>
                                    <td value="${section.arrivalTime.toString()}">${section.arrivalTime.toString()}</td>
                                    <td value="${section.distance.toString()}">${section.distance.toString()}</td>
                                    <td value="${section.price.toString()}">${section.price.toString()}</td>
                                    <td>
                                        <form method="POST" action="${contextPath}/admin/sections/all">
                                            <input type="hidden" name="sectionId" value="${section.id}">
                                            <input type="hidden" name="routId" value="${routForSection.id}">
                                            <input type="submit" name="add" value="Add">
                                            <input type="submit" name="delete" value="Delete">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="container">
                <h3>Create rout section</h3>
                <form:form method="POST" modelAttribute="routSectionForm" action="${contextPath}/admin/sections">
                    <form:input path="id" type="hidden"></form:input>
                    <input type="hidden" name="routId" value="${routForSection.id}">
                    <%--<form:input path="rout" type="hidden" name="rout"></form:input>--%>
                    <spring:bind path="departure">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Select from where </label>
                            <select id="comboboxSecFrom" name="departure" path="departure">
                                <option value="${routSectionForm.departure.stationName}">${routSectionForm.departure.stationName.toString()}</option>
                                <c:forEach items="${stationsFrom}" var="station">
                                    <option value="${station.stationName}">${station.stationName.toString()}</option>
                                </c:forEach>
                            </select>
                            <form:errors path="departure"></form:errors>
                        </div>
                    </spring:bind>

                    <spring:bind path="destination">
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <label>Select to where </label>
                            <select id="comboboxSecTo" placeholder="To where?" name="destination"
                                    path="destination">
                                <option value="${routSectionForm.destination.stationName}">${routSectionForm.destination.stationName.toString()}</option>
                                <c:forEach items="${stationsTo}" var="station">
                                    <option value="${station.stationName}">${station.stationName.toString()}</option>
                                </c:forEach>
                            </select>
                            <form:errors path="destination"></form:errors>
                        </div>
                    </spring:bind>
                    <form:input type="text" id="timeDeparture" placeholder="Departure time" class="form-control"
                                path="departureTime"></form:input>
                    <form:errors path="departureTime"></form:errors>
                    <form:input type="text" id="timeArrival" placeholder="Arrival time" class="form-control"
                                path="arrivalTime"></form:input>
                    <form:errors path="arrivalTime"></form:errors>
                    <form:input type="text" id="distance" placeholder="Distance" class="form-control"
                                path="distance"></form:input>
                    <form:errors path="distance"></form:errors>
                    <form:input type="text" id="price" placeholder="Price" class="form-control"
                                path="price"></form:input>
                    <form:errors path="price"></form:errors>

                    <button type="submit" name="save">Save</button>
                </form:form>
            </div>

            <div class="container rout-table">
                <div class="list">
                    <h3>Build rout ${routForSection.startStation.stationName.toString()}
                        - ${routForSection.endStation.stationName.toString()}</h3>
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
                        <c:forEach items="${sections}" var="section">
                            <tr>
                                <td value="${section.departure.stationName.toString()}">${section.departure.stationName.toString()}</td>
                                <td value="${section.destination.stationName.toString()}">${section.destination.stationName.toString()}</td>
                                <td value="${section.departureTime.toString()}">${section.departureTime.toString()}</td>
                                <td value="${section.arrivalTime.toString()}">${section.arrivalTime.toString()}</td>
                                <td value="${section.distance.toString()}">${section.distance.toString()}</td>
                                <td value="${section.price.toString()}">${section.price.toString()}</td>
                                <td>
                                    <form method="POST" action="${contextPath}/admin/sections">
                                        <input type="hidden" name="id" value="${section.id}">
                                        <input type="hidden" name="routId" value="${routForSection.id}">
                                        <input type="submit" name="change" value="Change">
                                        <input type="submit" name="delete" value="Delete">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
