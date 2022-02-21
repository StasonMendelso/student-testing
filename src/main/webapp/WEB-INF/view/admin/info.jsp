<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<html>
<head>
    <title>Testing</title>
</head>
<body class="body">

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
<div class="content">
    <h1>IT is showUsers.jsp</h1>
    <p>It is admin's page<p>
    <p>Your Name is: ${sessionScope.name}</p>
    <p>Your Surname is: ${sessionScope.surname}</p>
    <p>Your Login is: ${sessionScope.login}</p>
    <p>Your id is: ${sessionScope.id}</p>
    <p>Your Password is: ${sessionScope.password}</p>
</div>

</body>
</html>