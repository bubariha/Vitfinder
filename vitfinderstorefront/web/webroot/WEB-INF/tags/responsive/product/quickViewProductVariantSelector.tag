<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<%-- Determine if product is one of apparel style or size variant --%>
<c:if test="${product.variantType eq 'ApparelStyleVariantProduct'}">
	<c:set var="variantStyles" value="${product.variantOptions}" />
</c:if>
<c:if test="${(not empty product.baseOptions[0].options) and (product.baseOptions[0].variantType eq 'ApparelStyleVariantProduct')}">
	<c:set var="variantStyles" value="${product.baseOptions[0].options}" />
	<c:set var="variantSizes" value="${product.variantOptions}" />
	<c:set var="currentStyleUrl" value="${product.url}" />
</c:if>
<c:if test="${(not empty product.baseOptions[1].options) and (product.baseOptions[0].variantType eq 'ApparelSizeVariantProduct')}">
	<c:set var="variantStyles" value="${product.baseOptions[1].options}" />
	<c:set var="variantSizes" value="${product.baseOptions[0].options}" />
	<c:set var="currentStyleUrl" value="${product.baseOptions[1].selected.url}" />
</c:if>
<c:url value="${currentStyleUrl}" var="currentStyledProductUrl"/>
<%-- Determine if product is other variant --%>

<c:if test="${empty variantStyles}">
	<c:if test="${not empty product.variantOptions}">
		<c:set var="variantOptions" value="${product.variantOptions}" />
	</c:if>
	<c:if test="${not empty product.baseOptions[0].options}">
		<c:set  var="variantOptions" value="${product.baseOptions[0].options}" />
	</c:if>
</c:if>

<c:if test="${not empty variantStyles or not empty variantSizes}">
	<c:choose>
		<c:when test="${product.purchasable and product.stock.stockLevelStatus.code ne 'outOfStock' }">
			<c:set var="quickViewShowAddToCart"  value="${true}" scope="request" />
		</c:when>
		<c:otherwise>
			<c:set var="quickViewShowAddToCart" value="${false}" scope="request" />
		</c:otherwise>
	</c:choose>
	<c:if test="${not empty variantSizes}">						
		<c:forEach items="${variantSizes}" var="variantSize">
			<c:if test="${(variantSize.url eq product.url)}">
				<c:set var="quickViewShowAddToCart" value="${true}" scope="request" />
			</c:if>
		</c:forEach>
	</c:if>
</c:if>

<c:if test="${not empty variantOptions}">
<c:choose>

		<c:when test="${product.purchasable and product.stock.stockLevelStatus.code ne 'outOfStock' }">
			<c:set var="quickViewShowAddToCart"  value="${true}" scope="request"/>
		</c:when>
		<c:otherwise>
			<c:set var="quickViewShowAddToCart" value="${false}" scope="request" />
		</c:otherwise>
	</c:choose>
<div class="variant-section variant-section-vtf">
		<div class="variant-selector">
			<div class="variant-name">
				<label for="Type"><spring:theme code="product.variants.type"/><span class="variant-selected typeName"></span></label>
			</div>
			<select id="variant" class="form-control variant-select" disabled="disabled">
				<option selected="selected" disabled="disabled"><spring:theme code="product.variants.select.variant"/></option>
				<c:forEach items="${variantOptions}" var="variantOption">
					<c:set var="optionsString" value="" />
					<c:set var="nameString" value="" />
					<c:forEach items="${variantOption.variantOptionQualifiers}" var="variantOptionQualifier">
						<c:set var="optionsString">${optionsString}&nbsp;${variantOptionQualifier.name}&nbsp;${variantOptionQualifier.value}, </c:set>
						<c:set var="nameString">${variantOptionQualifier.value}</c:set>
					</c:forEach>

					<c:if test="${(variantOption.stock.stockLevel gt 0) and (variantSize.stock.stockLevelStatus ne 'outOfStock')}">
						<c:set var="stockLevel">${variantOption.stock.stockLevel} <spring:theme code="product.variants.in.stock"/></c:set>
					</c:if>
					<c:if test="${(variantOption.stock.stockLevel le 0) and (variantSize.stock.stockLevelStatus eq 'inStock')}">
						<c:set var="stockLevel"><spring:theme code="product.variants.available"/></c:set>
					</c:if>
					<c:if test="${(variantOption.stock.stockLevel le 0) and (variantSize.stock.stockLevelStatus ne 'inStock')}">
						<c:set var="stockLevel"><spring:theme code="product.variants.out.of.stock"/></c:set>
					</c:if>
					<c:choose>
						<c:when test="${product.purchasable and product.stock.stockLevelStatus.code ne 'outOfStock' }">
							<c:set var="showAddToCart"  value="${true}" scope="session"/>
						</c:when>
						<c:otherwise>
							<c:set var="showAddToCart" value="${false}" scope="session" />
						</c:otherwise>
					</c:choose>
					<c:url value="${variantOption.url}" var="variantOptionUrl"/>
					<c:if test="${(variantOption.url eq product.url)}">
						<c:set var="showAddToCart" value="${true}" scope="session" />
						<c:set var="currentSize" value="${nameString}" />
					</c:if>
					<option value="${variantOptionUrl}" ${(variantOption.url eq product.url) ? 'selected="selected"' : ''}>
						<span class="variant-selected">${optionsString}&nbsp;<format:price priceData="${variantOption.priceData}"/>&nbsp;&nbsp;${variantOption.stock.stockLevel}</span>
					</option>
				</c:forEach>
			</select>
			<div id="currentTypeValue" data-type-value="${currentSize}" ></div>
		
		</div>
		
	</div>
	
</c:if>
