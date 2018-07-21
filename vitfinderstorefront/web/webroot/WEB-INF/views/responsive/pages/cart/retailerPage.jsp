<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="storepickup"
	tagdir="/WEB-INF/tags/responsive/storepickup"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<template:page pageTitle="${pageTitle}">

<div class="compare-retailer-checkout-vtf">
<h3>Select your products from the list</h3>
 
<div class="tabs js-tabs tabs-responsive pdp-tabs-vtf">
	
		<div class="tabhead">
			<a href="">product 1</a> <span class="glyphicon"></span>
		</div>
		<div class="tabbody"> product 1 description </div>
		
		<div class="tabhead">
			<a href="">product 2</a> <span class="glyphicon"></span>
		</div>
		<div class="tabbody"> product 2 description </div>

		<div class="tabhead">
			<a href="">product 3</a> <span class="glyphicon"></span>
		</div>
		<div class="tabbody"> product 3 description </div>

		<div class="tabhead">
			<a href="">product 4</a> <span class="glyphicon"></span>
		</div>
		<div class="tabbody"> product 4 description </div>

		<div class="tabhead">
			<a href="">product 5</a> <span class="glyphicon"></span>
		</div>
		<div class="tabbody"> product 5 description </div>
	
</div>
</div>


	<div class="prodcut-brkdown-heading-vtf ">

		Retailer Cart for ${cartData.retailer.code}
		<c:forEach items="${cartData.entries}" var="entry">
			<table class="table">
				<thead>
					<tr>
						<ycommerce:testId code="cart_product_name">
							<a href="${entry.product.merchantURL}"><div
									class="name product-name-vtf">${entry.product.name}</div></a>
						</ycommerce:testId>
					</tr>
				</thead>
			</table>
		</c:forEach>
	</div>
</template:page>
