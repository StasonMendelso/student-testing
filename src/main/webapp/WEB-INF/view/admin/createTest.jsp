<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:03
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

    <form action="${pageContext.request.contextPath}/testing/admin/createTest" class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
        <h1>Create Test</h1>

        <label for="testName">Name of test</label>
        <input type="text" required id="testName" name="testName" class="form_input" placeholder="testName">

        <label for="disciplineName">Name of the Discipline</label>
        <input type="text" required id="disciplineName" name="disciplineName" class="form_input" placeholder="Discipline">


        <label for="duration">Duration of the test (in Minutes)</label>
        <input type="text" required id="duration" name="duration" class="form_input" placeholder="Minutes">

        <label for="difficulty">Select Difficulty</label>
        <span class="custom-dropdown" style="border: 1px solid black;">
        <select id="difficulty" name="difficulty">
            <option value="Easy" >Easy</option>
            <option value="Medium" >Medium</option>
            <option value="Hard" >Hard</option>
        </select>
        </span>
        <br>
        <br>
        <input type="submit" name="submit" value="Створити тест" class="form_button">
    </form>
</div>

</body>
</html>