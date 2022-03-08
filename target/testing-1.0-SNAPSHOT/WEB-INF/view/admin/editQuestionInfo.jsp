<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 04.03.2022
  Time: 21:34
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

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
<div class="content">
    <c:set var="test" value="${sessionScope.editedTest}"/>
    <c:set var="i" value="${1}" scope="page"/>
    <c:set var="question" value="${sessionScope.editedQuestion}"/>

    <table>
        <form>
        <tr><th style="-moz-text-align-last: center" colspan="3">Question information</th></tr>
        <tr><td>Id</td><td colspan="2">${question.id}</td></tr>
        <tr><td>Question</td><td colspan="2"><input type="text" class="form_input" id="questionText" name="questionText" value="${question.textQuestion}"></td></tr>
        <c:forEach var="answer" items="${question.answers}">
            <tr><td> <input type="checkbox" name="opt" value="${i}" <c:if test="${answer.isRightAnswer()}">checked</c:if>> Answer ${i}</td><td><input type="text" class="form_input" name="answer${i}" value="${answer.answer}"></td><td><button type="submit" class="td-center button-delete" name="DeleteId" value="${answer.id}" <c:if test="${question.answers.size()<=2}">disabled</c:if>>Delete</button></td></tr>
            <c:set var="i" value="${i+1}" scope="page"/>
        </c:forEach>

        <tr><td colspan="3">
            <input type="submit" class="button-edit input-submit center" style="width: 25%" name="Save" value="Save">
        </td></tr>
        </form>
    </table>

    <br>
    <c:if test="${question.answers.size()<=3}">
    <form>
        <table>
            <tr><th style="-moz-text-align-last: center" colspan="3">Add Answer</th></tr>
            <tr><td style="width: 20%"><label for="answerText"><input type="checkbox" name="opt" value="${i}"> Answer ${i}</label></td> <td colspan="2" style="width: 80%"><input type="text" id="answerText" name="answerText" class="form_input"></td></tr>
            <input hidden name="id" value="${question.id}">
            <tr><td colspan="3"><input type="submit" class="button-edit input-submit center" style="width: 25%" name="Add" value="Add"></td></tr>
        </table>
    </form>
    </c:if>




</div>

</body>
</html>