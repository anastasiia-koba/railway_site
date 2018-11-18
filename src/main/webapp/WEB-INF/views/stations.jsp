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

    <title>Stations</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'station-tab' ? 'active' : ''} text-style" id="station-tab">
        <div id="stationContainer" class="col-sm-3">
            <form:form name="stationForm" id="stationForm" class="form-group" modelAttribute="stationForm">
                <form:input path="id" type="hidden"></form:input>
                <spring:bind path="stationName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="text" id="stationName" placeholder="New Station" class="form-control"
                                    path="stationName"/>
                        <form:errors path="stationName"></form:errors>
                    </div>
                </spring:bind>
                <button type="submit" id="btnAddStation">Save</button>
            </form:form>
        </div>
        <br/>
        <div class="container station-table">
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
                    <c:forEach items="${stations}" var="station" varStatus="loop">
                        <tr>
                            <td value="${station.stationName.toString()}">${station.stationName.toString()}</td>
                            <td>
                                <input type="hidden" id="idStation-${loop.index}" value="${station.id}">
                                <button type="button" id="btnChangeStation-${loop.index}"
                                        onclick="stationEdit('${loop.index}')">
                                    Change
                                </button>
                                <button type="button" id="btnDeleteStation-${loop.index}"
                                        onclick="stationDelete('${loop.index}')">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
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
            // getStationList();
        }).fail(function () {
            alert('Delete station failed');
        });
    }

    $('#btnAddStation').click(function () {
        event.preventDefault();

        $.post("${contextPath}/admin/stations?save", $('#stationForm').serialize()).done(function (result) {
            $('#stations').html(result);
            // $('#stationMessage').empty().text(result);
            // getStationList();
        }).fail(function (e) {
            alert('Error: ' + JSON.stringify(e));
        });
    })

    <%--function getStationList() {--%>
    <%--event.preventDefault();--%>

    <%--$.get("${contextPath}/admin/stations?list", {}).done(function (result) {--%>
    <%--&lt;%&ndash;var list = ${stationsList}.val();&ndash;%&gt;--%>
    <%--// alert(list);--%>
    <%--$('#stations').html(result);--%>
    <%--// var $select = $("#stations");--%>
    <%--// $select.find("option").remove();--%>
    <%--// $.each(result, function (index, station) {--%>
    <%--//     $("<option>").val(station.id).text(station.name).appendTo($select);--%>
    <%--// });--%>

    <%--// var $container = $("#stations");--%>
    <%--// $container.empty();--%>
    <%--// $.each(JSON.stringify(result), function(index, value) {--%>
    <%--//     $container.append(value);--%>
    <%--// })--%>

    <%--// if (result.length > 0) {--%>
    <%--//     var stationTableHTML = '<table>';--%>
    <%--//     $.each(response, function (key,value) {--%>
    <%--//         stationTableHTML +=--%>
    <%--//             '<tr><td>' + key + '</td><td>' + value + '</td></tr>';--%>
    <%--//     });--%>
    <%--//     stationTableHTML += '</table>';--%>
    <%--//     $("#products_table").html( stationTableHTML );--%>
    <%--// }--%>
    <%--}).fail(function (e) {--%>
    <%--alert('Error: ' + e);--%>
    <%--});--%>
    <%--}--%>
</script>
</body>
</html>
