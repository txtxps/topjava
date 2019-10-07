<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 07.10.2019
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table>
    <style type="text/css">
        TABLE {
            width: 100%; /* Ширина таблицы */
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }
        TD, TH {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
            background: #fff97d; /* Цвет фона */
        }
        TH {
            background: #fc0; /* Цвет фона */
        }
    </style>
    <thread>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
    </thread>
    <tbody>
        <c:forEach var="list" items="${Meals}" >
            <tr style="color:${list.excess ? 'green' : 'red'}">
                <td>${list.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</td>
                <td>${list.description}</td>
                <td>${list.calories}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
