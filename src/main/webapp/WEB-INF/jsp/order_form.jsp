<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setBundle basename="lang"/>
<fmt:message key="order.make.title" var="title"/>
<c:set var="orderErrorMessage" value="${order_formErrorMessages}"/>

<t:genericpage title="${title}">

    <jsp:body>
        <div align="center"><b><p><fmt:message key="order.make.message"/></p></b></div>
        <hr>
        <form name="orderForm" action="/do/?action=make-order" method="post">

            <label><b><fmt:message key="order.make.form.firstname"/></b></label>
            <small><fmt:message key="order.make.form.firstname.range"/></small>
            <br>
            <input type="text" name="firstName" id="inputFirstName" minlength="2" maxlength="16" required autofocus
                   value="${sessionScope.firstName}">

            <t:output-errors errors="${firstNameErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="order.make.form.lasttname"/></b></label>
            <small><fmt:message key="order.make.form.lasttname.range"/></small>
            <br>
            <input type="text" name="lastName" id="inputLastName" minlength="2" maxlength="16" required
                   value="${sessionScope.lastName}">

            <t:output-errors errors="${lastNameErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="order.make.form.email"/></b></label>
            <small><fmt:message key="order.make.form.email.rule"/></small>
            <br>
            <input type="email" name="email" id="inputEmail" minlength="7" maxlength="30" required
                   value="${sessionScope.email}">

            <t:output-errors errors="${emailErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="order.make.form.phone"/></b></label>
            <small><fmt:message key="order.make.form.phone.rule"/></small>
            <br>
            <input type="tel" name="phone" id="inputPhone" minlength="3" maxlength="20"
                   onkeyup="this.value = this.value.replace(/[^0-9]/g, '');" required
                   value="${sessionScope.phone}">
            <t:output-errors errors="${phoneErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="order.make.form.checkin"/></b></label>
            <small><fmt:message key="order.make.form.checkin.rule"/></small>
            <br>
            <input type="date" readonly name="checkIn" id="from" required value="${sessionScope.checkIn}">
            <t:output-errors errors="${checkInErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="order.make.form.checkout"/></b></label>
            <small><fmt:message key="order.make.form.checkout.rule"/></small>
            <br>
            <input type="date" readonly name="checkOut" id="to" required value="${sessionScope.checkOut}">
            <t:output-errors errors="${checkOutErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="order.make.form.bed"/></b></label>
            <small><fmt:message key="order.make.form.bed.select"/></small>
            <br>
            <select size="1" name="bed">
                <option disabled selected><fmt:message key="order.make.form.bed.select.name"/></option>

                <c:forEach items="${bedList}" var="bed">
                    <option value="${bed.bed}">${bed.bed}</option>
                </c:forEach>
            </select>

            <t:output-errors errors="${bedErrorMessages}"/>
            <br><br>

            <label><b><fmt:message key="order.make.form.roomType"/></b></label>
            <small><fmt:message key="order.make.form.roomType.select"/></small>
            <br>
            <select size="1" name="roomType">
                <option disabled selected><fmt:message key="order.make.form.roomType.select.name"/></option>

                <c:forEach items="${roomTypeList}" var="roomType">
                    <option value="${roomType.roomTypeEn}">${roomType.roomType}</option>
                </c:forEach>
            </select>

            <t:output-errors errors="${roomTypeErrorMessages}"/>
            <br><br>

            <button type="submit"><fmt:message key="order.make.submit"/></button>
            <br>
            <c:if test="${not empty orderErrorMessage}">
                <div id="errorcolortext"><fmt:message key="${order_formErrorMessages}"/></div>
            </c:if>


        </form>


    </jsp:body>

</t:genericpage>
