<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h2 class="alt">Verification Confirmed</h2>

<div class="span-24 last">
	<form:form id="verification" modelAttribute="user">
		<div>
			<div class="span-21 last">
				<p>
					Thank you ${user.name}, you have now been verified and full access to the site.
					Please login.
				</p>
			</div>
		</div>
	</form:form>
</div>

