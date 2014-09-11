<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">

	<h2 class="alt">Account Details</h2>

    <c:if test="${not empty success}">
        <div id="message" class="success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div id="message" class="error">${error}</div>
    </c:if>
	
	<c:if test="${not empty user}">
		<div class="span-24 last">		
			<form:form id="user">
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
						<a href="mailto:${user.email}">${user.email}</a>
					</div>	
				</div>
				<div>
					<div class="span-6">
						<label for="mobile">Mobile:</label>
					</div>
					<div class="span-16 last">
						${user.mobile}
					</div>	
				</div>	
				<div class="span-24 last prepend-top">
					<spring:url var="editUrl" value="/users/{id}/edit">
						<spring:param name="id" value="${user.username}"/>
					</spring:url>
					<spring:url var="deleteUrl" value="/users/{id}/delete">
						<spring:param name="id" value="${user.username}"/>
					</spring:url>
					<c:if test="${editable}">
						<a class="button" href="${editUrl}">
							<img src="<c:url value="/resources/images/update.png"/>" alt=""/> Edit
						</a>
					</c:if>
					<security:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_ADMIN')">
						<a class="button" href="<c:url value="/account" />">
							<img src="<c:url value="/resources/images/list.png"/>" alt=""/> Back
						</a>
					</security:authorize>
					<security:authorize access="hasRole('ROLE_ADMIN')">
						<a class="button" href="${deleteUrl}">
							<img src="<c:url value="/resources/images/delete.png"/>" alt=""/> Delete
						</a>						
						<a class="button" href="<c:url value="/users/" />">
							<img src="<c:url value="/resources/images/list.png"/>" alt=""/> My Account
						</a>
					</security:authorize>
				</div>
			</form:form>	
		</div>
	</c:if>
	<c:if test="${empty user}">No user found with this username.</c:if>
	
</security:authorize>

<security:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
	<p class="error">You are not authorized to access this page.</p>
</security:authorize>

<script type="text/javascript">
<!--
dojo.style("message", "opacity", "0");
var fadeArgs = {
	node: "message",
    duration: 500
};
dojo.fadeIn(fadeArgs).play();
//-->
</script>