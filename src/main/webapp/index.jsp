


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.lang}"/> <!--Какая-то связь со всеми языками???-->
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<!DOCTYPE html>
<html lang = ${sessionScope.lang} >
<head>

  <title>Testing</title>
    <meta charset="UTF-8"/>

</head>
  <body class="body">

    <%@include file="WEB-INF/view/guest/navbar.jsp"%>
      <div class="content">

        <p>Укр Eng Рус</p>
        <h2>Text УКР РУС</h2>
        <fmt:message key="label.txt"/>


      </div>


  </body>
</html>