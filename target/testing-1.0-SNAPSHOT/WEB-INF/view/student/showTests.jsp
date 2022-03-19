<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 08.03.2022
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<jsp:include page="/WEB-INF/view/student/navbar.jsp"/>
<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex left-padding ">

        <div class="w-50 bg-dark  mb-1" style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 10px">
            <div class="text-left mt-2 pb-3 text-btn-primary">
                <h1 class="center">Sorting Available Tests</h1>
                <div class="row">
                    <div class="col-4">
                        <label for="orderBy">Сортувати за</label>
                        <select id="orderBy" class="form-select" >
                            <option value="1">Назвою</option>
                            <option value="2">Складністю</option>
                            <option value="3">Кількістю запитань</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label for="order">Порядок</label>
                        <select id="order" class="form-select" >
                            <option value="1">Зростання</option>
                            <option value="2">Спадання</option>
                        </select>
                    </div>
                    <div class="col-4">
                        <label for="discipline">Вибір предмета</label>
                        <select id="discipline" class="form-select" >
                            <option value="0" selected>Усі</option>
                            <option value="1">Математика</option>
                            <option value="2">Англійська</option>
                        </select>
                    </div>
                    <div class="col-2 text-center">
                        <br>
                        <button type="submit" class="btn btn-primary">Застосувати</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row d-flex justify-content-center table-responsive left-padding right-padding">
        <!--        <div class=" bg-light mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px; width:95%">-->
        <c:set var="tests" value="${requestScope.unsurpassedTestList}"/>
        <c:if var="result" test="${!empty tests}">

            <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top" >
                <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;"><span style="padding-left: 25px">Available Tests</span></caption>

                <thead class="table-dark text-center" >
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Discipline</th>
                    <th>Difficulty</th>
                    <th>Duration (Minutes)</th>
                    <th>Questions</th>
                    <th>Start</th>
                </tr>
                </thead>

                <c:if var="result" test="${!empty tests}">
                    <tbody class="bg-light">
                    <c:forEach items="${requestScope.unsurpassedTestList}" var="test">

                        <tr>
                            <td>${test.id}</td>
                            <td>${test.name}</td>
                            <td>${test.nameOfDiscipline}</td>
                            <td>${test.difficulty}</td>
                            <td>${test.timeMinutes}</td>
                            <td>${test.countOfQuestions}</td>
                            <td class="text-center"><button class="btn btn-success" onclick="location.href='/web-application/testing/student/test?id=${test.id}'">Start</button></td>
                        </tr>

                    </c:forEach>
                    </tbody>
                    <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
                </c:if>

            </table>
        </c:if>

        <c:if test="${empty tests}">
            <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
                You haven't passed any tests yet
            </div>
        </c:if>

    </div>
    <div class="row d-flex left-padding">

        <div class="w-50 bg-dark  mb-1 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 10px">
            <div class="text-left mt-2 pb-3 text-info">
                <h1 class="center">Sorting All Tests</h1>
                <div class="row">
                    <div class="col-4">
                        <label for="orderBy">Сортувати за</label>
                        <select id="orderBy" class="form-select" >
                            <option value="1">Назвою</option>
                            <option value="2">Складністю</option>
                            <option value="3">Кількістю запитань</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label for="order">Порядок</label>
                        <select id="order" class="form-select" >
                            <option value="1">Зростання</option>
                            <option value="2">Спадання</option>
                        </select>
                    </div>
                    <div class="col-4">
                        <label for="discipline">Вибір предмета</label>
                        <select id="discipline" class="form-select" >
                            <option value="0" selected>Усі</option>
                            <option value="1">Математика</option>
                            <option value="2">Англійська</option>
                        </select>
                    </div>
                    <div class="col-2 text-center">
                        <br>
                        <button class="btn btn-primary">Застосувати</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row d-flex justify-content-center table-responsive left-padding right-padding">
        <!--        <div class=" bg-light mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px; width:95%">-->
        <c:set var="tests" value="${requestScope.allTestList}"/>
        <c:if var="result" test="${!empty tests}">

            <table class="table table-bordered table-hover table-striped mt-3 rounded-top caption-top" >
                <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;"><span style="padding-left: 25px">All Tests</span></caption>

                <thead class="table-dark text-center" >
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Discipline</th>
                    <th>Difficulty</th>
                    <th>Duration (Minutes)</th>
                    <th>Questions</th>
                </tr>
                </thead>

                <c:if var="result" test="${!empty tests}">
                    <tbody class="bg-light">
                    <c:forEach items="${requestScope.allTestList}" var="test">

                        <tr>
                            <td>${test.id}</td>
                            <td>${test.name}</td>
                            <td>${test.nameOfDiscipline}</td>
                            <td>${test.difficulty}</td>
                            <td>${test.timeMinutes}</td>
                            <td>${test.countOfQuestions}</td>
                        </tr>

                    </c:forEach>
                    </tbody>
                    <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
                </c:if>

            </table>
        </c:if>

        <c:if test="${empty tests}">
            <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
                You haven't passed any tests yet
            </div>
        </c:if>

    </div>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>