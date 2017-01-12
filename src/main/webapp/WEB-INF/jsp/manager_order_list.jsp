<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="orders.show.form.title" var="title"/>
<c:set var="prefix" value="${pageContext.request.contextPath}"/>
<c:set var="orderListErrorMessage" value="${ordersErrorMessages}"/>
<c:set var="roomListErrorMessage" value="${roomsErrorMessages}"/>
<c:set var="confirmErrorMessage" value="${confirmErrorMessages}"/>
<c:set var="searchErrorMessage" value="${searchErrorMessages}"/>


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
                    <th scope='colgroup' width="70px"><fmt:message key="user.order.list.table.orderId"/></th>

                    <th scope='colgroup' width="150x"><fmt:message key="user.order.list.table.firstName"/></th>
                    <th scope='colgroup' width="150px"><fmt:message key="user.order.list.table.lastName"/></th>
                    <th scope='colgroup' width="150px"><fmt:message key="user.order.list.table.email"/></th>
                    <th scope='colgroup' width="150px"><fmt:message key="user.order.list.table.phone"/></th>
                    <th scope='colgroup' width="50px"><fmt:message key="user.order.list.table.bed"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="user.order.list.table.roomtype"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="user.order.list.table.checkIn"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="user.order.list.table.checkOut"/></th>
                    <th scope='colgroup' width="135px"><fmt:message key="user.order.list.table.status"/></th>
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
                    </tr>

                </c:forEach>
            </table>
        </div>

        <br>
        <c:if test="${not empty orderListErrorMessage}">
            <div id="errorcolortext"><fmt:message key="${ordersErrorMessages}"/></div>
        </c:if>
        <hr>
        <h2><fmt:message key="orders.manager.form.message.selectRoom"/><br></h2>

        <form action="/do/?action=select-room" method="post">
            <label><b><fmt:message key="orders.manager.form.select.order.id"/></b></label>
            <input type="text" name="orderId" id="inputOrderId"
                   onkeyup="this.value = this.value.replace(/[^0-9]/g, '');" placeholder=""
                   minlength="1" maxlength="10" min="1" max="2147483647" value="${sessionScope.orderId}" required
                   autofocus>

            <t:output-errors errors="${orderIdErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="orders.manager.form.select.room.id"/></b></label>
            <input type="text" name="roomNumber" id="inputRoomNumber" maxlength="4"
                   onkeyup="this.value = this.value.replace(/[^0-9]/g, '');"
                   value="${sessionScope.roomNumber}" minlength="1" min="1" max="2147483647" placeholder="" required>

            <t:output-errors errors="${roomNumberErrorMessages}"/>
            <br><br>

            <button type="submit"><fmt:message key="orders.manager.form.select.button.submit"/></button>
            <c:if test="${not empty confirmErrorMessage}">
                <div id="errorcolortext"><fmt:message key="${confirmErrorMessages}"/></div>
            </c:if>
            <br>

        </form>

        <hr>
        <div align="center"><p>
            <h2><b><fmt:message key="orders.manager.form.message.room"/></b></h2>
        </div>
        <hr>

        <div align="left"><p>
                <fmt:message key="orders.manager.form.select.date.message.room"/>
        </div>

        <form action="/do/?action=show-free-room-on-date" method="post">
            <label><b><fmt:message key="orders.manager.form.select.date.chekIn"/></b></label>
            <input type="date" readonly id="from" name="checkIn" placeholder="" value="${sessionScope.checkIn}"
                   required autofocus>
            <small><fmt:message key="orders.manager.form.select.date.chekIn.rules"/></small>

            <t:output-errors errors="${checkInErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="orders.manager.form.select.date.chekOut"/></b></label>
            <input type="date" readonly id="to" name="checkOut" value="${sessionScope.checkOut}" placeholder=""
                   required>
            <small><fmt:message key="orders.manager.form.select.date.chekOut.rules"/></small>

            <t:output-errors errors="${checkOutErrorMessages}"/>
            <br><br>

            <button type="submit"><fmt:message key="orders.manager.form.button.search"/></button>
            <c:if test="${not empty searchErrorMessage}">
                <div id="errorcolortext"><fmt:message key="${searchErrorMessages}"/></div>
            </c:if>
            <br>

        </form>

        <div align="center">
            <table border='2'>
                <thead>
                <tr>
                    <th scope='colgroup' width="75px"><fmt:message key="room.list.table.roomNumber"/></th>
                    <th scope='colgroup' width="100px"><fmt:message key="user.order.list.table.roomtype"/></th>
                    <th scope='colgroup' width="60px"><fmt:message key="user.order.list.table.bed"/></th>
                    <th scope='colgroup' width="90px"><fmt:message key="room.list.table.roomPrice"/></th>
                    <th scope='colgroup' width="90px"><fmt:message key="room.list.table.roomPhoto"/></th>

                </tr>
                </thead>
                <c:forEach var="room" items="${rooms}">
                    <tr align="center">
                        <td>
                                ${room.number}
                        </td>

                        <td>
                                ${room.roomType}
                        </td>
                        <td>
                                ${room.bed}
                        </td>


                        <td>
                                ${room.price}
                        </td>
                        <td>
                            <img src="${prefix}/image/?id=${room.photo.id}"/>
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
