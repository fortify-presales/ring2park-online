<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h2 class="alt">Parking Locator</h2>

<div id="locationSearch" class="span-21 last">
			
	<c:url var="resultsUrl" value="/locations/search"/>
	
	<form:form modelAttribute="searchCriteria" action="" method="get">
	    <span id="errors" class="errors span-21 last">
	    	<form:errors path="*"/>
	    </span>
	    
	    <div class="span-21 last">
			<label for="searchString">Enter text to search by name, address, city, or postal code:</label>
		</div>
		
		<div class="span-21 last">
			<div class="span-8">
				<p>
					<form:input cssStyle="width:300px;margin-top:8px" id="searchString" path="searchString"/>
				</p>
				<script type="text/javascript">
					Spring.addDecoration(new Spring.ElementDecoration(
					{
						elementId : "searchString",
						widgetType : "dijit.form.ValidationTextBox",
						widgetAttrs : {
							required : true,
							invalidMessage : "A search string is required."
						}
					}));
				</script>
			</div>
			<div class="span-12 last">
				<p>
					<button class="button" id="search" type="submit">
						<img src="<c:url value="/resources/images/search.png"/>" alt=""/> Search
					</button>
				</p>
			</div>		
		</div>
	</form:form>
</div>

<div id="locationResults" class="span-21 last">
	<c:if test="${not empty locationList}">
		<table>
			<thead>
				<tr>
					<th>Name</th>
					<th>Address</th>
					<th>City, Country</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="location" items="${locationList}">
					<tr>
						<td><a class="positive" href="details/${location.id}">${location.name}</a></td>
						<td>${location.address}</td>
						<td>${location.city}, ${location.state}, ${location.country}</td>
						<td style="vertical-align:top;">
							<security:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
								<form action="<c:url value="/booking" />" method="get">
									<input type="hidden" name="locationId" value="${location.id}" />
									<button class="button positive" style="margin-top:2px;" 
										type="submit" id="book" name="_eventId_book">Book Now</button>
								</form>
							</security:authorize>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="buttonGroup">
			<div class="span-3">
				<c:if test="${searchCriteria.page > 0}">
					<a class="button" id="prevResultsLink" href="?searchString=${searchCriteria.searchString}&pageSize=${searchCriteria.pageSize}&page=${searchCriteria.page - 1}">
						<img src="<c:url value="/resources/images/previous.gif"/>">
						Previous
					</a>
				</c:if>
			</div>
			<div class="span-3 append-12 last">
				<c:if test="${not empty locationList && fn:length(locationList) == searchCriteria.pageSize}">
					<a class="button" id="moreResultsLink" href="?searchString=${searchCriteria.searchString}&pageSize=${searchCriteria.pageSize}&page=${searchCriteria.page + 1}">
						Next
						<img src="<c:url value="/resources/images/next.gif"/>">
					</a>
				</c:if>		
			</div>
		</div>
	</c:if>
	
	<c:if test="${empty locationList}">
		<p>No locations found</p>
	</c:if>
	
</div>	

