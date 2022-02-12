<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/> <!--Какая-то связь со всеми языками???-->
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang = ${sessionScope.lang}>
<head>

  <title>Testing</title>
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

  <h1><%= "Hello World!" %></h1>
  <br/>
  <p>${sessionScope.lang}.</p>
  <fmt:message key="label.welcome"/><br/>


    <button onclick="location.href='/?lang=ua'" type="button"><fmt:message key="label.lang.ua" /></button>
    <button onclick="location.href='/?lang=ru'" type="button"><fmt:message key="label.lang.ru" /></button>
    <button onclick="location.href='/?lang=en'" type="button"><fmt:message key="label.lang.en" /></button>

<br><br>

<form action = "hello-servlet" method = "get">
  First Name: <input type = "text" name = "first_name">
  <br />
  Last Name: <input type = "text" name = "last_name" />
  <input type = "submit" value = "Submit" />
</form>
    <p>Укр Eng Рус</p>
    <h2>Text УКР РУС</h2>
    <p>${text['label.lang.en']}</p>


</body>
</html>