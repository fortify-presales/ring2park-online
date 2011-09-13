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
		<spring:hasBindErrors name="booking">
			<div class="error">
				<spring:bind path="booking.*">
					<c:forEach items="${status.errorMessages}" var="error">
						<c:out value="${error}"/><br>
					</c:forEach>
				</spring:bind>
			</div>
		</spring:hasBindErrors>
		<form:form modelAttribute="booking">
			<h2>Booking Details</h2>
	        <div>
				<div class="span-5">
					<label for="startDate">Start Date:</label>
				</div>
				<div class="span-8 last">
					<p><form:input path="startDate"/></p>
					<script type="text/javascript">
						Spring.addDecoration(new Spring.ElementDecoration({
							elementId : "startDate",
							widgetType : "dijit.form.DateTextBox",
							widgetAttrs : { datePattern : "MM-dd-yyyy", required : true }}));  
					</script>
				</div>
			</div>
			<div>
				<div class="span-5">
					<label for="endDate">End Date:</label>
				</div>
				<div class="span-8 last">
					<p><form:input path="endDate"/></p>
					<script type="text/javascript">
						Spring.addDecoration(new Spring.ElementDecoration({
							elementId : "endDate",
							widgetType : "dijit.form.DateTextBox",
							widgetAttrs : { datePattern : "MM-dd-yyyy", required : true }}));  
					</script>
				</div>
			</div>
			<div>
				<div class="span-5">SMS Text Confirmation:</div>
				<div id="radio" class="span-8 last">
					<p>
						<form:radiobutton id="confirmation" path="confirmation" label="Yes" value="true"/>
						<form:radiobutton id="no-confirmation" path="confirmation" label="No" value="false"/>
					</p>
					<script type="text/javascript">
						Spring.addDecoration(new Spring.ElementDecoration({
							elementId : 'confirmation',
							widgetType : "dijit.form.RadioButton",
							widgetModule : "dijit.form.CheckBox",
							widgetAttrs : { value : "true" }
						}));
						Spring.addDecoration(new Spring.ElementDecoration({
							elementId : 'no-confirmation',
							widgetType : "dijit.form.RadioButton",
							widgetModule : "dijit.form.CheckBox",
							widgetAttrs : { value : "false" }
						}));
					</script>
				</div>
			</div>						
			<div>
				<div class="span-5">SMS Text Reminder:</div>
				<div id="radio" class="span-8 last">
					<p>
						<form:radiobutton id="reminder" path="reminder" label="Yes" value="true"/>
						<form:radiobutton id="no-reminder" path="reminder" label="No" value="false"/>
					</p>
					<script type="text/javascript">
						Spring.addDecoration(new Spring.ElementDecoration({
							elementId : 'reminder',
							widgetType : "dijit.form.RadioButton",
							widgetModule : "dijit.form.CheckBox",
							widgetAttrs : { value : "true" }
						}));
						Spring.addDecoration(new Spring.ElementDecoration({
							elementId : 'no-reminder',
							widgetType : "dijit.form.RadioButton",
							widgetModule : "dijit.form.CheckBox",
							widgetAttrs : { value : "false" }
						}));
					</script>
				</div>
			</div>	
			<div>
		            <div class="span-5">&nbsp;</div>
		            <div class="span-8 last">
            			<p>SMS Text Messages are charged at your standard message rate</p>
            		</div>
        		</div>			
			<div class="span-14 last">
				<p>
				<button class="button" type="submit" id="back" name="_eventId_back">Back</button>
				<button class="button positive" type="submit" id="next" name="_eventId_next">Next</button>
				<button class="button" type="submit" name="_eventId_cancel" >Cancel</button>
				</p>
				<script type="text/javascript">
					
					Spring.addDecoration(new Spring.ValidateAllDecoration({
						elementId : 'next',
						event : 'onclick'
					}));
					Spring.addDecoration(new Spring.AjaxEventDecoration({
						elementId : 'next',
						event : 'onclick',
						formId : 'booking',
						params : {
							fragments : 'body'
						}
					}));
				</script>
			</div>
		</form:form>	
	</div>
</div>
