<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 09.03.2022
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
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
    <script type=”text/javascript” src="${pageContext.request.contextPath}/js/timer2.js"></script>
    <script><%@include file="../../../js/timer2.js"%></script>
</head>
<body>

<jsp:include page="/WEB-INF/view/student/navbar.jsp"/>

<main class="container-fluid bg-dark bg-opacity-25">
    <c:set var="i" value="${1}" scope="page"/>
    <c:set var="test" value="${sessionScope.test}"/>

    <c:set var="question" value="${requestScope.question}"/>
    <div class="row  d-flex justify-content-between ps-3 pe-3 ">
        <form  id="UserAnswers" method="post">
        <div class="bg-question mb-5 container" style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
            <div class="text-left mt-2 pb-3">
                <div class="row">
                    <div class="col-3"></div>
                    <div class="col-6 text-center">
                        <div class=" p-2">
                            <h1 class="t-5"><fmt:message key="doTest.question"/> №${question.questionNumber}</h1>
                        </div>
                    </div>
                    <div class="col-3">
                        <div class=" text-end fs-3 me-3 p-2" >
                          <span ><fmt:message key="doTest.remain"/>
                          <div class="timer" id="timer"></div>
                          </span>
                            <script>
                                startTimer(${sessionScope.outDateMilliseconds});

                            </script>

                        </div>
                    </div>
                </div>


                    <div class="row d-flex justify-content-center">

                        <div class="col-12 fs-5 ms-4">

                            <h2> ${question.textQuestion} </h2>
                            <c:forEach var="answer" items="${question.answers}">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="opt" value="${i}" id="flexCheckDefault" <c:if test="${question.userOptions.get(i-1)}">checked</c:if>>
                                    <label class="form-check-label" for="flexCheckDefault">
                                        ${answer.answer}
                                    </label>
                                </div>
                                <c:set var="i" value="${i+1}" scope="page"/>
                            </c:forEach>
                            <input hidden name="questionNumber" value="${question.questionNumber}">
                            <input hidden name="save" value="save"/>
                        </div>

                        <div class="row mt-3">
                            <div class="col-6">
                                <c:set var="previous" value="${question.questionNumber-1}"/>
                                <c:if test="${question.questionNumber !=1}"> <button class="btn btn-dark bg-dark bg-opacity-75 border-0 text-white w-100 fs-5"  type="submit" name="nextQuestion" value="${previous}"> <fmt:message key="doTest.previous"/> </button> </c:if>

                            </div>
                            <div class="col-6">
                                <c:set var="next" value="${question.questionNumber+1}"/>
                                <c:if test="${question.questionNumber !=test.countOfQuestions}"> <button class="btn btn-dark bg-dark bg-opacity-75 border-0 text-white w-100 fs-5" type="submit" name="nextQuestion" value="${next}"> <fmt:message key="doTest.next"/> </button> </c:if>

                            </div>

                        </div>
                        <div class="row pt-2">
                            <div class="col-12">
                                <button class="btn btn-dark w-100 fs-3" type="submit" name="finish" value="finish"><fmt:message key="doTest.finish"/></button>
                            </div>

                        </div>

                    </div>


            </div>

        </div>
        <nav >
            <ul class="pagination pagination-lg justify-content-center">
                <c:forEach var="count" begin="1" end="${test.countOfQuestions}">
                    <c:if test="${count==question.questionNumber}">
                        <li class="page-item active" >
                            <span class="page-link bg-light border-light text-black">${count}</span>
                        </li>
                    </c:if>
                    <c:if test="${count!=question.questionNumber}">
                        <li class="page-item "><button class="page-link1 bg-dark border-dark text-white" type="submit" name="nextQuestion" value="${count}">${count}</button></li>
                    </c:if>

                </c:forEach>


            </ul>
        </nav>
        </form>
    </div>
</main>
<jsp:include page="../footer.jsp"/>

</body>
</html>
