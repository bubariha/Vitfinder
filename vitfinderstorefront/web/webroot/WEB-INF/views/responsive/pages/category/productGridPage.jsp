<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<template:page pageTitle="${pageTitle}">
	
	<cms:pageSlot position="Section1" var="feature">
		<cms:component component="${feature}" element="div" />
	</cms:pageSlot>
	
	<c:if test="${isGenericCategory}">
	${categoryDescription}
	</c:if>
	
	<div class="row">
			<cms:pageSlot position="ProductLeftRefinements" var="feature">
				<cms:component component="${feature}"/>
			</cms:pageSlot>

			<cms:pageSlot position="ProductGridSlot" var="feature">
				<cms:component component="${feature}"/>
			</cms:pageSlot>

	</div>
	
	<c:if test="${isGenericCategory}" >
	<cms:pageSlot position="QuesstionareAdvice" var="feature">
				<cms:component component="${feature}"/>
			</cms:pageSlot>
	</c:if>
	<c:if test="${isCartUpdated }">
	Your recommended product added to the cart.
	</c:if>
	<c:if test="${isQuestionary }">
	

		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
		</div>
		<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3"> 
		<a href="/en_GB/questionary/allSymptoms/root-category" 	class="btn btn-default btn-block mini-cart-checkout-button gray-btn-vtf">Back To Questionnaire</a>
		</div>
		
		<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">		
		<a href="/en_GB/cart" 	class="btn btn-primary btn-block mini-cart-checkout-button addtocart-btn-vtf">View Basket</a>
		</div>
	</c:if>
	
	<storepickup:pickupStorePopup />
</template:page>
