<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>

    <jsp:attribute name="title"/>

    <jsp:attribute name="sidebar"/>

    <jsp:attribute name="header"/>

    <jsp:attribute name="footer"/>

    <jsp:body>
        <p>Dear Guest, please register. After registration you can make a book order.</p>
        <p>Registration form:</p>
        <form name="registerForm" action="/do/?action=register" method="post">
            <label><b>Login: </b></label>
            <small>(3-12 characters)</small>
            <br>
            <input type="text" name="login" placeholder="Enter login" required autofocus value="">
            <c:forEach var="element" items="${loginErrorMessages}">
                "<c:out value="${element}"/>"
            </c:forEach>
            <br><br>
            <label><b>Password: </b></label>
            <small>(6-16 characters)</small>
            <br>
            <input type="password" name="password" value="" placeholder="Enter password" required
                   onchange1="form.confirm_password.pattern = this.value;">
            <c:forEach var="element" items="${passwordErrorMessages}">
                "<c:out value="${element}"/>"
            </c:forEach>
            <br>
            <label><b>Confirm Password:</b></label><br>
            <input type="password" name="confirm_password" value="" placeholder="Confirm password" required>

            <c:forEach var="element" items="${confirm_passwordErrorMessages}">
                "<c:out value="${element}"/>"
            </c:forEach>
            <br><br>

            <input type="submit" value="Register">

        </form>

    </jsp:body>

</t:genericpage>