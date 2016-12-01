<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="prefix" value="${pageContext.request.contextPath}"/>

<t:genericpage>

    <jsp:attribute name="title"/>

    <jsp:attribute name="sidebar"/>



    <jsp:attribute name="header"/>

    <jsp:attribute name="footer"/>

    <jsp:body>
        <p>Content</p>
    </jsp:body>

</t:genericpage>
