<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>

    <jsp:attribute name="title">
      EPAM Hotel Deluxe and SPA
    </jsp:attribute>

    <jsp:attribute name="sidebar">
        <p><a href="jsp/login/login.jsp">Login</a></p>
        <p><a href="jsp/register/reg.jsp">Registration</a></p>
    </jsp:attribute>

    <jsp:attribute name="header">
        <h1>Welcome to EPAM Hotel!</h1>
    </jsp:attribute>

    <jsp:attribute name="footer">
         <p id="copyright">&copy; Copyright 2016-2017. Andrey Shulga.</p>
    </jsp:attribute>

    <jsp:body>
        <p>Content</p>
    </jsp:body>

</t:genericpage>
