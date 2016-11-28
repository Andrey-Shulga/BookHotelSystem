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
        <p>Please login:</p>

        <form action="/do/login" method="post">
            <label><b>Username:</b></label>
            <input type="text" id="username" placeholder="Enter username" value="" required> <br><br>
            <label><b>Password:</b></label>
            <input type="password" id="password" value="" placeholder="Enter password" required><br><br>
            <input type="submit" value="Login">
        </form>
    </jsp:body>

</t:genericpage>
