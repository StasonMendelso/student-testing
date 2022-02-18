<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />
<%request.setCharacterEncoding("UTF-8");%>
<%response.setCharacterEncoding("UTF-8");%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<!DOCTYPE html>
<html>

<head>
    <title>Testing</title>

</head>
<body class="body">
    <jsp:include page="/WEB-INF/view/guest/navbar.jsp"/>
        <div class="content">

            <form action="controller" method="post" accept-charset="UTF-8" class="form" style="margin: 0 auto;margin-top: 100px; ">
                <input name="action" value="login" type="hidden">
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
    <jsp:include page="/footer.jsp"/>
</body>
</html>