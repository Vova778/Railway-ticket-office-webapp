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
        <div class="container"><a class="navbar-brand d-flex align-items-center" href="#"><img style="margin: 0px;margin-right: 0px;width: 110px;height: 110px;" src="img/est.2012%20(1).png" width="120" height="120"></a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-2"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-2"><span class="fs-3 text-dark navbar-text">Railway ticket office</span>

                <c:if test="${sessionScope.user.role.id == null}">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item text-end">
                        <div class="container"><a class="active" href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="22" height="22"></a>
                            <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="22" height="22" style="width: 26px;height: 26px;"></a></div>
                    </li>

                    <li class="nav-item"><a class="nav-link" href="login.jsp"><span style="color: var(--bs-navbar-active-color);"><fmt:message key="text.basket" /></span><br></a></li>
                    <li class="nav-item"><a class="nav-link" href="registration.jsp" style="color: var(--bs-navbar-active-color);"><fmt:message key="text.register" /></a></li>
                    <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a></li>
                </ul>
                <a class="btn btn-primary ms-md-2" role="button" href="login.jsp"><fmt:message key="text.sign.up"/> </a>
                </c:if>

                <c:if test="${sessionScope.user.role.id == 1}">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item text-end">
                            <div class="container"><a class="active" href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="22" height="22"></a>
                                <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="22" height="22" style="width: 26px;height: 26px;"></a></div>
                        </li>

                        <li class="nav-item"><a class="nav-link" href="controller?command=logout"><span style="color: var(--bs-navbar-active-color);"><fmt:message key="text.logout" /></span><br></a></li>
                        <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a></li>
                    </ul>
                    <a class="btn btn-primary ms-md-2" role="button" href="basket.jsp"><fmt:message key="text.basket"/> </a>
                </c:if>

                <c:if test="${sessionScope.user.role.id == 2}">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item text-end">
                            <div class="container"><a class="active" href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="22" height="22"></a>
                                <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="22" height="22" style="width: 26px;height: 26px;"></a></div>
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
    <hr style="color: var(--bs-blue);height: 3px;background: var(--bs-blue);margin-top: 0px;">
    <div class="row d-flex justify-content-center" style="background: var(--bs-gray);">
        <div class="col-md-6">
            <div class="card mb-4">
                <div class="card-body d-flex flex-column align-items-center">
                    <form  class="text-center" action="controller" method="get">
                        <input hidden name="command" value="find_routes_between_stations"/>
                        <div class="mb-3"><input class="form-control" type="text" name="startingStation" placeholder="From"></div>
                        <div class="mb-3"><input class="form-control" type="text" name="finalStation" placeholder="To"></div>
                        <div class="mb-3"><input class="form-control" type="date" min="20.10.2022" name="date"></div>
                        <div class="mb-3">
                            <button class="btn btn-primary btn btn-secondary d-block w-100" type="submit">Button</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="include/footer.jsp" %>
    <script src="js/bootstrap.min.js"></script>
</body>

</html>