<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>

<template:page pageTitle="${pageTitle}">

	<cms:pageSlot position="Article" var="feature">
		<cms:component component="${feature}" element="div" class=""/>
	</cms:pageSlot>
		
	<cms:pageSlot position="DisqusEmbededCode" var="feature">
		<cms:component component="${feature}" element="div" class=""/>
	</cms:pageSlot>
	
</template:page>
