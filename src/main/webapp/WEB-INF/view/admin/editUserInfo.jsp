<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 22.02.2022
  Time: 1:06
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
<body class="body">

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
<c:set var="user" value="${requestScope.user}"/>

    <main class="container-fluid bg-dark bg-opacity-25">
        <div class="row d-flex justify-content-center ">

            <div class="w-50 bg-question  mb-5 bg-dark bg-opacity-50" style="border-radius: 30px 30px 30px 30px;box-shadow: 0 0 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
                <div class="text-left mt-2 pb-3 table-responsive">
                    <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top">
                        <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span style="padding-left: 25px">User information</span></caption>
                        <tbody class="bg-light">
                        <tr>
                            <td>Id</td>
                            <td>${user.id}</td>
                        </tr>
                        <tr>
                            <td>Login</td>
                            <td>${user.login}</td>
                        </tr>
                        <tr>
                            <td>Surname</td>
                            <td>${user.surname}</td>
                        </tr>
                        <tr>
                            <td>Name</td>
                            <td>${user.name}</td>
                        </tr>
                        </tbody>
                        <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0 0 30px 30px;"></caption>

                    </table>
                </div>
                <br>
                <div class="text-left mt-2 pb-3 table-responsive">
                    <form method="post">
                        <input hidden name="id" value="${user.id}">
                        <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top">
                            <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0 0;"><span style="padding-left: 25px">Edit user information</span></caption>
                            <tbody class="bg-light">
                            <tr>
                                <td>
                                    <input type="text" class="form-control"  id="surname" name="surname" placeholder="Surname">
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    <input type="text" class="form-control" id="name" name="name" placeholder="Name">
                                </td>


                            </tr>
                            <tr class="text-center">
                                <td>
                                    <c:forEach items="${requestScope.errorsList}" var="er">
                                        <errorlocalizator:localize error="${er}" lang="${sessionScope.lang}"/> <br>
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr class="text-center">
                                <td colspan="2"><input type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#editModal" style="width: 25%" value="Save"></td>
                            </tr>
                            </tbody>
                            <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0 0 30px 30px;"></caption>
                            <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLabel">Save edited information</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <form method="post" action="/web-application/testing/admin/editUser">
                                            <div class="modal-body">
                                                <label for="secretPassword" class="form-label">Уведіть ключ безпеки:</label>
                                                <input required id="secretPassword" type="password" name="secretPassword" class="form-control">
                                            </div>
                                            <div class="modal-footer">

                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <button  class="btn btn-outline-success" >Save</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </table>
                    </form>
                </div>

            </div>

        </div>
    </main>

<jsp:include page="../footer.jsp"/>


</body>
</html>