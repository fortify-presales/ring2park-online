<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty location}">

	<h2 class="alt">${location.name}</h2>
	
	<div class="span-11">
		<p>
			${location.address}
		</p>
		<p>
			${location.city}, ${location.state}, ${location.zip}
		</p>
		<p>
			${location.country}
		</p>
		<br/>
		<p>
			Daily rate: <fmt:formatNumber type="currency" value="${location.price}"
  				currencyCode="${location.currency}" />
		</p>
		<br/>
		<security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
			<form action="<c:url value="/booking" />" method="get">
				<input type="hidden" name="locationId" value="${location.id}" />
				<div>
					<button class="button" type="submit" id="book" name="_eventId_book">Book Parking</button>
				</div>
			</form>
		</security:authorize>
	</div>
	
	<div class="span-10 last">
		<iframe width="400" height="4004"
			src="http://maps.googleapis.com/maps/api/staticmap?center=${location.city}, ${location.state}, ${location.zip}&zoom=14&size=400x400&markers=color:green%7Clabel:P%7${location.name}&sensor=true">
		</iframe>
		<br class="clearfix"/>
	</div>

</c:if>
<c:if test="${empty location}">No location found with this id.</c:if>
