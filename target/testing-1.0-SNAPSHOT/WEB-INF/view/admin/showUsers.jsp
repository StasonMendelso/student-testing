<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="errorlocalizator" uri="errorLocalizationURI" %>
<%@ taglib prefix="converter" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

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

                <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top">

                    <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;">
                        <div class="row align-items-center">
                            <span class="col-2" style="padding-left: 25px" >  <fmt:message key="show_users.users"/>  </span>
                            <span class="col-8 text-center"><errorlocalizator:localize error="${error}" lang="${sessionScope.lang}"/></span>
                            <span class="col-1"></span>
                            <span class="col-1" style="padding-right: 25px">
                                 <form method="post" id="pagination_form" >
                                <select class="form-select" name="paginationParameter" id="pagination">
                                    <option value="5" <c:if test="${requestScope.paginationParameter==5}">selected</c:if> >5</option>
                                    <option value="10" <c:if test="${requestScope.paginationParameter==10}">selected</c:if> >10</option>
                                    <option value="15" <c:if test="${requestScope.paginationParameter==15}">selected</c:if> >15</option>
                                </select>
                            </form>
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
                        <th><fmt:message key="table.users.name"/></th>
                        <th><fmt:message key="table.users.surname"/></th>
                        <th><fmt:message key="table.users.role"/></th>
                        <th><fmt:message key="table.users.blocked"/></th>
                        <th colspan="4"><fmt:message key="table.users.manage"/></th>
                    </tr>
                    </thead>
                    <tbody class="bg-light ">
                        <c:set var="i" value="${1}"></c:set>
                        <c:forEach items="${requestScope.userList}" var="user">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.login}</td>
                                <td>${user.name}</td>
                                <td>${user.surname}</td>
                                <td>${user.role}</td>
                                <td><converter:blocked blockedValue="${user.blocked}"/></td>

                                <td class="text-center align-middle"><button class="btn btn-primary"type="button" <c:if test="${user.role=='ADMIN'}">disabled</c:if> onclick="location.href='/web-application/testing/admin/userTests?id=${user.id}&pageNumber=${requestScope.pageNumber}&paginationParameter=${requestScope.paginationParameter}'"><fmt:message key="button.tests"/></button></td>
                                <td class="text-center align-middle"><button class="btn btn-success"type="button" onclick="location.href='/web-application/testing/admin/editUser?id=${user.id}&pageNumber=${requestScope.pageNumber}&paginationParameter=${requestScope.paginationParameter}'"><fmt:message key="button.edit"/></button></td>
                                <c:if test="${!user.blocked}"> <td class="text-center align-middle"><button class="btn btn-warning" <c:if test="${user.role=='ADMIN'}">disabled</c:if> class="button-block" data-bs-toggle="modal" data-bs-target="#blockModal${i}" type="button" ><fmt:message key="button.block"/></button></td></c:if>
                                <c:if test="${user.blocked}"> <td class="text-center align-middle"><button class="btn btn-info" type="button" data-bs-toggle="modal" data-bs-target="#unblockModal${i}"><fmt:message key="button.unblock"/></button></td></c:if>
                                <td class="text-center align-middle"><button class="btn btn-danger" type="button" data-bs-toggle="modal" data-bs-target="#deleteModal${i}"><fmt:message key="button.delete"/></button></td>

                                <!-- Block Modal -->
                                <div class="modal fade" id="blockModal${i}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="modal.block.user"/></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <form method="post" action="/web-application/testing/admin/blockUser">
                                                <div class="modal-body">
                                                    <label for="secretPassword" class="form-label"><fmt:message key="modal.enter.secret.key"/>:</label>
                                                    <input required id="secretPassword" type="password" name="secretPassword" class="form-control">
                                                    <input hidden type="text" name="pageNumber" value="${requestScope.pageNumber}">
                                                    <input hidden type="text" name="paginationParameter" value="${requestScope.paginationParameter}">
                                                    <input hidden type="text" name="id" value="${user.id}"/>
                                                </div>
                                                <div class="modal-footer">

                                                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"><fmt:message key="modal.close"/></button>
                                                    <button  class="btn btn-outline-warning" ><fmt:message key="modal.block"/></button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                    <%-- block modal--%>
                                <!-- Unblock Modal -->
                                <div class="modal fade" id="unblockModal${i}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="modal.unblock.user"/></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <form method="post" action="/web-application/testing/admin/unblockUser">
                                                <div class="modal-body">
                                                    <label for="secretPassword" class="form-label"><fmt:message key="modal.enter.secret.key"/>:</label>
                                                    <input required id="secretPassword" type="password" name="secretPassword" class="form-control">
                                                    <input hidden type="text" name="pageNumber" value="${requestScope.pageNumber}">
                                                    <input hidden type="text" name="paginationParameter" value="${requestScope.paginationParameter}">
                                                    <input hidden type="text" name="id" value="${user.id}"/>
                                                </div>
                                                <div class="modal-footer">

                                                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"><fmt:message key="modal.close"/></button>
                                                    <button  class="btn btn-outline-info" ><fmt:message key="modal.unblock"/></button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                    <%-- Unblock modal--%>
                                <!-- Delete Modal -->
                                <div class="modal fade" id="deleteModal${i}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="modal.delete.user"/></h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <form method="post" action="/web-application/testing/admin/deleteUser">
                                                <div class="modal-body">
                                                    <label for="secretPassword" class="form-label"><fmt:message key="modal.enter.secret.key"/>:</label>
                                                    <input required id="secretPassword" type="password" name="secretPassword" class="form-control">
                                                    <input hidden type="text" name="pageNumber" value="${requestScope.pageNumber}">
                                                    <input hidden type="text" name="paginationParameter" value="${requestScope.paginationParameter}">
                                                    <input hidden type="text" name="id" value="${user.id}"/>
                                                </div>
                                                <div class="modal-footer">
                                                     <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"><fmt:message key="modal.close"/></button>
                                                    <button  class="btn btn-outline-danger"><fmt:message key="modal.delete"/></button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                    <%-- Delete modal--%>
                            </tr>
                            <c:set var="i" value="${i+1}"></c:set>
                        </c:forEach>

                    </tbody>
                    <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
                </table>
           <form method="post">
               <input hidden name="paginationParameter" value="${requestScope.paginationParameter}">
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
            </form>
                </c:if>
                 <c:if test="${empty userList}">
                     <div class="w-25 bg-dark  mb-5 "
                          style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 45px">
                         <div class="text-left mt-2 pb-3 text-info">
                             <fmt:message key="show_users.not.found.any.user"/>
                         </div>
                     </div>
                </c:if>


        </div>
    </main>

    <jsp:include page="../footer.jsp"/>
</body>
</html>