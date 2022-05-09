<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "errorLocalization" uri="errorLocalizationURI"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
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
                <h1 class="text-center t-5"><fmt:message key="create_test.create.new.test"/></h1>
                <form method="post" >
                    <div class="mb-3 mt-3 justify-content-center form-floating">
                        <textarea class="form-control"  required id="testName" name="testName" placeholder="nameExample" style="min-height: 99px" ></textarea>
                        <label for="testName"><fmt:message key="test.test"/></label>
                    </div>
                    <div class="mb-3 mt-3 justify-content-center form-floating">
                        <textarea class="form-control" required id="disciplineName" name="disciplineName"  placeholder="nameExample" style="min-height: 85px"></textarea>
                        <label for="disciplineName"><fmt:message key="test.discipline"/></label>
                    </div>
                    <div class="mb-3 mt-3 justify-content-center form-floating">
                        <input type="number" class="form-control" required id="duration" name="duration"  placeholder="nameExample" inputmode="numeric" style="min-height: 85px">
                        <label for="duration"><fmt:message key="test.duration"/></label>
                    </div>
                    <div class="mb-3 mt-3 d-flex ">
                        <label class="ms-auto align-self-center" for="difficulty" ><fmt:message key="test.difficulty"/></label>
                        <select id="difficulty" name="difficulty" class="ms-2 form-select flex-grow-1" aria-label="Default select example">
                            <option value="1" selected><fmt:message key="test.difficulty.easy"/></option>
                            <option value="2"><fmt:message key="test.difficulty.medium"/></option>
                            <option value="3"><fmt:message key="test.difficulty.hard"/></option>
                        </select>
                    </div>
                    <div class="mb-1 mt-3 text-center">
                        <c:forEach items="${requestScope.errorsList}" var="er">
                            <errorLocalization:localize error="${er}" lang="${sessionScope.lang}" /><br>
                        </c:forEach>
                    </div>
                    <div class="mb-1 mt-3 text-center">
                        <input type="submit" name="submit" value="<fmt:message key="create_test.create"/>" class="btn btn-primary">
                    </div>
                </form>
            </div>

        </div>

    </div>
</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>