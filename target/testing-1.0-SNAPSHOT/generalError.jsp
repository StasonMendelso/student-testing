<%@ page import="com.stason.testing.controller.utils.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />



<!DOCTYPE html>
<html>

<head>
  <title>Testing</title>
  <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">--%>
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
<c:if test="${sessionScope.role=='ADMIN'}">
  <%@include file="WEB-INF/view/admin/navbar.jsp"%>
</c:if>
<c:if test="${sessionScope.role=='STUDENT'}">
  <%@include file="WEB-INF/view/student/navbar.jsp"%>
</c:if>
<c:if test="${sessionScope.role=='GUEST'}">
  <%@include file="WEB-INF/view/guest/navbar.jsp"%>
</c:if>

<main class="container-fluid bg-dark bg-opacity-25">
  <div class="row d-flex justify-content-center ">

    <div class="w-50 bg-dark  mb-5 " style="border-radius: 30px 30px 30px 30px;box-shadow: 0px 0px 50px 1px rgba(0,0,0,0.5); margin-top: 100px">
      <div class="text-center mt-2">
        <img src="https://cdn-icons.flaticon.com/png/512/738/premium/738884.png?token=exp=1648637245~hmac=3cbb97ac3c0f0e932197227fcd1cf6a7" class="w-25">
        <h1 class="text-danger bold">Something went wrong...</h1>
      </div>
      <div  class="text-white-50 text-center mb-4">

          <div  style="margin: 0 auto;margin-top: 10px; ">
            <h4 class="text-white-50">Please, try again later</h4>
          </div>

      </div>

    </div>

  </div>
</main>
<jsp:include page="WEB-INF/view/footer.jsp"/>

</body>
</html>