<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

    <jsp:attribute name="title"/>

    <jsp:attribute name="sidebar">
        <p><a href="/jsp/login/login.jsp">Login</a></p>
        <p><a href="/jsp/register/reg.jsp">Registration</a></p>
    </jsp:attribute>

    <jsp:attribute name="header"/>

    <jsp:attribute name="footer"/>

    <jsp:body>
        <p>Dear Guest, please register. After registration you can make a book order.</p>
        <p>Registration form:</p>
        <form name="registerForm" action="/do/register" method="post">
            <input type="hidden" name="command" value="register"/>
            <label><b>Login:</b></label><br>
            <input type="text" name="login" placeholder="Enter login" required autofocus value=""> (in range 3-12
            symbols)<br><br>
            <label><b>Password:</b></label><br>
            <input type="password" name="password" value="" placeholder="Enter password" required> (in range 6-16
            symbols)<br>
            <label><b>Password again:</b></label><br>
            <input type="password" name="password2" value="" placeholder="Reenter password" required><br><br>
            <input type="submit" value="Register">
        </form>
    </jsp:body>

</t:genericpage>