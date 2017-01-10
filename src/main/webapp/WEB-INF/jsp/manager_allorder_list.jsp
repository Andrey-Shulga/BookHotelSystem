<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="allOrders.show.form.title" var="title"/>
<c:set var="orderListErrorMessage" value="${ordersErrorMessages}"/>

<t:genericpage title="${title}">

    <jsp:body>
        <div align="center">
            <p>
            <h2><b><fmt:message key="allOrders.show.form.message"/></b></h2>
        </div>
        <hr>

        <div align="center">
            <table border='2'>
                <thead>
                <tr>
                    <th scope='colgroup' width="70px"><fmt:message key="user.order.list.table.orderId"/></th>

                    <th scope='colgroup' width="150px"><fmt:message key="user.order.list.table.firstName"/></th>
                    <th scope='colgroup' width="150px"><fmt:message key="user.order.list.table.lastName"/></th>
                    <th scope='colgroup' width="150px"><fmt:message key="user.order.list.table.email"/></th>
                    <th scope='colgroup' width="130px"><fmt:message key="user.order.list.table.phone"/></th>
                    <th scope='colgroup' width="50px"><fmt:message key="user.order.list.table.bed"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="user.order.list.table.roomtype"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="user.order.list.table.checkIn"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="user.order.list.table.checkOut"/></th>
                    <th scope='colgroup' width="135px"><fmt:message key="user.order.list.table.status"/></th>

                    <th scope='colgroup' width="75px"><fmt:message key="manager.all.order.list.table.roomNumber"/></th>
                    <th scope='colgroup' width="70px"><fmt:message key="manager.all.order.list.table.roomPrice"/></th>
                    <th scope='colgroup' width="70px"><fmt:message key="manager.all.order.list.table.fullCost"/></th>
                </tr>
                </thead>
                <c:forEach var="order" items="${orders}">
                    <tr align="center">
                        <td>
                                ${order.id}
                        </td>


                        <td>
                                ${order.firstName}
                        </td>
                        <td>
                                ${order.lastName}
                        </td>
                        <td>
                                ${order.email}
                        </td>
                        <td>
                                ${order.phone}
                        </td>
                        <td>
                                ${order.bed}
                        </td>
                        <td>
                                ${order.roomType}
                        </td>
                        <td>
                            <ftm:formatDate value="${order.checkIn}"/>
                        </td>
                        <td>
                            <ftm:formatDate value="${order.checkOut}"/>
                        </td>
                        <td>
                                ${order.status}
                        </td>

                        <td>
                            <c:if test="${order.room.number!=0}">
                                ${order.room.number}
                            </c:if>
                        </td>
                        <td>
                                ${order.room.price}
                        </td>
                        <td>
                            <c:if test="${order.fullCost!=0}">
                                ${order.fullCost}
                            </c:if>
                        </td>
                    </tr>

                </c:forEach>
            </table>
        </div>

        <br>
        <c:if test="${not empty orderListErrorMessage}">
            <div id="errorcolortext"><fmt:message key="${ordersErrorMessages}"/></div>
        </c:if>

    </jsp:body>
</t:genericpage>
