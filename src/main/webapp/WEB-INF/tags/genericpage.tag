<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@tag description="Overall Page template" pageEncoding="UTF-8" %>
<fmt:setBundle basename="lang"/>
<fmt:setLocale value="en"/>

<%@attribute name="title" type="java.lang.String" required="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="menu" fragment="true" %>
<%@attribute name="sidebar" fragment="true" %>

<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>${title}</title>

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
    <fmt:message key="index.header.message"/>
</div>

<div id="sidebar">
    <jsp:invoke fragment="sidebar"/>
    <p><a href="${prefix}/do/?action=show-index"><fmt:message key="sidebar.menu.main"/></a></p>
    <p><a href="${prefix}/do/?action=show-register-form"><fmt:message key="sidebar.menu.register"/></a></p>
    <p><a href="${prefix}/do/?action=show-login-form"><fmt:message key="sidebar.menu.login"/></a></p>
</div>


<div id="content">
    <jsp:doBody/>
</div>

<div id="footer">
    <jsp:invoke fragment="footer"/>
    <p id="copyright"><fmt:message key="index.footer.copryght"/></p>
</div>

</body>
</html>
