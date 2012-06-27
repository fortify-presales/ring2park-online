<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="application_name" /></title>

<spring:url value="/resources/dojo/dojo.js" var="dojo_url" />
<spring:url value="/resources/dijit/themes/claro/claro.css" var="claro_url" />
<spring:url value="/resources/spring/Spring.js" var="spring_url" />
<spring:url value="/resources/spring/Spring-Dojo.js" var="spring_dojo_url" />
<spring:url value="/resources/styles/blueprint/" var="blueprint_url" />
<spring:url value="/resources/styles/ring2park.css" var="ring2park_url" />
<spring:url value="/resources/images/favicon.ico" var="favicon" />

<link rel="stylesheet" type="text/css" href="${claro_url}" />
<link rel="stylesheet" type="text/css" href="/styles/blueprint/screen.css" 
	media="screen, projection" />
<link rel="stylesheet" type="text/css" href="/styles/blueprint/plugins/buttons/screen.css"
	media="screen, projection" />
<link rel="stylesheet" type="text/css" href="/styles/blueprint/plugins/link-icons/screen.css"
	media="screen, projection" />
<link rel="stylesheet" type="text/css" href="/styles/blueprint/print.css"
	media="print" />
<link rel="stylesheet" type="text/css" href="/styles/ring2park.css"
	media="screen, projection" />
<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="${blueprint_url}ie.css" media="screen, projection"/>
<![endif]-->
<link rel="SHORTCUT ICON" href="${favicon}" />

<script src="${dojo_url}" type="text/javascript"></script>
<script src="${spring_url}" type="text/javascript"></script>
<script src="${spring_dojo_url}" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript">
	dojo.require("dojo.parser");
	// get some data, convert to JSON
	dojo.xhrGet({
	    url:"/login.json",
	    handleAs:"json",
	    load: function(data){
	        for(var i in data){
	           console.log("key", i, "value", data[i]);
	        }
	    }
</script>

</head>

<body class="claro">
	<div id="page" class="container">

		<div id="header" class="span-24 prepend-top">
			<div id="logo" class="span-12">
				<h2>
					<img src="<c:url value="/resources/images/logo.png"/>"
						alt="Ring2Park logo" />
				</h2>
			</div>
			<div id="security" class="span-12 last">
				<p>
					<security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
						<c:if test="${pageContext.request.userPrincipal != null}">
							<span><spring:message code="global_welcome" />, <a
								class="first"
								href="<c:url value="/users/${pageContext.request.userPrincipal.name}" />">
									${pageContext.request.userPrincipal.name} </a> </span>
						</c:if>
						<a href="<c:url value="/logout" />">
							<spring:message code="global_logout" />
						</a>
					</security:authorize>
					<security:authorize ifAllGranted="ROLE_ANONYMOUS">
						<span><spring:message code="global_welcome" />, Guest</span>
						<a href="<c:url value="/login" />">
							<spring:message code="global_login" />
						</a>
						<a href="<c:url value="/register" />">
							<spring:message code="global_register" />
						</a>
					</security:authorize>
				</p>
			</div>
		</div>
		<!-- /header -->

		<div id="navigation" class="span-22 prepend-1 append-1">
			<security:authorize ifAllGranted="ROLE_ADMIN">
				<ul class="span-22 prepend-1 append-1">
					<li class="first"><a href="<c:url value="/users/" />">
						<spring:message code="global_menu_users" />
					</a>
					</li>
					<li><a href="<c:url value="/account" />">
						<spring:message code="global_menu_account" />
					</a>
					</li>
					<li><a href="<c:url value="/help" />">
						<spring:message code="global_menu_help" />
					</a>
					</li>
				</ul>
			</security:authorize>
			<security:authorize ifAllGranted="ROLE_USER">
				<ul class="span-22 prepend-1 append-1">
					<li class="first"><a href="<c:url value="/booking" />">
						<spring:message code="global_menu_book" />
					</a>
					</li>
					<li><a href="<c:url value="/locations/search" />">
						<spring:message code="global_menu_parking" />
					</a>
					</li>
					<li><a href="<c:url value="/statements/" />">
						<spring:message code="global_menu_statements" />
					</a>
					</li>
					<li><a href="<c:url value="/account" />">
						<spring:message code="global_menu_account" />
					</a>
					</li>
					<li><a href="<c:url value="/help" />">
						<spring:message code="global_menu_help" />
					</a>
					</li>
				</ul>
			</security:authorize>
			<security:authorize ifAllGranted="ROLE_ANONYMOUS">
				<ul class="span-22 prepend-1 append-1">
					<li class="first"><a href="<c:url value="/register" />">
						<spring:message	code="global_menu_register" />
					</a>
					</li>
					<li><a href="<c:url value="/about" />">
						<spring:message	code="global_menu_about" />
					</a>
					</li>
				</ul>
			</security:authorize>
		</div>
		<!-- /navigation -->

		<div id="content" class="span-22 prepend-1 append-1">
			<div id="main" class="span-22 prepend-top append-bottom">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
		<!--  /content -->

		<div id="footer" class="span-24 prepend-top append-bottom">
			<spring:message code="global_copyright" />
		</div>
		<!--  /footer -->

	</div>
	<!-- /page -->

</body>
</html>