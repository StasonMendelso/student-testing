<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 22.02.2022
  Time: 1:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/styles.css">
<link rel="shortcut icon" href="http://surl.li/bjfgy" type="image/x-icon">

<html>
<head>
    <title>Testing</title>
</head>
<body class="body">

<jsp:include page="/WEB-INF/view/admin/navbar.jsp"/>
<div class="content">
    <h1>IT is editUser.jsp</h1>
    <c:set var="user" value="${requestScope.user}"/>


    <div class="form" style="margin: 0 auto;margin-top: 10px;width: 30%">
        <table>
            <tr><th style="-moz-text-align-last: center" colspan="2">User information</th></tr>
            <tr><td>Id</td><td>${user.id}</td></tr>
            <tr><td>Login</td><td>${user.login}</td></tr>
            <tr><td>Surname</td><td>${user.surname}</td></tr>
            <tr><td>Name</td><td>${user.name}</td></tr>
        </table>
        <br>
        <form>
            <input hidden name="id" value="${user.id}">
            <table>
                <tr><th style="-moz-text-align-last: center" colspan="2">Edit user information</th></tr>
                <tr><td><label for="surname">Surname</label></td><td> <input type="text" id="surname" name="surname" class="form_input"></td></tr>
                <tr><td> <label for="name">Name</label></td><td> <input type="text" id="name" name="name" class="form_input"></td></tr>
                <tr><td colspan="2"><input type="submit" class="button-edit input-submit center" style="width: 25%" value="Save"></td></tr>
            </table>
        </form>
    </div>



</div>

</body>
</html>