<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="rooms.show.form.title" var="title"/>
<c:set var="prefix" value="${pageContext.request.contextPath}"/>
<c:set var="roomListErrorMessage" value="${roomsErrorMessages}"/>
<c:set var="addRoomErrorMessage" value="${manager_room_listErrorMessages}"/>


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
        <c:if test="${not empty roomListErrorMessage}">
            <div id="errorcolortext"><fmt:message key="${roomsErrorMessages}"/></div>
        </c:if>
        <br>
        <hr>
        <div align="center">
            <h1><fmt:message key="room.list.add.room.notice"/></h1>
        </div>

        <form action="/do/?action=add-room" method="post" enctype="multipart/form-data" name="addRoom">

            <label><b><fmt:message key="add.room.number.label"/></b></label>
            <small><fmt:message key="add.room.number.label.rules"/></small>
            <br>
            <input type="text" size="3" name="roomNumber" placeholder="" value="" required autofocus><br>
            <c:forEach var="errorMessage" items="${roomNumberErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>

            <label><b><fmt:message key="add.room.bed.label"/></b></label><br>
            <select size="1" name="bed">
                <option disabled selected><fmt:message key="order.make.form.bed.select.name"/></option>

                <c:forEach items="${bedList}" var="bed">
                    <option value="${bed.bed}">${bed.bed}</option>
                </c:forEach>
            </select><br>
            <c:forEach var="errorMessage" items="${bedErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>

            <label><b><fmt:message key="add.room.type.label"/></b></label><br>
            <select size="1" name="roomType">
                <option disabled selected><fmt:message key="order.make.form.roomType.select.name"/></option>

                <c:forEach items="${roomTypeList}" var="roomType">
                    <option value="${roomType.roomTypeEn}">${roomType.roomType}</option>
                </c:forEach>
            </select><br>
            <c:forEach var="errorMessage" items="${roomTypeErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>

            <label><b><fmt:message key="add.room.price.label"/></b></label>
            <small><fmt:message key="add.room.price.label.rules"/></small>
            <br>
            <input type="text" name="roomPrice" value=" " placeholder="" size="12" required>
            <br>
            <c:forEach var="errorMessage" items="${roomPriceErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>

            <label><b><fmt:message key="add.room.image.label"/></b></label>
                <fmt:message key="add.room.number.image.rules"/></small><br>
            <input type="file" name="photo" value="" accept="image/*"/><br><br>
            <c:forEach var="errorMessage" items="${photoErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>

            <button type="submit"><fmt:message key="add.room.button.submit"/></button>
            <br>
            <c:if test="${not empty addRoomErrorMessage}">
                <div id="errorcolortext"><fmt:message key="${manager_room_listErrorMessages}"/></div>
            </c:if>
        </form>

    </jsp:body>

</t:genericpage>