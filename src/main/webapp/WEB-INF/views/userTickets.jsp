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

    <title>User tickets</title>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<jsp:include page="userNavbar.jsp"></jsp:include>

<div class="tab-content" id="containerContainingTabs">
    <div class="tab-pane ${selectedTab == 'ticket-tab' ? 'active' : ''} text-style" id="ticket-tab">
        <div class="container">
            <div class="list">
                <h3>Your tickets</h3>
                <table class="table" id="myTableTickets">
                    <thead>
                    <tr>
                        <th>Your name</th>
                        <th>Train</th>
                        <th>Train's rout</th>
                        <th>Date</th>
                        <th>Station from</th>
                        <th>Station to</th>
                        <th>Price</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${tickets}" var="ticket" varStatus="loop">
                        <tr>
                            <td>${ticket.profile.surname} ${ticket.profile.firstname}</td>
                            <td>${ticket.finalRout.rout.routName}</td>
                            <td>${ticket.finalRout.rout.startStation.stationName}
                                - ${ticket.finalRout.rout.endStation.stationName}</td>
                            <td>${ticket.finalRout.date}</td>
                            <td>${ticket.startStation.stationName}</td>
                            <td>${ticket.endStation.stationName}</td>
                            <td>${ticket.price}</td>
                            <td>
                                <form method="GET" action="${contextPath}/user/tickets">
                                    <input type="hidden" id="idTicket-${loop.index}" name="ticketId"
                                           value="${ticket.id}">
                                    <input type="submit" id="btnPrint-${loop.index}" name="pdf" value="PDF"/>
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
