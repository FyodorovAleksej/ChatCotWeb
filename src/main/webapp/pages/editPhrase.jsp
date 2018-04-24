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
<form class="text-center" action="/editPerform" method="post">
    <input type="text" name="phraseTextNew" value="${phraseText}"/>
    <select class="form-control" name="choiceType">
        <option value="standard greetings" ${phraseType == 'standard greetings' ? 'selected' : ''}>standard greetings</option>
        <option value="special greetings" ${phraseType == 'special greetings' ? 'selected' : ''}>special greetings</option>
        <option value="question greetings" ${phraseType == 'question greetings' ? 'selected' : ''}>question greetings</option>
        <option value="greetings answer" ${phraseType == 'greetings answer' ? 'selected' : ''}>greetings answer</option>
        <option value="yes" ${phraseType == 'yes' ? 'selected' : ''}>yes</option>
        <option value="no" ${phraseType == 'no' ? 'selected' : ''}>no</option>
        <option value="default" ${phraseType == 'default' ? 'selected' : ''}>default</option>
        <option value="initialize" ${phraseType == 'initialize' ? 'selected' : ''}>initialize</option>
        <option value="filter" ${phraseType == 'filter' ? 'selected' : ''}>filter</option>
        <option value="filter by check" ${phraseType == 'filter by check' ? 'selected' : ''}>filter check</option>
        <option value="adding" ${phraseType == 'adding' ? 'selected' : ''}>adding</option>
    </select>
    <button type="submit" class="btn btn-primary">Change</button>
    <button type="button" class="btn btn-primary" onclick='location.href="/admin"'>Cancel</button>
    <input name="phraseItemId" value="${phraseId}" style="visibility: hidden"/>
</form>
<!-- Bootstrap CSS -->
<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>