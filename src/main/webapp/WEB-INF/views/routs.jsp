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

    <title>Routs</title>
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
                                <label>Rout name</label>
                                <form:input type="text" id="routName" placeholder="New Rout" class="form-control"
                                            path="routName"></form:input>
                                <form:errors path="routName"></form:errors>
                            </div>
                        </spring:bind>
                    </div>
                    <spring:bind path="startStation">
                        <div class="col-md-2 pt-3">
                            <div class="form-group ">
                                <label>Select start </label>
                                <form:select path="startStation" id="comboboxStart" name="startStation"
                                             class="form-control">
                                    <option></option>
                                    <c:forEach items="${stationsFrom}" var="station">
                                        <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                                    </c:forEach>
                                </form:select>
                            </div>
                        </div>
                    </spring:bind>

                    <spring:bind path="endStation">
                        <div class="col-md-2 pt-3">
                            <div class="form-group">
                                <label>Select end </label>
                                <form:select path="endStation" id="comboboxEnd" name="endStation" class="form-control">
                                    <option></option>
                                    <c:forEach items="${stationsTo}" var="station">
                                        <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                                    </c:forEach>
                                </form:select>
                                <form:errors path="endStation"></form:errors>
                            </div>
                        </div>
                    </spring:bind>

                    <div class="col-md-3">
                        <button type="submit" id="btnAddRout">Save</button>
                        <button type="submit" id="btnClearRout">Clear</button>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="container routs-table">
            <div class="list">
                <h3>Routs</h3>
                <div id="routMessage"></div>
                <table class="table" id="myTableRouts">
                    <thead>
                    <tr>
                        <th>Rout name</th>
                        <th>Station from</th>
                        <th>Station to</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <script id="template" type="text/x-handlebars-template">
                        {{#each routs}}
                        <tr>
                            <td value="{{routName}}">{{routName}}</td>
                            <td value="{{startStation.stationName}}">{{startStation.stationName}}</td>
                            <td value="{{endStation.stationName}}">{{endStation.stationName}}</td>
                            <form method="POST" action="${contextPath}/admin/routs">
                                <td>
                                    <input type="hidden" id="idRout-{{@index}}" value="{{id}}">
                                    <button id="btnChangeRout-{{@index}}"
                                            onclick="routEdit('{{@index}}')">Change
                                    </button>
                                    <button id="btnDeleteRout-{{@index}}"
                                            onclick="routDelete('{{@index}}')">Delete
                                    </button>
                                </td>
                            </form>
                        </tr>
                        {{/each}}
                    </script>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    function routEdit(index) {
        event.preventDefault();

        var rout = $("#idRout-" + index).val();

        var object = {routId: rout};

        $.post("${contextPath}/admin/routs?change", object).done(function (result) {
            $('#routMessage').text('');

            $('form[name=routForm]').val(result);
            $('#idForm').val(result.id);
            $('#routName').val(result.routName);
            $('#comboboxStart').val(result.startStation.stationName);
            $('#comboboxEnd').val(result.endStation.stationName);
        }).fail(function (e) {
            alert('Error: ' + e);
        });
    }

    function routDelete(index) {
        event.preventDefault();

        var rout = $("#idRout-" + index).val();

        var object = {routId: rout};

        $.post("${contextPath}/admin/routs?delete", object).done(function (result) {
            $('#routMessage').empty().text(result);
            getRoutList();
        }).fail(function () {
            alert('Delete rout failed');
        });
    }

    $('#btnAddRout').click(function () {
        event.preventDefault();

        $.post("${contextPath}/admin/routs?save", $('#routForm').serialize()).done(function (result) {
            $('#routMessage').empty().text(result);
            getRoutList();
        }).fail(function (e) {
            alert('Error: ' + JSON.stringify(e));
        });
    })

    $('#btnClearRout').click(function () {
        event.preventDefault();
        $('form input[type="text"], form input[type="hidden"]').val('');
        $('form[name=routForm]').trigger('reset');
    })

    function getRoutList() {
        event.preventDefault();

        $.get("${contextPath}/admin/routs?list", {}).done(function (result) {
            var data = {routs: result};
            var template = Handlebars.compile($('#template').html());
            $("#myTableRouts tr>td").remove();
            $('.table').append(template(data));
        }).fail(function (e) {
            alert('Error: ' + e);
        });
    }

    window.onload = getRoutList;
</script>
</body>
</html>
