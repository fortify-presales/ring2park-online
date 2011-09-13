<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="org.springframework.security.core.AuthenticationException"%>

<script type="text/javascript">
	dojo.require("dijit.TitlePane");
	dojo.require("dijit.form.Button");
</script>
  
<div class="span-22 last">

	<div class="span-8">
		<p><spring:message code="security_login_message" /></p>
	</div>
	
	<div id="_login_div" class="span-14 last">
		<c:if test="${not empty param.login_error}">
			<div class="error span-13 last">
				<spring:message code="security_login_unsuccessful" />
          		<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />	
			</div>
		</c:if>
		<form name="f" action="<c:url value="/login/authenticate" />" method="post">
			<div>
				<div class="span-4">
					<label for="j_username"><spring:message code="security_login_form_name" />:</label>
				</div>
				<div class="span-8 last">
					<p>
						<input type="text" name="j_username" id="j_username"
							<c:if test="${not empty param.login_error}">value="<%= session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY) %>"</c:if> />
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "j_username",
								widgetType : "dijit.form.ValidationTextBox",
								widgetAttrs : {
									promptMessage : '<spring:message code="security_login_form_name_message" />',
									required : true,
									invalidMessage : '<spring:message code="security_login_form_name_required" />'
								}
							}));
						</script>
					</p>
				</div>
			</div>
			<div>
				<div class="span-4">
					<label for="j_password"><spring:message code="security_login_form_password" />:</label>
				</div>
				<div class="span-8 last">
					<p>
						<input type="password" name="j_password" id="j_password" />
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "j_password",
								widgetType : "dijit.form.ValidationTextBox",
								widgetAttrs : {
									promptMessage : '<spring:message code="security_login_form_password_message" />',
									required : true,
									invalidMessage : '<spring:message code="security_login_form_password_required" />',
								}
							}));
						</script>
					</p>
				</div>
			</div>
			<div>
				<div class="span-4">&nbsp;</div>
				<div class="span-8 last">
					<p>
						<input type="checkbox" name="_spring_security_remember_me"
							id="remember_me" /> <label for="remember_me"><spring:message code="security_login_form_remember" /></label>
						<script type="text/javascript">
							Spring.addDecoration(new Spring.ElementDecoration({
								elementId : "remember_me",
								widgetType : "dijit.form.CheckBox"
							}));
						</script>
					</p>
				</div>
			</div>
			<div>
				<div class="span-4">&nbsp;</div>
				<div class="span-8 last">
					<button id="submit" class="button positive" type="submit"><spring:message code="security_login" /></button>
					<script type="text/javascript">
						Spring.addDecoration(new Spring.ValidateAllDecoration({
							event : 'onclick',
							elementId : 'submit'
						}));
					</script>
				</div>
			</div>
		</form>
		<div class="clear"></div>
	</div>
</div>
<script type="text/javascript">
	Spring.addDecoration(new Spring.ElementDecoration({
		elementId : '_login_div',
		widgetType : 'dijit.TitlePane',
		widgetAttrs : {
			title : '<spring:message code="security_login_title" />'
		}
	}));
</script>
