<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 04.03.2022
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="errorLocalization" uri="errorLocalizationURI" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<html>
<head>
    <title>Testing</title>
    <style>
        <%@include file="../../../css/style.css"%>
    </style>
    <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/262/262825.png" type="image/x-icon"/>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<body>

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>

<main class="container-fluid bg-dark bg-opacity-25">
    <c:set var="test" value="${sessionScope.editedTest}"/>
    <c:set var="i" value="${1}" scope="page"/>
    <c:set var="question" value="${sessionScope.editedQuestion}"/>
    <div class="row d-flex justify-content-center ">

        <div class="w-50 bg-question  mb-5 bg-dark bg-opacity-50"
             style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 50px">
            <div class="text-left mt-2 pb-3 table-responsive">
                <form method="post">
                    <table class="table table-bordered  mt-3  rounded-top caption-top">
                        <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;"><span
                                style="padding-left: 25px"><fmt:message key="edit_question_info.edit.question.info"/></span></caption>


                        <tbody class="bg-light">
                        <tr>
                            <td colspan="2">
                                <div class="input-group mb-1">
                                    <span class="input-group-text fs-5"
                                          id="basic-addon1"><fmt:message key="edit_question_info.question"/> ${question.questionNumber}</span>
                                    <textarea type="text" class="form-control" required id="questionText"
                                              name="questionText"
                                              placeholder="What...">${question.textQuestion}</textarea>
                                </div>
                            </td>
                        </tr>
                        <c:forEach var="answer" items="${question.answers}">
                            <tr>
                                <td class="w-75">
                                    <div class="input-group  mb-1">
                                        <div class="input-group-text">
                                            <input class="form-check-input mt-0" type="checkbox" name="opt" value="${i}"
                                                   <c:if test="${answer.isRightAnswer()}">checked</c:if>>
                                        </div>
                                        <input type="text" class="form-control" name="answer${i}"
                                               value="${answer.answer}" placeholder="<fmt:message key="edit_question_info.answer"/> ${i}">
                                    </div>
                                </td>
                                <td class="text-center">
                                    <button type="submit" class="btn btn-outline-danger" name="DeleteId"
                                            value="${answer.id}"
                                            <c:if test="${question.answers.size()<=2}">disabled</c:if>><fmt:message key="button.delete"/>
                                    </button>
                                </td>
                            </tr>
                            <c:set var="i" value="${i+1}" scope="page"/>
                        </c:forEach>
                        <c:if test="${not empty requestScope.errorsList}">
                            <tr>
                                <td colspan="3" class="text-center">
                                    <c:forEach items="${requestScope.errorsList}" var="er">
                                        <errorLocalization:localize error="${er}" lang="${sessionScope.lang}"/><br>
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:if>



                        <tr>
                            <td class="text-center" colspan="2">
                                <input type="submit" class="btn btn-outline-success" style="width: 25%" name="Save"
                                       value="<fmt:message key="button.save"/>">
                            </td>
                        </tr>
                        </tbody>
                        <caption class="bg-light p-2 fs-5"
                                 style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
                    </table>
                </form>
                <br>
                <c:if test="${question.answers.size()<=3}">
                    <form method="post">

                        <table class="table table-bordered  mt-3  rounded-top caption-top">
                            <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;"><span
                                    style="padding-left: 25px"><fmt:message key="edit_question_info.add_answer"/></span></caption>
                            <tbody class="bg-light">
                            <tr>
                                <td>
                                    <div class="input-group  mb-1">
                                        <div class="input-group-text">
                                            <input class="form-check-input mt-0" type="checkbox" name="opt" value="${i}"
                                                   ->
                                        </div>
                                        <input type="text" class="form-control" id="answerText" name="answerText"
                                               placeholder="<fmt:message key="edit_question_info.answer"/> ${i}">
                                    </div>
                                </td>
                            </tr>
                            <input hidden name="id" value="${question.id}">
                            <tr class="text-center">
                                <td colspan="3">
                                    <c:if test="${not empty requestScope.errorAddedQuestion}">
                                        <errorLocalization:localize error="${requestScope.errorAddedQuestion}" lang="${sessionScope.lang}"/><br>
                                    </c:if>
                                    <input type="submit" class="btn btn-outline-success" style="width: 25%"
                                                       name="Add" value="<fmt:message key="edit_question_info.answer.add"/>"/>
                                </td>
                            </tr>
                            </tbody>
                            <caption class="bg-light p-2 fs-5"
                                     style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>
                        </table>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>