<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>
<%@ attribute name="pageCss" required="false" fragment="true"%>
<%@ attribute name="pageScripts" required="false" fragment="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="header"
	tagdir="/WEB-INF/tags/responsive/common/header"%>
<%@ taglib prefix="footer"
	tagdir="/WEB-INF/tags/responsive/common/footer"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart" %>

<template:master pageTitle="${pageTitle}">

	<jsp:attribute name="pageCss">
		<jsp:invoke fragment="pageCss" />
	</jsp:attribute>

	<jsp:attribute name="pageScripts">
		<jsp:invoke fragment="pageScripts" />
	</jsp:attribute>

	<jsp:body>
		<main data-currency-iso-code="${currentCurrency.isocode}">
			<spring:theme code="text.skipToContent" var="skipToContent" />
			<a href="#skip-to-content" class="skiptocontent" data-role="none">${skipToContent}</a>
			<spring:theme code="text.skipToNavigation" var="skipToNavigation" />
			<a href="#skiptonavigation" class="skiptonavigation" data-role="none">${skipToNavigation}</a>


			<header:header hideHeaderLinks="${hideHeaderLinks}" />
<%-- 
			<div class="container">
			<cms:pageSlot position="Section1" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
			</div> 
			 --%>
			
			<a id="skip-to-content"></a>
		
			<div class="container banner-fullwd-vtf">
				<common:globalMessages />
				<cart:cartRestoration />
				<jsp:doBody />

				
<%-- <div class="homepage-component-vit">
<div id="bannerCVtf" class="owl-carousel carousel js-owl-carousel js-owl-carousel-reference home-banner-carousel-vtf">
	<figure>
		<img src="${themeResourcePath}/images/banner-1.jpg" alt="" title="" class="img-responsive img-fullwidth" />
		<figcaption>Wakey <span>Wakey</span><a href="#" class="btn btn-vtf">Early riser <i class="fa fa-angle-right" aria-hidden="true"></i></a></figcaption>
	</figure>
	<figure>
		<img src="${themeResourcePath}/images/banner-2.jpg" alt="" title="" class="img-responsive img-fullwidth" />
		<figcaption>Wakey <span>Wakey</span><a href="#" class="btn btn-vtf">Early riser <i class="fa fa-angle-right" aria-hidden="true"></i></a></figcaption>
	</figure>
</div> 			
				
				
<div class="container">
<div class="row promotion-block-vtf">

<div class="col-xs-12 col-sm-12 col-md-4"> 
<a href="/questionary/allSymptoms/root-category">
<div class="row promotion-block-a-vtf">
 <h3>Personal <span> Health Check </span></h3>
 <p>A five minute questionnaire to determine the vitamins you need to stay up-to-speed</p>
 <i class="fa fa-check-square-o" aria-hidden="true"></i> <i class="fa fa-heart" aria-hidden="true"></i>
</div></a>
</div>

<div class="col-xs-12 col-sm-12 col-md-4 "> 
<a href="#">
<div class="row promotion-block-b-vtf">
 <h3>Vitamin <span> Shop </span></h3>
 <p>Got a good idea what you need? Browse the latest products and best offers</p>
 <i class="fa fa-shopping-basket" aria-hidden="true"> </i> 
</div></a>
</div>

<div class="col-xs-12 col-sm-12 col-md-4 "> 
<a href="#">
<div class="row promotion-block-c-vtf">
 <h3>Common Condition <span> Treatments</span></h3>
 <p>Obis am aut voluptatia natia dipidit officab oremquunt, volor anis rescidia con rem nobite dolorei </p>
 <i class="fa fa-medkit" aria-hidden="true"> </i> 
</div></a>
</div>

</div>

<div class="row ">

<div class="col-xs-12 col-sm-12 col-md-4 product-add-vtf">
<div class="row home-product-add-vtf">
<img src="${themeResourcePath}/images/t-shirt_vitfinder.jpg" alt="" title="" class="img-responsive img-fullwidth" /> 
<span>Free <i class="fa fa-angle-right" aria-hidden="true"></i> </span>
</div> 
</div>

<div class="col-xs-12 col-sm-12 col-md-8 home-shop-vtf"> 
<div id="homeProductVtf" class="owl-carousel carousel js-owl-carousel js-owl-carousel-reference homeProduct-carousel-vtf row">
				   <figure>
						<img src="${themeResourcePath}/images/fit-bid-product.jpg" alt="" title="" class="img-responsive" />
						<figcaption><img src="${themeResourcePath}/images/fit-bit-logo.png" alt="" title=""  /><span>Wireless Activity +<span>Sleep Wristband </span> </span>
						<a href="#" class="btn btn-vtf">Shop fitbit<i class="fa fa-angle-right" aria-hidden="true"></i></a></figcaption>
				   </figure>
				    <figure>
						<img src="${themeResourcePath}/images/fit-bid-product.jpg" alt="" title="" class="img-responsive" />
						<figcaption><img src="${themeResourcePath}/images/fit-bit-logo.png" alt="" title=""  /><span>Wireless Activity +<span>Sleep Wristband </span> </span>
						<a href="#" class="btn btn-vtf">Shop fitbit<i class="fa fa-angle-right" aria-hidden="true"></i></a></figcaption>
				   </figure>
				    <figure>
						<img src="${themeResourcePath}/images/fit-bid-product.jpg" alt="" title="" class="img-responsive" />
						<figcaption><img src="${themeResourcePath}/images/fit-bit-logo.png" alt="" title="" /><span>Wireless Activity +<span>Sleep Wristband </span> </span>
						<a href="#" class="btn btn-vtf">Shop fitbit<i class="fa fa-angle-right" aria-hidden="true"></i></a></figcaption>
				   </figure>
				</div>
</div>
 
</div>
</div>

</div>				
			</div>

 --%>			
		</main>
<footer:footer />
	</jsp:body>

</template:master>
