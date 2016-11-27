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
        <p>Please register. After registration you can make a book order.</p>
        <p>Registration form:</p>
        <form action="" method="post">
            <label><b>Username:</b></label>
            <input type="text" id="username" placeholder="Enter username" value="" required> (in range 3-12 symbols)<br><br>
            <label><b>Password:</b></label>
            <input type="password" id="password" value="" placeholder="Enter password" required> (in range 6-16 symbols)<br><br>
            <label><b>Password again:</b></label>
            <input type="password" value="" placeholder="Reenter password"><br><br>
            <input type="submit" value="Register">
        </form>
    </jsp:body>

</t:genericpage>