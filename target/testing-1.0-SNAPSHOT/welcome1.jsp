<%--
  Created by IntelliJ IDEA.
  User: STASON
  Date: 10.02.2022
  Time: 0:13
  To change this template use File | Settings | File Templates.
--%>

<!-- fmt:setBundle ставит Bundle  для этого префикса, basename - это папочка Resource Bundle 'messages', по дефолту будет выбран файл messages.properties, Чтобы добавить локализацию нужен Locale-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/> <!--Какая-то связь со всеми языками???-->
<fmt:setBundle basename="messages"/>

  <p> <fmt:message key="label.welcome" /></p>  <!-- fmt:message используется для того чтобы вытягнуть с наших Ресурс файлов значенияпо их ключу-->
