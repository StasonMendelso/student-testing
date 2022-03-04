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
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<html>
<head>
    <title>Testing</title>
</head>
<body class="body">

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
<div class="content">
    <c:set var="test" value="${sessionScope.editedTest}"/>
    <c:if var="result" test="${!empty test}">
        ${test}
        <div  class="form" style="margin: 0 auto;margin-top: 10px; width: 90%">
            Id:${test.id}<br>
            Test:${test.name}<br>
            Discipline:${test.nameOfDiscipline}<br>
            Difficulty:${test.difficulty}<br>
            Duration(Minutes):${test.timeMinutes}<br>
            Count of questions:${test.countOfQuestions}<br>
            <button class="td-center button-edit" type="button" onclick="location.href='/web-application/testing/admin/editTestInfo'">Edit</button>
            <hr>
            <c:forEach var="question" items="${sessionScope.editedTest.questions}">
                Id:${question.id} <br>
                Question<br>
                ${question.textQuestion} <br>
                Answers <br>
                <ul>
                <c:forEach var="answer" items="${question.answers}">
                    <c:if test="${answer.isRightAnswer()}">
                        <li style="color: greenyellow"><span style="color: black">${answer.answer}</span><br></li>
                    </c:if>
                    <c:if test="${!answer.isRightAnswer()}">
                        <li><span>${answer.answer}</span><br></li>
                    </c:if>

                </c:forEach>
                </ul>
                <button class="button-edit" type="button" onclick="location.href='/web-application/testing/admin/editQuestionInfo?id=${question.id}'">Edit</button>
                <button class="button-delete" type="button" onclick="location.href='/web-application/testing/admin/editTestDeleteQuestion?id=${question.id}'" <c:if test="${test.countOfQuestions==1}">disabled</c:if>>Delete</button>
                <hr>
            </c:forEach>
            <button class="td-center button-add" type="button" onclick="location.href='/web-application/testing/admin/addQuestion'" >Add question</button>
            <hr>
            <button class="center">Save Edited test</button>

        </div>
    </c:if>

    <c:if test="${empty test}">
        <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
            No tests have been found :(
        </div>
    </c:if>
</div>

</body>
</html>