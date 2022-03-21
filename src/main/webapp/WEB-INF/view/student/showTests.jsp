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
<%--    <script type="text/javascript" src="${pageContext.request.contextPath}/js/sendTime.js"></script>--%>
    <script type=”text/javascript” src="${pageContext.request.contextPath}/js/sendTime.js"></script>
    <script><%@include file="../../../js/sendTime.js"%></script>

</head>
<body>

<jsp:include page="/WEB-INF/view/student/navbar.jsp"/>
<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex left-padding ">

        <div class="w-50 bg-dark  mb-1" style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 10px">
            <div class="text-left mt-2 pb-3 text-btn-primary">
                <h1 class="center">Sorting Tests</h1>
                <form name="sorting" method="get">
                <div class="row">
                    <div class="col-4">
                        <label for="orderBy">Сортувати за</label>

                        <select id="orderBy" name="orderBy" class="form-select"  >
                            <option value="name" <c:if test="${requestScope.sortingOptions.orderBy=='name'}"> selected</c:if> >Назвою</option>
                            <option value="difficulty" <c:if test="${requestScope.sortingOptions.orderBy=='difficulty'}"> selected</c:if> >Складністю</option>
                            <option value="countOfQuestions" <c:if test="${requestScope.sortingOptions.orderBy=='countOfQuestions'}"> selected</c:if> >Кількістю запитань</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label for="order">Порядок</label>
                        <select id="order" name="order" class="form-select" >
                            <option value="ascending"<c:if test="${requestScope.sortingOptions.order=='ascending'}"> selected</c:if> >Зростання</option>
                            <option value="descending"<c:if test="${requestScope.sortingOptions.order=='descending'}"> selected</c:if>  >Спадання</option>
                        </select>
                    </div>
                    <div class="col-4">
                        <label for="discipline">Вибір предмета</label>
                        <select id="discipline" name="discipline" class="form-select" >
                            <option value="all" selected>Усі</option>
                            <c:forEach var="discipline" items="${requestScope.disciplinesList}">
                                <option value="${discipline}" <c:if test="${requestScope.sortingOptions.discipline==discipline}"> selected</c:if>  >${discipline}</option>
                            </c:forEach>


                        </select>
                    </div>
                    <div class="col-2 text-center">
                        <br>
                        <button type="submit" class="btn btn-primary">Застосувати</button>
                    </div>
                </div>

                    <div class="row mt-2">

                        <div class="col-10">
                            <button type="submit" class="btn btn-primary w-100" name="clear" value="clear">Очистити сортування</button>
                        </div>
                        <div class="col-2"></div>
                    </div>
                </form>


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

                            <td class="text-center"><a class="btn btn-success" onclick="sendClientTime(${test.id})" href="#">Start</a></td>
                        </tr>

                    </c:forEach>
                    </tbody>
                    <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
                </c:if>

            </table>
        </c:if>



    </div>
    <c:if test="${empty tests}">
        <div class="w-50 bg-dark " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 20px 1px rgba(0,0,0,0.5); margin-top: 10px">
            <span class="text-white ms-3 fs-3">You don't have any available tests</span>
        </div>
    </c:if>

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



    </div>
    <c:if test="${empty tests}">
        <div class="w-50 bg-dark " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 20px 1px rgba(0,0,0,0.5); margin-top: 10px">
            <span class="text-white ms-3 fs-3">You don't have any available tests</span>
        </div>
    </c:if>
</main>
<jsp:include page="../footer.jsp"/>
</body>
</html>