<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 10.02.2022
  Time: 0:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="fr"/>
<fmt:setBundle basename="messages"/>
<!-- fmt:setBundle ставит Bundle  для этого префикса, basename - это папочка Resource Bundle 'messages', по дефолту будет выбран файл messages.properties, Чтобы добавить локализацию нужен Locale-->
<%--Добавим Locale на нашу страничку--%>
<fmt:setLocale value="fr"/>
<html>
<head>
    <title>PhraseApp - i18n</title>
</head>
<body>
<h2>
    <fmt:message key="label.welcome" /> <!-- fmt:message используется для того чтобы вытягнуть с наших Ресурс файлов значенияпо их ключу-->
    <br>
    <button type="button" disabled>France</button>
</h2>
</body>
</html>
