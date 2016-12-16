<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="rooms.show.form.title" var="title"/>
<c:set var="roomListErrorMessage" value="${roomsErrorMessages}"/>


<t:genericpage title="${title}">

    <jsp:body>

        <div align="center">
            <p>
            <h2><b><fmt:message key="rooms.show.form.message"/></b></h2>
        </div>
        <hr>

        <div align="center">
            <table border='2'>
                <thead>
                <tr>
                    <th scope='colgroup' width="70px"><fmt:message key="room.list.table.roomId"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="user.order.list.table.roomtype"/></th>
                    <th scope='colgroup' width="60px"><fmt:message key="user.order.list.table.bed"/></th>
                    <th scope='colgroup' width="75px"><fmt:message key="room.list.table.roomNumber"/></th>
                    <th scope='colgroup' width="90px"><fmt:message key="room.list.table.roomPrice"/></th>

                </tr>
                </thead>
                <c:forEach var="room" items="${rooms}">
                    <tr align="center">
                        <td>
                                ${room.id}
                        </td>

                        <td>
                                ${room.roomType}
                        </td>
                        <td>
                                ${room.bed}
                        </td>
                        <td>
                                ${room.number}
                        </td>

                        <td>
                                ${room.price}
                        </td>

                    </tr>

                </c:forEach>
            </table>
        </div>
        <c:if test="${not empty roomListErrorMessage}">
            <div id="errorcolortext"><fmt:message key="${roomsErrorMessages}"/></div>
        </c:if>

    </jsp:body>

</t:genericpage>
