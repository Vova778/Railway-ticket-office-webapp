<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta charset="UTF-8" http-equiv="Content-Type" name="viewport"
          content="text/html, width=device-width, initial-scale=1.0, shrink-to-fit=no"/>
    <title><fmt:message key="text.add.route"/></title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md bg-info py-3" style="height: 130px;">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="#">
            <img style="margin: 0px;margin-right: 0px;width: 110px;height: 110px;" src="img/est.2012%20(1).png"
                 width="120" height="120"></a>
        <span class="fs-3"><fmt:message key="text.brand"/></span>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-2"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-2">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item text-end">
                    <div class="container"><a class="active"
                                              href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img
                            src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;"></a>
                        <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img
                                class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png"
                                style="width: 26px;height: 26px;"></a></div>
                </li>
            </ul>
        </div>
    </div>
</nav>


<div class="container">
    <div class="col-md-8 col-xl-6 text-center mx-auto">
        <h2><fmt:message key="text.edit.route"/></h2>
    </div>
    <div class="row">
        <div class="col">
            <section class="position-relative py-4 py-xl-5">
                <div class="row d-flex justify-content-center">
                    <div class="col-md-6 col-xl-4">
                        <div class="card mb-5">
                            <div class="card-body d-flex flex-column align-items-center">
                                <form action="controller" method="post">

                                    <input hidden name="command" value="edit_route"/>
                                    <input hidden name="routeId" value="${route.id}">
                                    <input hidden name="scheduleId" value="${param.scheduleId}"/>


                                    <div class="form-group">
                                        <div class="mb-3 row">
                                            <c:if test="${routes.size() == 0 || route.stoppageNumber == 1}">

                                                <label for="startingStation"
                                                       class="form-label"><fmt:message
                                                        key="text.starting.station"/> </label>
                                                <div class="col-sm-10">
                                                    <input required list="stationList1" class="form-control"
                                                           id="startingStation"
                                                           name="startingStation" placeholder="From">
                                                    <datalist id="stationList1">
                                                        <c:forEach var="station" items="${sessionScope.stations}">
                                                            <option value="${station.name}"></option>
                                                        </c:forEach>
                                                    </datalist>
                                                </div>
                                            </c:if>

                                            <c:if test="${routes.size() != 0 && route.stoppageNumber != 1}">
                                                <label class="form-label " for="staticStartingStation"><fmt:message
                                                        key="text.starting.station"/> </label>
                                                <div class="col-sm-10">
                                                    <input readonly class="form-control text-secondary" name="startingStation"
                                                           id="staticStartingStation"
                                                           value="${route.startingStation.name}"/></div>
                                            </c:if>
                                        </div>

                                        <div class="mb-3"><label class="form-label" for="departureTime"><fmt:message
                                                key="text.departure.time"/></label>
                                            <div class="col-sm-10">
                                                <input required class="form-control"
                                                       type="time"
                                                       placeholder="Departure Time"
                                                       name="departureTime"
                                                       value="${route.departureTime}"
                                                       id="departureTime"
                                                /></div>
                                        </div>

                                        <div class="mb-3">
                                            <label for="finalStation"
                                                   class="form-label"><fmt:message key="text.final.station"/> </label>
                                            <div class="col-sm-10">
                                                <input required list="stationList2" class="form-control"
                                                       id="finalStation"
                                                       name="finalStation" placeholder="From">
                                                <datalist id="stationList2">
                                                    <c:forEach var="station" items="${sessionScope.stations}">
                                                        <option value="${station.name}"></option>
                                                    </c:forEach>
                                                </datalist>
                                            </div>
                                        </div>


                                        <div class="mb-3"><label class="form-label" for="departureTime"><fmt:message
                                                key="text.arrival.time"/></label>
                                            <div class="col-sm-10">
                                                <input required class="form-control"
                                                       type="time"
                                                       placeholder="Arrival Time"
                                                       name="arrivalTime"
                                                       value="${route.arrivalTime}"
                                                       id="arrivalTime"
                                                /></div>
                                        </div>


                                        <div class="mb-3"><label class="form-label" for="price"><fmt:message
                                                key="text.price"/></label>
                                            <div class="col-sm-10">
                                                <input required class="form-control"
                                                       type="text"
                                                       placeholder="Price"
                                                       name="price"
                                                       value="${route.price}"
                                                       id="price"/>
                                            </div>
                                        </div>


                                        <div class="mb-3">
                                            <button class="btn btn-success d-block w-100" type="submit"
                                                    value=""><fmt:message key="text.edit.route"/>
                                            </button>
                                        </div>


                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

<%@ include file="include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>
