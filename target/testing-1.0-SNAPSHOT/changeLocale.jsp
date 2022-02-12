<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${param.lang}"/>
<fmt:setBundle basename="messages"/>

<html lang="${param.lang}">
<head>
    <title>PhraseApp - i18n</title>
</head>
<body>
<h2>
    <fmt:message key="label.chooseSessionLocale" />
</h2>
<ul>
    <li><a href="?sessionLocale=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?sessionLocale=fr"><fmt:message key="label.lang.fr" /></a></li>
    <li><a href="?sessionLocale="><fmt:message key="label.lang.default" /></a></li>






    <label><input type="radio" name="master" value="1"href="?sessionLocale=">Стажер</label>
    <label><input type="radio" name="master" value="2" href="?sessionLocale=">Барбер</label>
    <label><input type="radio" name="master" value="3" href="?sessionLocale=">VIP Барбер</label>
    <input type="submit" name="zayavka_submit" value="Записаться">
</ul>
<c:if test="${not empty param.sessionLocale}">
    <fmt:message key="label.cookieChangeSuccess" />
    <button><a href="sessionLocale.jsp"><fmt:message key="label.viewPage" /></a></button>
</c:if>
</body>
</html>