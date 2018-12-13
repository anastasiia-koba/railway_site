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

    <script type="text/javascript" src="${contextPath}/resources/js/registration.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.3.4/dist/leaflet.css"
          integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA=="
          crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.3.4/dist/leaflet.js"
            integrity="sha512-nMMmRyTVoLYqjP9hrbed9S+FzjZHW5gY1TWCHA5ckwXZBadntCNs8kEqAWdrb9O7rxbCaA4lKTIWjDXZxflOcA=="
            crossorigin=""></script>

    <title>Buy ticket</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="jumbotron jumbotron-sm">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <h3>Your order</h3>
                <h3 id="errorMessage"></h3>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <div class="well well-sm">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group ">
                            <label>Train: </label>
                            <label>${rout.rout.routName}</label>
                        </div>
                        <div class="form-group ">
                            <label>Train's route: </label>
                            <label>${rout.rout.startStation.stationName}
                                - ${rout.rout.endStation.stationName}</label>
                        </div>
                        <div class="form-group ">
                            <label>Date: </label>
                            <label>${rout.date.toString()}</label>
                        </div>
                        <div class="form-group ">
                            <label>Your route: </label>
                            <label>${stationFrom.stationName} - ${stationTo.stationName}</label>
                        </div>
                        <div class="form-group ">
                            <label>Departure time: </label>
                            <label>${departureTime}</label>
                        </div>
                        <div class="form-group ">
                            <label>Arrival time: </label>
                            <label>${arrivalTime}</label>
                        </div>
                        <input type="hidden" id="priceForTicket" value="${price}">
                        <div class="form-group ">
                            <label>Price: </label>
                            <label id="orderPrice">${price}</label>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="form-group">
                            <h3>Passengers</h3>
                            <div id="userMessage"></div>
                            <table class="table" id="myTablePassengers">
                                <thead>
                                <tr>
                                    <th>Last Name</th>
                                    <th>First Name</th>
                                    <th>Date of Birth</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <script id="template" type="text/x-handlebars-template">
                                    {{#each users}}
                                    <tr>
                                        <td>{{surname}}</td>
                                        <td>{{firstname}}</td>
                                        <td>{{birthDate}}</td>
                                        <td>
                                            <input type="hidden" id="idUser-{{@index}}" value="{{id}}">
                                            <input type="hidden" id="surname-{{@index}}" value="{{surname}}">
                                            <input type="hidden" id="firstname-{{@index}}" value="{{firstname}}">
                                            <input type="hidden" id="birthDate-{{@index}}" value="{{birthDate}}">
                                            <button id="btnChangeUser-{{@index}}"
                                                    onclick="userEdit('{{@index}}')">Change
                                            </button>
                                            <button id="btnDeleteUser-{{@index}}"
                                                    onclick="userDelete('{{@index}}')">Delete
                                            </button>
                                        </td>
                                    </tr>
                                    {{/each}}
                                </script>
                                </tbody>
                            </table>
                            <button onclick="showAddUser()" class="btn btn-primary pull-right">Add passenger</button>
                            <br/>
                            <div id="contPassenger" hidden>
                                <form:form id="passengerForm" modelAttribute="passengerForm" name="passengerForm">
                                    <form:input path="id" id="idForm" type="hidden"/>
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <spring:bind path="firstname">
                                            <label for="firstName">First Name</label>
                                            <form:input path="firstname" type="text" id="firstname"
                                                        placeholder="First name"
                                                        class="form-control"/>
                                            <form:errors path="firstname"></form:errors>
                                        </spring:bind>
                                    </div>
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <spring:bind path="surname">
                                            <label for="surname">Last Name</label>
                                            <form:input path="surname" type="text" id="surname" placeholder="Last name"
                                                        class="form-control"/>
                                            <form:errors path="surname"></form:errors>
                                        </spring:bind>
                                    </div>
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <spring:bind path="birthDate">
                                            <label for="birthDate">Date of Birth</label>
                                            <form:input type="date" id="birthDate" path="birthDate"
                                                        class="form-control"/>
                                            <form:errors path="birthDate"></form:errors>
                                        </spring:bind>
                                    </div>
                                    <button onclick="save()" id="btnSave">Save passenger</button>
                                </form:form> <!-- /form -->
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12">
                        <input type="hidden" id="routId" value="${rout.id}">
                        <input type="hidden" id="stationFrom" value="${stationFrom.id}">
                        <input type="hidden" id="stationTo" value="${stationTo.id}">
                        <input type="hidden" id="price" value="${price}">
                        <button id="btnBuy" onclick="buy()" class="btn btn-primary pull-left">Purchase</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div id="mapid" style="width: 300px; height: 400px;"></div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${contextPath}/resources/js/ticket.js"></script>
<script>
    var contextPath = '${contextPath}';
    getMap();
</script>
</body>
</html>
