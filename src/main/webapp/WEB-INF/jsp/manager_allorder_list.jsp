<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="allorders.show.form.title" var="title"/>
<c:set var="orderListErrorMessage" value="${ordersErrorMessages}"/>

<t:genericpage title="${title}">

    <jsp:body>
        <div align="center">
            <p>
            <h2><b><fmt:message key="allorders.show.form.message"/></b></h2>
        </div>
        <hr>

        <div align="center">
            <table border='2'>
                <thead>
                <tr>
                    <th scope='colgroup' width="70px"><fmt:message key="userorderlist.table.orderid"/></th>

                    <th scope='colgroup' width="200px"><fmt:message key="userorderlist.table.firstname"/></th>
                    <th scope='colgroup' width="200px"><fmt:message key="userorderlist.table.lastname"/></th>
                    <th scope='colgroup' width="200px"><fmt:message key="userorderlist.table.email"/></th>
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

    </jsp:body>
</t:genericpage>
