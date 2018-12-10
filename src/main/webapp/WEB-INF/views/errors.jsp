<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h3>This is default exception page</h3>
<h1>${errorMsg}</h1>
<p>Exception: <b>${exception}</b></p>
<a href="/">Go Home</a>
</body>
</html>
