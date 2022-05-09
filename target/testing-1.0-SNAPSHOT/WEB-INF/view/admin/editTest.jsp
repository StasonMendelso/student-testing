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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="converter" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="errorlocalization" uri="errorLocalizationURI" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

</head>
<body class="body">

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>


<main class="container-fluid bg-dark bg-opacity-25">
    <c:set var="test" value="${sessionScope.editedTest}"/>
    <c:if var="result" test="${!empty test}">
        <div class="row d-flex justify-content-center ">

            <div class="w-75 bg-question bg-dark bg-opacity-50 mb-3"
                 style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 40px">
                <div class="text-left mt-1  table-responsive">
                    <table class="table table-bordered table-hover table-striped mt-2  rounded-top caption-top">
                        <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;">
                            <span style="padding-left: 25px"><fmt:message key="edit_test.test.info"/>
                                <button class="ms-2 btn btn-primary" type="button"
                                        onclick="location.href='/web-application/testing/admin/editTestInfo'"><fmt:message
                                        key="button.edit"/></button>
                            </span>
                        </caption>
                        <tbody class="bg-light">
                        <tr>
                            <td>Id:${test.id}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="test.test"/>: ${test.name}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="test.discipline"/>: ${test.nameOfDiscipline}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="test.difficulty"/>:<converter:difficulty
                                    intDifficulty="${test.difficulty}"/></td>
                        </tr>
                        <tr>
                            <td><fmt:message key="test.duration"/>: ${test.timeMinutes}</td>
                        </tr>
                        <tr>
                            <td><fmt:message key="test.number.questions"/>: ${test.countOfQuestions}</td>
                        </tr>
                        </tbody>
                        <caption class="bg-light p-2 fs-5"
                                 style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>

                    </table>
                </div>

                <nav>
                    <form method="post">
                        <input hidden name="id" value="${sessionScope.editedTest.id}">
                        <ul class="pagination pagination-lg justify-content-center">
                            <c:forEach var="i" begin="1" end="${sessionScope.editedTest.questions.size()}">
                                <c:if test="${requestScope.questionPageNumber==i}">
                                    <li class="page-item active">
                                        <span class="page-link bg-light border-light text-black">${i}</span>
                                    </li>
                                </c:if>
                                <c:if test="${requestScope.questionPageNumber!=i}">
                                    <li class="page-item ">
                                        <button class="page-link1 bg-dark border-dark text-white" type="submit"
                                                name="questionNumber" value="${i}">${i}</button>
                                    </li>
                                </c:if>

                            </c:forEach>
                        </ul>
                    </form>
                </nav>
                <div class="text-left mt-1 pb-3 table-responsive">
                    <table class="table table-bordered   mt-1  rounded-top caption-top">
                        <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;">
                            <span style="padding-left: 25px"><fmt:message key="edit-test.question.info"/></span>
                        </caption>
                        <tbody class="bg-light">
                        <tr>
                            <td>

                                <div>
                                    <c:set var="question" value="${requestScope.currentQuestion}"/>
                                    <div class="input-group mb-1">
                                        <span class="input-group-text fs-5" id="basic-addon1"><fmt:message
                                                key="edit_question_info.question"/> ${question.questionNumber}</span>
                                        <textarea type="text" class="form-control" disabled required id="questionName"
                                                  name="questionName"
                                                  placeholder="What...">${question.textQuestion}</textarea>
                                    </div>

                                    <h4><fmt:message key="create_question.answers"/></h4>

                                    <c:forEach var="answer" items="${question.answers}">
                                        <c:if test="${answer.isRightAnswer()}">
                                            <div class="input-group  mb-1">
                                                <div class="input-group-text">
                                                    <input class="form-check-input mt-0" checked disabled
                                                           type="checkbox" name="opt" value="2"
                                                           aria-label="Checkbox for following text input">
                                                </div>
                                                <input type="text" class="form-control" disabled id="answer 1"
                                                       aria-label="Text input with checkbox"
                                                       placeholder="${answer.answer}">
                                            </div>
                                        </c:if>
                                        <c:if test="${!answer.isRightAnswer()}">
                                            <div class="input-group mb-1">
                                                <div class="input-group-text">
                                                    <input class="form-check-input mt-0" disabled type="checkbox"
                                                           name="opt" value="2"
                                                           aria-label="Checkbox for following text input">
                                                </div>
                                                <input type="text" class="form-control" disabled id="answer2"
                                                       aria-label="Text input with checkbox"
                                                       placeholder="${answer.answer}">
                                            </div>
                                        </c:if>

                                    </c:forEach>
                                    <div style="margin: 3px">
                                        <div class="row">
                                            <div class="mt-3 text-start col-6">
                                                <button class="btn btn-outline-primary" type="button"
                                                        onclick="location.href='/web-application/testing/admin/editQuestionInfo?id=${question.id}'">
                                                    <fmt:message key="button.edit"/></button>
                                            </div>
                                            <div class="mt-3 text-end col-6">
                                                <button class="btn btn-outline-danger" type="button"
                                                        onclick="location.href='/web-application/testing/admin/editTestDeleteQuestion?id=${question.id}'"
                                                        <c:if test="${test.countOfQuestions==1}">disabled</c:if>>
                                                    <fmt:message key="button.delete"/></button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-center">
                                <form method="post">
                                    <button class="btn btn-outline-info" type="button"
                                            onclick="location.href='/web-application/testing/admin/addQuestion'">
                                        <fmt:message key="button.add.question"/></button>

                                </form>
                            </td>
                        </tr>
                        <tr>
                            <td class="text-center">

                                <button type="submit" class="btn btn-success" data-bs-toggle="modal"
                                        data-bs-target="#saveModal"><fmt:message
                                        key="button.save.edited.test"/></button>
                                <c:if test="${not empty error}"><br><span class="mt-1"><errorlocalization:localize
                                        error="${error}" lang="${seesionScope.lang}"/></span> </c:if>
                            </td>
                        </tr>

                        </tbody>
                        <caption class="bg-light p-2 fs-5"
                                 style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>

                    </table>
                    <!-- Delete Modal -->
                    <div class="modal fade" id="saveModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel"><fmt:message
                                            key="modal.save.edited.test"/></h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <form method="post" action="/web-application/testing/admin/editTest">
                                    <div class="modal-body">
                                        <label for="secretPassword" class="form-label"><fmt:message
                                                key="modal.enter.secret.key"/>:</label>
                                        <input required id="secretPassword" type="password" name="secretPassword"
                                               class="form-control">
                                        <input hidden type="text" name="pageNumber" value="${requestScope.pageNumber}">
                                        <input hidden type="text" name="paginationParameter"
                                               value="${requestScope.paginationParameter}">
                                        <input hidden name="id" value="${test.id}">
                                        <input hidden name="Save" value="Save">
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">
                                            <fmt:message key="modal.close"/></button>
                                        <button class="btn btn-outline-danger"><fmt:message key="modal.save"/></button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                        <%-- Delete modal--%>
                </div>

            </div>
        </div>
    </c:if>

</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>