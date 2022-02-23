<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 23.02.2022
  Time: 12:56
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
        <table>
            <tr><th style="-moz-text-align-last: center" colspan="2">Test information</th></tr>
            <tr><td>Id</td><td>${test.id}</td></tr>
            <tr><td>Test</td><td>${test.name}</td></tr>
            <tr><td>Discipline</td><td>${test.nameOfDiscipline}</td></tr>
            <tr><td>Difficulty</td><td>${test.difficulty}</td></tr>
            <tr><td>Duration</td><td>${test.timeMinutes}</td></tr>
            <tr><td>Count of questions</td><td>${test.countOfQuestions}</td></tr>
        </table>
        <br>

    <form>
        <table>
            <tr><th style="-moz-text-align-last: center" colspan="2">Edit Test information</th></tr>
            <tr><td><label for="testName">Test</label></td><td> <input type="text" id="testName" name="testName" class="form_input"></td></tr>
            <tr><td><label for="disciplineName">Discipline</label></td><td> <input type="text" id="disciplineName" name="disciplineName" class="form_input"></td></tr>
            <tr><td colspan="2"><label for="difficulty">Difficulty</label> <select id="difficulty" name="difficulty">
                <option value="Easy" >Easy</option>
                <option value="Medium" >Medium</option>
                <option value="Hard" >Hard</option>
            </select>
            </td></tr>
            <tr><td> <label for="duration">Duration(Minutes)</label></td><td> <input type="text" id="duration" name="duration" class="form_input"></td></tr>
            <tr><td colspan="2"><input type="submit" class="button-edit input-submit center" style="width: 25%" value="Save"></td></tr>
        </table>
    </form>
</div>

</body>
</html>