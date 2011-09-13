<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<security:authorize ifAllGranted="ROLE_USER">

	<h2 class="alt">Delete Vehicle</h2>
	
	<c:if test="${not empty vehicle}">
		<div class="span-24 last">		
			<spring:url var="deleteUrl" value="/vehicles/{id}">
				<spring:param name="id" value="${vehicle.id}"/>
			</spring:url>
			<form:form id="vehicle" action="${deleteUrl}" method="delete" modelAttribute="vehicle">
				<div>
					<div class="span-6">
						<label for="license">License number:</label>
					</div>
					<div class="span-16 last">
						${vehicle.license}
					</div>
				</div>	
				<div>
					<div class="span-6">
						<label for="preferred">Preferred:</label>
					</div>
					<div class="span-16 last">
						<c:if test="${vehicle.preferred}">Yes</c:if>
						<c:if test="${not vehicle.preferred}">No</c:if>
					</div>
				</div>				
				<div>
					<div class="span-6">
						<label for="brand">Brand:</label>
					</div>
					<div class="span-16 last">
						${vehicle.brand}
					</div>	
				</div>
				<div>
					<div class="span-6">
						<label for="color">Color:</label>
					</div>
					<div class="span-16 last">
						${vehicle.color}
					</div>	
				</div>					
				<div class="span-22 last prepend-top">
					<p id="message" class="notice">Are you sure you want to delete this vehicle?</p>
						<button type="submit" class="button positive" name="delete">
						<img src="<c:url value="/resources/images/delete.png"/>" alt=""/> Yes, Delete!
					</button>				
					<a class="button" href="<c:url value="/vehicles/" />">
						<img src="<c:url value="/resources/images/list.png"/>" alt=""/>
						No, Back
					</a>
				</div>
			</form:form>	
		</div>
	</c:if>
	<c:if test="${empty vehicle}">No vehicle found with this id.</c:if>

</security:authorize>

<security:authorize ifNotGranted="ROLE_USER">
	<p class="error">You are not authorized to access this page.</p>
</security:authorize>	
