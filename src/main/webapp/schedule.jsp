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
        <a class="navbar-brand d-flex align-items-center" href="#">
            <img style="margin: 0px;width: 110px;height: 110px;" src="img/est.2012%20(1).png"></a>
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
                <li class="nav-item"><a class="nav-link active" href="controller?command=routes"><fmt:message
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
    <div class="col-md-8 col-xl-6 text-center mx-auto" style="margin-top: 50px">
        <h2><fmt:message key="text.schedule"/></h2>
    </div>
    <hr class="bg-secondary border-2 border-top border-secondary">
    <div class="container justify-content-center">
           <%-- <button type="button"
                    data-bs-toggle="modal"
                    data-bs-target="#modalAddRouteForm"
                    class="btn btn-outline-secondary">
                <fmt:message key="text.add.route"/>
            </button> --%>
               <button type="button"
                       onclick="window.location='controller?command=add_route_form'"
                       class="btn btn-outline-secondary">
                   <fmt:message key="text.add.route"/>
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
                <td><custom:NumberFormatter number="${route.price}" format="${sessionScope.lang}" /></td>
                <td>
                    <button type="button"
                            class="btn btn-outline-info"
                            onclick="window.location='controller?command=edit_route_form&routeId=${route.id}&scheduleId=${param.scheduleId}'">
                        <fmt:message key="text.edit.route"/>
                    </button>
                   <%-- <button type="button" data-bs-toggle="modal"
                            data-bs-target="#modalEditRouteForm"
                            id="editBtn"
                            class="btn btn-outline-secondary"
                            data-bs-id="${route.id}"
                            data-bs-departureTime="${route.departureTime}"
                            data-bs-arrivalTime="${route.departureTime}"
                            data-bs-startingStation="${route.startingStation.name}"
                            data-bs-finalStation="${route.finalStation.name}"
                            data-bs-price="${route.price}">
                        <fmt:message key="text.edit.route"/>
                    </button> --%>

                </td>
                <td>
                    <button type="button"
                            class="btn btn-outline-danger"
                            onclick="window.location='controller?command=remove_route&routeId=${route.id}&scheduleId=${param.scheduleId}'">
                        <fmt:message key="text.remove"/></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>

<div class="modal fade" id="modalAddRouteForm" tabindex="-1" role="form" aria-labelledby="modalAddRouteFormLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form class="text-center" action="controller" method="post">

            <input hidden name="command" value="add_route"/>
            <input hidden name="scheduleId" value="${param.scheduleId}"/>
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h4 class="modal-title w-100 font-weight-bold">
                        <fmt:message key="text.add.route"/>
                    </h4>
                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">

                    <%--@elvariable id="routes" type="java.util.List"--%>
                    <c:if test="${routes.size() == 0}">
                        <label>
                            <select name="startingStation" style="margin-bottom: 10px">
                                    <%--@elvariable id="stations" type="java.util.List"--%>
                                <c:forEach var="station" items="${stations}">
                                    <option value="${station.name}">${station.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </c:if>

                    <c:if test="${routes.size() != 0}">
                        <input readonly name="startingStation"
                               value="${routes.get(routes.size()-1).finalStation.name}"/>
                    </c:if>


                    <div style="margin-bottom: 16px;">
                        <input class="form-control" type="time"
                               placeholder="Departure Time"
                               name="departureTime"
                        />
                    </div>

                    <label>
                        <select name="finalStation" style="margin-bottom: 10px">
                            <%--@elvariable id="stations" type="java.util.List"--%>
                            <c:forEach var="station" items="${stations}">
                                <option value="${station.name}">${station.name}</option>
                            </c:forEach>
                        </select>
                    </label>

                    <div style="margin-bottom: 16px;"><input class="form-control" type="time"
                                                             placeholder="Arrival Time"
                                                             name="arrivalTime"/></div>

                    <div style="margin-bottom: 16px;"><input class="form-control"
                                                             type="text"
                                                             placeholder="Price"
                                                             name="price"/></div>


                </div>
                <div class="modal-footer d-flex justify-content-center">
                    <div class="d-grid mb-2">
                        <button class="btn btn-primary btn-login text-uppercase fw-bold"
                                type="submit">
                            <fmt:message key="text.add.route"/>
                        </button>
                    </div>

                </div>
            </div>
        </form>
    </div>
</div>


<div class="modal fade"
     id="modalEditRouteForm"
     tabindex="-1"
     role="form"
     aria-labelledby="modalEditRouteFormLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form class="text-center" action="controller" method="post">
            <input hidden name="command" value="add_route"/>
            <input hidden name="scheduleId" value="${param.scheduleId}"/>
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h4 class="modal-title w-100 font-weight-bold">
                        <fmt:message key="text.edit.route"/>
                    </h4>
                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">
                    <%--@elvariable id="routes" type="java.util.List"--%>
                    <c:if test="${routes.size() == 0}">
                        <label>
                            <select name="startingStation" style="margin-bottom: 10px">
                                    <%--@elvariable id="stations" type="java.util.List"--%>
                                <c:forEach var="station" items="${stations}">
                                    <option value="${station.name}">${station.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </c:if>

                    <c:if test="${routes.size() != 0}">
                        <label>
                            <input readonly name="startingStation"
                                   value="${routes.get(routes.size()-1).finalStation.name}"/>
                        </label>
                    </c:if>


                    <div style="margin-bottom: 16px;">
                        <label>
                            <input class="form-control" type="time"
                                   placeholder="Departure Time"
                                   name="departureTime"
                            />
                        </label>
                    </div>

                    <label>
                        <select name="finalStation" style="margin-bottom: 10px">
                            <%--@elvariable id="stations" type="java.util.List"--%>
                            <c:forEach var="station" items="${stations}">
                                <option value="${station.name}">${station.name}</option>
                            </c:forEach>
                        </select>
                    </label>

                    <div style="margin-bottom: 16px;">
                        <label >
                            <input class="form-control" type="time"
                                   placeholder="Arrival Time"
                                   name="arrivalTime"/>
                        </label
                        ></div>

                    <div style="margin-bottom: 16px;"><label>
                        <input class="form-control"
                               type="text"
                               placeholder="Price"
                               name="price"/>
                    </label></div>


                </div>
                <div class="modal-footer d-flex justify-content-center">
                    <div class="d-grid mb-2">
                        <button class="btn btn-primary btn-login text-uppercase fw-bold"
                                type="submit">
                            <fmt:message key="text.edit.route"/>
                        </button>
                    </div>

                </div>
            </div>
        </form>
    </div>
</div>


<script>

    const modalEditRouteForm = document.getElementById('modalEditRouteForm')
    modalEditRouteForm.addEventListener('show.bs.modal', function (event) {
        // Button that triggered the modal
        const button = event.relatedTarget
        // Extract info from data-bs-* attributes
        const id = button.getAttribute('data-bs-id')
        const startingStation = button.getAttribute('data-bs-startingStation')

        const modalTitle = modalEditRouteForm.querySelector('.modal-title')
        const modalBodyInput = modalEditRouteForm.querySelector('.modal-body input')
        const modalBody = modalEditRouteForm.ge('.modal-body input')
        modalBodyInput.value = startingStation


    })
</script>


<%@ include file="include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>
