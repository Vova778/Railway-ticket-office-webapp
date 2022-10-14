<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <h2><fmt:message key="text.schedule"/></h2>
    </div>
    <hr class="bg-secondary border-2 border-top border-secondary">
    <div class="container justify-content-center">
        <a href="controller?command=route_form&scheduleId=${param.scheduleId}" style="text-decoration:none;">
            <button type="button" class="btn btn-outline-secondary">
                <fmt:message key="text.add.route"/></button>
        </a>
    </div>
    <hr class="bg-secondary border-2 border-top border-secondary">
    <table class="table">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="text.train.number"/></th>
            <th scope="col"><fmt:message key="text.stoppage.number"/> </th>
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
                <td><c:out value="${route.train.number}"/>
                </td>
                <td><c:out value="${route.stoppageNumber}"/>
                </td>
                <td><c:out value="${route.startingStation.name}"/>
                </td>
                <td><c:out value="${route.departureTime}"/>
                </td>
                <td><c:out value="${route.finalStation.name}"/>
                </td>
                <td><c:out value="${route.arrivalTime}"/>
                </td>
                <td><c:out value="${route.availableSeats}"/>
                </td>
                <td><c:out value="${route.day}"/>
                </td>
                <td><c:out value="${route.price}"/>
                </td>
                <td>
                    <button type="button"
                            class="btn btn-outline-warning"
                            onclick="window.location='controller?command=edit_route_form&routeId=${route.id}&scheduleId=${param.scheduleId}'">
                        Edit</button>
                </td>
                <td>
                    <button type="button"
                            class="btn btn-outline-danger" onclick="window.location='controller?command=remove_route&routeId=${route.id}&scheduleId=${param.scheduleId}'">
                        Remove</button>
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
