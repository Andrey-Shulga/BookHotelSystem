<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
        <p>Content</p>
    </jsp:body>

</t:genericpage>
