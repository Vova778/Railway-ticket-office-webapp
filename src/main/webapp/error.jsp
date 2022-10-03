<%--
  Created by IntelliJ IDEA.
  User: vohav
  Date: 02.10.2022
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no"/>
    <title>Error page</title>
</head>

<body>
<div class="container w-100 h-100" style="width: 100%;height: 100%;">
    <div class="col">
        <div class="row">
            <div class="col" style="text-align: center;"><img class="img-fluid"
                                                              src="${pageContext.request.contextPath}/images/error.png"
                                                              alt="error image"/></div>
        </div>
        <div class="row">
            <div class="col">
                <h1 style="text-align: center;">IT&#39;S NOT YOU<br/>IT&#39;S ME
                </h1>
                <h3 style="text-align: center;"><a class="text-muted" href="controller?command?=home"
                                                   style="color: #008000;"><fmt:message key="text.home"/> </a></h3>
                <p class="lead shadow-none"
                   style="text-align: center;font-size: 15px;margin: 54px;">
                    Something went terribly wrong...<br/>
                    Request from ${pageContext.errorData.requestURI} is failed <br/>
                    Status code: ${pageContext.errorData.statusCode} <br/>
                    Exception Message: ${pageContext.exception.message} <br/>
                </p>
            </div>
        </div>
    </div>
</div>
</body>

</html>