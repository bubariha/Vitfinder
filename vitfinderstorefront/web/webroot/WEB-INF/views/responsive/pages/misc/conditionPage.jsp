<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<template:page pageTitle="${pageTitle}">

	<div class="bone-health-category-vtf">
		<h2>${rootCategory.name}</h2>
		<p>${rootCategory.description}</p>

		<a href="${rootCategory.url}"><h4>Recommended Products for
				${rootCategory.name} </h4></a>

		<cms:pageSlot position="QuesstionareAdvice" var="feature">
			<cms:component component="${feature}" />
		</cms:pageSlot>
	</div>

</template:page>
