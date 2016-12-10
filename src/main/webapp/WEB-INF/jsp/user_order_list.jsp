<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<fmt:setBundle basename="lang"/>
<fmt:message key="user.order.list.title" var="title"/>


<t:genericpage title="${title}">

    <jsp:body>
        <div align="center">
            <h2><b><p><fmt:message key="user.order.list.notice"/></p></b></h2>
        </div>
        <hr>

        <div align="center">
            <table border='2'>
                <thead>
                <tr>
                    <th scope='colgroup' width="70px">Order id</th>
                    <th scope='colgroup' width="200px">First name</th>
                    <th scope='colgroup' width="200px">Last name</th>
                    <th scope='colgroup' width="200px">Email</th>
                    <th scope='colgroup' width="150px">Phone</th>
                    <th scope='colgroup' width="50px">Beds</th>
                    <th scope='colgroup' width="100px">Type</th>
                    <th scope='colgroup' width="100px">Check in</th>
                    <th scope='colgroup' width="100px">Check out</th>
                    <th scope='colgroup' width="110px">Status</th>
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




    </jsp:body>

</t:genericpage>
