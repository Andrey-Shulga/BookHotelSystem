<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="lang"/>

<fmt:message key="error.title.500" var="title"/>

<t:genericpage title="${title}">
    <jsp:body>

        <b><fmt:message key="access.error.message.label"/></b> <fmt:message key="database.connection.failure.msg"/><br>
        <b> <fmt:message key="access.error.code.label"/></b> ${pageContext.errorData.statusCode}<br>
        <hr>

        <b><fmt:message key="internal.error.admin.message"/></b> <br><br>
        <b>Exception:</b> ${pageContext.exception}<br>
        <b>Message: </b>${pageContext.exception.message}<br>
        <b>Stacktrace: </b><br>
        <c:forEach var="trace" items="${pageContext.exception.stackTrace}">
            ${trace} <br>
        </c:forEach>

    </jsp:body>
</t:genericpage>
