<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="alt">Registration Success</h2>

<div class="span-22 last">
	<form:form id="registration" modelAttribute="user">
		<div>
			<div class="span-22 last">
				<p>
					Thank you ${user.name}, an email has been sent to <a href="${user.email}">${user.email}</a>.
					Please click on the link contained within this email to complete the registration process.
				</p>
			</div>
		</div>
	</form:form>
</div>

