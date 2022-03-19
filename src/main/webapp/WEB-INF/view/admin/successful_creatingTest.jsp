<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 21.02.2022
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />



<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!DOCTYPE html>
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
            <div class="text-left mt-2 pb-3 text-center">
                <span class="display-4 bold text-success">Test successfully created</span>
                <br>
                <span class="display-6 ">View all tests</span>
                <br>
                <button class="btn btn-outline-success btn-lg mt-2" onclick="location.href='/web-application/testing/admin/showTests'"><span class="h5">Tests</span></button>
            </div>

        </div>

    </div>
</main>

<jsp:include page="../footer.jsp"/>
</body>
</html>
