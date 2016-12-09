<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="user.order.list.title" var="title"/>


<t:genericpage title="${title}">

    <jsp:body>
        <div align="center"><b><p><fmt:message key="user.order.list.notice"/></p></b></div>
        <hr>
        <c:forEach var="order" items="${orders}">
            ${order}<br>
            <hr>
        </c:forEach>



    </jsp:body>

</t:genericpage>
