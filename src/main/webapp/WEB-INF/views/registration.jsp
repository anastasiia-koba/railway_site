<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

    <link type="text/css" rel="stylesheet" href="${contextPath}/resources/css/registration.css">
    <script type="text/javascript" src="${contextPath}/resources/js/registration.js"></script>
    <title>Create an account</title>
</head>
<body>
<div class="container">
    <form:form method="POST" modelAttribute="userForm" id="userForm" class="form-horizontal" role="form"
               data-toggle="validator">
        <h2>Create your account</h2>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="username" class="col-sm-3 control-label">Login</label>
                <div class="col-sm-9">
                    <form:input type="text" id="username" placeholder="Username" class="form-control" autofocus="true"
                                path="username"></form:input>
                    <form:errors path="username"></form:errors>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="firstname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="firstName" class="col-sm-3 control-label">First Name</label>
                <div class="col-sm-9">
                    <form:input type="text" id="firstname" placeholder="First name" class="form-control"
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
                    <form:input type="text" id="surname" placeholder="Last name" class="form-control" autofocus="true"
                                path="surname"></form:input>
                    <form:errors path="surname"></form:errors>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="password" class="col-sm-3 control-label">Password</label>
                <div class="col-sm-9">
                    <form:input type="password" path="password" class="form-control"
                                placeholder="Password"></form:input>
                    <form:errors path="password"></form:errors>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="confirmPassword">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label for="password" class="col-sm-3 control-label">Confirm Password</label>
                <div class="col-sm-9">
                    <form:input type="password" path="confirmPassword" class="form-control"
                                placeholder="Confirm your password"></form:input>
                    <form:errors path="validPasswords"></form:errors>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="birthDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-3 control-label">Date of Birth</label>
                <div class="col-sm-9">
                    <form:input type="date" name="birthDate" path="birthDate" class="form-control"></form:input>
                    <form:errors path="birthDate"></form:errors>
                </div>
            </div>
        </spring:bind>

        <button type="submit" class="btn btn-primary btn-block">Register</button>
    </form:form> <!-- /form -->
</div> <!-- ./container -->
</body>
</html>
