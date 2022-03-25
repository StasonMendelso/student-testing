<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 14.02.2022
  Time: 13:00
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<body>

<jsp:include page="navbar.jsp"/>
<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex justify-content-center ">

        <div class="w-25 bg-dark  mb-2 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 35px">
            <div class="text-left mt-2 pb-3 text-info">
                <h1 class="text-center t-5">Student info</h1>
                <p>Login: ${sessionScope.login}</p>
                <p>Student name: ${sessionScope.name}</p>
                <p>Student surname: ${sessionScope.surname}</p>
            </div>
        </div>
    </div>
    <div class="row d-flex justify-content-center table-responsive  left-padding right-padding">
        <!--        <div class=" bg-light mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px; width:95%">-->
        <c:set var="tests" value="${requestScope.testList}"/>
        <c:if var="result" test="${!empty tests}">
        <form id="pagination_form" method="post">
            <table class="table table-bordered table-hover table-striped mt-3 rounded-top caption-top" >
                <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;">
                    <div class="row align-items-center">
                        <span class="col-1" >Passed Tests</span>
                        <span class="col-10"></span>
                        <span class="col-1" style="padding-right: 25px">
                        <select class="form-select" name="paginationParameter" id="pagination">
                            <option value="5" <c:if test="${requestScope.paginationParameter==5}">selected</c:if> >5</option>
                            <option value="10" <c:if test="${requestScope.paginationParameter==10}">selected</c:if> >10</option>
                            <option value="15" <c:if test="${requestScope.paginationParameter==15}">selected</c:if> >15</option>
                        </select>
                    </span>

                    </div>

                    <script>
                        document.getElementById("pagination").onchange= function () {
                            document.getElementById("pagination_form").submit();
                        }
                    </script>
                </caption>

                <thead class="table-dark text-center" >
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Discipline</th>
                    <th>Difficulty</th>
                    <th>Duration (Minutes)</th>
                    <th>Questions</th>
                    <th>Mark</th>

                </tr>
                </thead>

                <c:if var="result" test="${!empty tests}">
                    <tbody class="bg-light">
                    <c:forEach items="${requestScope.testList}" var="test">

                        <tr>
                            <td>${test.id}</td>
                            <td>${test.name}</td>
                            <td>${test.nameOfDiscipline}</td>
                            <td>${test.difficulty}</td>
                            <td>${test.timeMinutes}</td>
                            <td>${test.countOfQuestions}</td>

                            <td>${test.mark} %</td>
                        </tr>

                    </c:forEach>
                    </tbody>
                    <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
                </c:if>

            </table>
            </c:if>

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
        <c:if test="${empty tests}">
            <div class="w-25 bg-dark  mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 45px">
                <div class="text-left mt-2 pb-3 text-info">
                    You haven't passed any tests yet
                </div>
            </div>
        </c:if>

    </div>
</main>

<jsp:include page="../footer.jsp"/>

</body>
</html>
