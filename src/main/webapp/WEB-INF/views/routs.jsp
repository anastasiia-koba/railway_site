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

    <title>Routs</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'rout-tab' ? 'active' : ''} text-style" id="rout-tab">
        <div class="container">
            <form:form method="POST" modelAttribute="routForm" action="${contextPath}/admin/routs">
                <form:input path="id" type="hidden"></form:input>
                <spring:bind path="routName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label>Rout name</label>
                        <form:input type="text" id="routName" placeholder="New Rout" class="form-control"
                                    path="routName"></form:input>
                        <form:errors path="routName"></form:errors>
                    </div>
                </spring:bind>

                <spring:bind path="startStation">
                    <div class="col-md-2 pt-3">
                        <div class="form-group ">
                            <label>Select start </label>
                            <select id="comboboxStart" placeholder="From where?" name="startStation"
                                    class="form-control">
                                <option value="${routForm.startStation.stationName.toString()}">${routForm.startStation.stationName.toString()}</option>
                                <c:forEach items="${stationsFrom}" var="station">
                                    <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                                </c:forEach>
                            </select>
                            <form:errors path="startStation"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <spring:bind path="endStation">
                    <div class="col-md-2 pt-3">
                        <div class="form-group">
                            <label>Select end </label>
                            <select id="comboboxEnd" placeholder="To where?" name="endStation" class="form-control">
                                <option value="${routForm.endStation.stationName.toString()}">${routForm.endStation.stationName.toString()}</option>
                                <c:forEach items="${stationsTo}" var="station">
                                    <option value="${station.stationName.toString()}">${station.stationName.toString()}</option>
                                </c:forEach>
                            </select>
                            <form:errors path="endStation"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <button type="submit" name="save">Save</button>
            </form:form>
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
                    <c:forEach items="${routs}" var="rout" varStatus="loop">>
                        <tr>
                            <td value="${rout.routName.toString()}">${rout.routName.toString()}</td>
                            <td value="${rout.startStation.stationName.toString()}">${rout.startStation.stationName.toString()}</td>
                            <td value="${rout.endStation.stationName.toString()}">${rout.endStation.stationName.toString()}</td>
                            <form method="POST" action="${contextPath}/admin/routs">
                                <td>
                                    <input type="hidden" name="id" value="${rout.id}">
                                    <button id="btnChangeRout-${loop.index}"
                                            onclick="routEdit('${loop.index}')">Change</button>
                                    <button id="btnDeleteRout-${loop.index}"
                                            onclick="routDelete('${loop.index}')">Delete</button>
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

<script>
    function routEdit(index) {
        event.preventDefault();

        var rout = $("#idRout-" + index).val();

        var object = {routId: rout};

        $.post("${contextPath}/admin/routs?change", object).done(function (result) {
            $('#routMessage').text('');

            for (var list = Object.keys(result), i = list.length, form = document.forms.namedItem('routForm'); i--;) {
                if (form[list[i]]) {
                    form[list[i]].value = result[list[i]];
                }
            }
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
            // getRoutList();
        }).fail(function () {
            alert('Delete rout failed');
        });
    }
</script>
</body>
</html>
