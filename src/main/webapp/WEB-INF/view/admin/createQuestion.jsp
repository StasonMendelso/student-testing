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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@include file="../../../css/style.css"%>
    </style>
    <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/262/262825.png" type="image/x-icon"/>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="body">

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex justify-content-center ">

        <div class="w-50 bg-question  mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
            <div class="text-left mt-2 pb-3">
                <h1 class="text-center t-5">Create question</h1>
                <div class="mt-1 mb-1 fs-4">
                    Всього запитань наявно в тесті: ${sessionScope.test.countOfQuestions}
                </div>

                <form method="post" >
                    <div class="input-group mb-1">
                        <span class="input-group-text fs-5" id="basic-addon1">Question</span>
                        <textarea type="text" class="form-control" required id="questionName" name="questionName" placeholder="What..."></textarea>
                    </div>

                    <h4>Answers</h4>

                    <div class="input-group mb-3">
                        <div class="input-group-text">
                            <input class="form-check-input mt-0" type="checkbox" name="opt" value="1">
                        </div>
                        <input type="text" class="form-control" required id="answer1" name="answer1" placeholder="Answer 1">
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text">
                            <input class="form-check-input mt-0" type="checkbox" name="opt" value="2">
                        </div>
                        <input type="text" class="form-control" required id="answer2" name="answer2" placeholder="Answer 2">
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text">
                            <input class="form-check-input mt-0" type="checkbox" name="opt" value="3">
                        </div>
                        <input type="text" class="form-control" id="answer3" name="answer3" placeholder="Answer 3">
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text">
                            <input class="form-check-input mt-0" type="checkbox" name="opt" value="4" >
                        </div>
                        <input type="text" class="form-control" id="answer4" name="answer4" placeholder="Answer 4">
                    </div>
                    <div class="text-center ">
                        <input class="btn btn-primary" type="submit" name="SaveQuestion" value="Сохранити питання та добавити наступне">
                        <input  class="btn btn-primary" type="submit" name="SaveTest" value="Сохранити питання та закінчити створення Тесту">
                    </div>
                </form>

            </div>

        </div>

    </div>
</main>

<footer class="bg-dark bg-opacity-75 text-center navbar-nav p-3 text-light container-fluid">
    <!-- Copyright -->
    <div class="text-center">
        © 2022 Copyright:
        <a class="text-light" href="#" style="text-decoration: none">Stas Hlova</a>
    </div>
    <!-- Copyright -->
</footer>
</body>
</html>