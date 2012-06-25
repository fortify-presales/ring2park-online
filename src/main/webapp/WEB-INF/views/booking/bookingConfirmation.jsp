<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
		<form:form id="confirm">
			<h2>Booking Confirmed</h2>
			<div>
				<div class="span-14 last">
					<p>Thank you for your booking at ${booking.location.name}, 
						your confirmation number is <em>${booking.formattedId}</em></p>
				</div>
			</div>
			<div>
				<div class="span-5">Vehicle:</div>
				<div class="span-8 last">
					<p>${booking.vehicle.license}</p>
				</div>
			</div>
			<div>
				<div class="span-5">Start Date:</div>
				<div class="span-8 last">
					<p><fmt:formatDate value="${booking.startDate}" type="date"/></p>
				</div>
			</div>
			<div>
				<div class="span-5">End Date:</div>
				<div class="span-8 last">
					<p><fmt:formatDate value="${booking.endDate}" type="date"/></p>
				</div>
			</div>
			<div>
				<div class="span-5">Total Cost:</div>
				<div class="span-8 last">
					<p>
						<fmt:formatNumber type="currency" value="${booking.total}"
  							currencyCode="${booking.location.currency}" />
  					</p>
				</div>
			</div>
		</form:form>
	</div>

</div>
