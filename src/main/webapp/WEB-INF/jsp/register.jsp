<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

    <jsp:attribute name="title"/>

    <jsp:attribute name="sidebar"/>

    <jsp:attribute name="header"/>

    <jsp:attribute name="footer"/>

    <jsp:body>
        <p>Dear Guest, please register. After registration you can make a book order.</p>
        <p>Registration form:</p>
        <form name="registerForm" action="/do/?action=register" method="post">
            <label><b>Login:</b></label><br>
            <input type="text" name="login" placeholder="Enter login" required autofocus value=""> (in range 3-12
            symbols)<br><br>
            <label><b>Password:</b></label><br>
            <input type="password" name="password" value="" placeholder="Enter password" required
                   onchange="form.confirm_password.pattern = this.value;"> (in range 6-16
            symbols)<br>
            <label><b>Confirm Password:</b></label><br>
            <input type="password" name="confirm_password" value="" placeholder="Confirm password" required><br><br>

            <input type="submit" value="Register">

        </form>

    </jsp:body>

</t:genericpage>