<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="orders.show.form.title" var="title"/>
<c:set var="orderListErrorMessage" value="${ordersErrorMessages}"/>
<c:set var="roomListErrorMessage" value="${roomsErrorMessages}"/>
<c:set var="orderIdErrorMessage" value="${orderIdErrorMessages}"/>
<c:set var="roomIdErrorMessage" value="${roomIdErrorMessages}"/>


<t:genericpage title="${title}">

    <jsp:body>
        <div align="center">
            <p>
            <h2><b><fmt:message key="orders.show.form.message"/></b></h2>
        </div>
        <hr>

        <div align="center">
            <table border='2'>
                <thead>
                <tr>
                    <th scope='colgroup' width="70px"><fmt:message key="userorderlist.table.orderid"/></th>

                    <th scope='colgroup' width="150x"><fmt:message key="userorderlist.table.firstname"/></th>
                    <th scope='colgroup' width="150px"><fmt:message key="userorderlist.table.lastname"/></th>
                    <th scope='colgroup' width="150px"><fmt:message key="userorderlist.table.email"/></th>
                    <th scope='colgroup' width="150px"><fmt:message key="userorderlist.table.phone"/></th>
                    <th scope='colgroup' width="50px"><fmt:message key="userorderlist.table.bed"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="userorderlist.table.roomtype"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="userorderlist.table.checkin"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="userorderlist.table.checkout"/></th>
                    <th scope='colgroup' width="110px"><fmt:message key="userorderlist.table.status"/></th>
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
                                ${order.checkIn}
                        </td>
                        <td>
                                ${order.checkOut}
                        </td>
                        <td>
                                ${order.status}
                        </td>
                    </tr>

                </c:forEach>
            </table>
        </div>

        <br>
        <c:if test="${not empty orderListErrorMessage}">
            <div id="errorcolortext"><fmt:message key="${ordersErrorMessages}"/></div>
        </c:if>
        <hr>
        <h2><fmt:message key="orders.manager.form.message.selectroom"/><br></h2>

        <form action="/do/?action=select-room" method="post">
            <label><b><fmt:message key="orders.manager.form.select.order.id"/></b></label>
            <input type="text" name="orderId" placeholder="" value="" required1 autofocus>
            <c:if test="${not empty orderIdErrorMessage}">
                <div id="errorcolortext">
                    <fmt:message key="${orderIdErrorMessages}"/></div>
            </c:if>
            <br><br>
            <label><b><fmt:message key="orders.manager.form.select.room.id"/></b></label>
            <input type="text" name="roomId" value="" placeholder="" required1>
            <c:if test="${not empty roomIdErrorMessage}">
                <div id="errorcolortext"><fmt:message key="${roomIdErrorMessages}"/></div>
            </c:if>
            <br><br>
            <button type="submit"><fmt:message key="orders.manager.form.select.button.submit"/></button>
            <br>

        </form>

        <hr>
        <div align="center"><p>
            <h2><b><fmt:message key="orders.manager.form.message.room"/></b></h2>
        </div>
        <hr>


        <div align="center">
            <table border='2'>
                <thead>
                <tr>
                    <th scope='colgroup' width="70px"><fmt:message key="roomlist.table.roomid"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="userorderlist.table.roomtype"/></th>
                    <th scope='colgroup' width="60px"><fmt:message key="userorderlist.table.bed"/></th>
                    <th scope='colgroup' width="75px"><fmt:message key="roomlist.table.roomnumber"/></th>
                    <th scope='colgroup' width="80px"><fmt:message key="roomlist.table.roomstatus"/></th>
                    <th scope='colgroup' width="90px"><fmt:message key="roomlist.table.roomprice"/></th>

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
                                ${room.roomStatus}
                        </td>
                        <td>
                                ${room.price}
                        </td>

                    </tr>

                </c:forEach>
            </table>
        </div>
        <br>
        <c:if test="${not empty roomListErrorMessage}">
            <div id="errorcolortext"><fmt:message key="${roomsErrorMessages}"/></div>
        </c:if>

    </jsp:body>

</t:genericpage>
