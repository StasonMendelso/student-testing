<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="converter" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

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

<main class="container-fluid bg-dark bg-opacity-25">
    <form method="post">
        <input hidden name="paginationParameter" value="${requestScope.paginationParameter}">
        <div class="row d-flex left-padding ">

            <div class="w-50 bg-dark  mb-1" style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 10px">
                <div class="text-left mt-2 pb-3 text-btn-primary">
                    <h1 class="center"><fmt:message key="sorting.tests"/></h1>
                    <div class="row">
                        <div class="col-3">
                            <label for="orderBy"><fmt:message key="sorting.order.by"/></label>
                            <select id="orderBy" name="orderBy" class="form-select" >
                                <option value="name" <c:if test="${requestScope.sortingOptions.orderBy=='name'}"> selected</c:if> ><fmt:message key="sorting.order.by.name"/></option>
                                <option value="difficulty" <c:if test="${requestScope.sortingOptions.orderBy=='difficulty'}"> selected</c:if> ><fmt:message key="sorting.order.by.difficulty"/></option>
                                <option value="countOfQuestions" <c:if test="${requestScope.sortingOptions.orderBy=='countOfQuestions'}"> selected</c:if> ><fmt:message key="sorting.order.by.number.of.question"/></option>
                            </select>
                        </div>
                        <div class="col-3">
                            <label for="order"><fmt:message key="sorting.order"/></label>
                            <select id="order" name="order" class="form-select" >
                                <option value="ASC"<c:if test="${requestScope.sortingOptions.order=='ASC'}"> selected</c:if> ><fmt:message key="sorting.order.ascending"/></option>
                                <option value="DESC"<c:if test="${requestScope.sortingOptions.order=='DESC'}"> selected</c:if>  ><fmt:message key="sorting.order.descending"/></option>
                            </select>
                        </div>
                        <div class="col-4">
                            <label for="discipline"><fmt:message key="sorting.choose.subject"/></label>
                            <select id="discipline" name="discipline" class="form-select" >
                                <option value="all" selected><fmt:message key="sorting.choose.subject.all"/></option>
                                <c:forEach var="discipline" items="${requestScope.disciplinesList}">
                                    <option value="${discipline}" <c:if test="${requestScope.sortingOptions.discipline==discipline}"> selected</c:if>  >${discipline}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2 text-center">
                            <br>
                            <button type="submit" class="btn btn-primary"><fmt:message key="sorting.apply"/></button>
                        </div>
                    </div>
                    <div class="row mt-2">
                        <div class="col-10">
                            <button class="btn btn-primary w-100" type="submit" name="clear" value="clear"><fmt:message key="sorting.clear"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>


        <div class="table-responsive-md">
        <c:set var="tests" value="${requestScope.testList}"/>
        <c:if var="result" test="${!empty tests}">
        <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top">
            <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;">
                <div class="row align-items-center">
                    <span class="col-2" style="padding-left: 25px" ><fmt:message key="show_tests.tests"/></span>
                    <span class="col-8 text-center">${error}</span>
                    <span class="col-1"></span>
                    <span class="col-1" style="padding-right: 25px">
                        <form method="post" id="pagination_form">
                        <input hidden name="orderBy" value="${requestScope.sortingOptions.orderBy}">
                        <input hidden name="order" value="${requestScope.sortingOptions.order}">
                        <input hidden name="discipline" value="${requestScope.sortingOptions.discipline}">
                            <select class="form-select" name="paginationParameter" id="pagination">
                                <option value="5" <c:if test="${requestScope.paginationParameter==5}">selected</c:if> >5</option>
                                <option value="10" <c:if test="${requestScope.paginationParameter==10}">selected</c:if> >10</option>
                                <option value="15" <c:if test="${requestScope.paginationParameter==15}">selected</c:if> >15</option>
                            </select>
                                     <script>
                                document.getElementById("pagination").onchange= function () {
                                    let form = document.getElementById("pagination_form").submit();
                                }
                            </script>
                               </form>
                        </span>

                </div>
            <thead class="table-dark text-center">

            <tr>
                <th>Id</th>
                <th><fmt:message key="table.tests.test"/></th>
                <th><fmt:message key="table.tests.discipline"/></th>
                <th><fmt:message key="table.tests.difficulty"/></th>
                <th><fmt:message key="table.tests.duration"/></th>
                <th><fmt:message key="table.tests.number.of.questions"/></th>
                <th colspan="2"><fmt:message key="table.tests.manage"/></th>
            </tr>
            </thead>
            <c:if var="result" test="${!empty tests}">

                    <tbody class="bg-light">
                    <c:set var="i" value="${1}"></c:set>
                    <c:forEach items="${requestScope.testList}" var="test">
                    <tr>
                        <td>${test.id}</td>
                        <td>${test.name}</td>
                        <td>${test.nameOfDiscipline}</td>
                        <td><converter:difficulty intDifficulty="${test.difficulty}"/></td>
                        <td>${test.timeMinutes}</td>
                        <td>${test.countOfQuestions}</td>

                        <td class="text-center"><button  class="btn btn-success" type="button" onclick="location.href='/web-application/testing/admin/editTest?id=${test.id}'"><fmt:message key="button.edit"/></button></td>
                        <td class="text-center"><button class="btn btn-danger" type="button" data-bs-toggle="modal" data-bs-target="#deleteModal${i}"><fmt:message key="button.delete"/></button></td>
                    </tr>
                        <!-- Delete Modal -->
                        <div class="modal fade" id="deleteModal${i}" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="modal.delete.test"/></h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <form method="post" action="/web-application/testing/admin/deleteTest">
                                        <div class="modal-body">
                                            <label for="secretPassword" class="form-label"><fmt:message key="modal.enter.secret.key"/>:</label>
                                            <input required id="secretPassword" type="password" name="secretPassword" class="form-control">
                                            <input hidden type="text" name="pageNumber" value="${requestScope.pageNumber}">
                                            <input hidden type="text" name="paginationParameter" value="${requestScope.paginationParameter}">
                                            <input hidden type="text" name="id" value="${test.id}"/>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal"><fmt:message key="modal.close"/></button>
                                            <button  class="btn btn-outline-danger" ><fmt:message key="modal.delete"/></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%-- Delete modal--%>
                        <c:set var="i" value="${i+1}"></c:set>

                    </c:forEach>
                    </tbody>

            </c:if>
            <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
        </table>
            <form method="post">
            <input hidden name="orderBy" value="${requestScope.sortingOptions.orderBy}">
            <input hidden name="order" value="${requestScope.sortingOptions.order}">
            <input hidden name="discipline" value="${requestScope.sortingOptions.discipline}">
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



    </div>
    <c:if test="${empty tests}">

        <div class="w-25 bg-dark  mb-5 "
             style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 45px">
            <div class="text-left ms-3 mt-3 pb-3 text-info">
                <fmt:message key="show_tests.not_any_tests"/>
            </div>
        </div>


    </c:if>
</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>