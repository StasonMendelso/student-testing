<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:03
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
        <h1>IT is showUsers.jsp</h1>
        <c:set var="list" value="${requestScope.userList}"/>
        <c:if var="result" test="${!empty list}">
            <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
                <c:forEach items="${requestScope.userList}" var="user">
                    <c:out value="${user}"/><br>
                </c:forEach>
            </div>
        </c:if>
        <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
            ${list}
        </div>
        <c:if test="${empty list}">
            <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
                No users have been found :(
            </div>
        </c:if>
    </div>

</body>
</html>