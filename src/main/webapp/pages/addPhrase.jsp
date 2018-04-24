<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="en" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<html lang="${language}">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Edit phrase</title>
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<form class="text-center" action="/addPerform" method="post">
    <input type="text" name="phraseTextNew"/>
    <select class="form-control" name="choiceType">
        <option value="standard greetings">standard greetings</option>
        <option value="special greetings">special greetings</option>
        <option value="question greetings">question greetings</option>
        <option value="greetings answer">greetings answer</option>
        <option value="yes">yes</option>
        <option value="no">no</option>
        <option value="default" selected>default</option>
        <option value="initialize">initialize</option>
        <option value="filter">filter</option>
        <option value="filter by check">filter check</option>
        <option value="adding">adding</option>
    </select>
    <button type="submit" class="btn btn-primary">Add</button>
    <button type="button" class="btn btn-primary" onclick='location.href="/admin"'>Cancel</button>
</form>
<!-- Bootstrap CSS -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>