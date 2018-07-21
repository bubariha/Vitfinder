<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
	<div class="col-md-6 col-lg-6">
		<product:productImagePanel galleryImages="${galleryImages}"
			product="${product}" />
	</div>
	<div class="col-md-6 col-lg-6">
		<div class="row">

			<div class="col-md-12">
				<div class="product-details product-details-vtf">
					<ycommerce:testId
						code="productDetails_productNamePrice_label_${product.code}">
						<div class="name product-name-vtf">${product.name}
							<span class="sku sku-id-vtf">ID ${product.code}</span>
						</div>
					</ycommerce:testId>
					<product:productReviewSummary product="${product}" showLinks="true" />

				</div>
			</div>

			<div class="col-md-12">
				<div class="product-details product-details-price-vtf">
					<product:productPromotionSection product="${product}" />

					<ycommerce:testId
						code="productDetails_productNamePrice_label_${product.code}">
						<product:productPricePanel product="${product}" />
					</ycommerce:testId>

					<div class="description">${product.summary}</div>
				</div>
			</div>

			<div class="col-md-12">
				<c:if test="${product.retailer ne null}">
					<a href="${product.merchantURL}" target="_blank"
						class="btn btn-default btn-block mini-cart-checkout-button gray-btn-vtf open-in-myprotein-vtf">
						<spring:theme code="retailer.checkout"
							arguments="${product.retailer}" />
					</a>
				</c:if>
				<cms:pageSlot position="VariantSelector" var="component">
					<cms:component component="${component}" />
				</cms:pageSlot>

				<cms:pageSlot position="AddToCart" var="component">
					<cms:component component="${component}" />
				</cms:pageSlot>

			</div>
		</div>
	</div>
</div>

