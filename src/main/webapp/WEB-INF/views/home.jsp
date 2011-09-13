<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
<!--
dojo.require("dijit.TitlePane");
dojo.require("dijit.form.Button");
dojo.require('dojo.NodeList-fx');
dojo.addOnLoad(function() {
  /* settings */
  var list = dojo.query('#news-feed ul'),
    items = list.query("li"),
    showDuration = 3000,
    scrollDuration = 500,
    scrollTopDuration = 200,
    index = 0,
    interval;

  /* movement */
  var start = function() { interval = setInterval(move,showDuration); };
  var stop = function() { if(interval) clearInterval(interval); };
  var reset = function() {
      list.anim({ top: 0}, scrollTopDuration, null, start);
  };
  /* action! */
  var move = function() {
      list.anim({ top: (0 - (dojo.coords(items[++index]).t)) }, scrollDuration, null, function(){
      if(index == items.length - 1) {
        index = 0-1;
        stop();
        setTimeout(reset,showDuration);
      }
      });
  };

  /* stop and start during mouseenter, mouseleave  */
  list.onmouseenter(stop).onmouseleave(start);

  /* go! */
  start();
});
//-->
</script>

<div id="news-feed" class="span-22 last append-bottom">
	<ul>
	    <li><em>3 days ago</em> - Ring2Park has now gone live in 10 major European cities...&nbsp;<a href="#">Read More</a></li>
	    <li><em>5 days ago</em> - a new version of the Ring2Park iPhone App has been updated with some exciting features...&nbsp;<a href="#">Read More</a></li>
	    <li><em>1 week ago</em> - today Ring2Park celebrates its 2 year birthday with some special offers for new and existing customers...&nbsp;<a href="#">Read More</a></li>
	</ul>
</div>

<div class="span-24 last">
	<div class="span-10">
		<img src="<spring:url value="/resources/images/man_on_phone.jpg"/>"	alt="man on phone" />
	</div>
	<div class="span-12 last">
		<p>
			<spring:message code="home_text" /> 
		</p>
		<p>
			<spring:url var="registerUrl" value="/register" />
			<spring:message code="home_register" arguments="${registerUrl}"/>
		</p>	
		<hr/>
		<div class="span-6">
			<p>
				<spring:message code="home_statement" />
			</p>
		</div>	
		<div class="span-6 last">
			<img src="<spring:url value="/resources/images/recycle-car.jpg"/>" alt="recycle car" />
		</div>
	</div>
</div>
