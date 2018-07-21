<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>


<div id="addToCartTitle" style="display:none"><spring:theme code="basket.added.to.basket"/></div>

<c:choose>
		<c:when test="${not empty orderData.code}">
			<c:url value="/cart/addOrder" var="addToCartUrl"/>
		</c:when>
		<c:otherwise>
			<c:url value="${url}" var="addToCartUrl"/>
		</c:otherwise>
</c:choose>

<form:form method="post" id="addToCartForm" class="add_to_cart_form" action="${addToCartUrl}">
<c:if test="${product.purchasable}">
	<input type="hidden" maxlength="3" size="1" id="qty" name="qty" id="addToCartQty" class="qty js-qty-selector-input" value="1">
</c:if>

<c:if test="${empty showAddToCart ? true : showAddToCart}">
	
	<c:set var="buttonType">submit</c:set>
	<c:choose>
		<c:when test="${not empty orderData.code}">
		<input type="hidden" name="orderCodePost" value="${orderData.code}"/>
		<ycommerce:testId code="reOrderButton">
			<div class="col-sm-6 col-md-6">	
				<button id="reOrderButton" type="${buttonType}" class="btn btn-primary btn-block js-add-to-cart addtocart-vtf" disabled="disabled" >
					<spring:theme code="text.account.orderDetails.reorder"/>
				</button>
			</div>
			</ycommerce:testId>
		</c:when>
		<c:otherwise>
		<input type="hidden" name="productCodePost" value="${product.code}"/>
			<ycommerce:testId code="addToCartButton">
				<div class="col-sm-6 col-md-6">
					<button id="addToCartButton" type="${buttonType}" class="btn btn-primary btn-block js-add-to-cart addtocart-vtf" disabled="disabled" >
						<spring:theme code="basket.add.to.basket"/>
					</button>
				</div>
			</ycommerce:testId>
		</c:otherwise>
	</c:choose>
</c:if>
</form:form>

