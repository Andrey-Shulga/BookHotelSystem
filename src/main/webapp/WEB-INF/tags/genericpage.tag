<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="sidebar" fragment="true" %>
<%@attribute name="title" fragment="true" %>
<c:set var="prefix" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>
        <jsp:invoke fragment="title"/>
        EPAM Hotel Deluxe & SPA
    </title>

    <style>
        body {
            font: 11pt Arial, Helvetica, sans-serif; /* Рубленый шрифт текста */
            margin: 0; /* Отступы на странице */
        }

        h1 {
            font-size: 36px; /* Размер шрифта */
            margin: 0; /* Убираем отступы */
            color: #fc6; /* Цвет текста */

        }

        h2 {
            margin-top: 0; /* Убираем отступ сверху */
        }

        #header { /* Верхний блок */
            background: #0080c0; /* Цвет фона */
            padding: 10px; /* Поля вокруг текста */
            text-align: center;
        }

        #sidebar { /* Левая колонка */
            float: left; /* Обтекание справа */
            border: 1px solid #333; /* Параметры рамки вокруг */
            width: 10%; /* Ширина колонки */
            padding: 5px; /* Поля вокруг текста */
            margin: 10px 10px 20px 5px; /* Значения отступов */
        }

        #content { /* Правая колонка */
            margin: 10px 5px 20px 12%; /* Значения отступов */
            padding: 5px; /* Поля вокруг текста */
            border: 1px solid #333; /* Параметры рамки */
        }

        #footer { /* Нижний блок */
            background: #333; /* Цвет фона */
            padding: 5px; /* Поля вокруг текста */
            color: #fff; /* Цвет текста */
            clear: left; /* Отменяем действие float */
            text-align: center;
        }
    </style>

</head>

<body>

<div id="header">
    <jsp:invoke fragment="header"/>
    <h1>Welcome to EPAM Hotel!</h1>
</div>

<div id="sidebar">
    <jsp:invoke fragment="sidebar"/>
    <p><a href="${prefix}/do/?action=show-index">Main</a></p>
    <p><a href="${prefix}/do/?action=show-register-form">Register</a></p>
    <p><a href="${prefix}/do/?action=show-login-form">Login</a></p>
</div>

<div id="content">
    <jsp:doBody/>
</div>

<div id="footer">
    <jsp:invoke fragment="footer"/>
    <p id="copyright">&copy; Copyright 2016-2017. Andrey Shulga.</p>
</div>

</body>
</html>