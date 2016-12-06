<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="book.title" var="title"/>


<t:genericpage title="${title}">

    <jsp:body>
        <div align="center"><b><p><fmt:message key="book.message"/></p></b></div>


    </jsp:body>

</t:genericpage>
