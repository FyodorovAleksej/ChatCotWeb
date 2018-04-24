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
    <title>ChatCot admin</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<table>
    <tr>
        <th>
            <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;"></h5>
        </th>
        <th>
            <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;"></h5>
        </th>
        <th>
            <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">Phrase</h5>
        </th>
        <th>
            <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">Type</h5>
        </th>
        <th>
            <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">Owner</h5>
        </th>
        <th>
            <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">Date</h5>
        </th>
    </tr>
    <c:forEach items="${phrasesList}" var="phraseItem">
        <tr>
            <td>
                <button class="btn btn-primary" onclick='location.href="/edit?phraseItemId=${phraseItem.getId()}"'>Edit</button>
            </td>
            <td>
                <button class="btn btn-primary" onClick='location.href="/delete?phraseItemId=${phraseItem.getId()}"'>Delete</button>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">${phraseItem.getPhrase()}</h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">${phraseItem.getType()}</h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">${phraseItem.getOwner()}</h5>
            </td>
            <td>
                <h5 style="padding-left: 25px; margin-left: 10px; margin-bottom: 20px; margin-right: 20px;">${phraseItem.getDate()}</h5>
            </td>
        </tr>
    </c:forEach>
</table>
<button class="btn btn-primary" onClick='location.href="/pages/addPhrase.jsp"'>Add</button>

<script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
<script src="${request.contextPath}/js/jquery-latest.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
