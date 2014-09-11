<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<security:authorize ifAllGranted="ROLE_USER">

	<h2 class="alt">My Payment Cards</h2>

    <c:if test="${not empty success}">
        <div id="message" class="success">${success}</div>
    </c:if>
	<c:if test="${not empty error}">
		<div id="message" class="error">${error}</div>
	</c:if>
	
	<c:if test="${not empty paymentCardList}">
		<c:forEach var="paymentcard" items="${paymentCardList}">
			<div class="span-21 last box">
				<div class="span-18 last">
					<spring:url var="cardUrl" value="/cards/{id}">
						<spring:param name="id" value="${paymentcard.id}"/>
					</spring:url>
					<span class="">
						<a class="positive"	href="${cardUrl}">${paymentcard.type}&nbsp;${paymentcard.number}</a> 
					</span>
					<c:if test="${paymentcard.preferred}">&nbsp;(Preferred)</c:if>
				</div>
				<div class="span-18 last">Expires ${paymentcard.expiryMonth}/${paymentcard.expiryYear}</div>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${empty paymentCardList}">No payment cards found.</c:if>
	
	<div class="span-24 last">
		<a class="button" href="<c:url value="/cards/add" />">
			<img src="<c:url value="/resources/images/create.png"/>" title="Add Payment Card">
			Add Payment Card
		</a>
        <a class="button" href="<c:url value="/account" />">
            <img src="<c:url value="/resources/images/list.png"/>" alt=""/> My Account
        </a>
	</div>
	
</security:authorize>

<security:authorize ifNotGranted="ROLE_USER">
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