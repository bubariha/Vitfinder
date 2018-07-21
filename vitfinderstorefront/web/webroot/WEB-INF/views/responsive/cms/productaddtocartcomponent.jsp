<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="action" tagdir="/WEB-INF/tags/responsive/action"%>

<div class="addtocart-component addtocart-component-vtf ">

	<c:if test="${empty showAddToCart ? true : showAddToCart}">
		<div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
			<div class="row">
			<div
				class="qty-selector input-group js-qty-selector qty-selector-vtf">
				<span class="input-group-btn">
					<button
						class="btn btn-primary js-qty-selector-minus qty-selector-minus-vtf "
						type="button">
						<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
					</button>
				</span> <input type="number" maxlength="3"
					class="form-control js-qty-selector-input" size="1" value='1'
					data-max="${product.stock.stockLevel}" data-min="1" name="qty"
					id="pdpAddtoCartInput" disabled /> <span class="input-group-btn">
					<button
						class="btn btn-primary js-qty-selector-plus qty-selector-plus-vtf"
						type="button">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					</button>
				</span>
			</div>
		</div>
		</div>
	</c:if>
	
	<div class="col-xs-12 col-sm-8 col-md-9 col-lg-10 pdp_addtocart-sec-vtf">
		<div class="row">
			<action:actions element="div" parentComponent="${component}" />
		</div>
	</div>
	
	
	
	<c:if test="${product.stock.stockLevel gt 0}">
		<div class="col-xs-12 col-md-4 hidden product-stock-vtf">
			<div class="row">
				<c:set var="productStockLevel">${product.stock.stockLevel}&nbsp;
						<spring:theme code="product.variants.in.stock" />
				</c:set>
			</div>	
		</div>
	</c:if>
	<c:if test="${product.stock.stockLevelStatus.code eq 'lowStock'}">
		<div class="col-xs-12 col-md-4 hidden product-stock-vtf">
			<div class="row">
				<c:set var="productStockLevel">
					<spring:theme code="product.variants.only.left"
						arguments="${product.stock.stockLevel}" />
				</c:set>
			</div>
		</div>
	</c:if>
	<c:if
		test="${product.stock.stockLevelStatus.code eq 'inStock' and empty product.stock.stockLevel}">
		<div class="col-xs-12 col-md-4 hidden product-stock-vtf">
			<div class="row">
				<c:set var="productStockLevel">
					<spring:theme code="product.variants.available" />
				</c:set>
			</div>
		</div>
	</c:if>
	<c:if test="${product.stock.stockLevelStatus.code eq 'outOfStock'}">
		<!-- vtf 89 -->
		<div class="col-xs-12 col-md-4 hidden product-stock-vtf">
			<div class="row">
				<c:set var="productStockLevel">
					<spring:theme code="product.out.of.stock" />
				</c:set>
			</div>
		</div>
	</c:if>
	<div class="col-xs-12 col-md-4 hidden product-stock-vtf ">
			<div class="row">
				<div class="stock-status">${productStockLevel}</div>
			</div>
	</div>
	
	<div class="col-xs-12 col-md-8 pdp-share-btn-vtf">
		<div class="row">
		<!-- Lockerz Share BEGIN -->
			<div class="a2a_kit a2a_default_style LoveShare span-10 last clearfix">
				<a class="a2a_dd share" href="https://www.addtoany.com/share_save" title="<spring:theme code="product.share.viewMoreServices"/>" >
					<spring:theme code="product.share.share" text="Share" />
				</a>
			</div>
			<script type="text/javascript" src="https://static.addtoany.com/menu/page.js"></script>
		<!-- Lockerz Share END -->
		</div>	
	</div>

</div>
