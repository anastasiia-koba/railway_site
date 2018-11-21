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

    <title>Trains</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'train-tab' ? 'active' : ''} text-style" id="train-tab">
        <div class="container">
            <div class="row align-items-center justify-content-center">
                <form:form modelAttribute="trainForm" if="trainForm" name="trainForm">
                    <div class="col-md-3">
                        <spring:bind path="trainName">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <form:input path="id" id="idForm" type="hidden"></form:input>
                                <form:input type="text" id="trainName" placeholder="New Train" class="form-control"
                                            path="trainName"></form:input>
                                <form:errors path="trainName"></form:errors>
                            </div>
                        </spring:bind>
                    </div>
                    <div class="col-md-3">
                        <spring:bind path="placesNumber">

                            <form:input type="text" id="placesNumber" placeholder="Count" class="form-control"
                                        path="placesNumber"></form:input>
                            <form:errors path="placesNumber"></form:errors>

                        </spring:bind>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" id="btnAddTrain">Save</button>
                    </div>
                </form:form>
            </div>

            <div class="container train-table">
                <div class="list">
                    <h3>Trains</h3>
                    <div id="trainMessage"></div>
                    <table class="table" id="myTableTrains">
                        <thead>
                        <tr>
                            <th>Train name</th>
                            <th>Places</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <script id="template" type="text/x-handlebars-template">
                            {{#each trains}}
                            <tr>
                                <td value="{{trainName}}">{{trainName}}</td>
                                <td value="{{placesNumber}}">{{placesNumber}}</td>
                                <td>
                                    <input type="hidden" id="idTrain-{{@index}}" value="{{id}}">
                                    <button id="btnChangeTrain-{{@index}}"
                                            onclick="trainEdit('{{@index}}')">Change
                                    </button>
                                    <button id="btnDeleteTrain-{{@index}}"
                                            onclick="trainDelete('{{@index}}')">Delete
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
        function trainEdit(index) {
            event.preventDefault();

            var train = $("#idTrain-" + index).val();

            var object = {trainId: train};

            $.post("${contextPath}/admin/trains?change", object).done(function (result) {
                $('#trainMessage').text('');

                $('form[name=trainForm]').val(result);
                $('#idForm').val(result.id);
                $('#trainName').val(result.trainName);
                $('#placesNumber').val(result.placesNumber);
            }).fail(function (e) {
                alert('Error: ' + e);
            });
        }

        function trainDelete(index) {
            event.preventDefault();

            var train = $("#idTrain-" + index).val();

            var object = {trainId: train};

            $.post("${contextPath}/admin/trains?delete", object).done(function (result) {
                $('#trainMessage').empty().text(result);
                getTrainList();
            }).fail(function () {
                alert('Delete rout failed');
            });
        }

        $('#btnAddTrain').click(function () {
            event.preventDefault();

            $.post("${contextPath}/admin/trains?save", $('#trainForm').serialize()).done(function (result) {
                $('#trainMessage').empty().text(result);
                getTrainList();
            }).fail(function (e) {
                alert('Error: ' + JSON.stringify(e));
            });
        })

        $('#btnClearTrain').click(function () {
            event.preventDefault();
            $('form input[type="text"], form input[type="hidden"]').val('');
        })

        function getTrainList() {
            event.preventDefault();

            $.get("${contextPath}/admin/trains?list", {}).done(function (result) {
                var data = {trains: result};
                var template = Handlebars.compile($('#template').html());
                $("#myTableTrains tr>td").remove();
                $('.table').append(template(data));
            }).fail(function (e) {
                alert('Error: ' + e);
            });
        }

        window.onload = getTrainList;
    </script>
</body>
</html>
