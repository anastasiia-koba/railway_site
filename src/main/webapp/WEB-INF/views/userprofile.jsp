<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/registration.css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.min.js"></script>

    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/registration.css">
    <script type="text/javascript" src="${contextPath}/resources/js/registration.js"></script>

    <title>User profile</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="userNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'profile-tab' ? 'active' : ''} text-style" id="profile-tab">
        <div class="container">
            <form:form modelAttribute="userForm" id="userForm" class="form-horizontal"
                       role="form" data-toggle="validator">
                <h2 id="message"></h2>
                <form:input path="id" id="idForm" type="hidden"></form:input>
                <spring:bind path="firstname">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label for="firstName" class="col-sm-3 control-label">First Name</label>
                        <div class="col-sm-9">
                            <form:input type="text" id="firstname" placeholder="First name"
                                        class="form-control"
                                        autofocus="true"
                                        path="firstname"></form:input>
                            <form:errors path="firstname"></form:errors>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="surname">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label for="surname" class="col-sm-3 control-label">Last Name</label>
                        <div class="col-sm-9">
                            <form:input type="text" id="surname" placeholder="Last name"
                                        class="form-control"
                                        autofocus="true"
                                        path="surname"></form:input>
                            <form:errors path="surname"></form:errors>
                        </div>
                    </div>
                </spring:bind>
                <spring:bind path="birthDate">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <label class="col-sm-3 control-label">Date of Birth</label>
                        <div class="col-sm-9">
                            <form:input type="date" name="birthDate" path="birthDate"
                                        class="form-control"></form:input>
                            <form:errors path="birthDate"></form:errors>
                        </div>
                    </div>
                </spring:bind>

                <button type="submit" class="btn btn-primary btn-block" id="btnSave">Save</button>
            </form:form> <!-- /form -->
        </div> <!-- ./container -->
    </div>
</div>
<script>
    $('#userForm').submit(function (event) {
        event.preventDefault();

        $.post("${contextPath}/user/profile", $('#userForm').serialize()).done(function (result) {
            $('#message').empty().text(result);
        }).fail(function (e) {
            alert('Error: ' + JSON.stringify(e));
        });
    })
</script>
</body>
</html>
