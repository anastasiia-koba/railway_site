<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

<div class="container-fluid">
    <nav class="navbar navbar-default">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#navbar-collapse-2">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${contextPath}/home">Some railway</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="navbar-collapse-2">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">Home</a></li>
                    <li><a href="${contextPath}/schedule">Schedule</a></li>
                    <li><a href="#">Contact</a></li>

                    <c:if test="${pageContext.request.userPrincipal.name != null}">
                        <c:if test="${pageContext.request.isUserInRole('ADMIN')}">
                            <li class="dropdown">
                                <a href="#" data-toggle="dropdown">Admin <span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li><a href="${contextPath}/admin/passengers">Passengers</a></li>
                                    <li><a href="${contextPath}/admin/stations">Build routs</a></li>
                                </ul>
                            </li>
                        </c:if>
                    </c:if>

                    <li>
                        <c:if test="${pageContext.request.userPrincipal.name != null}">
                            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </form>
                            <a class="btn btn-default btn-outline btn-circle"
                               onclick="document.forms['logoutForm'].submit()">Logout</a>
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name == null}">
                            <a class="btn btn-default btn-outline btn-circle" href="${contextPath}/login">Sign in</a>
                        </c:if>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container -->
    </nav><!-- /.navbar -->
</div>
