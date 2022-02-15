
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">
<!DOCTYPE html>
<html lang="${sessionScope.lang}">

<head>
    <title>Testing</title>

</head>
<body class="body">
    <jsp:include page="/WEB-INF/view/guest/navbar.jsp"/>
        <div class="content">
<%--            <form >--%>
<%--                <select id="language" name="lang" onchange="submit()">--%>
<%--                    <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}>English</option>--%>
<%--                    <option value="ua" ${sessionScope.lang == 'ua' ? 'selected' : ''}>Ukraine</option>--%>
<%--                    <option value="ru" ${sessionScope.lang == 'ru' ? 'selected' : ''}>Russian</option>--%>
<%--                </select>--%>
<%--            </form>--%>
            <form action="controller" method="post" class="form" style="margin: 0 auto;margin-top: 100px; ">
                <input name="action" value="login" type="hidden">
                <h1 class="form_title"><fmt:message key="label.login"/></h1>
                <div class="form_grup">
                    <label for="login" class="form_label"></label>
                    <input type="text" id="login" name="login" class="form_input" placeholder="<fmt:message key="login.label.username"/>">
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