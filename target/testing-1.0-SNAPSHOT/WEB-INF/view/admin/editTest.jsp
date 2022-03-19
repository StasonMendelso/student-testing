<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 23.02.2022
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:02
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
<body class="body">

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>


<main class="container-fluid bg-dark bg-opacity-25">
    <c:set var="test" value="${sessionScope.editedTest}"/>
    <c:if var="result" test="${!empty test}">
        <div class="row d-flex justify-content-center ">

            <div class="w-75 bg-question bg-dark bg-opacity-50 mb-3" style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 40px">
                <div class="text-left mt-2  table-responsive">
                    <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top">
                        <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;">
                            <span style="padding-left: 25px" >Test information
                                <button class="ms-2 btn btn-primary" type="button" onclick="location.href='/web-application/testing/admin/editTestInfo'">Edit</button>
                            </span>
                        </caption>
                        <tbody class="bg-light">
                        <tr>
                            <td>Id:${test.id}</td>
                        </tr>
                        <tr>
                            <td>Test:${test.name}</td>
                        </tr>
                        <tr>
                            <td>Discipline:${test.nameOfDiscipline}</td>
                        </tr>
                        <tr>
                            <td>Difficulty:${test.difficulty}</td>
                        </tr>
                        <tr>
                            <td>Duration(Minutes):${test.timeMinutes}</td>
                        </tr>
                        <tr>
                            <td>Questions:${test.countOfQuestions}</td>
                        </tr>
                        </tbody>
                        <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>

                    </table>
                </div>

                <div class="text-left mt-2 pb-3 table-responsive">
                    <table class="table table-bordered   mt-3  rounded-top caption-top">
                        <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;">
                            <span style="padding-left: 25px" >Questions information</span>
                        </caption>
                        <tbody class="bg-light">
                        <c:forEach var="question" items="${sessionScope.editedTest.questions}">
                        <tr>
                            <td>

                                <div>

                                    <div class="input-group mb-1">
                                        <span class="input-group-text fs-5" id="basic-addon1">Question ${question.questionNumber}</span>
                                        <textarea type="text" class="form-control" disabled required id="questionName" name="questionName" placeholder="What...">${question.textQuestion}</textarea>
                                    </div>

                                    <h4>Answers</h4>

                                    <c:forEach var="answer" items="${question.answers}">
                                        <c:if test="${answer.isRightAnswer()}">
                                            <div class="input-group  mb-1">
                                                <div class="input-group-text">
                                                    <input class="form-check-input mt-0" checked disabled type="checkbox" name="opt" value="2" aria-label="Checkbox for following text input">
                                                </div>
                                                <input type="text" class="form-control"  disabled id="answer 1" aria-label="Text input with checkbox" placeholder="${answer.answer}">
                                            </div>
                                        </c:if>
                                        <c:if test="${!answer.isRightAnswer()}">
                                            <div class="input-group mb-1">
                                                <div class="input-group-text">
                                                    <input class="form-check-input mt-0" disabled type="checkbox" name="opt" value="2" aria-label="Checkbox for following text input">
                                                </div>
                                                <input type="text" class="form-control"  disabled id="answer2" aria-label="Text input with checkbox" placeholder="${answer.answer}">
                                            </div>
                                        </c:if>

                                    </c:forEach>

                                    <div class="mt-3">
                                        <button class="btn btn-outline-primary" type="button" onclick="location.href='/web-application/testing/admin/editQuestionInfo?id=${question.id}'">Edit</button>
                                        <button class="btn btn-outline-danger" type="button" onclick="location.href='/web-application/testing/admin/editTestDeleteQuestion?id=${question.id}'" <c:if test="${test.countOfQuestions==1}">disabled</c:if>>Delete</button>
                                    </div>
                                </div>
                            </td>

                        </tr>

                        </c:forEach>
                        <tr>
                            <td class="text-center">
                                <form method="post">
                                    <button class="btn btn-outline-info" type="button" onclick="location.href='/web-application/testing/admin/addQuestion'" >Add question</button>

                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-center">
                                <form method="post">
                                    <button type="submit" class="btn btn-success" name="Save" value="Save">Save Edited test</button>
                                </form>
                            </td>
                        </tr>

                        </tbody>
                        <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>

                    </table>
                </div>
            </div>
        </div>
    </c:if>

</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>