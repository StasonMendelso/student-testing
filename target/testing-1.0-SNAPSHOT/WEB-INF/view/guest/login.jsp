
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<!DOCTYPE html>
<html>

<head>
    <title>Testing</title>
<%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">--%>
    <link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body class="body">
<%--    <jsp:include page="/WEB-INF/view/guest/navbar.jsp"/>--%>
    <%@include file="navbar.jsp"%>
        <div class="content">
            <c:set var="list" value="${requestScope.errorsList}"/>
            <c:if var="result" test="${!empty list}">
                <div class="form" style="margin: 0 auto;margin-top: 10px; ">
                    <c:forEach items="${requestScope.errorsList}" var="er">
                        <c:out value="${er}"/><br>
                    </c:forEach>
                </div>
            </c:if>

            <form name="loginform" action="${pageContext.request.contextPath}/testing/login" method="post" accept-charset="UTF-8" class="form" style="margin: 0 auto;margin-top: 100px; ">

                <h1 class="form_title"><fmt:message key="label.login"/></h1>
                <div class="form_grup">
                    <label for="login" class="form_label"></label>
                    <input type="text" id="login" name="login" class="form_input" placeholder="<fmt:message key="login.label.login"/>">
                </div>
                <div class="form_grup">
                    <label for="password" class="form_label" ></label>
                    <input type="password" id="password" name="password"  class="form_input" placeholder="<fmt:message key="login.label.password"/>">
                </div>
                <fmt:message key="login.button.submit" var="buttonValue" />
                <input type="submit" name="submit" value="${buttonValue}" class="form_button">
            </form>
        </div>

</body>
</html>