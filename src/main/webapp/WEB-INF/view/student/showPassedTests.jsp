<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 08.03.2022
  Time: 12:46
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
    <c:set var="tests" value="${requestScope.testList}"/>
    <c:if var="result" test="${!empty tests}">
        <table style="width: 90%; margin: 5%; margin-top: 2%"  >
            <tr><th>Id</th><th>Name</th><th>Discipline</th><th>Difficulty</th><th>Duration (Minutes)</th><th>Questions</th><th colspan="2">Mark</th></tr>
            <c:if var="result" test="${!empty tests}">
                <c:forEach items="${requestScope.testList}" var="test">
                    <tr>
                        <td>${test.id}</td>
                        <td>${test.name}</td>
                        <td>${test.nameOfDiscipline}</td>
                        <td>${test.difficulty}</td>
                        <td>${test.timeMinutes}</td>
                        <td>${test.countOfQuestions}</td>

                        <td>${test.mark} %</td>
                    </tr>
                </c:forEach>
            </c:if>

        </table>
    </c:if>

    <c:if test="${empty tests}">
        <div class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
            You haven't passed any tests yet
        </div>
    </c:if>
</div>

</body>
</html>