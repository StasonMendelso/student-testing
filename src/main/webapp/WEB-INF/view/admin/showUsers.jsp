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
        <c:if var="result" test="${!empty userList}">
        <div class="pt-3 table-responsive-md">
            <c:set var="userList" value="${requestScope.userList}"/>
            <form method="post" id="pagination_form">
                <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top">
                    <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;">
                        <div class="row align-items-center">
                            <span class="col-2" style="padding-left: 25px" >Users</span>
                            <span class="col-9"></span>
                            <span class="col-1" style="padding-right: 25px">
                                <select class="form-select" name="paginationParameter" id="pagination">
                                    <option value="5" <c:if test="${requestScope.paginationParameter==5}">selected</c:if> >5</option>
                                    <option value="10" <c:if test="${requestScope.paginationParameter==10}">selected</c:if> >10</option>
                                    <option value="15" <c:if test="${requestScope.paginationParameter==15}">selected</c:if> >15</option>
                                </select>
                                         <script>
                                    document.getElementById("pagination").onchange= function () {
                                        document.getElementById("pagination_form").submit();
                                    }
                                </script>
                            </span>

                        </div>
                    </caption>
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

                        <c:forEach items="${requestScope.userList}" var="user">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.login}</td>
                                <td>${user.name}</td>
                                <td>${user.surname}</td>
                                <td>${user.role}</td>
                                <td>${user.getStringBlocked()}</td>

                                <c:if test="${!user.blocked}"> <td class="text-center align-middle"><button class="btn btn-warning" <c:if test="${user.role=='ADMIN'}">disabled</c:if> class="button-block" type="button" onclick="location.href='/web-application/testing/admin/blockUser?id=${user.id}&pageNumber=${requestScope.pageNumber}&paginationParameter=${requestScope.paginationParameter}'">Block</button></td></c:if>
                                <c:if test="${user.blocked}"> <td class="text-center align-middle"><button class="btn btn-info" type="button" onclick="location.href='/web-application/testing/admin/unblockUser?id=${user.id}&pageNumber=${requestScope.pageNumber}&paginationParameter=${requestScope.paginationParameter}'">UnBlock</button></td></c:if>
                                <td class="text-center align-middle"><button class="btn btn-success"type="button" onclick="location.href='/web-application/testing/admin/editUser?id=${user.id}&pageNumber=${requestScope.pageNumber}&paginationParameter=${requestScope.paginationParameter}'">Edit</button></td>
                                <td class="text-center align-middle"><button class="btn btn-danger" type="button" onclick="location.href='/web-application/testing/admin/deleteUser?id=${user.id}&pageNumber=${requestScope.pageNumber}&paginationParameter=${requestScope.paginationParameter}'">Delete</button></td>
                            </tr>

                        </c:forEach>

                    </tbody>
                    <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
                </table>
                <nav >
                    <ul class="pagination pagination-lg justify-content-center">
                        <c:forEach var="i" begin="1" end="${requestScope.countOfPageNumberButtons}">
                            <c:if test="${requestScope.pageNumber==i}">
                                <li class="page-item active" >
                                    <span class="page-link bg-light border-light text-black">${i}</span>
                                </li>
                            </c:if>
                            <c:if test="${requestScope.pageNumber!=i}">
                                <li class="page-item "><button class="page-link1 bg-dark border-dark text-white" type="submit" name="pageNumber" value="${i}">${i}</button></li>
                            </c:if>

                        </c:forEach>

                    </ul>
                </nav>
                </c:if>
                 <c:if test="${empty userList}">
                <div>
                    No users have been found :(
                </div>
                </c:if>
            </form>

        </div>
    </main>

    <jsp:include page="../footer.jsp"/>
</body>
</html>