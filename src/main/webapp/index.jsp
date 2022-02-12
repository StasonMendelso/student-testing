
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>

<%@ page session="true" %> <!--Что-то новенькое отвечающее за доступность сессии-->

<fmt:setLocale value="${sessionScope.lang}"/> <!--Какая-то связь со всеми языками???-->
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang = ${sessionScope.lang}>
<head>

  <title>JSP - Hello World</title>
    <meta charset="UTF-8"/>

    <style>
        .c-button {
            -webkit-writing-mode: horizontal-tb !important;
            -webkit-appearance: button;
            border-color: rgb(0, 0, 0 ) rgb(0, 0, 0) rgb(0, 0, 0);
            border-style: solid;
            border-width: 1px;

            padding: 1px 7px 2px;
            text-rendering: auto;
            text-decoration: none;
            color: initial;
            display: inline-block;
            text-align: start;
            margin: 0em;
            background: #e9e9e9;
            font: 400 11px system-ui;
            font-size: 14px;
        }
    </style>
</head>
<body>
  <p>${sessionScope.lang}.</p>
  <fmt:message key="label.welcome"/><br/>

    <a class="c-button" href="?lang=en"><fmt:message key="label.lang.en" /></a>
    <a class="c-button" href="?lang=ua"><fmt:message key="label.lang.ua" /></a>
    <a class="c-button" href="?lang=ru"><fmt:message key="label.lang.ru" /></a>

    <p>Укр Eng Рус</p>
    <h2>Text УКР РУС</h2>



</body>
</html>