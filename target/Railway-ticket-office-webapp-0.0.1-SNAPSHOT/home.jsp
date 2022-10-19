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
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item text-end">
                        <div class="container"><a class="active" href="controller?command=setLang&locale=ua&pageToProcess=${param.command}"><img src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="22" height="22"></a>
                            <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="22" height="22" style="width: 26px;height: 26px;"></a></div>
                    </li>
                    <li class="nav-item"><a class="nav-link" href="login.jsp"><span style="color: var(--bs-navbar-active-color);">Basket</span><br></a></li>
                    <li class="nav-item"><a class="nav-link" href="registration.jsp" style="color: var(--bs-navbar-active-color);">Register</a></li>
                    <li class="nav-item"><a class="nav-link active" href="home.jsp"><fmt:message key="text.home"/></a></li>
                </ul><a class="btn btn-primary ms-md-2" role="button" href="login.jsp"><fmt:message key="text.sign.up"/> </a>
            </div>
        </div>
    </nav>
    <hr style="color: var(--bs-blue);height: 3px;background: var(--bs-blue);margin-top: 0px;">
    <section class="text-bg-light" style="background: var(--bs-gray-700);padding: 30px;padding-right: 22px;padding-left: 22px;padding-top: 55px;padding-bottom: 55px;">
        <form class="text-center" action="controller" method="get">
            <input hidden name="command" value="find_routes_between_stations"/>
            <div class="row">
                <input type="text" name="startingStation">
            </div>
            <div class="row">
                <input type="text" name="finalStation">
            </div>
            <div class="row">
                    <input type="date" name="date">
            </div>

            <div class="mb-1">
                <button class="btn btn-success d-block w-100 " type="submit"
                        value=""><fmt:message key="text.search"/>
                </button>
            </div>


        </form>
    </section>
    <%@ include file="include/footer.jsp" %>
    <script src="js/bootstrap.min.js"></script>
</body>

</html>