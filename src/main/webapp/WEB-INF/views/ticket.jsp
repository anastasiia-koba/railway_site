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

    <script type="text/javascript" src="${contextPath}/resources/js/ticket.js"></script>
    <title>Buy ticket</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>

<div class="container">
    <h2 id="errorMessage"></h2>
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Train: </label>
            <label>${rout.rout.routName.toString()}</label>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Train's rout: </label>
            <label>${rout.rout.startStation.stationName.toString()}
                - ${rout.rout.endStation.stationName.toString()}</label>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Date: </label>
            <label>${rout.date.toString()}</label>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Your rout: </label>
            <label>${stationFrom.stationName.toString()} - ${stationTo.stationName.toString()}</label>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="form-group ">
            <label>Price: </label>
            <label>${price}</label>
        </div>
    </div>
    <div class="col-md-2">
        <div class="form-group ">
            <%--<form id="submitForm">--%>
                <input type="hidden" id="routId" value="${rout.id}">
                <input type="hidden" id="stationFrom" value="${stationFrom.id}">
                <input type="hidden" id="stationTo" value="${stationTo.id}">
                <input type="hidden" id="price" value="${price}">
                <button type="submit" id="btnBuy">Purchase</button>
            <%--</form>--%>
        </div>
    </div>
</div>

<div class="container">
    <h3>Passenger</h3>
    <table class="table">
        <thead>
        <tr>
            <th>Last Name</th>
            <th>First Name</th>
            <th>Date of Birth</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${user.surname}</td>
            <td>${user.firstname}</td>
            <td>${user.birthDate}</td>
        </tr>
        </tbody>
    </table>
    <button onclick="showAddUser()" class="btn btn-primary btn-block">Add passenger</button>
</div>


<div class="container passengers-table">
    <div class="list">
        <h3>Additional passengers</h3>
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
    </div>
</div>


<div class="container" id="passenger" hidden>
    <form id="passengerForm" name="passengerForm" class="form-horizontal" role="form">
        <input path="id" id="idForm" type="hidden">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label for="firstName" class="col-sm-3 control-label">First Name</label>
            <div class="col-sm-9">
                <input type="text" id="firstname" placeholder="First name" class="form-control">
                <%--<form:errors path="firstname"></form:errors>--%>
            </div>
        </div>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label for="surname" class="col-sm-3 control-label">Last Name</label>
            <div class="col-sm-9">
                <input type="text" id="surname" placeholder="Last name" class="form-control">
                <%--<form:errors path="surname"></form:errors>--%>
            </div>
        </div>
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <label class="col-sm-3 control-label">Date of Birth</label>
            <div class="col-sm-9">
                <input type="date" id="birthDate" path="birthDate" class="form-control">
                <%--<form:errors path="birthDate"></form:errors>--%>
            </div>
        </div>
        <div class="col-md-3">
            <button type="submit" id="btnSave">Save passenger</button>
            <button type="submit" id="btnClear">Clear</button>
        </div>
    </form> <!-- /form -->
</div>

<%--<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
<%--<div class="modal-dialog" role="document">--%>
<%--<div class="modal-content">--%>
<%--<div class="modal-header">--%>
<%--<button type="button" class="close" data-dismiss="modal" aria-label="Close">--%>
<%--<span aria-hidden="true">&times;</span>--%>
<%--</button>--%>
<%--<h4 class="modal-title" id="myModalLabel">You have successfully bought a ticket!</h4>--%>
<%--</div>--%>
<%--<div class="modal-footer">--%>
<%--<button type="button" class="btn btn-secondary" data-dismiss="modal">Ok</button>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<script type="text/javascript" src="${contextPath}/resources/js/ticket.js"></script>
<script>
    var contextPath = '${contextPath}';
</script>
</body>
</html>
