<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<security:authorize ifAllGranted="ROLE_USER">

	<h2 class="alt">Edit Payment Card</h2>

	<c:if test="${not empty paymentCard}">
		<div class="span-24 last">
			<div id="errors" class="span-22 last">
				<spring:hasBindErrors name="user">
					<div class="error">
						<spring:bind path="user.*">
							<c:forEach items="${status.errorMessages}" var="error">
								<c:out value="${error}" />
								<br>
							</c:forEach>
						</spring:bind>
					</div>
				</spring:hasBindErrors>
			</div>
			<spring:url var="editUrl" value="/cards/{id}">
				<spring:param name="id" value="${paymentCard.id}"/>
			</spring:url>
			<form:form action="${editUrl}" method="POST" modelAttribute="paymentCard">
				<div>
					<div class="span-6">
						<label for="number">Card Number:</label>
					</div>
					<div class="span-16 last">
						<p>
							<form:input id="number" path="number" maxlength="40" />
							<form:errors cssClass="ferror" path="number" />
						</p>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration(
							{
								elementId : "number",
								widgetType : "dijit.form.ValidationTextBox",
								widgetAttrs : {
									required : true,
									invalidMessage : "A 16-digit credit card number is required.",
									regExp : "[0-9]{16}"
								}
							}));
						</script>
					</div>
				</div>
				<div>
					<div class="span-6">
						<label for="creditCardType">Card Type:</label>
					</div>
					<div class="span-16 last">
						<p>
							<form:select id="type" path="type">
								<form:option label="Visa" value="VISA" />
								<form:option label="Mastercard" value="MASTERCARD" />
								<form:option label="American Express" value="AMEX" />
								<form:option label="Visa Debit" value="VISADEBIT" />
								<form:option label="Maestro" value="MAESTRO" />
							</form:select>
							<script type="text/javascript">
								Spring.addDecoration(new Spring.ElementDecoration(
								{
									elementId : "type",
									widgetType : "dijit.form.FilteringSelect",
									widgetAttrs : {
										hasDownArrow : true
									}
								}));
							</script>
						</p>
					</div>
				</div>
				<div>
					<div class="span-6">
						<label for="expiryMonth">Expiration Date:</label>
					</div>
					<div class="span-16 last">
						<p>
							<form:select id="expiryMonth" path="expiryMonth" style="width: 80px">
								<form:option label="Jan" value="1" />
								<form:option label="Feb" value="2" />
								<form:option label="Mar" value="3" />
								<form:option label="Apr" value="4" />
								<form:option label="May" value="5" />
								<form:option label="Jun" value="6" />
								<form:option label="Jul" value="7" />
								<form:option label="Aug" value="8" />
								<form:option label="Sep" value="9" />
								<form:option label="Oct" value="10" />
								<form:option label="Nov" value="11" />
								<form:option label="Dec" value="12" />
							</form:select>
							<script type="text/javascript">
								Spring.addDecoration(new Spring.ElementDecoration(
								{
									elementId : "expiryMonth",
									widgetType : "dijit.form.FilteringSelect",
									widgetAttrs : {
										hasDownArrow : true
									}
								}));
							</script>
							<form:select id="expiryYear" path="expiryYear" style="width: 80px">
								<form:option label="2011" value="11" />
								<form:option label="2012" value="12" />
								<form:option label="2013" value="13" />
								<form:option label="2014" value="14" />
								<form:option label="2015" value="15" />
							</form:select>
							<script type="text/javascript">
								Spring.addDecoration(new Spring.ElementDecoration(
								{
									elementId : "expiryYear",
									widgetType : "dijit.form.FilteringSelect",
									widgetAttrs : {
										hasDownArrow : true
									}
								}));
							</script>
						</p>
					</div>
				</div>
				<div>
					<div class="span-6">
						<label for="preferred">Preferred:</label>
					</div>
					<div class="span-16 last">
						<p>
							<form:radiobutton id="preferred" path="preferred" label="Yes"
								value="true" />
							<form:radiobutton id="not-preferred" path="preferred" label="No"
								value="false" />
						</p>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : 'preferred',
								widgetType : "dijit.form.RadioButton",
								widgetModule : "dijit.form.CheckBox",
								widgetAttrs : {
									value : "true"
								}
							}));
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : 'not-preferred',
								widgetType : "dijit.form.RadioButton",
								widgetModule : "dijit.form.CheckBox",
								widgetAttrs : {
									value : "false"
								}
							}));
						</script>
					</div>
				</div>
				<div class="span-24 last">
					<form:input path="id" type="hidden" value="${paymentCard.id}" />
					<form:input path="user.username" type="hidden"
						value="${paymentCard.user.username}" />
					<button type="submit" class="button positive" id="save" name="save">
						<img src="<c:url value="/resources/images/update.png"/>" alt="" />
						Save
					</button>
					<script type="text/javascript">
						Spring.addDecoration(new Spring.ValidateAllDecoration({
							elementId : 'save',
							event : 'onclick'
						}));
					</script>
					<spring:url var="listUrl" value="/cards/{id}">
						<spring:param name="id" value="${paymentCard.id}"/>
					</spring:url>
					<a class="button" href="${listUrl}"> <img
						src="<c:url value="/resources/images/list.png"/>" alt="" /> Back </a>
				</div>
			</form:form>
		</div>
	</c:if>
	<c:if test="${empty paymentCard}">No payment card found with this id.</c:if>

</security:authorize>

<security:authorize ifNotGranted="ROLE_USER">
	<p class="error">You are not authorized to access this page.</p>
</security:authorize>

<script type="text/javascript">
<!--
	dojo.style("errors", "opacity", "0");
	var fadeArgs = {
		node : "errors",
		duration : 500,
	};
	dojo.fadeIn(fadeArgs).play();
//-->
</script>