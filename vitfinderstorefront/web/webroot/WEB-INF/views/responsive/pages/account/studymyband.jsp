<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<template:page pageTitle="${pageTitle}">
	<jsp:body>
		<div id="globalMessages">
			<common:globalMessages />
		</div> 
		<div> Study My Band</div>
<a href="/fitbit/banddata?type=activity">Activity</a> <br/>
<a href="/fitbit/banddata?type=body">Body & Weight</a> <br/>
<a href="/fitbit/banddata?type=devices">Devices</a> <br/>
<a href="/fitbit/banddata?type=food">Food Logging</a> <br/>
<a href="/fitbit/banddata?type=friends">Friends</a> <br/>
<a href="/fitbit/banddata?type=heart">Heart Rate</a> <br/>
<a href="/fitbit/banddata?type=sleep">Sleep</a> <br/>
<a href="/fitbit/banddata?type=subscriptions">Subscriptions</a> <br/>
<a href="/fitbit/banddata?type=user">User</a> <br/>
		
		
		</jsp:body>
</template:page>