<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="register.title" var="title"/>
<fmt:message key="register.login.placeholder" var="loginPlaceholder"/>
<fmt:message key="register.password.placeholder" var="passwordPlaceholder"/>
<fmt:message key="register.password.confirm.placeholder" var="passwordConfirmPlaceholder"/>
<c:set var="errorUserExist" value="${registerErrorMessages}"/>

<t:genericpage title="${title}">

    <jsp:body>

        <p><fmt:message key="register.hello.message"/></p>
        <form name="registerForm" action="/do/?action=register" method="post">
            <label><b><fmt:message key="register.login"/></b></label>
            <small><fmt:message key="register.login.range"/></small>
            <br>
            <input type="text" name="login" placeholder="${loginPlaceholder}" required autofocus value="">
            <c:forEach var="errorMessage" items="${loginErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>
            <label><b><fmt:message key="register.password"/></b></label>
            <small><fmt:message key="register.password.range"/></small>
            <br>
            <input type="password" name="password" value="" placeholder="${passwordPlaceholder}" required
                   onchange1="form.confirm_password.pattern = this.value;">
            <c:forEach var="errorMessage" items="${passwordErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br>
            <label><b><fmt:message key="register.password.confirm"/></b></label><br>
            <input type="password" name="confirm_password" value="" placeholder="${passwordConfirmPlaceholder}"
                   required>

            <c:forEach var="errorMessage" items="${confirm_passwordErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <button type="submit"><fmt:message key="register.button.submit"/></button>
            <c:if test="${not empty errorUserExist}">
                <div id="errorcolortext"><fmt:message key="${registerErrorMessages}"/></div>
            </c:if>

        </form>

    </jsp:body>

</t:genericpage>