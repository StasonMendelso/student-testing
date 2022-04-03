<%@ page import="com.stason.testing.controller.utils.Constants" %><%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 16.02.2022
  Time: 23:10
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="errorlocalization" uri="errorLocalizationURI" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />



<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!DOCTYPE html>
<html>

<head>
    <title>Testing</title>
    <style>
        <%@include file="../../../css/style.css"%>
    </style>
    <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/262/262825.png" type="image/x-icon"/>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- reCAPTCHA with Auto language -->
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body >
<%@include file="navbar.jsp"%>
<main class="container-fluid bg-dark bg-opacity-25">
    <div class="row d-flex justify-content-center ">

        <div class="w-25 bg-dark  mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
            <div class="text-center mt-2">
                <h2 class="text-white-50">Registration</h2>

            </div>
            <div class="text-white-50 text-center">
                <c:set var="test" value="${requestScope.errorsList}"/>
                <c:if var="result" test="${!empty test}">
                    <div class="form" style="margin: 0 auto;margin-top: 10px; ">
                        <c:forEach items="${requestScope.errorsList}" var="er">
                            <errorlocalization:localize error="${er}" lang="${sessionScope.lang}"/><br>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <form method="post" action="" accept-charset="UTF-8">
                <div class="mb-3 mt-3 justify-content-center form-floating">
                    <input type="text" class="form-control" id="name" name="name"  placeholder="name@example.com">
                    <label for="name">First name</label>
                </div>
                <div class="mb-3 mt-3 justify-content-center form-floating">
                    <input type="text" class="form-control"id="surname" name="surname"  placeholder="name@example.com">
                    <label for="surname">Last name</label>
                </div>
                <div class="mb-3 mt-3 justify-content-center form-floating">
                    <input type="email" class="form-control" id="login" name="login" placeholder="name@example.com">
                    <label for="surname">Email</label>
                </div>
                <div class="mb-3 justify-content-center form-floating">
                    <input type="password" class="form-control" id="password" name="password" placeholder="pass">
                    <label for="password" >Password</label>
                </div>
                <div class="mb-3 justify-content-center form-floating">
                    <input type="password" class="form-control" id="repeated-password" name="repeated-password" placeholder="pass">
                    <label for="repeated-password" >Repeated Password</label>
                </div>
                <div class="mb-2 d-flex justify-content-center">
                    <div class="g-recaptcha"
                         data-sitekey="<%=Constants.SITE_KEY%>"
                         data-callback="callback">
                    </div>
                </div>
                <div class="mb-3 text-center">
                    <fmt:message key="register.button.submit" var="buttonValue" />
                    <button type="submit" name="submit" disabled id="submit-button" class="btn btn-primary" >${buttonValue}</button>
                </div>
                <script type="text/javascript">
                    function callback() {
                        const submitButton = document.getElementById("submit-button");
                        submitButton.removeAttribute("disabled");
                    }
                </script>
            </form>
        </div>

    </div>
</main>
<jsp:include page="../footer.jsp"/>

</body>
</html>