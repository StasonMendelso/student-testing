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
<c:if test="${sessionScope.role=='ADMIN'}">
    <%@include file="../../admin/navbar.jsp"%>
</c:if>
<c:if test="${sessionScope.role=='STUDENT'}">
    <%@include file="../../student/navbar.jsp"%>
</c:if>
<c:if test="${sessionScope.role=='GUEST'}">
    <%@include file="../navbar.jsp"%>
</c:if>

<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex justify-content-center ">

        <div class="w-25 bg-dark  mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
            <div class="text-center mt-2">
                <h2 class="text-white-50">Create new password</h2>
            </div>
            <div  class="text-white-50">
                <c:if var="result" test="${!empty requestScope.error}">
                    <div class="form" style="margin: 0 auto;margin-top: 10px; ">
                        <c:out value="${requestScope.error}"/><br>
                    </div>
                </c:if>

            </div>

            <form name="loginform"  method="post" accept-charset="UTF-8">
                <div class="mb-3 mt-3 justify-content-center form-floating">
                    <input type="password" required class="form-control" id="password" name="password" placeholder="name@example.com">
                    <label for="password">Password</label>
                </div>
                <div class="mb-3 mt-3 justify-content-center form-floating">
                    <input type="password" required class="form-control" id="repeatedPassword" name="repeatedPassword" placeholder="name@example.com">
                    <label for="repeatedPassword">Repeat password</label>
                </div>

                <div class="mb-2 text-center">
                    <fmt:message key="login.button.submit" var="buttonValue" />
                    <input id="submit-button"  type="submit" name="submit" value="${buttonValue}" class="btn btn-primary"/>
                </div>

            </form>
        </div>

    </div>
</main>
<jsp:include page="../../footer.jsp"/>

</body>
</html>