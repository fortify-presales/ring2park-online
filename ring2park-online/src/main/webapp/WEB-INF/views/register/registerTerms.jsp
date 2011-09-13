<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h2 class="alt">Terms and Conditions</h2>

<div class="span-24 last">

	<c:forEach items="${flowRequestContext.messageContext.allMessages}"
		var="message">
		<c:if test="${message.severity.toString eq 'Info'}">
			<div class="info">${message.text}</div>
		</c:if>
		<c:if test="${message.severity.toString eq 'Error'}">
			<div class="error">${message.text}</div>
		</c:if>
	</c:forEach>

	<form:form id="registration" modelAttribute="user">
		<div>
			<div class="span-21">
				<p>
					<textarea id="terms" cols="80" rows="8" disabled="disabled"><spring:message code="termsandconditions" htmlEscape="false"/></textarea>
				</p>
				<script type="text/javascript">
					Spring.addDecoration(new Spring.ElementDecoration({
						elementId : "terms",
						widgetType : "dijit.form.Textarea"
					}));
				</script>
			</div>
		</div>

		<div>
			<div class="span-21 last">
				<p>
					<form:checkbox id="acceptTerms" path="acceptTerms" label="I accept"
						value="true" />
				</p>
				<script type="text/javascript">
					Spring.addDecoration(new Spring.ElementDecoration({
						elementId : 'acceptTerms',
						widgetType : "dijit.form.CheckBox",
						widgetModule : "dijit.form.CheckBox",
						widgetAttrs : {
							value : "true"
						}
					}));
				</script>
			</div>
		</div>
		<div class="span-14 last">
			<p>
				<button class="button" type="submit" id="back"
					name="_eventId_back">Back</button>
				<button class="button positive" type="submit" id="register"
					name="_eventId_register">Register</button>
			</p>
			<script type="text/javascript">
				Spring.addDecoration(new Spring.ValidateAllDecoration({
					elementId : 'register',
					event : 'onclick'
				}));
			</script>
		</div>
	</form:form>
</div>
