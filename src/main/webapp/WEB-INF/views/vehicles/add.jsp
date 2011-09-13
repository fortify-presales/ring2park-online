<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<security:authorize ifAllGranted="ROLE_USER">
	
	<h2 class="alt">Add Vehicle</h2>
	
	<div class="span-24 last">
		<div id="errors" class="span-22 last">
			<spring:hasBindErrors name="user">
				<div class="error">
					<spring:bind path="user.*">
						<c:forEach items="${status.errorMessages}" var="error">
							<c:out value="${error}"/><br>
						</c:forEach>
					</spring:bind>
				</div>
			</spring:hasBindErrors>
		</div>
		<spring:url var="addUrl" value="/vehicles/"/>
		<form:form action="${addUrl}" method="put" modelAttribute="vehicle">
			<div>
				<div class="span-6">
					<label for="brand">Brand:</label>
				</div>
				<div class="span-16 last">
					<p>
						<form:select path="brand">
							<form:options id="brand" items="${brandList}" />
						</form:select>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "brand",
								widgetType : "dijit.form.FilteringSelect",
								widgetAttrs : {
									required : true,
									invalidMessage : "A brand is required",
									hasDownArrow : true
								}
							}));
						</script>
					</p>
				</div>
			</div>
			<div>
				<div class="span-6">
					<label for="color">Color:</label>
				</div>
				<div class="span-16 last">
					<p>
						<form:select path="color">
							<form:options id="color" items="${colorList}" />
						</form:select>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "color",
								widgetType : "dijit.form.FilteringSelect",
								widgetAttrs : {
									required : true,
									invalidMessage : "A brand is required",
									hasDownArrow : true
								}
							}));
						</script>
					</p>
				</div>
			</div>
			<div>
				<div class="span-6">
					<label for="license">License number:</label>
				</div>
				<div class="span-16 last">
					<p>
						<form:input id="license" path="license" maxlength="40" />
					</p>
					<script type="text/javascript">
						Spring.addDecoration(new Spring.ElementDecoration({
							elementId : "license",
							widgetType : "dijit.form.ValidationTextBox",
							widgetAttrs : {
								required : true,
								regExp : "[a-zA-Z0-9 ]{6,50}",
								invalidMessage : "License number must be alphanumeric with spaces and at least 6 characters long",
								trim : true
							}
						}));
					</script>
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
				<button type="submit" class="button positive" id="save" name="save">
					<img src="<c:url value="/resources/images/update.png"/>" alt=""/> Save
				</button>
				<script type="text/javascript">
					Spring.addDecoration(new Spring.ValidateAllDecoration({
						elementId : 'save',
						event : 'onclick'
					}));
				</script>
				<a class="button" href="<c:url value="/vehicles/" />">
					<img src="<c:url value="/resources/images/list.png"/>" alt=""/> Back to List
				</a>
			</div>
		</form:form>
	</div>

</security:authorize>

<security:authorize ifNotGranted="ROLE_USER">
	<p class="error">You are not authorized to access this page.</p>
</security:authorize>

<script type="text/javascript">
<!--
dojo.style("errors", "opacity", "0");
var fadeArgs = {
	node: "errors",
    duration: 500,
};
dojo.fadeIn(fadeArgs).play();
//-->
</script>	
