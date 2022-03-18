<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 08.03.2022
  Time: 12:44
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
    <c:set var="tests" value="${requestScope.unsurpassedTestList}"/>
    <c:if var="result" test="${!empty tests}">
        <table style="width: 90%; margin: 0 auto; margin-top: 2%"  >
            <caption style="text-align: left"><div class="form" style="margin: 1%; padding: 10px">All available tests</div></caption>
            <tr><th>Id</th><th>Name</th><th>Discipline</th><th>Difficulty</th><th>Duration (Minutes)</th><th>Questions</th><th colspan="2"></th></tr>
            <c:if var="result" test="${!empty tests}">
                <c:forEach items="${requestScope.unsurpassedTestList}" var="test">
                    <tr>
                        <td>${test.id}</td>
                        <td>${test.name}</td>
                        <td>${test.nameOfDiscipline}</td>
                        <td>${test.difficulty}</td>
                        <td>${test.timeMinutes}</td>
                        <td>${test.countOfQuestions}</td>

                        <td><button class="td-center button-edit" type="button" onclick="location.href='/web-application/testing/student/test?id=${test.id}'">Start</button></td>
                    </tr>
                </c:forEach>
            </c:if>

        </table>
    </c:if>

    <c:if test="${empty tests}">
        <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
            You have passed all available tests
        </div>
    </c:if>

        <c:set var="alltests" value="${requestScope.allTestList}"/>
        <c:if var="result" test="${!empty alltests}">
            <table style="width: 90%; margin: 5%; margin-top: 2%"  >
                <caption style="text-align: left"><div class="form" style="margin: 1%; padding: 10px">All Tests</div></caption>

                <tr><th>Id</th><th>Name</th><th>Discipline</th><th>Difficulty</th><th>Duration (Minutes)</th><th>Questions</th></tr>
                <c:if var="result" test="${!empty alltests}">
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
                </c:if>

            </table>
        </c:if>

</div>

</body>
</html>