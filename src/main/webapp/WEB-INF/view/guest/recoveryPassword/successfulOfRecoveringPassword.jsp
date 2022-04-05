<%@ page import="com.stason.testing.controller.utils.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />



<!DOCTYPE html>
<html>

<head>
    <title>Testing</title>
    <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">--%>
    <style>
        <%@include file="../../../../css/style.css"%>
    </style>
    <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/262/262825.png" type="image/x-icon"/>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body >
<%@include file="../navbar.jsp"%>

<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex justify-content-center ">

        <div class="w-25 bg-dark  mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
            <div class="text-center mt-2 pb-3">
                <h2 class="text-white-50"><fmt:message key="recovery.password.recovery"/></h2>
                <h4 class="text-success"><fmt:message key="recovery.password.successful"/></h4>
                <span class="text-white-50"><fmt:message key="recovery.password.you.can.login"/></span> <br>
                <a class="btn-primary btn mt-2" href="/web-application/testing/login"><fmt:message key="recovery.password.login"/></a>
            </div>

        </div>

    </div>
</main>
<jsp:include page="../../footer.jsp"/>

</body>
</html>