<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: ananasa
  Date: 13.10.18
  Time: 18:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users list</title>
</head>
<body>
    <spring:form modelAttribute="userFromServer" method="post" action="/users/check">
        <spring:input path="name"/>
        <spring:input path="password"/>
        <spring:button >check user</spring:button>
    </spring:form>
</body>
</html>
