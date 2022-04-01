<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 14.02.2022
  Time: 13:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages"/>
<c:if test="${not empty sessionScope.test}">
    <script><%@include file="../../../js/timerForNavbar.js"%></script>
    </c:if>

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
            <span class="text-white-50">Role:${sessionScope.role}</span>
        </div>
        <div class="col-3">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/web-application/testing/student/info"><fmt:message key="navbar.student.label.info"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/web-application/testing/student/tests"><fmt:message key="navbar.student.label.tests"/></a>
                </li>
            </ul>
        </div>
        <div class="col-2">
            <c:set var="url" value="${(fn:replace(pageContext.request.requestURL, 0, pageContext.request.contextPath))}"/>
            <c:if test="${!url.contains('doTest')}">
                <c:if test="${not empty sessionScope.test}">
                    <ul class="navbar-nav">
                        <li class="nav-item bg-success w-100 text-center btn p-1">
                            <a class="nav-link text-warning bold font-monospace fs-5" href="/web-application/testing/student/test?id=${sessionScope.test.id}">BACK TO TEST  <span class="timer" id="timer"></span></a>
                        </li>
                    </ul>
                    <script>
                        startTimerForNavbar(${sessionScope.outDateMilliseconds},${sessionScope.test.id});
                    </script>
                </c:if>
            </c:if>
        </div>
        <div class="col-3">

        </div>

        <div class="col-1">
            <ul class="navbar-nav justify-content-end">
                <li class="nav-item">
                    <a class="nav-link <c:if test="${not empty sessionScope.test}">disabled</c:if>" href="/web-application/testing/logout"><fmt:message key="navbar.label.logout"/></a>
                </li>
            </ul>
        </div>
        <div class="col-1">
            <ul class="navbar-nav">
                <li class="dropdown nav-item">
                    <form method="post">
                        <select class="form-select bg-secondary bg-opacity-100 text-white-50" style="border: none" id="language" name="lang" onchange="submit()">
                            <option class="dropdown-item text-white-50" value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}><fmt:message key="navbar.label.lang.en" /></option>
                            <option class="dropdown-item text-white-50" value="ua" ${sessionScope.lang == 'ua' ? 'selected' : ''}><fmt:message key="navbar.label.lang.ua" /></option>
                        </select>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
