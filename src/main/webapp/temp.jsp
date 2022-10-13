<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>WebRailway</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        body{
            background-image: url('http://2d.by/wallpapers/s/sero-biryuzovyy_fon.jpg');
            /*background-image: url('https://w-dog.net/wallpapers/13/16/454958512120338/england-steam-engine-train-cornwood-viaduc-railroad-nature-forest.jpg');*/
            -moz-background-size: 100%; /* Firefox 3.6+ */
            -webkit-background-size: 100%; /* Safari 3.1+ и Chrome 4.0+ */
            -o-background-size: 100%; /* Opera 9.6+ */
            background-size: 100%; /* Современные браузеры */
        }
        .footer {
            position: fixed;
            left: 0;
            bottom: 0;
            opacity: 0.8;
            width: 100%;
            color: white;
            text-align: center;
            background-color: #333;
            height: 45px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" style="font-style:italic;opacity:0.8">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand">WebRailway</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a onclick="ViewHomePage()">Главная страница</a></li>
            <li><a onclick="WorkWithUsers()">Работа с пользователями</a></li>
            <li><a onclick="AddingANewTrain()">Добавление нового поезда</a></li>
            <li><a onclick="Exit()">Выйти</a></li>
            </li>
        </ul>
    </div>
</nav>
<div class = "container" style="font-style:italic;opacity:0.8;margin-top:80px">
    <div style="text-align:center;"><h3>Доступные поезда:</h3></div>
    <form method="GET" action="/Controller" class="well">
        <table class = "table table-striped table-bordered table-hover">
            <!--table-striped : добавляет полосы к таблице, table-bordered : добавляет границу к таблице, table-hover : добавляет эффект зависания в таблицу -->
            <thead> <!-- Группировка заголовков -->
            <tr class = "info"> <!-- Светло-синий фон в строке таблицы -->
                <th>Номер поезда</th>
                <th>Станция отправления</th>
                <th>Станция прибытия</th>
                <th>Время отправления</th>
                <th>Время прибытия</th>
                <th>Количество свободных мест</th>
            </tr>
            </thead>
            <tbody> <!-- Группировка содержимого тела в таблице HTML -->
            <c:forEach var="train" items="${listOfTrains}">
            <tr>
                <td>${train.numberTrain}</td>
                <td>${train.startStation}</td>
                <td>${train.endStation}</td>
                <td>${train.startTime}</td>
                <td>${train.endTime}</td>
                <td>${train.countPlaces}</td>
                </c:forEach>
            </tr>
            </tbody>
        </table>

    </form>
</div>
<div class="footer">
    <h4 style="font-style:italic;opacity:0.8">Администратор</h4>
</div>
</body>
<script type= "text/javascript">
    function WorkWithUsers() {
        window.location = "/RequestWorkWithUsers.jsp";
    }
    function ViewHomePage() {
        window.location = "/AdminHomePage.jsp";
    }
    function AddingANewTrain() {
        window.location = "/RequestAddNewTrainAdmin.jsp";
    }
    function Exit() {
        window.location = "/index.jsp";
    }
</script>
</html>
