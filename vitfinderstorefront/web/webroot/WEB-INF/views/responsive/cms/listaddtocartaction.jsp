<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<c:url value="/cart/add" var="addToCartUrl"/>

<product:productListerClassifications product="${product}"/>
<c:choose>
	<c:when test="${isGenericCategory and !isQuickTips}">
		<a href="${request.contextPath}/search/?q=${product.code}&sort=price-asc" class="btn btn-primary btn-block"><spring:theme code="generic.product.compare"/></a>
	</c:when>
	<c:otherwise>
		<form:form id="addToCartForm${product.code}" action="${addToCartUrl}" method="post" class="add_to_cart_form">
	
	<ycommerce:testId code="addToCartButton">
		<input type="hidden" name="productCodePost" value="${product.code}"/>
		<input type="hidden" name="productNamePost" value="${product.name}"/>
		<input type="hidden" name="productPostPrice" value="${product.price.value}"/>
		
		<button type="submit" class="btn btn-primary btn-block addtocart-btn-vtf  <c:if test="
			${product.stock.stockLevelStatus.code eq 'outOfStock' }">out-of-stock</c:if>"
				<c:if test="${product.stock.stockLevelStatus.code eq 'outOfStock' }"> disabled="disabled" aria-disabled="true"</c:if>>${addToCartText}</button>
	</ycommerce:testId>
</form:form>
	</c:otherwise>
</c:choose>



