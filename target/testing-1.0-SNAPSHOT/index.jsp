


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.lang}"/> <!--Какая-то связь со всеми языками???-->
<fmt:setBundle basename="messages"/>



<!DOCTYPE html>
<html lang = ${sessionScope.lang} >
<head>

  <title>Testing</title>
    <meta charset="UTF-8"/>
  <!-- Настройка viewport -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <style>
    <%@include file="css/style.css"%>
  </style>
  <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/262/262825.png" type="image/x-icon"/>
  <!-- Latest compiled and minified CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
  <!-- Latest compiled JavaScript -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

</head>
  <body >

    <%@include file="WEB-INF/view/guest/navbar.jsp"%>

    <main class="container-fluid ">
      <div class="row d-flex justify-content-center ">

        <div class="w-25 bg-light mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
          <p class="display-5">@ Testing project</p>
          <span class="note">Це фінальний веб-проект зовнішнього курса <mark>Java Epam Autumn-Winter Program 2021-2022</mark>.</span>
          <br>
          <br>

        </div>

      </div>
    </main>
    <jsp:include page="WEB-INF/view/footer.jsp"/>

  </body>
</html>