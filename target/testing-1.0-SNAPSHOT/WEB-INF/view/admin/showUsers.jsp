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
        <c:set var="userList" value="${requestScope.userList}"/>

            <table style="width: 90%; margin: 5%; margin-top: 2%">
                <tr><th>Id</th><th>Login</th><th>Name</th><th>Surname</th><th>Role</th><th>Blocked</th><th colspan="3">Действия Админа</th></tr>
                <c:if var="result" test="${!empty userList}">
                        <c:forEach items="${requestScope.userList}" var="user">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.login}</td>
                                <td>${user.name}</td>
                                <td>${user.surname}</td>
                                <td>${user.role}</td>
                                <td>${user.getStringBlocked()}</td>

                                <c:if test="${!user.blocked}"> <td class="td-center"><button <c:if test="${user.role=='ADMIN'}">disabled</c:if> class="button-block" type="button" onclick="location.href='/web-application/testing/admin/blockUser?id=${test.id}'">Block</button></td></c:if>
                                <c:if test="${user.blocked}"> <td><button class="td-center button-unblock" type="button" onclick="location.href='/web-application/testing/admin/unblockUser?id=${user.id}'">UnBlock</button></td></c:if>
                                <td><button class="td-center button-edit" type="button" onclick="location.href='/web-application/testing/admin/editUser?id=${user.id}'">Edit</button></td>
                                <td><button class="td-center button-delete" type="button" onclick="location.href='/web-application/testing/admin/deleteUser?id=${user.id}'">Delete</button></td>
                            </tr>
                        </c:forEach>
                </c:if>

            </table>

        <c:if test="${empty userList}">
            <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
                No users have been found :(
            </div>
        </c:if>


    </div>

</body>
</html>