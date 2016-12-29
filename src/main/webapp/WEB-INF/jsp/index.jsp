<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="lang"/>

<fmt:message key="index.title" var="title"/>

<t:genericpage title="${title}">
    <jsp:body>
        <p><fmt:message key="main.page about.hotel.text"/></p>
    </jsp:body>
</t:genericpage>