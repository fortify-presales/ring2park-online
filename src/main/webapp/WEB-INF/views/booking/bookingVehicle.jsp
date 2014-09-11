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
		<spring:hasBindErrors name="vehicle">
			<div class="error">
				<spring:bind path="vehicle.*">
					<c:forEach items="${status.errorMessages}" var="error">
						<c:out value="${error}"/><br>
					</c:forEach>
				</spring:bind>
			</div>
		</spring:hasBindErrors>
		<form:form modelAttribute="vehicle">
			<h2>Select a Vehicle</h2>
			<c:if test="${!empty vehicleList}">
				<div>
					<div class="span-14 last">
						<p>Select one of your registered vehicles:</p>
					</div>
				</div>
				<div>
					<div class="span-5">
						<label for="vehicle">Vehicles:</label>
					</div>
					<div class="span-8 last">
						<p>
		                	<form:select path="id">
		                    	<form:options itemValue="id" itemLabel="license" items="${vehicleList}"/>
		                	</form:select>
		                	<script type="text/javascript">
			                	Spring.addDecoration(new Spring.ElementDecoration({
			                		elementId : "id", 
			                		widgetType: "dijit.form.FilteringSelect", 
			                		widgetAttrs : {hasDownArrow : true}})); 
		                	</script>
						</p>
					</div>
				</div>
			</c:if>		
			<div>
				<div class="span-14 last">
					<p><p>or <a href="/vehicles/add">register a new vehicle.</a></p>
				</div>
			</div>			
			<div class="span-14 last">
				<p>
					<button class="button positive" type="submit" id="next" name="_eventId_next">Next</button>
					<button class="button" type="submit" name="_eventId_cancel" >Cancel</button>
				</p>
				<script type="text/javascript">
					Spring.addDecoration(new Spring.ValidateAllDecoration({elementId:'next', event:'onclick'}));
					Spring.addDecoration(new Spring.AjaxEventDecoration({elementId:'next',event:'onclick',formId:'vehicle',params:{fragments:'body'}}));
				</script>
			</div>
		</form:form>	
	</div>
</div>
