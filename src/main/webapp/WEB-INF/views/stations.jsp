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

    <title>Stations</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'station-tab' ? 'active' : ''} text-style" id="station-tab">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <form:form name="stationForm" id="stationForm" class="form-group" modelAttribute="stationForm">
                    <form:input path="id" type="hidden"></form:input>
                    <div class="col-md-3">
                        <spring:bind path="stationName">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input type="text" id="stationName" placeholder="New Station" class="form-control"
                                            path="stationName"/>
                                <form:errors path="stationName"></form:errors>
                            </div>
                        </spring:bind>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" id="btnAddStation">Save</button>
                        <button type="submit" id="btnClearStation">Clear</button>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="container">
            <div class="list">
                <h3>Stations</h3>
                <div id="stationMessage"></div>
                <table class="table" id="myTableStations">
                    <thead>
                    <tr>
                        <th>Station name</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <script id="template" type="text/x-handlebars-template">
                        {{#each stations}}
                        <tr>
                            <td value="{{stationName}}">{{stationName}}</td>
                            <td>
                                <input type="hidden" id="idStation-{{@index}}" value="{{id}}">
                                <button type="button" id="btnChangeStation-{{@index}}"
                                        onclick="stationEdit('{{@index}}')">
                                    Change
                                </button>
                                <button type="button" id="btnDeleteStation-{{@index}}"
                                        onclick="stationDelete('{{@index}}')">
                                    Delete
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

<script>
    function stationEdit(index) {
        event.preventDefault();

        var station = $("#idStation-" + index).val();

        var object = {stationId: station};

        $.post("${contextPath}/admin/stations?change", object).done(function (result) {
            $('#stationMessage').text('');

            for (var list = Object.keys(result), i = list.length, form = document.forms.namedItem('stationForm'); i--;) {
                if (form[list[i]]) {
                    form[list[i]].value = result[list[i]];
                }
            }
            // $("#btnChangeStation-"+index).prop("disabled", true);
        }).fail(function (e) {
            alert('Error: ' + e);
        });
    }

    function stationDelete(index) {
        event.preventDefault();

        var station = $("#idStation-" + index).val();

        var object = {stationId: station};

        $.post("${contextPath}/admin/stations?delete", object).done(function (result) {
            $('#stationMessage').empty().text(result);
            getStationList();
        }).fail(function () {
            alert('Delete station failed');
        });
    }

    $('#btnAddStation').click(function () {
        event.preventDefault();

        $.post("${contextPath}/admin/stations?save", $('#stationForm').serialize()).done(function (result) {
            $('#stationMessage').empty().text(result);
            getStationList();
        }).fail(function (e) {
            alert('Error: ' + JSON.stringify(e));
        });
    })

    $('#btnClearStation').click(function () {
        event.preventDefault();
        $('form input[type="text"], form input[type="hidden"]').val('');
    })

    function getStationList() {
        event.preventDefault();

        $.get("${contextPath}/admin/stations?list", {}).done(function (result) {
            var data = {stations: result};
            var template = Handlebars.compile($('#template').html());
            $("#myTableStations tr>td").remove();
            $('.table').append(template(data));
        }).fail(function (e) {
            alert('Error: ' + e);
        });
    }

    window.onload = getStationList;
</script>
</body>
</html>
