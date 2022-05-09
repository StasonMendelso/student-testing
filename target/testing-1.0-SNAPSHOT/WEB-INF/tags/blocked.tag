<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="blockedValue" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<c:if test="${blockedValue==true}"> <fmt:message key="user.blocked"/></c:if>
<c:if test="${blockedValue==false}"> <fmt:message key="user.unblocked"/> </c:if>
