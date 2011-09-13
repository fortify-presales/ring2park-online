<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div id="bookingForm">
	<div class="span-6 box prepend-top">
		<p><em>Booking parking for:</em></p>
		<h4>${booking.location.name}</h4>
		
		<address>
			${booking.location.address}
			<br/>
			${booking.location.city}, ${booking.location.state}, ${booking.location.zip}
			<br/>
			${booking.location.country}
		</address>
		<p>
			Daily rate: <fmt:formatNumber type="currency" value="${booking.location.price}"
  				currencyCode="${booking.location.currency}" />
		</p>
		<input type="hidden" name="locationId" value="${booking.location.id}" />
	</div>
	<div class="span-14 prepend-1 last">
		<form:form id="confirm" modelAttribute="booking">
			<h2>Confirm Booking Details</h2>
			<div>
				<div class="span-5">Vehicle:</div>
				<div class="span-8 last">
					<p><spring:bind path="vehicle.license">${status.value}</spring:bind></p>
				</div>
			</div>
			<div>
				<div class="span-5">Start Date:</div>
				<div class="span-8 last">
					<p><spring:bind path="startDate">${status.value}</spring:bind></p>
				</div>
			</div>
			<div>
				<div class="span-5">End Date:</div>
				<div class="span-8 last">
					<p><spring:bind path="endDate">${status.value}</spring:bind></p>
				</div>
			</div>
	        <div>
	            <div class="span-5">Number of Days:</div>
	            <div class="span-8 last">
	            	<p><spring:bind path="days">${status.value}</spring:bind></p>
	            </div>
	        </div>
	        <div>
	            <div class="span-5">Total Payment:</div>
	            <div class="span-8 last">
	            	<p><spring:bind path="total">${status.value}</spring:bind></p>
	            </div>
	        </div>
			<div>
				<div class="span-5">Card Number:</div>
				<div class="span-8 last">
					<c:set var="card" value="${booking.card.number}"/>
					<p>************<c:out value="${fn:substring(card,12,16)}"/></p>
				</div>
			</div>
			<div class="span-14 last">
				<button class="button" type="submit" name="_eventId_back">Back</button>
				<button class="button positive" type="submit" name="_eventId_confirm">Confirm Booking</button>
				<button class="button" type="submit" name="_eventId_cancel">Cancel</button>
			</div>
		</form:form>
	</div>
</div>
