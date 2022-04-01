<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 23.02.2022
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="converter" tagdir="/WEB-INF/tags" %>


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
<body>

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>

<main class="container-fluid bg-dark bg-opacity-25">
    <c:set var="test" value="${sessionScope.editedTest}"/>
    <div class="row d-flex justify-content-center ">

        <div class="w-50 bg-question  mb-1 bg-dark bg-opacity-50" style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 45px">
            <div class="text-left mt-1 pb-1 table-responsive">
                <table class="table table-bordered table-hover table-striped mt-3  rounded-top caption-top">
                    <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;"><span style="padding-left: 25px">Test information</span></caption>
                    <tbody class="bg-light">
                    <tr>
                        <td>Id</td>
                        <td>${test.id}</td>
                    </tr>
                    <tr>
                        <td>Test</td>
                        <td>${test.name}</td>
                    </tr>
                    <tr>
                        <td>Discipline</td>
                        <td>${test.nameOfDiscipline}</td>
                    </tr>
                    <tr>
                        <td>Difficulty</td>
                        <td><converter:difficulty intDifficulty="${test.difficulty}"/></td>
                    </tr>
                    <tr>
                        <td>Duration</td>
                        <td>${test.timeMinutes}</td>
                    </tr>
                    <tr>
                        <td>Count of questions</td>
                        <td>${test.countOfQuestions}</td>
                    </tr>
                    </tbody>
                    <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>

                </table>
            </div>
            <br>
            <div class="text-left mt-1 pb-3 table-responsive">
                <form method="post">

                    <table class="table table-bordered table-hover table-striped mt-1  rounded-top caption-top">
                        <caption class="bg-dark text-light p-2 fs-5" style="border-radius: 30px 30px 0px 0px;"><span style="padding-left: 25px">Edit test information</span></caption>
                        <tbody class="bg-light">
                        <tr>
                            <td>
                                <input type="text" class="form-control" id="testName" name="testName" placeholder="Test">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="text" class="form-control"  id="disciplineName" name="disciplineName" placeholder="Discipline">
                            </td>
                        </tr>
                        <tr>
                            <td class="mb-3 mt-3 ">

                                <select id="difficulty" name="difficulty" class="mt-1 form-select flex-grow-1">
                                    <optgroup label="Difficulty">
                                        <option value="1" selected>Easy</option>
                                        <option value="2">Medium</option>
                                        <option value="3">Hard</option>
                                    </optgroup>

                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="number" id="duration" name="duration" class="form-control" placeholder="Duration(in minutes)" inputmode="numerical">
                            </td>
                        </tr>

                        <tr class="text-center">
                            <td colspan="2"><input type="submit" class="btn btn-primary" style="width: 25%" value="Save"></td>
                        </tr>
                        </tbody>
                        <caption class="bg-light p-2 fs-5" style="caption-side: bottom;border-radius: 0px 0px 30px 30px;"></caption>

                    </table>
                </form>
            </div>

        </div>

    </div>
</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>