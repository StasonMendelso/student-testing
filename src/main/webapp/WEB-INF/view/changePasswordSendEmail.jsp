
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="errorlocalization" uri="errorLocalizationURI" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />



<!DOCTYPE html>
<html>

<head>
    <title>Testing</title>
    <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">--%>
    <style>
        <%@include file="../../css/style.css"%>
    </style>
    <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/262/262825.png" type="image/x-icon"/>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body >
<c:if test="${sessionScope.role=='ADMIN'}">
    <%@include file="admin/navbar.jsp"%>
</c:if>
<c:if test="${sessionScope.role=='STUDENT'}">
    <%@include file="student/navbar.jsp"%>
</c:if>

<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex justify-content-center ">

        <div class="w-25 bg-dark  mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
            <div class="text-center mt-2 pb-3">
                <c:if test="${not empty requestScope.error}">
                    <h4 class="text-info"><errorlocalization:localize error="${requestScope.error}" lang="${sessionScope.lang}"/></h4>
                </c:if>
                <h4 class="text-success text-center"><fmt:message key="change_password_send_email.activation_link_was_sent_to_email"/></h4>
                <span class="text-white-50 text-center"><fmt:message key="change_password_send_email.check.email"/></span> <br>
            </div>

        </div>

    </div>
</main>
<jsp:include page="footer.jsp"/>

</body>
</html>