<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/responsive/cart"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<template:page pageTitle="${pageTitle}">


	<cart:cartValidation />
	<cart:cartPickupValidation />
	
	<div class="login-advice">
		<a href="/login">Please click here to create account</a>
	</div>
	
	<div class="row">
		<div class="col-md-8 comparison-page-heading-vtf">
			<h1>Basket comparison</h1>
			<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p>
		</div>
		
		<div class="col-md-4 hidden">
			<div class="basket-contents-vtf">
				<cart:basketContents cartData="${cartData}" />
			</div>
		</div>
		
		<div class="col-md-4">
			<div class="filter-contents-vtf">
				<h4>Filter Result</h4>
				<cart:basketFilters cartData="${cartData }"/>
			</div>
			
		</div>
	</div>
	
	
	<div class="cart-top-bar cart-top-bar-vtf hidden">

		<div class="text-right">
			<a href="" class="help js-cart-help"
				data-help="<spring:theme code="text.help" />"><spring:theme
					code="text.help" text="Help" /> <span
				class="glyphicon glyphicon-info-sign"></span> </a>
			<div class="help-popup-content-holder js-help-popup-content">
				<div class="help-popup-content">
					<strong>${retailerBasket.code}</strong>
					<spring:theme code="basket.page.cartHelpContent"
						text="Need Help? Contact us or call Customer Service at 1-###-###-####. If you are calling regarding your shopping cart, please reference the Shopping Cart ID above." />
				</div>
			</div>
		</div>
	</div>
	<div class="table-responsive comparison-table-vtf">
		<table class="table">
			<thead>
				<tr class="compare-border-top-vtf compare-border-right-vtf compare-border-left-vtf">
					<th>SUPPLIER</th>
					<th>ITEMS</th>
					<th>SAVINGS</th>
					<th>DELIVERY</th>
					<th>TOTAL PRICE</th>
					<th> </th>
				</tr>
			</thead>
			<c:forEach items="${cartData.retailerBaskets}" var="retailerBasket">
			
				<c:if test="${not empty retailerBasket.entries}">
						<script type="text/javascript" >
						$(document).ready(function(){
							// compare page breakdown
							
							
							$("#deliveryCostLightbox${retailerBasket.retailer.code}").click(function() {
								var retailername = $('#retailername${retailerBasket.retailer.code}').val();
								$.ajax({
									url : '/cart/deliveryCosts',
									type : "POST",
									data : {retailerName:retailername},
									title: 'hello',
									success : function(data) {
										var titleHeader = $('#showDeliveryCosts${retailerBasket.retailer.code}').html();
										ACC.colorbox.open(titleHeader,{
											html : data,
											width : 650,
											height : 700,
										});
													
									}
								});
							});
							
							
							
                            if(${retailerCartId != retailerBasket.code}){
                                $("#retailerBasket${retailerBasket.code}").fadeToggle();
                            }

						    $("#basketView${retailerBasket.code}").click(function(){
						    	$('.brkdown-arrow${retailerBasket.code}').toggleClass('fa-up');
						        $("#retailerBasket${retailerBasket.code}").slideToggle("slow");
						    });
						    $("#retailerViewAll${retailerBasket.code}").click(function(){
						    	$('.product-breakdown-button-vtf .brkdown-arrow${retailerBasket.code} .fa').toggleClass('fa-up');
						    	$('.brkdown-arrow${retailerBasket.code}').toggleClass('fa-up');
						    	$("#retailerBasket${retailerBasket.code}").slideToggle("slow");
						    });
						    $("#retailerBasket${retailerBasket.code} .tolerable-upper-btn-vtf").click(function(){
						    	$('#retailerBasket${retailerBasket.code} .tolerable-upper-btn-vtf i').toggleClass('fa-up');
						    	$("#retailerBasket${retailerBasket.code} .compare-tolerable-table-vtf").slideToggle("slow");
						    });
						});
						</script>
				<c:url value="/cart/checkout" var="checkoutUrl" scope="session" />
							<c:url value="${continueUrl}" var="continueShoppingUrl"
								scope="session" />

							<c:set var="showTax" value="false" />
				<tr
					class="compare-border-top-vtf compare-border-right-vtf compare-border-left-vtf">
						<td class="comparison-retailer-vtf">
								<p>
									<c:choose>
										<c:when
											test="${retailerBasket.retailer.thumbnail!=null && retailerBasket.retailer.thumbnail.url!=null}">
											<img src="${retailerBasket.retailer.thumbnail.url}"
												alt="${alt}" title="${title}" class="img-responsive" />
										</c:when>
										<c:otherwise>
									         Retailer - ${retailerBasket.retailer.name}
										</c:otherwise>
									</c:choose>
								</p>
							</td>
							<td class="comparison-item-vtf">
								<p>
									<c:choose>
										<c:when test="${fn:length(retailerBasket.entries) > 1}">
											<spring:theme code="retailer.basket.page.totals.total.items"
												text="${fn:length(retailerBasket.entries)} "
												arguments="${fn:length(retailerBasket.entries)}" />
										</c:when>
										<c:otherwise>
											<spring:theme
												code="retailer.basket.page.totals.total.items.one"
												text="${fn:length(retailerBasket.entries)} "
												arguments="${fn:length(retailerBasket.entries)}" />
										</c:otherwise>
									</c:choose>
									<br /><a id="retailerViewAll${retailerBasket.code}" class="comparison-item-view-all-vtf">view
										all</a>
								</p>
							</td>
							<td class="comparison-saving-vtf"><p>
									<c:choose>
										<c:when test="${retailerBasket.totalDiscounts.value > 0}">
										${retailerBasket.totalDiscounts.formattedValue}
									</c:when>
										<c:otherwise>
											-
									</c:otherwise>
									</c:choose>
								</p>
							</td>

							<td class="comparison-delivery-vtf">
									<div class="comparison-delivery-btn" id="deliveryCostPopup">
							<input type="hidden" id="retailername${retailerBasket.retailer.code}" name="retailerName" value="${retailerBasket.retailer.code}"/>
								<div id="showDeliveryCosts${retailerBasket.retailer.code}" style="display: none">
									<spring:theme code="text.checkout.deliverycosts.title" />
								</div>
								<button class="btn btn-primary btn-block addtocart-btn-vtf"
									id="deliveryCostLightbox${retailerBasket.retailer.code}" onclick="javascript://">
									<spring:theme code="text.checkout.deliverycosts" />
								</button>
							</div>
							</td>

							<td class="comparison-total-price-vtf">
								<p>
									<ycommerce:testId code="cart_totalPrice_label">
										<c:choose>
											<c:when test="${showTax}">
												<format:price
													priceData="${retailerBasket.totalPriceWithTax}" />
											</c:when>
											<c:otherwise>
												<format:price priceData="${retailerBasket.totalPrice}" />
											</c:otherwise>
										</c:choose>
									</ycommerce:testId>		
								</p>					
							</td>							
							<td class="comparison-checkout-vtf">
								<p>
									<a href="/en_GB/cart/basketSummary/${retailerBasket.code}"
										class="btn btn-primary btn-block mini-cart-checkout-button addtocart-btn-vtf">
										<spring:theme code="checkout.checkout" />
									</a>
								</p>
							</td>
				</tr>

				<tr class="compare-border-right-vtf compare-border-left-vtf">

					<td colspan="6" align="center"  class="product-breakdown-button-vtf">
						<a id="basketView${retailerBasket.code}" ><i class="brkdown-arrow${retailerBasket.code} fa fa-chevron-circle-down" aria-hidden="true"></i> PRODUCT BREAKDOWN </a>

					</td>
				</tr>
				
				<tr
					class="compare-border-right-vtf compare-border-left-vtf compare-border-bottom-vtf">
		<td colspan="6" class="comparison-breakdown-detail"><div id="retailerBasket${retailerBasket.code}"><cart:retailerCartItems
							cartData="${retailerBasket}" /></div>
					</td>
				</tr> 
				<tr class="compare-blank-tr-vtf">
					<td> </td>
					<td> </td>
					<td> </td>
					<td> </td>
					<td> </td>
					<td> </td>
				 </tr>
				</c:if>

			</c:forEach>
		</table>
		</div>
</template:page>