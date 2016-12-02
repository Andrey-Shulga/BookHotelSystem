<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@tag description="Overall Page template" pageEncoding="UTF-8" %>

<fmt:setBundle basename="lang"/>

<%@attribute name="title" type="java.lang.String" required="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="menu" fragment="true" %>
<%@attribute name="sidebar" fragment="true" %>

<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>${title}</title>

</head>

<body>

<div id="header">
    <jsp:invoke fragment="header"/>
    <fmt:message key="index.header.message"/>
</div>

<div id="sidebar">
    <jsp:invoke fragment="sidebar"/>
    <p><a href="/do/?action=show-index"><fmt:message key="sidebar.menu.main"/></a></p>
    <p><a href="/do/?action=show-register-form"><fmt:message key="sidebar.menu.register"/></a></p>
    <p><a href="/do/?action=show-login-form"><fmt:message key="sidebar.menu.login"/></a></p>
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
