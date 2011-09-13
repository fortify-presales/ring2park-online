<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<security:authorize ifAllGranted="ROLE_USER">
	
	<h2 class="alt">View Payment Card</h2>
	
	<c:if test="${not empty message}">
		<div id="message" class="span-21 last ${message.type}">${message.text}</div>	
	</c:if>
	
	<c:if test="${not empty paymentCard}">
		<div class="span-24 last">		
			<form:form id="vehicle">
				<div>
					<div class="span-6">
						<label for="license">Card Number:</label>
					</div>
					<div class="span-16 last">
						${paymentCard.number}
					</div>
				</div>				
				<div>
					<div class="span-6">
						<label for="type">Type:</label>
					</div>
					<div class="span-16 last">
						${paymentCard.type.toString()}
					</div>	
				</div>
				<div>
					<div class="span-6">
						<label for="expiryMonth">Expires:</label>
					</div>
					<div class="span-16 last">
						${paymentCard.expiryMonth}/${paymentCard.expiryYear}
					</div>	
				</div>	
				<div>
					<div class="span-6">
						<label for="preferred">Preferred:</label>
					</div>
					<div class="span-16 last">
						<c:if test="${paymentCard.preferred}">Yes</c:if>
						<c:if test="${not paymentCard.preferred}">No</c:if>
					</div>
				</div>					
				<div class="span-24 last prepend-top">
					<spring:url var="editUrl" value="/cards/{id}/edit">
						<spring:param name="id" value="${paymentCard.id}"/>
					</spring:url>
					<a class="button" href="${editUrl}">
						<img src="<c:url value="/resources/images/update.png"/>"alt=""/>
						Edit
					</a>
					<spring:url var="deleteUrl" value="/cards/{id}/delete">
						<spring:param name="id" value="${paymentCard.id}"/>
					</spring:url>
					<a class="button" href="${deleteUrl}">
						<img src="<c:url value="/resources/images/delete.png"/>" alt=""/>
						Delete
					</a>				
					<a class="button" href="<c:url value="/cards/" />">
						<img src="<c:url value="/resources/images/list.png"/>" alt=""/>
						Back
					</a>
				</div>
			</form:form>	
		</div>
	</c:if>
	<c:if test="${empty paymentCard}">No payment card found with this id.</c:if>

</security:authorize>

<security:authorize ifNotGranted="ROLE_USER">
	<p class="error">You are not authorized to access this page.</p>
</security:authorize>

<script type="text/javascript">
<!--
dojo.style("message", "opacity", "0");
var fadeArgs = {
	node: "message",
    duration: 500,
};
dojo.fadeIn(fadeArgs).play();
//-->
</script>	
