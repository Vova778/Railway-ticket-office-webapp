<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">

<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'  />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="text.login"/></title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-light navbar-expand-md bg-info py-3" style="height: 130px;">
    <div class="container"><a class="navbar-brand d-flex align-items-center" href="#"><img style="margin: 0px;margin-right: 0px;width: 110px;height: 110px;" src="img/est.2012%20(1).png" width="120" height="120"></a><span class="fs-3">Railway ticket office webapp</span><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-2"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
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
<div class="container">
    <div class="col-md-8 col-xl-6 text-center mx-auto" style="margin-top: 50px" >

    </div>
    <div class="row">
        <div class="col">
            <section class="position-relative py-4 py-xl-5">
                <div class="container">
                    <div class="row d-flex justify-content-center">
                        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                            <div class="card border-0 shadow rounded-3 my-5">
                                <div class="card-body p-4 p-sm-5">
                                    <h2 class="card-title text-center mb-5 fw-light fs-5"><fmt:message key="text.login.form"/></h2>
                                    <form class="text-center" action="controller" method="post">
                                        <input hidden name="command" value="login">
                                        <div class="form-outline mb-4"><input class="form-control form-control-lg" type="text" name="login"
                                                                 placeholder="Login" minlength="4" maxlength="16"
                                                                 pattern=".{4,16}" required="">
                                        </div>
                                        <div class="form-outline mb-4"><input class="form-control form-control-lg" type="password" name="password"
                                                                 placeholder="Password" required="" minlength="6"
                                                                 pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,65}$">
                                        </div>
                                        <div class="d-grid mb-2">
                                            <button class="btn btn-primary btn-login text-uppercase fw-bold" type="submit" ><fmt:message key="text.login"/></button>
                                        </div>
                                        <a href="registration.jsp" class="text-muted"><fmt:message key="text.have.not.account"/></a>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
<%@ include file = "include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>