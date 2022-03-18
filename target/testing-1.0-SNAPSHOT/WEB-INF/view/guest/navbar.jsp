
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
<nav class="navbar navbar-expand-sm bg-dark navbar-dark" style="box-shadow: 0 4px 16px #4c3c3c; ">
    <div class="container-fluid row align-middle">
        <div class="col-1">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <img src="https://cdn-icons-png.flaticon.com/512/262/262825.png" alt="Avatar Logo" style="width:50px;" class="rounded-pill">
                    <span class="text-white-50">Testing</span>

                </li>
            </ul>
        </div>
        <div class="col-9">
            <span class="text-white-50">Role</span>
        </div>
        <div class="col-1">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/web-application/testing/login">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/web-application/testing/registration">Register</a>
                </li>
            </ul>
        </div>
        <div class="col-1">
            <ul class="navbar-nav">
                <li class="dropdown nav-item">
                    <form method="post">
                        <select class="form-select bg-secondary bg-opacity-100 text-white-50" style="border: none" id="language" name="lang" onchange="submit()">
                            <option class="dropdown-item text-white-50" value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}><fmt:message key="label.lang.en" /></option>
                            <option class="dropdown-item text-white-50" value="ua" ${sessionScope.lang == 'ua' ? 'selected' : ''}><fmt:message key="label.lang.ua" /></option>
                            <option class="dropdown-item text-white-50" value="ru" ${sessionScope.lang == 'ru' ? 'selected' : ''}><fmt:message key="label.lang.ru" /></option>
                        </select>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>

