<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 19.02.2022
  Time: 13:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<nav class="navbar">
    <div>
        <div class = "left vertical-center"  style="min-width: 300px">
        @Testing  Role:${sessionScope.role}
        </div>
        <div class = "left vertical-center">
            <button onclick="location.href='/web-application/testing/admin/info'"><fmt:message key="admin.label.info"/></button>
            <button onclick="location.href='/web-application/testing/admin/showTests'"><fmt:message key="admin.label.showTests"/></button>
            <button onclick="location.href='/web-application/testing/admin/createTest'"><fmt:message key="admin.label.createTest"/></button>
            <button onclick="location.href='/web-application/testing/admin/showUsers'"><fmt:message key="admin.label.showUsers"/></button>
        </div>
        <div class="right vertical-center">


            <button onclick="location.href='/web-application/testing/logout'"><fmt:message key="label.unlogin"/></button>
            <div style="float: right; margin-left: 5px; ">
                <form  method="post">
                <span class="custom-dropdown">
                <select  id="language" name="lang" onchange="submit()">
                    <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}><fmt:message key="label.lang.en" /></option>
                    <option value="ua" ${sessionScope.lang == 'ua' ? 'selected' : ''}><fmt:message key="label.lang.ua" /></option>
                    <option value="ru" ${sessionScope.lang == 'ru' ? 'selected' : ''}><fmt:message key="label.lang.ru" /></option>
                </select>
                </span>
                </form>
            </div>
        </div>
    </div>


</nav>