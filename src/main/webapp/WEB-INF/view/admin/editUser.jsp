<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 22.02.2022
  Time: 1:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<html>
<head>
    <title>Testing</title>
</head>
<body class="body">

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
<div class="content">
    <h1>IT is editUser.jsp</h1>
    <c:set var="user" value="${requestScope.user}"/>
    <p>${user}</p>

    <div class="form" style="margin: 0 auto;margin-top: 10px; width: 90%">


    </div>



</div>

</body>
</html>