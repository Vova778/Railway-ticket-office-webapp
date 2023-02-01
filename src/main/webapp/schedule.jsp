<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://train.com/jsp/tld/mytags" prefix="custom" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">

<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Schedule</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>

<nav class="navbar navbar-light navbar-expand-md bg-info py-3" style="height: 130px;">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" >
            <img style="margin: 0;width: 110px;height: 110px;" src="img/est.2012%20(1).png" width="500"
                 height="500"></a>
        <span class="fs-3"><fmt:message key="text.brand"/></span>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-2"><span
                class="visually-hidden">Toggle navigation</span>
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navcol-2">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item text-end">
                    <div class="container"><a class="active"
                                              href="controller?command=setLang&locale=ua&pageToProcess=${param.command}">
                        <img src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;"></a>
                        <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}">
                            <img class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png"
                                 style="width: 26px;height: 26px;"></a></div>
                </li>
                <li class="nav-item"><a class="nav-link active" href="controller?command=trains"><fmt:message
                        key="text.admin.page"/></a></li>
                <li class="nav-item"><a class="nav-link" href="controller?command=logout"><span
                        style="color: var(--bs-navbar-active-color);"><fmt:message key="text.logout"/></span><br></a>
                </li>
                <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a></li>
            </ul>
            <a class="btn btn-primary ms-md-2" role="button" href="controller?command=basket">
                <fmt:message key="text.basket"/>
            </a>
        </div>
    </div>
</nav>
<div class="container py-4 py-xl-5">
    <div class="col-md-8 col-xl-6 text-center mx-auto">
        <h2><fmt:message key="text.schedule"/> ID: ${param.scheduleId}</h2>
    </div>
    <hr class="bg-secondary border-2 border-top border-secondary">
    <div class="container justify-content-center">
        <button type="button"
                onclick="window.location='controller?command=add_route_form&scheduleId=${param.scheduleId}'"
                class="btn btn-info">
            <fmt:message key="text.add.route"/>
        </button>

        <button type="button"
                onclick="window.location='controller?command=remove_schedule&scheduleId=${param.scheduleId}'"
                class="btn btn-danger">
            <fmt:message key="text.remove.schedule"/>
        </button>
    </div>
    <hr class="bg-secondary border-2 border-top border-secondary">
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="text.stoppage.number"/></th>
            <th scope="col"><fmt:message key="text.train.number"/></th>
            <th scope="col"><fmt:message key="text.starting.station"/></th>
            <th scope="col"><fmt:message key="text.departure.time"/></th>
            <th scope="col"><fmt:message key="text.final.station"/></th>
            <th scope="col"><fmt:message key="text.arrival.time"/></th>
            <th scope="col"><fmt:message key="text.available.seats"/></th>
            <th scope="col"><fmt:message key="text.day"/></th>
            <th scope="col"><fmt:message key="text.price"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="route" items="${routes}">
            <tr>
                <td>${route.stoppageNumber}</td>
                <td>${route.train.number}</td>
                <td>${route.startingStation.name}</td>
                <td>${route.departureTime}</td>
                <td>${route.finalStation.name}</td>
                <td>${route.arrivalTime}</td>
                <td>${route.availableSeats}</td>
                <td>${route.day}</td>
                <td><custom:NumberFormatter number="${route.price}" format="${sessionScope.lang}"/></td>
                <td>
                    <button type="button"
                            class="btn btn-info"
                            onclick="window.location='controller?command=edit_route_form&routeId=${route.id}&scheduleId=${param.scheduleId}'">
                        <fmt:message key="text.edit.route"/>
                    </button>
                </td>
                <td>
                    <button type="button"
                            class="btn btn-danger"
                            onclick="window.location='controller?command=remove_route&routeId=${route.id}&scheduleId=${param.scheduleId}'">
                        <fmt:message key="text.remove"/></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>
