<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="${sessionScope.lang}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Railway ticket office</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/css/Navbar-Centered-Links-icons.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md bg-secondary py-3" style="height: 120px;background: var(--bs-indigo);">
    <div class="container">
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-2"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <img style="margin: 10px;margin-right: 0px;width: 120px;height: 120px;" src="assets/img/est.2012%20(1).png"
             width="157" height="172">
        <div class="collapse navbar-collapse" id="navcol-2">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item text-end">
                    <div class="d-md-flex"><a class="active" href="#"></a><a href="#"><img
                            src="assets/img/icons8-ukraine-16.png" style="width: 20px;height: 20px;"></a></div>
                    <img class="d-md-flex justify-content-md-end" src="assets/img/icons8-usa-16.png" width="16"
                         height="18" style="width: 20px;height: 20px;">
                </li>
                <li class="nav-item"><a class="nav-link" href="login.jsp"><span
                        style="color: var(--bs-navbar-active-color);">Basket</span><br></a></li>
                <li class="nav-item"><a class="nav-link" href="registration.jsp"
                                        style="color: var(--bs-navbar-active-color);">Register</a></li>
                <li class="nav-item"><a class="nav-link active" href="home.jsp">Home</a></li>
            </ul>
            <a class="btn btn-primary ms-md-2" role="button" href="login.jsp">Sing in</a>
        </div>
    </div>
</nav>
<section
        style="background: var(--bs-gray-700);padding: 30px;padding-right: 22px;padding-left: 22px;padding-top: 45px;padding-bottom: 45px;">
    <div class="container text-bg-secondary"></div>
</section>
<%@ include file="include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>