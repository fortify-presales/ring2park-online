<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">

	<h2 class="alt">Parking History</h2>
	
	<div id="bookingResults">	
		<c:if test="${not empty bookingList}">
			<table>
				<thead>
					<tr>
						<th>Confirmation</th>
						<th>Name</th>
						<th>Address</th>
						<th>City, State</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th>Vehicle</th>
						<!-- 
						<th></th>
						-->
					</tr>
				</thead>
				<tbody>
					<c:forEach var="booking" items="${bookingList}">
						<tr>
							<td>${booking.getFormattedId()}</td>
							<td>${booking.location.name}</td>
							<td>${booking.location.address}</td>
							<td>${booking.location.city}, ${booking.location.state}</td>
							<td><fmt:formatDate value="${booking.startDate}" type="date"/></td>
							<td><fmt:formatDate value="${booking.startDate}" type="date"/></td>
							<td>${booking.vehicle.license}</td>
							<!-- 
							<td>
								<spring:url var="bookingUrl" value="/bookings/extend/{id}">
									<spring:param name="id" value="${booking.id}"/>
								</spring:url>
								<form:form action="${bookingUrl}" method="post">
									<button type="submit">Extend</button>
								</form:form>
							</td>
							-->
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		
		<c:if test="${empty bookingList}">
			<p>No bookings found.</p>
		</c:if>
		
	</div>
	
</security:authorize>

<security:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
	<p class="error">You are not authorized to access this page.</p>
</security:authorize>