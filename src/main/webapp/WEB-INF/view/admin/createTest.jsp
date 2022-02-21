<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:03
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
    <h1>IT is createTest.jsp</h1>
    <form action="${pageContext.request.contextPath}/testing/admin/createTest" class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
        <h1>Create Test</h1>

        <label for="testName">Name of test</label>
        <input type="text" required id="testName" name="testName" class="form_input" placeholder="testName">

        <label for="disciplineName">Name of the Discipline</label>
        <input type="text" required id="disciplineName" name="disciplineName" class="form_input" placeholder="Discipline">


        <label for="duration">Duration of the test (in Minutes)</label>
        <input type="text" required id="duration" name="duration" class="form_input" placeholder="Minutes">

        <label for="difficulty">Select Difficulty</label>
        <select id="difficulty" name="difficulty">
            <option value="easy" >Easy</option>
            <option value="medium" >Medium</option>
            <option value="hard" >Hard</option>
        </select>
        <br>
        <br>
        <input type="submit" name="submit" value="Створити тест" class="form_button">
    </form>
</div>

</body>
</html>