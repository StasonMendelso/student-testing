<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 14.02.2022
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <p>It is student's page<p>
    <p>Your Name is: ${sessionScope.name}</p>
    <p>Your Surname is: ${sessionScope.surname}</p>
    <p>Your Login is: ${sessionScope.login}</p>
    <p>Your id is: ${sessionScope.id}</p>
    <p>Your Password is: ${sessionScope.password}</p>
</div>

</body>
</html>
