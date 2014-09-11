<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h2 class="alt">Enter Details</h2>

<div class="span-22 last">

	<div class="span-22 last">
		<c:forEach items="${flowRequestContext.messageContext.allMessages}"
			var="message">
			<c:if test="${message.severity.toString eq 'Info'}">
				<div class="info">${message.text}</div>
			</c:if>
			<c:if test="${message.severity.toString eq 'Error'}">
				<div class="error">${message.text}</div>
			</c:if>
		</c:forEach>
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

	<form:form id="registration" modelAttribute="user">
		<div>
			<div class="span-6">
				<label for="name">Username:</label>
			</div>
			<div class="span-15 last">
				<p>
					<form:input id="username" path="username" maxlength="40" />
				</p>
				<script type="text/javascript">
					Spring
							.addDecoration(new Spring.ElementDecoration(
									{
										elementId : "username",
										widgetType : "dijit.form.ValidationTextBox",
										widgetAttrs : {
											required : true,
											regExp : "[a-zA-Z0-9]{4,50}",
											invalidMessage : "Username must be alphanumeric and at least 4 characters.",
											trim : true
										}
									}));
				</script>
			</div>
		</div>
		<div>
			<div class="span-6">
				<label for="name">Name:</label>
			</div>
			<div class="span-15 last">
				<p>
					<form:input id="name" path="name" maxlength="40" />
				</p>
				<script type="text/javascript">
					Spring
							.addDecoration(new Spring.ElementDecoration(
									{
										elementId : "name",
										widgetType : "dijit.form.ValidationTextBox",
										widgetAttrs : {
											required : true,
											regExp : "[a-zA-Z0-9 ]{6,50}",
											invalidMessage : "Name must be at least 6 characters",
											trim : true
										}
									}));
				</script>
			</div>
		</div>
		<div>
			<div class="span-6">
				<label for="password">Password:</label>
			</div>
			<div class="span-15 last">
				<p>
					<form:input type="password" id="password" path="password"
						maxlength="40" />
				</p>
				<script type="text/javascript">
					Spring
							.addDecoration(new Spring.ElementDecoration(
									{
										elementId : "password",
										widgetType : "dijit.form.ValidationTextBox",
										widgetAttrs : {
											required : true,
											regExp : "[a-zA-Z0-9]{6,50}",
											invalidMessage : "Password must be alphanumeric with no spaces and at least 6 characters",
											trim : true
										}
									}));
				</script>
			</div>
		</div>
		<div>
			<div class="span-6">
				<label for="confirmPassword">Confirm Password:</label>
			</div>
			<div class="span-15 last">
				<p>
					<form:input type="password" id="confirmPassword"
						path="confirmPassword" maxlength="40" />
				</p>
				<script type="text/javascript">
					Spring
							.addDecoration(new Spring.ElementDecoration(
									{
										elementId : "confirmPassword",
										widgetType : "dijit.form.ValidationTextBox",
										widgetAttrs : {
											required : true,
											regExp : "[a-zA-Z0-9]{6,50}",
											invalidMessage : "Password must be alphanumeric with no spaces and at least 6 characters",
											trim : true
										}
									}));
				</script>
			</div>
		</div>
		<div>
			<div class="span-6">
				<label for="email">Email:</label>
			</div>
			<div class="span-15 last">
				<p>
					<form:input id="email" path="email" maxlength="40" />
				</p>
				<script type="text/javascript">
					Spring
							.addDecoration(new Spring.ElementDecoration(
									{
										elementId : "email",
										widgetType : "dijit.form.ValidationTextBox",
										widgetAttrs : {
											required : true,
											regExp : "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}",
											invalidMessage : "A valid email address is required.",
											trim : true
										}
									}));
				</script>
			</div>
		</div>
		<div>
			<div class="span-6">
				<label for="email">Mobile Number:</label>
			</div>
			<div class="span-15 last">
				<p>
					<form:input id="mobile" path="mobile" maxlength="40" />
				</p>
				<script type="text/javascript">
					Spring
							.addDecoration(new Spring.ElementDecoration(
									{
										elementId : "mobile",
										widgetType : "dijit.form.ValidationTextBox",
										widgetAttrs : {
											required : true,
											regExp : "[0-9]{10,20}",
											invalidMessage : "A valid mobile number is required.",
											trim : true
										}
									}));
				</script>
			</div>
		</div>
		<div class="span-14 last">
			<p>
				<button class="button positive" type="submit" id="next"
					name="_eventId_next">Next</button>
			</p>
			<script type="text/javascript">
				Spring.addDecoration(new Spring.ValidateAllDecoration({
					elementId : 'next',
					event : 'onclick'
				}));
				Spring.addDecoration(new Spring.AjaxEventDecoration({
					elementId : 'next',
					event : 'onclick',
					formId : 'registration',
					params : {
						fragments : 'body'
					}
				}));
			</script>
		</div>
	</form:form>
</div>
