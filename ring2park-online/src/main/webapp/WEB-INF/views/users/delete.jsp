<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<security:authorize ifAllGranted="ROLE_ADMIN">

	<h2 class="alt">Delete Account</h2>

	<c:if test="${not empty user}">
		<spring:url var="deleteUrl" value="/users/{id}">
			<spring:param name="id" value="${user.username}"/>
		</spring:url>
		<div class="span-24 last">		
			<form:form action="${deleteUrl}" method="delete" modelAttribute="user">
			<div>
					<div class="span-6">
						<label for="username">Username:</label>
					</div>
					<div class="span-16 last">
						${user.username}
					</div>
				</div>	
				<div>
					<div class="span-6">
						<label for="enabled">Enabled:</label>
					</div>
					<div class="span-16 last">
						<c:if test="${user.enabled}">Yes</c:if>
						<c:if test="${not user.enabled}">No</c:if>
					</div>
				</div>				
				<div>
					<div class="span-6">
						<label for="name">Name:</label>
					</div>
					<div class="span-16 last">
						${user.name}
					</div>	
				</div>
				<div>
					<div class="span-6">
						<label for="email">Email:</label>
					</div>
					<div class="span-16 last">
						${user.email}
					</div>	
				</div>					
				<div class="span-22 last prepend-top">
					<p id="message" class="notice">Are you sure you want to delete this user - once deleted they cannot be recovered?</p>
					<button type="submit" class="button positive" name="delete">
						<img src="<c:url value="/resources/images/delete.png"/>" alt=""/> Yes, Delete!
					</button>				
					<a class="button" href="<c:url value="/users/" />">
						<img src="<c:url value="/resources/images/list.png"/>" alt=""/>
						No, Back
					</a>
				</div>
			</form:form>	
		</div>
	</c:if>
	<c:if test="${empty user}">No user found with this username.</c:if>

</security:authorize>

<security:authorize ifNotGranted="ROLE_ADMIN">
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
