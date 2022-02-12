<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 10.02.2022
  Time: 0:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<%@ page session="true" %> <!--Что-то новенькое отвечающее за доступность сессии-->

<fmt:setLocale value="${sessionScope.lang}"/> <!--Какая-то связь со всеми языками???-->
<fmt:setBundle basename="messages"/>

<html lang="${sessionScope.lang}">
<head>
    <title>PhraseApp - i18n</title>
</head>
<body>
<h2>
    <fmt:message key="label.welcome" />
</h2>
<p>
<%--    <fmt:message key="label.sessionLocaleContent" />--%>
</p>
</body>
</html>