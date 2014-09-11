<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<security:authorize ifAllGranted="ROLE_USER">
	
	<h2 class="alt">View Vehicle</h2>

    <c:if test="${not empty success}">
        <div id="message" class="success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div id="message" class="error">${error}</div>
    </c:if>
	
	<c:if test="${not empty vehicle}">
		<div class="span-24 last">		
			<form:form id="vehicle">
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
				<div class="span-24 last prepend-top">
					<spring:url var="editUrl" value="/vehicles/{id}/edit">
						<spring:param name="id" value="${vehicle.id}"/>
					</spring:url>
					<a class="button" href="${editUrl}">
						<img src="<c:url value="/resources/images/update.png"/>"alt=""/>
						Edit
					</a>
					<spring:url var="deleteUrl" value="/vehicles/{id}/delete">
						<spring:param name="id" value="${vehicle.id}"/>
					</spring:url>
					<a class="button" href="${deleteUrl}">
						<img src="<c:url value="/resources/images/delete.png"/>" alt=""/>
						Delete
					</a>				
					<a class="button" href="<c:url value="/vehicles/" />">
						<img src="<c:url value="/resources/images/list.png"/>" alt=""/>
						Back
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
