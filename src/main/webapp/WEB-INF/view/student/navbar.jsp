<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 14.02.2022
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<nav class="navbar">
    <section></section>
    <div class = "left  vertical-center">
        @Testing  Role:${sessionScope.role}

    </div>
    <div class="right vertical-center">

        <button onclick="location.href='/web-application/testing/logout'"><fmt:message key="label.unlogin"/></button>
        <div style="float: right; margin-left: 5px;">
            <form  method="post">
                <span class="custom-dropdown" style="border: 2px solid red;">
                <select  id="language" name="lang" onchange="submit()">
                    <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}><fmt:message key="label.lang.en" /></option>
                    <option value="ua" ${sessionScope.lang == 'ua' ? 'selected' : ''}><fmt:message key="label.lang.ua" /></option>
                    <option value="ru" ${sessionScope.lang == 'ru' ? 'selected' : ''}><fmt:message key="label.lang.ru" /></option>
                </select>
                </span>
            </form>
        </div>
    </div>

</nav>
