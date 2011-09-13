<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h2 class="alt">Access Denied</h2>

<p class="error">You have tried to access a page for which you have insufficient permissions.</p>

<script type="text/javascript">
<!--
dojo.style("error", "opacity", "0");
var fadeArgs = {
	node: "error",
    duration: 500,
};
dojo.fadeIn(fadeArgs).play();
//-->
</script>
