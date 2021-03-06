<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<%@tag description="Overall Page template" pageEncoding="UTF-8" %>

<fmt:setBundle basename="lang"/>

<%@attribute name="title" type="java.lang.String" required="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="menu" fragment="true" %>
<%@attribute name="sidebar" fragment="true" %>

<c:set var="prefix" value="${pageContext.request.contextPath}"/>
<c:set var="role" value="${sessionScope.user.role}"/>
<c:set var="USER" value="USER"/>
<c:set var="MANAGER" value="MANAGER"/>


<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="${prefix}/css/style.css">

    <script type="text/javascript" src="${prefix}/js/jquery.js"></script>
    <script type="text/javascript" src="${prefix}/js/jquery-ui.js"></script>
    <script type="text/javascript" src="${prefix}/js/datepicker.js"></script>
    <script type="text/javascript" src="${prefix}/js/form-filler.js"></script>
    <link href="${prefix}/js/jquery-ui.css" rel="stylesheet">

    <title>${title}</title>

</head>

<body>

<div id="header">
    <jsp:invoke fragment="header"/>
    <h1><fmt:message key="index.header.message"/></h1>
    <div align="left">
        <a href="${prefix}/do/?action=lang&locale=en" style="color: white">English</a>
        <a href="${prefix}/do/?action=lang&locale=ru" style="color: white">Русский</a>
    </div>
    <br>
    <div align="left">
        <c:if test="${not empty sessionScope.user.login}">
            <b><fmt:message key="generic.page.login.name"/></b><ctg:hello role="${sessionScope.user.login}"/>
        </c:if>
    </div>
</div>

<div id="sidebar">
    <jsp:invoke fragment="sidebar"/>

    <p><a href="${prefix}/do/?action=show-index"><fmt:message key="sidebar.menu.main"/></a></p>

    <c:if test="${empty role}">
        <p><a href="${prefix}/do/?action=show-register-form"><fmt:message key="sidebar.menu.register"/></a></p>
        <p><a href="${prefix}/do/?action=show-login-form"><fmt:message key="sidebar.menu.login"/></a></p>
    </c:if>

    <c:if test="${not empty role}">

        <c:if test="${role==MANAGER}">
            <p><a href="${prefix}/do/?action=show-manager-order-list"><fmt:message key="sidebar.menu.order.list"/></a>
            </p>
            <p><a href="${prefix}/do/?action=show-manager-allorder-list"><fmt:message
                    key="sidebar.menu.allorder.list"/></a></p>
            <p><a href="${prefix}/do/?action=show-manager-room-list"><fmt:message key="sidebar.menu.room.list"/></a></p>
        </c:if>

        <c:if test="${role==USER}">
            <p><a href="${prefix}/do/?action=show-order-form"><fmt:message key="sidebar.menu.make.order"/></a></p>
            <p><a href="${prefix}/do/?action=show-user-order-list"><fmt:message key="sidebar.menu.user.order.list"/></a>
            <p><a href="${prefix}/do/?action=show-user-invoice-list"><fmt:message
                    key="sidebar.menu.user.invoice.list"/></a>
            </p>
        </c:if>

        <p><a href="${prefix}/do/?action=logout"><fmt:message key="sidebar.menu.logout"/></a>
            [${sessionScope.user.login}]</p>
    </c:if>

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
