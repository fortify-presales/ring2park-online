<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="alt">Booking Error</h2>

<div class="span-24 last">

	<c:forEach items="${flowRequestContext.messageContext.allMessages}"
		var="message">
		<c:if test="${message.severity eq 'INFO'}">
			<div class="info">${message.text}</div>
		</c:if>
		<c:if test="${message.severity eq 'ERROR'}">
			<div class="error">${message.text}</div>
		</c:if>
	</c:forEach>
	
</div>

