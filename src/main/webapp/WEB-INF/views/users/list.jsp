<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<security:authorize ifAllGranted="ROLE_ADMIN">

	<h2 class="alt">User Accounts</h2>

	<c:if test="${not empty message}">
		<div id="message" class="span-21 last ${message.type}">${message.text}</div>	
	</c:if>
	
	<div class="span-24 last">	
		<c:if test="${not empty userList}">
			<c:forEach var="user" items="${userList}">
				<spring:url var="viewUrl" value="/users/{id}">
					<spring:param name="id" value="${user.username}"/>
				</spring:url>
				<div class="span-21 last box">
					<div class="span-18 last">
						<span class="">
							<a class="positive"	href="${viewUrl}">${user.username}</a>
							<c:if test="${user.enabled}">(enabled)</c:if>
							<c:if test="${not user.enabled}">(disabled)</c:if>
						 </span>
					</div>
					<div class="span-18 last">${user.name}&nbsp;<a href="mailto:${user.email}">${user.email}</a></div>
					<div class="span-18 last">${user.mobile}</div>
				</div>
			</c:forEach>
			</c:if>
		<c:if test="${empty userList}">No users found.</c:if>
	</div>
	
	<div class="span-24 last">
		<a class="button" href="<c:url value="/users/add" />">
			<img src="<c:url value="/resources/images/create.png"/>" title="Add User">
			Add User
		</a>
	</div>
	
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