<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 21.02.2022
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />



<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!DOCTYPE html>
<html>

<head>
    <title>Testing</title>
    <style>
        <%@include file="/styles/styles.css"%>
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="body">
<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
<div class="content">

    <div class="form" style="text-align: center; margin-left: 40%">
        <h1>Successfully  creating Test!</h1>
        <p>Переглянути всі тести</p>
        <button onclick="location.href='/web-application/testing/admin/showTests'"><fmt:message key="admin.label.showTests"/></button>
    </div>

</div>

</body>
</html>
