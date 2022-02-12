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


      <p> <a href="hello-servlet">Hello Servlet</a> </p>
      <p> <a href="welcome1.jsp">Welcome1.jsp</a> </p>
      <p> <a href="welcome2.jsp">Welcome2.jsp</a> </p>
      <p> <a href="changeLocale.jsp">ChangeLocale.jsp</a> </p>
      <p> <a href="sessionLocale.jsp">SessionLocale.jsp</a> </p>
<%--  <c:set var="lang" scope="session" value="en"/>--%>

<%--  <select id="lang" name="lang" onchange="submit()">--%>
<%--      <option value="en" ${sessionScope.lang="en"}>English</option>--%>
<%--      <option value="fr" ${sessionScope.lang="fr"}>France</option>--%>
<%--  </select>--%>
<%--  <button onclick="<c:set var="lang" scope="session" value="en"/>">English</button>--%>
<%--  <button onclick="${sessionScope.lang="fr"}">France</button>--%>

<%--  <c:set var="lang" scope="session" value="${language}"/>--%>
<%--    <jsp:include page="welcome1.jsp"/>--%>

</body>
</html>