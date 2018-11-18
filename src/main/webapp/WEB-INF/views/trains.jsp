<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <title>Trains</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="adminNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'train-tab' ? 'active' : ''} text-style" id="train-tab">
        <div class="container">
            <form:form method="POST" modelAttribute="trainForm" action="${contextPath}/admin/trains">
                <spring:bind path="trainName">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="id" type="hidden"></form:input>
                        <form:input type="text" id="trainName" placeholder="New Train" class="form-control"
                                    path="trainName"></form:input>
                        <form:errors path="trainName"></form:errors>

                        <form:input type="text" id="placesNumber" placeholder="Count" class="form-control"
                                    path="placesNumber"></form:input>
                        <form:errors path="placesNumber"></form:errors>
                    </div>
                </spring:bind>
                <button type="submit" name="save">Save</button>
            </form:form>
        </div>

        <div class="container train-table">
            <div class="list">
                <h3>Trains</h3>
                <table class="table" id="myTableTrains">
                    <thead>
                    <tr>
                        <th>Train name</th>
                        <th>Places</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${trains}" var="train">
                        <tr>
                            <td value="${train.trainName.toString()}">${train.trainName.toString()}</td>
                            <td value="${train.placesNumber.toString()}">${train.placesNumber.toString()}</td>
                            <td>
                                <form method="POST" action="${contextPath}/admin/trains">
                                    <input type="hidden" name="id" value="${train.id}">
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
    </div>
</div>

</body>
</html>
