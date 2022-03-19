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
<nav class="navbar navbar-expand-sm bg-dark navbar-dark" style="box-shadow: 0 4px 16px #4c3c3c; ">
    <div class="container-fluid row">
        <div class="col-1">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <img src="https://cdn-icons-png.flaticon.com/512/262/262825.png" alt="Avatar Logo" style="width:50px;" class="rounded-pill">
                    <span class="text-white-50 ">Testing</span>

                </li>
            </ul>
        </div>
        <div class="col-1">
            <span class="text-white-50">Role:Student</span>
        </div>
        <div class="col-8">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/web-application/testing/student/info">Info</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/web-application/testing/student/tests">Tests</a>
                </li>
            </ul>
        </div>
        <div class="col-1">
            <ul class="navbar-nav justify-content-end">
                <li class="nav-item">
                    <a class="nav-link" href="/web-application/testing/logout">Log out</a>
                </li>
            </ul>
        </div>
        <div class="col-1">
            <ul class="navbar-nav">
                <li class="dropdown nav-item">
                    <form method="post">
                        <select class="form-select bg-secondary bg-opacity-100 text-white-50" style="border: none" id="language" name="lang" onchange="submit()">
                            <option class="dropdown-item text-white-50" value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}>English </option>
                            <option class="dropdown-item text-white-50" value="ua" ${sessionScope.lang == 'ua' ? 'selected' : ''}>Ukrainian</option>
                            <option class="dropdown-item text-white-50" value="ru" ${sessionScope.lang == 'ru' ? 'selected' : ''}><fmt:message key="label.lang.ru" /></option>
                        </select>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
