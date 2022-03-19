<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 10.03.2022
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

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
<body >

<jsp:include page="/WEB-INF/view/student/navbar.jsp"/>
<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex justify-content-center ">

        <div class="w-75 fs-3 bg-result  mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
            <div class="text-left mt-2 pb-3 row me-3 ms-3 d-flex align-items-center">
                <div class="col-1">

                </div>
                <div class="col-10">
                    <h1 class="text-center t-5">Test result</h1>
                </div>
                <div class="col-1"></div>
            </div>

            <div class="text-left mt-2 pb-3 row me-3 ms-3 d-flex align-items-center">


                <div class="col-12">
                    <c:set var="test" value="${requestScope.test}"/>
                    <p>Назва теста: <<${test.name}>></p>
                    <p>Дисципліна: ${test.nameOfDiscipline}</p>
                    <div class="progress">
                        <div class="progress-bar progress-bar-striped progress-bar-animated fw-bold fs-6" role="progressbar" aria-valuenow="${requestScope.mark}" aria-valuemin="0" aria-valuemax="100" style="width: ${requestScope.mark}%">${requestScope.mark}%</div>
                    </div>
                    <p class="fw-bold pt-2">Correct Answer: ${requestScope.countOfRightAnswers}/${test.countOfQuestions}</p>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>
