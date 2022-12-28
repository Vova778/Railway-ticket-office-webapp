<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">

<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Search</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>

<nav class="navbar navbar-light navbar-expand-md bg-info py-3" style="height: 130px;">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="#">
            <img style="margin: 0px;margin-right: 0px;width: 110px;height: 110px;" src="img/est.2012%20(1).png"></a>
        <span class="fs-3"><fmt:message key="text.brand"/></span>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-2"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-2">
            <c:if test="${sessionScope.user.role.id == 1}">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item text-end">
                        <div class="container"><a class="active" href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;"></a>
                            <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" style="width: 26px;height: 26px;"></a></div>
                    </li>

                    <li class="nav-item"><a class="nav-link" href="controller?command=logout"><span style="color: var(--bs-navbar-active-color);"><fmt:message key="text.logout" /></span><br></a></li>
                    <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a></li>
                </ul>
                <a class="btn btn-primary ms-md-2" role="button" href="basket.jsp"><fmt:message key="text.basket"/> </a>
            </c:if>

            <c:if test="${sessionScope.user.role.id == 2}">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item text-end">
                        <div class="container"><a class="active" href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" ></a>
                            <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" style="width: 26px;height: 26px;"></a></div>
                    </li>
                    <li class="nav-item"><a class="nav-link active" href="controller?command=routes"><fmt:message key="text.admin.page"/></a></li>
                    <li class="nav-item"><a class="nav-link" href="controller?command=logout"><span style="color: var(--bs-navbar-active-color);"><fmt:message key="text.logout" /></span><br></a></li>
                    <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a></li>
                </ul>
                <a class="btn btn-primary ms-md-2" role="button" href="controller?command=basket"><fmt:message key="text.basket"/> </a>
            </c:if>
        </div>
    </div>
</nav>

<div class="container py-4 py-xl-5">
    <hr class="bg-secondary border-2 border-top border-secondary">
    <table class = "table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th scope="col"><fmt:message key="text.train.number" /> </th>
            <th scope="col"><fmt:message key="text.starting.station" /> </th>
            <th scope="col"><fmt:message key="text.departure.time" /> </th>
            <th scope="col"><fmt:message key="text.final.station" /> </th>
            <th scope="col"><fmt:message key="text.arrival.time" /> </th>
            <th scope="col"><fmt:message key="text.time.of.travel" /> </th>
            <th scope="col"><fmt:message key="text.available.seats" /> </th>
            <th scope="col"><fmt:message key="text.price" /> </th>
            <th scope="col"><fmt:message key="text.details" /> </th>
            <th scope="col"> </th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="route" items="${routes}">
            <tr>
                <td>${route.key.train.number}</td>
                <td>${route.key.startingStation.name}</td>
                <td>${route.key.schedule.date} ${route.key.departureTime}</td>
                <td>${route.key.finalStation.name}</td>
                <td>${route.key.schedule.date} ${route.key.arrivalTime}</td>
                <td>${route.key.travelTime}</td>
                <td>${route.key.availableSeats}</td>
                <td>${route.key.price}</td>
                <td> <a href=""> <fmt:message key="text.details" /> </a>
                </td>
                <td>
                    <form action="controller" method="post">
                        <input hidden name="command" value="book_ticket"/>
                        <input hidden name="trainNumber" value="${route.key.train.number}"/>
                    <button type="submit"
                            class="btn btn-outline-info" >
                        <fmt:message key="text.book" /></button></form>
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