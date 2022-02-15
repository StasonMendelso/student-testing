
<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 12.02.2022
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<nav class="navbar">
 <section></section>
    <div class = "left  vertical-center">
        @Testing
     </div>
        <div class="right vertical-center">

            <button onclick="location.href='login'"><fmt:message key="label.login"/></button>
            <button onclick="location.href='controller?action=register'"><fmt:message key="label.register"/></button>
            <button onclick="location.href='?lang=en'"><fmt:message key="label.lang.en" /></button>
            <button onclick="location.href='?lang=ua'"><fmt:message key="label.lang.ua" /></button>
            <button onclick="location.href='?lang=ru'"><fmt:message key="label.lang.ru" /></button>
        </div>

</nav>

