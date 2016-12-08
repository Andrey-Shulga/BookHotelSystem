<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="order.make.title" var="title"/>


<t:genericpage title="${title}">

    <jsp:body>
        <div align="center"><b><p><fmt:message key="order.make.message"/></p></b></div>
        <hr>
        <form name="orderForm" action="/do/?action=make-order" method="post">

            <label><b><fmt:message key="order.make.form.firstname"/></b></label>
            <small><fmt:message key="order.make.form.firstname.range"/></small>
            <br>
            <input type="text" name="firstName" required autofocus value="Andrey">
            <c:forEach var="errorMessage" items="${firstNameErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <label><b><fmt:message key="order.make.form.lasttname"/></b></label>
            <small><fmt:message key="order.make.form.lasttname.range"/></small>
            <br>
            <input type="text" name="lastName" required value="Shulga">
            <c:forEach var="errorMessage" items="${lastNameErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <label><b><fmt:message key="order.make.form.email"/></b></label>
            <small><fmt:message key="order.make.form.email.rule"/></small>
            <br>
            <input type="text" name="email" required value="solyariz@gmail.com">
            <c:forEach var="errorMessage" items="${emailErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <label><b><fmt:message key="order.make.form.phone"/></b></label>
            <small><fmt:message key="order.make.form.phone.rule"/></small>
            <br>
            <input type="text" name="phone" required value="77058266603">
            <c:forEach var="errorMessage" items="${phoneErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <label><b><fmt:message key="order.make.form.checkin"/></b></label>
            <small><fmt:message key="order.make.form.checkin.rule"/></small>
            <br>
            <input type="date" name="checkIn" required value="25/12/2017">
            <c:forEach var="errorMessage" items="${checkInErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <label><b><fmt:message key="order.make.form.checkout"/></b></label>
            <small><fmt:message key="order.make.form.checkout.rule"/></small>
            <br>
            <input type="date" name="checkOut" required value="29/12/2017">
            <c:forEach var="errorMessage" items="${checkOutErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <label><b><fmt:message key="order.make.form.bed"/></b></label>
            <small><fmt:message key="order.make.form.bed.select"/></small>
            <br>
            <select size="1" name="bed">
                <option disabled><fmt:message key="order.make.form.bed.select.name"/></option>
                <option value="1" selected>1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
            </select>
            <c:forEach var="errorMessage" items="${bedErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <label><b><fmt:message key="order.make.form.roomType"/></b></label>
            <small><fmt:message key="order.make.form.roomType.select"/></small>
            <br>
            <select size="1" name="roomType">
                <option disabled><fmt:message key="order.make.form.roomType.select.name"/></option>
                <option value="Standard"><fmt:message key="order.make.form.roomType.select.name.standart"/></option>
                <option value="Junior suit" selected><fmt:message
                        key="order.make.form.roomType.select.name.jsuit"/></option>
                <option value="Suit"><fmt:message key="order.make.form.roomType.select.name.suit"/></option>
            </select>
            <c:forEach var="errorMessage" items="${roomTypeErrorMessages}">
                <div id="errorcolortext"><fmt:message key="${errorMessage}"/></div>
            </c:forEach>
            <br><br>

            <button type="submit"><fmt:message key="order.make.submit"/></button>

        </form>


    </jsp:body>

</t:genericpage>
