<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="text" />
<html lang=${language}>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Register</title>

    <!-- Bootstrap CSS -->
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <script src="${request.contextPath}/js/http_cdnjs.cloudflare.com_ajax_libs_popper.js_1.12.9_umd_popper.js"></script>
    <script src="${request.contextPath}/js/jquery-latest.js"></script>
    <script src="${request.contextPath}/js/bootstrap.min.js"></script>
</head>
<body>
<form action="reg" method="post">
    <h4>Login:</h4>
    <input type="text" class="text" name="login">
    <br/>
    <h4>Email:</h4>
    <input type="text" class="text" name="email">
    <br/>
    <h4>Password:</h4>
    <input type="password" class="password-field" name="password">
    <br/>
    <h4>Repeat password:</h4>
    <input type="password" class="password-field" name="repPassword">
    <br/>
    <button type="submit" class="btn btn-primary">signUp</button>
</form>
<h1>${registerResult}</h1>
</body>
</html>