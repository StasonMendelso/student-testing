<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 09.03.2022
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<html>
<head>
    <title>Testing</title>
    <style>
        <%@include file="/styles/styles.css"%>
    </style>
</head>
<body class="body">

<jsp:include page="/WEB-INF/view/student/navbar.jsp"/>
<div class="content">
    <div>
        <c:set var="i" value="${1}" scope="page"/>
        <c:set var="test" value="${sessionScope.test}"/>
        <div style="height: 50px; width: 600px">
        <c:forEach var="count" begin="1" end="${test.countOfQuestions}">
                <button type="button" onclick="location.href='/web-application/testing/student/test?question=${count}'">${count}</button>
        </c:forEach>
        </div>
        <c:set var="question" value="${requestScope.question}"/>
        ${question}
        <div class="form">
            <p>Question</p>
            <p> ${question.textQuestion} </p>
            <form method="post">
            <c:forEach var="answer" items="${question.answers}">
                <tr><td> <input type="checkbox" name="opt" value="${i}" <c:if test="${question.userOptions.get(i-1)}">checked</c:if> /> </td><td>${answer.answer}</td></tr>
                <br>
                <c:set var="i" value="${i+1}" scope="page"/>
            </c:forEach>
                <input hidden name="questionNumber" value="${question.questionNumber}">
                <input hidden name="save" value="save"/>
                <c:set var="previous" value="${question.questionNumber-1}"/>
            <c:if test="${question.questionNumber !=1}"> <button type="submit" name="nextQuestion" value="${previous}">Previous</button> </c:if>
                <c:set var="next" value="${question.questionNumber+1}"/>
            <c:if test="${question.questionNumber !=test.countOfQuestions}">  <button type="submit" name="nextQuestion" value="${next}">Next</button> </c:if>
            <br>
            <button type="submit" name="finish" value="finish">Finish test</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
