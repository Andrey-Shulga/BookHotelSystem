<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="login.title" var="title"/>
<fmt:message key="register.login.placeholder" var="loginPlaceholder"/>
<fmt:message key="register.password.placeholder" var="passwordPlaceholder"/>
<c:set var="loginErrorMessage" value="${loginButtonErrorMessages}"/>

<t:genericpage title="${title}">

    <jsp:body>
        <p><fmt:message key="login.message"/></p>

        <form action="/do/?action=login" method="post">
            <label><b><fmt:message key="register.login"/></b></label><br>
            <input type="text" name="login" id="inputLogin" minlength="3" maxlength="12"
                   placeholder="${loginPlaceholder}"
                   value="${sessionScope.login}" required autofocus>
            <br><br>
            <label><b><fmt:message key="register.password"/></b></label><br>
            <input type="password" name="password" minlength="6" maxlength="16" value=""
                   placeholder="${passwordPlaceholder}" required><br><br>
            <button type="submit"><fmt:message key="login.button.submit"/></button>
            <br>
            <c:if test="${not empty loginErrorMessage}">
                <div id="errorcolortext"><fmt:message key="${loginButtonErrorMessages}"/></div>
            </c:if>

        </form>
    </jsp:body>

</t:genericpage>
