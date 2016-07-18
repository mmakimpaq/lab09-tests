<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link href="/resources/css/main.css" rel="stylesheet">
</head>
<body>
<div id="payback-form">
    <h2>Enter transaction data:</h2>
    <form:form method="POST"
               action="${pageContext.request.contextPath}/payback/confirm"
               commandName="purchaseForm">

        <form:label path="creditCardNumber">Credit card number:</form:label>
        <form:input path="creditCardNumber"/>

        <form:label path="merchantNumber">Merchant number:</form:label>
        <form:input path="merchantNumber"/>

        <form:label path="transactionValue">Transaction value:</form:label>
        <form:input path="transactionValue"/>

        <input type="submit" value="Get Payback!" id="submit" />
    </form:form>
</div>
</body>
</html>
