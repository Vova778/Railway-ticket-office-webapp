<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">

<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="text.basket" /> </title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>

<nav class="navbar navbar-light navbar-expand-md bg-info py-3" style="height: 130px;">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="#">
            <img style="margin: 0px;margin-right: 0px;width: 110px;height: 110px;" src="img/est.2012%20(1).png" width="120" height="120"></a>
        <span class="fs-3"><fmt:message key="text.brand"/></span>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-2"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-2">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item text-end">
                    <div class="container"><a class="active" href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="22" height="22"></a>
                        <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="22" height="22" style="width: 26px;height: 26px;"></a></div>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container py-4 py-xl-5">
    <div class="col-md-8 col-xl-6 text-center mx-auto" style="margin-top: 50px" >
        <h2><fmt:message key="text.hello"/> <c:out value="${sessionScope.user.firstName }"/> <c:out value="${sessionScope.user.lastName }"/></h2>
    </div>
    <hr class="bg-secondary border-2 border-top border-secondary">
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="text.train.number" /> </th>
            <th scope="col"><fmt:message key="text.starting.station" /> </th>
            <th scope="col"><fmt:message key="text.departure.time" /> </th>
            <th scope="col"><fmt:message key="text.final.station" /> </th>
            <th scope="col"><fmt:message key="text.arrival.time" /> </th>
            <th scope="col"><fmt:message key="text.fare" /> </th>
            <th scope="col"><fmt:message key="text.ticket.status" /> </th>
            <th scope="col"> </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="station" items="${tickets}">
            <tr>
                <td><c:out value="${station.trainNumber}"/>
                </td>
                <td><c:out value="${station.startingStation}"/>
                </td>
                <td><c:out value="${station.departureTime}"/>
                </td>
                <td><c:out value="${station.finalStation}"/>
                </td>
                <td><c:out value="${station.arrivalTime}"/>
                </td>
                <td><c:out value="${station.fare}"/>
                </td>
                <td><c:out value="${station.ticketStatus}"/>
                </td>
                <td> <a href=""> <fmt:message key="text.details" /> </a>
                </td>
                <td>
                    <button type="button"
                            class="btn btn-outline-danger" onclick="window.location='controller?command=cancel_ticket&ticket=${station.id}'">
                        <fmt:message key="text.cancel" /></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <nav>
        <ul class="pagination justify-content-center">
            <c:if test="${param.page-1 >= 1}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${param.page-1}">Previous</a>
                </li>
            </c:if>

            <c:forEach var="page" items="${pages}">

                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${page}">${page}</a>
                </li>

            </c:forEach>
            <c:set var="size" scope="page" value="${requestScope.pages}"/>

            <c:if test="${param.page+1 <= size.size()}">
                <li class="page-item"><a class="page-link"
                                         href="controller?command=${param.command}&page=${param.page+1}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>
<%@ include file="include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>