<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<security:authorize ifAllGranted="ROLE_USER">

	<h2 class="alt">Delete Payment Card</h2>
	
	<c:if test="${not empty paymentCard}">
		<div class="span-24 last">		
			<spring:url var="deleteUrl" value="/cards/{id}">
				<spring:param name="id" value="${paymentCard.id}"/>
			</spring:url>
			<form:form id="paymentCard" action="${deleteUrl}" method="delete" modelAttribute="paymentCard">
				<div>
					<div class="span-6">
						<label for="number">Card number:</label>
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
						${paymentCard.type}
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
				<div class="span-22 last prepend-top">
					<p id="message" class="notice">Are you sure you want to delete this payment card?</p>
						<button type="submit" class="button positive" name="delete">
						<img src="<c:url value="/resources/images/delete.png"/>" alt=""/> Yes, Delete!
					</button>				
					<a class="button" href="<c:url value="/cards/" />">
						<img src="<c:url value="/resources/images/list.png"/>" alt=""/>
						No, Back
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
