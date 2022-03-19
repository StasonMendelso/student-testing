<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<html>
<head>
    <title>Testing</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../../../css/style.css"%>
    </style>
    <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/262/262825.png" type="image/x-icon"/>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>

    <jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
    <main class="container-fluid bg-dark bg-opacity-25">
        <div class="pt-3 table-responsive-md">
            <c:set var="userList" value="${requestScope.userList}"/>

            <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top">
                <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;"><span style="padding-left: 25px">Tests</span></caption>
                <thead class="table-dark text-center">
                <tr>
                    <th>Id</th>
                    <th>Login</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Role</th>
                    <th>Blocked</th>
                    <th colspan="3">Действия Админа</th>
                </tr>
                </thead>
                <tbody class="bg-light ">
                <c:if var="result" test="${!empty userList}">
                    <c:forEach items="${requestScope.userList}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.login}</td>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>${user.role}</td>
                            <td>${user.getStringBlocked()}</td>

                            <c:if test="${!user.blocked}"> <td class="text-center align-middle"><button class="btn btn-warning" <c:if test="${user.role=='ADMIN'}">disabled</c:if> class="button-block" type="button" onclick="location.href='/web-application/testing/admin/blockUser?id=${user.id}'">Block</button></td></c:if>
                            <c:if test="${user.blocked}"> <td class="text-center align-middle"><button class="btn btn-info" type="button" onclick="location.href='/web-application/testing/admin/unblockUser?id=${user.id}'">UnBlock</button></td></c:if>
                            <td class="text-center align-middle"><button class="btn btn-success"type="button" onclick="location.href='/web-application/testing/admin/editUser?id=${user.id}'">Edit</button></td>
                            <td class="text-center align-middle"><button class="btn btn-danger" type="button" onclick="location.href='/web-application/testing/admin/deleteUser?id=${user.id}'">Delete</button></td>
                        </tr>

                    </c:forEach>
                </c:if>
                </tbody>
                <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
            </table>

             <c:if test="${empty userList}">
            <div>
                No users have been found :(
            </div>
            </c:if>


        </div>
    </main>

    <footer class="bg-dark bg-opacity-75 text-center navbar-nav p-3 text-light container-fluid">
        <!-- Copyright -->
        <div class="text-center">
            © 2022 Copyright:
            <a class="text-light" href="#" style="text-decoration: none">Stas Hlova</a>
        </div>
        <!-- Copyright -->
    </footer>
</body>
</html>