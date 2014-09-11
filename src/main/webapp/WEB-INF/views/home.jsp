<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
    dojo.require("dijit.TitlePane");
    dojo.require("dijit.form.Button");
    dojo.require('dojo.NodeList-fx');
    dojo.require("dojo.NodeList-traverse");

    var count = 0;
    var currentItem = 1;

    // Create a slideshow
    function dojoSlideShow(){
       console.log("in slideshow")
        var active = dojo.query(".active-image");
        var next = active.next("img");
        active.removeClass("active-image").addClass("hidden-image");
        if(next == "") {
            currentItem = 0;
            next = dojo.query("#slideshow img").first();
        }
        currentItem++;
        next.removeClass("hidden-image").addClass("active-image");
        setDecription(next.attr("title"));

    }

    function setDecription(title) {
        dojo.byId("slideshow-desc").innerHTML = title;
    }

    dojo.addOnLoad(function() {

        count = dojo.query("img", "slideshow").length;
        setDecription(dojo.query(".active-image").attr("title"));
        console.log("starting slideshow");
        setInterval("dojoSlideShow()", 3000);

        // Create a news feed
        var list = dojo.query('#news-feed ul'),
                items = list.query("li"),
                showDuration = 3000,
                scrollDuration = 500,
                scrollTopDuration = 200,
                index = 0,
                interval;
        var start = function() { interval = setInterval(move,showDuration); };
        var stop = function() { if(interval) clearInterval(interval); };
        var reset = function() {
            list.anim({ top: 0}, scrollTopDuration, null, start);
        };
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
        start();
    });
</script>

<div id="news-feed" class="span-22 last append-bottom">
    <ul>
        <li><em>3 days ago</em> - Ring2Park has now gone live in 10 major European cities...&nbsp;<a href="#">Read More</a></li>
        <li><em>5 days ago</em> - a new version of the Ring2Park iPhone App has been updated with some exciting features...&nbsp;<a href="#">Read More</a></li>
        <li><em>1 week ago</em> - today Ring2Park celebrates its 2 year birthday with some special offers for new and existing customers...&nbsp;<a href="#">Read More</a></li>
    </ul>
</div>

<div class="span-24 last">
    <div class="span-11">
        <!--  img src="<spring:url value="/resources/images/man_on_phone.jpg"/>"	alt="man on phone" /-->
        <p class="large">
            <spring:message code="home_title" arguments="Ring2Park"/>
        </p>
        <p>
            <spring:message code="home_text" />
        </p>
        <p>
            <spring:url var="registerUrl" value="/register" />
            <spring:message code="home_register" arguments="${registerUrl}"/>
        </p>
        <div class="span-5">
            <img src="<spring:url value="/resources/images/app-store.jpg"/>" alt="app store" />
        </div>
        <div class="span-5 last">
            <img src="<spring:url value="/resources/images/google-play.jpg"/>" alt="google play" />
        </div>
        <div class="span-11 last prepend-top">
            <p>
                <spring:url var="locationsUrl" value="/locations" />
                <spring:message code="home_locations" arguments="${locationsUrl}"/>
            </p>
        </div>
        <div class="span-11 last">
            <p>
                <spring:url var="awardsUrl" value="/awards" />
                <spring:message code="home_awards" arguments="${awardsUrl}"/>
            </p>
            <img src="<spring:url value="/resources/images/park-mark.jpg"/>" alt="park mark" />
        </div>
    </div>
    <div class="span-11 last">
        <div id="slideshow">
            <img src="<spring:url value="/resources/images/00-parking-sky.jpg"/>" title="Welcome to Ring2Park Online - your portal to the world of Ring2Park." class="active-image"/>
            <img src="<spring:url value="/resources/images/01-car-park-drive.jpg"/>" title="We are the leaders in digital parking solutions." class="hidden-image"/>
            <img src="<spring:url value="/resources/images/02-phone-in-car.jpg"/>" title="Pay for parking using your phone, smartphone or computer" class="hidden-image"/>
            <img src="<spring:url value="/resources/images/03-car-park-light.jpg"/>" title="Manage your account and details online" class="hidden-image"/>
            <img src="<spring:url value="/resources/images/04-young-people-phone.jpg"/>" title="Join thousands of customers by parking with Ring2Park" class="hidden-image"/>
            <div id="slideshow-desc"></div>
        </div>
        <br/>
        <div class="span-5">
            <img src="<spring:url value="/resources/images/recycle-car.jpg"/>" alt="recycle car" />
        </div>
        <div class="span-6 last prepend-top">
            <p>
                <spring:message code="home_statement" />
            </p>
        </div>
    </div>
</div>
