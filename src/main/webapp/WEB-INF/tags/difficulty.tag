<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="intDifficulty" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<c:if test="${intDifficulty==1}"> <fmt:message key="test.difficulty.easy"/></c:if>
<c:if test="${intDifficulty==2}"> <fmt:message key="test.difficulty.medium"/> </c:if>
<c:if test="${intDifficulty==3}"> <fmt:message key="test.difficulty.hard"/> </c:if>