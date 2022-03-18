<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 21.02.2022
  Time: 13:07
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


    ${sessionScope.test}
    <form  class="form" style="margin: 0 auto;margin-top: 10px; width: 80%">
        <h1>Create Test</h1>

        <label for="questionName">Question</label>
        <input type="text" required id="questionName" name="questionName" class="form_input">

        <p><b>Answers</b></p>
        <p>
            <input type="checkbox" name="opt" value="1"> <input type="text" required id="answer1" name="answer1" class="form_input" style="width: 90%"><br><br>
            <input type="checkbox" name="opt" value="2"> <input type="text" required id="answer2" name="answer2" class="form_input" style="width: 90%"><br><br>
            <input type="checkbox" name="opt" value="3"> <input type="text"  id="answer3" name="answer3" class="form_input" style="width: 90%"><br><br>
            <input type="checkbox" name="opt" value="4"> <input type="text"  id="answer4" name="answer4" class="form_input" style="width: 90%"><br><br>

        <p>

        <input type="submit" name="SaveQuestion" value="Сохранити питання та добавити наступне" class="form_button">
        <input type="submit" name="SaveTest" value="Сохранити питання та закінчити створення Тесту" class="form_button">
    </form>
</div>

</body>
</html>