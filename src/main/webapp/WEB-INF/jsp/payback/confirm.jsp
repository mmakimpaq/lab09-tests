<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link href="/resources/css/main.css" rel="stylesheet">
</head>
<body>
<div id="payback-form">
    <h2>Payback confirmation</h2>
    <div>
        <span class="payback-label">Confirmation number:</span>
        <span class="payback-value">${paybackConfirmation.number}</span>
    </div>
    <div>
        <span class="payback-label">Account number:</span>
        <span class="payback-value">${paybackConfirmation.income.accountNumber}</span>
    </div>
    <div>
        <span class="payback-label">Payback awarded:</span>
        <span class="payback-value">${paybackConfirmation.income.amount}</span>
    </div>
    <div>
        <span class="payback-label">Payback distribution:</span>
        <div style="padding-left: 25px">
        <c:forEach var="distr" items="${paybackConfirmation.income.distributions}">
            <div class="payback-value">
                ${distr.amount} (${distr.allocation})
                added to ${distr.objective}
                totals in ${distr.totalSavings}
            </div>
        </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
