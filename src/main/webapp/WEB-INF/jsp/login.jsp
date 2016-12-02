<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="login.title" var="title"/>

<t:genericpage title="${title}">

    <jsp:body>
        <p>Please login:</p>

        <form action="/do/?action=login" method="post">
            <label><b>Username:</b></label>
            <input type="text" name="login" placeholder="Enter username" value="" required autofocus> <br><br>
            <label><b>Password:</b></label>
            <input type="password" name="password" value="" placeholder="Enter password" required><br><br>
            <input type="submit" value="Login">
        </form>
    </jsp:body>

</t:genericpage>
