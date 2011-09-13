<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">

	<h2 class="alt">My Account</h2>

	<c:if test="${not empty message}">
		<div id="message" class="span-21 last ${message.type}">${message.text}</div>	
	</c:if>
	
	<div class="span-24 last">	
		<div class="span-10 prepend-1 append-1 box" style="height:100px;">
			<span class="span-3">
				<img src="<c:url value="/resources/images/user.png"/>" align="top" alt=""/>
			</span>
			<span class="span-7 last">
				<a class="large positive"href="<c:url value="/users/${pageContext.request.userPrincipal.name}" />">Account Details</a><br/>
				Amend your account details.
			 </span>
		</div>
	
		<div class="span-10 prepend-1 append-1 box last" style="height:100px;">
			<span class="span-3">
				<img src="<c:url value="/resources/images/documents.png"/>" align="top" alt=""/>
			</span>
			<span class="span-7 last">
				<a class="large positive" href="<c:url value="/statements/" />">Statements</a><br/>
				View and print parking session statements.
			 </span>
		</div>
	</div>
	
	<security:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_ADMIN')">
		<div class="span-24 last">	
			<div class="span-10 prepend-1 append-1 box" style="height:100px;">
				<span class="span-3">
					<img src="<c:url value="/resources/images/car.png"/>" align="top" alt=""/>
				</span>
				<span class="span-7 last">
					<a class="large positive" href="<c:url value="/vehicles/" />">Vehicle Details</a><br/>
					Add, edit and delete vehicles.
				 </span>
			</div>
		
			<div class="span-10 prepend-1 append-1 box last" style="height:100px;">
				<span class="span-3">
					<img src="<c:url value="/resources/images/creditcard.png"/>" align="top" alt=""/>
				</span>
				<span class="span-7 last">
					<a class="large positive" href="<c:url value="/cards/" />">Payment Card Details</a><br/>
					Add, edit and delete payment cards.
				 </span>
			</div>
		</div>
	</security:authorize>
	
</security:authorize>

<security:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
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