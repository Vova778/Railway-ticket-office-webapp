<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Railway ticket office</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/css/Navbar-Centered-Links-icons.css">
</head>

<body class="text-info text-bg-light">
<nav class="navbar navbar-light navbar-expand-md bg-info py-3" style="height: 130px;">
    <div class="container"><a class="navbar-brand d-flex align-items-center" href="#"><img
            style="margin: 0;width: 110px;height: 110px;" src="img/est.2012%20(1).png" width="500"
            height="500"></a>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-2"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-2"><span class="fs-3 text-dark navbar-text">Railway ticket office</span>

            <c:if test="${sessionScope.user.role.id == null}">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item text-end">
                        <div class="container"><a class="active"
                                                  href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img
                                src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="16"
                                height="16"></a>
                            <a class="active"
                               href="controller?command=setLang&locale&pageToProcess=${param.command}"><img
                                    class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="16"
                                    height="16" style="width: 26px;height: 26px;"></a></div>
                    </li>

                    <li class="nav-item"><a class="nav-link" href="login.jsp"><span
                            style="color: var(--bs-navbar-active-color);"><fmt:message
                            key="text.basket"/></span><br></a></li>
                    <li class="nav-item"><a class="nav-link" href="registration.jsp"
                                            style="color: var(--bs-navbar-active-color);"><fmt:message
                            key="text.register"/></a></li>
                    <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a>
                    </li>
                </ul>
                <a type="button" class="btn btn-primary" data-bs-toggle="modal"
                   data-bs-target="#modalLoginForm"><fmt:message key="text.sign.up"/> </a>
            </c:if>

            <c:if test="${sessionScope.user.role.id == 1}">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item text-end">
                        <div class="container"><a class="active"
                                                  href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img
                                src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="16"
                                height="16"></a>
                            <a class="active"
                               href="controller?command=setLang&locale&pageToProcess=${param.command}"><img
                                    class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="16"
                                    height="16" style="width: 26px;height: 26px;"></a></div>
                    </li>

                    <li class="nav-item"><a class="nav-link" href="controller?command=logout"><span
                            style="color: var(--bs-navbar-active-color);"><fmt:message
                            key="text.logout"/></span><br></a></li>
                    <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a>
                    </li>
                </ul>
                <a class="btn btn-primary ms-md-2" role="button" href="basket.jsp"><fmt:message key="text.basket"/> </a>
            </c:if>

            <c:if test="${sessionScope.user.role.id == 2}">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item text-end">
                        <div class="container"><a class="active"
                                                  href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img
                                src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="16"
                                height="16"></a>
                            <a class="active"
                               href="controller?command=setLang&locale&pageToProcess=${param.command}"><img
                                    class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="16"
                                    height="16" style="width: 26px;height: 26px;"></a></div>
                    </li>
                    <li class="nav-item"><a class="nav-link active" href="controller?command=routes"><fmt:message
                            key="text.admin.page"/></a></li>
                    <li class="nav-item"><a class="nav-link" href="controller?command=logout"><span
                            style="color: var(--bs-navbar-active-color);"><fmt:message
                            key="text.logout"/></span><br></a></li>
                    <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a>
                    </li>
                </ul>
                <a class="btn btn-primary ms-md-2" role="button" href="controller?command=basket"><fmt:message
                        key="text.basket"/> </a>
            </c:if>

        </div>
    </div>
</nav>

<hr style="color: var(--bs-blue);height: 3px;background: var(--bs-blue);margin-top: 0px;">
<div class="row d-flex justify-content-center" style="background: var(--bs-gray);">
    <div class="col-md-6">
        <div class="card mb-4">
            <div class="card-body d-flex flex-column align-items-center">
                <form class="text-center" action="controller" method="get">
                    <c:if test="${sessionScope.user.role.id == null}">
                        <input hidden name="command" value="registrationForm"/>
                    </c:if>
                    <c:if test="${sessionScope.user.role.id != null}">
                        <input hidden name="command" value="find_routes_between_stations"/>
                    </c:if>

                    <div class="mb-3">
                        <p><input list="fromStation" required class="form-control"
                                  type="text" name="startingStation" placeholder="From">
                            <datalist id="fromStation">
                                <c:forEach var="station" items="${sessionScope.stations}">
                                    <option value="${station.name}"></option>
                                </c:forEach>
                            </datalist>
                        </p>
                    </div>
                    <div class="mb-3">
                        <p><input list="toStation" required class="form-control"
                                  type="text" name="finalStation" placeholder="To">
                            <datalist id="toStation">
                                <c:forEach var="station" items="${sessionScope.stations}">
                                    <option value="${station.name}"></option>
                                </c:forEach>
                            </datalist>
                        </p>
                    </div>

                    <div class="mb-3"><input required class="form-control"
                                             type="date" min="" name="date"></div>
                    <div class="mb-3">
                        <button class="btn btn-primary btn btn-secondary d-block w-100"
                                type="submit"><fmt:message key="text.search"/></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>

<div class="modal fade" id="modalLoginForm" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <form class="text-center" action="controller" method="post">
            <input hidden name="command" value="login">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h4 class="modal-title w-100 font-weight-bold"><fmt:message key="text.login.form"/></h4>
                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body mx-3">
                    <div class="form-outline mb-4">
                        <input class="form-control form-control-lg" type="text" name="login"
                                                          placeholder="Login" minlength="4" maxlength="16"
                                                          pattern=".{4,16}" required>
                    </div>
                    <div class="form-outline mb-4"><input class="form-control form-control-lg" type="password"
                                                          name="password"
                                                          placeholder="Password"  minlength="6"
                                                          pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,65}$"
                                                          required>
                    </div>

                </div>
                <div class="modal-footer d-flex justify-content-center">
                    <div class="d-grid mb-2">
                        <button class="btn btn-primary btn-login text-uppercase fw-bold"
                                type="submit">
                            <fmt:message key="text.login"/>
                        </button>
                    </div>

                </div>
            </div>
        </form>
    </div>
</div>

</body>

</html>