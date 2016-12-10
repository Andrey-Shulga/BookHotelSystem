<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="lang"/>

<fmt:message key="error.title" var="title"/>

<t:genericpage title="${title}">
    <jsp:body>
        Error code: ${pageContext.errorData.statusCode}<br>
        Message: ${pageContext.exception.getMessage}<br>
        Exception: ${pageContext.exception}<br>

        <c:forEach var="trace" items="${pageContext.exception.stackTrace}">
            <p>${trace}</p>
        </c:forEach>
    </jsp:body>
</t:genericpage>
