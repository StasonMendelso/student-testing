
<%@ page contentType="text/html;text/css;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>




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


<jsp:include page="WEB-INF/view/guest/navbar.jsp"/>
  <div class="content">

    <p>Укр Eng Рус</p>
    <h2>Text УКР РУС</h2>
    <fmt:message key="label.txt"/>


  </div>
<jsp:include page="footer.jsp"/>
</body>
</html>