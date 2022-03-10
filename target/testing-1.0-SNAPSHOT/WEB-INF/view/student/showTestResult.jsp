<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 10.03.2022
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<html>
<head>
    <title>Testing</title>
    <style>
        <%@include file="/styles/styles.css"%>
    </style>
</head>
<body class="body">

<jsp:include page="/WEB-INF/view/student/navbar.jsp"/>
<div class="content">
    <div>
        <c:set var="test" value="${sessionScope.test}"/>
        <div class="form">
            <p>Your Result</p>
            <p>${requestScope.mark} %</p>
        </div>
    </div>
</div>
</body>
</html>
