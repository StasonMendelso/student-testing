
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

<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">--%>
<nav class="navbar">
 <section></section>
    <div class = "left  vertical-center">
        @Testing  Role:${sessionScope.role}
     </div>
        <div class="right vertical-center">
    <div style="float: left">
            <button onclick="location.href='/web-application/testing/login'"><fmt:message key="label.login"/></button>
            <button onclick="location.href='/web-application/testing/registration'"><fmt:message key="label.register"/></button>
    </div>
            <div style="float: right; margin-left: 5px;">
                <form method="post">

                <select id="language" name="lang" onchange="submit()">
                    <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}><fmt:message key="label.lang.en" /></option>
                    <option value="ua" ${sessionScope.lang == 'ua' ? 'selected' : ''}><fmt:message key="label.lang.ua" /></option>
                    <option value="ru" ${sessionScope.lang == 'ru' ? 'selected' : ''}><fmt:message key="label.lang.ru" /></option>
                </select>
            </form>
            </div>

<%--            <button onclick="location.href='?lang=en'"><fmt:message key="label.lang.en" /></button>--%>
<%--            <button onclick="location.href='?lang=ua'"><fmt:message key="label.lang.ua" /></button>--%>
<%--            <button onclick="location.href='?lang=ru'"><fmt:message key="label.lang.ru" /></button>--%>
        </div>

</nav>

