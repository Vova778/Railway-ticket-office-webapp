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
                            src="img/icons8-ukraine-16.png" style="width: 26px;height: 26px;" width="22"
                            height="22"></a>
                        <a class="active" href="controller?command=setLang&locale&pageToProcess=${param.command}"><img
                                class="d-md-flex justify-content-md-end" src="img/icons8-usa-16.png" width="22"
                                height="22" style="width: 26px;height: 26px;"></a></div>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="col-md-8 col-xl-6 text-center mx-auto">
        <h2><fmt:message key="text.add.route"/></h2>
    </div>
    <div class="row">
        <div class="col">
            <section class="position-relative py-4 py-xl-5">
                <div class="container">
                    <div class="row d-flex justify-content-center">
                        <div class="col-md-6 col-xl-4">
                            <div class="card mb-5">
                                <div class="card-body d-flex flex-column align-items-center">
                                    <form class="text-center" action="controller" method="post">


                                        <input hidden name="command" value="add_route"/>
                                        <input hidden name="scheduleId" value="${param.scheduleId}"/>


                                        <div style="margin-bottom: 16px;"><input class="form-control" type="time"
                                                                                 placeholder="Departure Time"
                                                                                 name="departureTime"
                                        /></div>

                                        <label>
                                            <select name="destinationPoint" style="margin-bottom: 10px">
                                                <c:forEach var="station" items="${stations}">
                                                    <option value="${station.name}">${station.name}</option>
                                                </c:forEach>
                                            </select>
                                        </label>

                                        <div style="margin-bottom: 16px;"><input class="form-control" type="time"
                                                                                 placeholder="Arrival Time"
                                                                                 name="arrivalTime"
                                        /></div>

                                        <div style="margin-bottom: 16px;"><input class="form-control" type="text"
                                                                                 placeholder="Price"
                                                                                 name="price"
                                        /></div>

                                        <div class="mb-3">
                                            <button class="btn btn-success d-block w-100" type="submit"
                                                    value=""><fmt:message key="text.add.route"/>
                                            </button>
                                        </div>
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
<%@ include file="include/footer.jsp" %>
<script src="js/bootstrap.min.js"></script>
</body>

</html>