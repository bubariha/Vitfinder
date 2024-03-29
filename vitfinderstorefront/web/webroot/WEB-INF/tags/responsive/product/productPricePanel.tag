<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>

<div class="price">
<c:choose>
	<c:when test="${empty product.volumePrices}">
		<c:choose>
			<c:when test="${ not empty product.price.discountValue}">
				<br>Was  <s>${product.price.formattedWasPrice}</s>
				<br>Now  <format:fromPrice priceData="${product.price}" />
				<br><span class="saveValue"><span>Save  ${product.price.formattedSavePrice}</span></span>
				<br><span class="numberCircle"><span>${product.price.discountValue}</span></span> 
			</c:when>
			<c:otherwise>
				<format:fromPrice priceData="${product.price}" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<table class="volume-prices" cellpadding="0" cellspacing="0" border="0">
			<thead>
					<th class="volume-prices-quantity"><spring:theme code="product.volumePrices.column.qa"/></th>
					<th class="volume-price-amount"><spring:theme code="product.volumePrices.column.price"/></th>
			</thead>
			<tbody>
				<c:forEach var="volPrice" items="${product.volumePrices}">
					<tr>
						<td class="volume-price-quantity">
							<c:choose>
								<c:when test="${empty volPrice.maxQuantity}">
									${volPrice.minQuantity}+
								</c:when>
								<c:otherwise>
									${volPrice.minQuantity}-${volPrice.maxQuantity}
								</c:otherwise>
							</c:choose>
						</td>
						<td class="volume-price-amount">${volPrice.formattedValue}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:otherwise>
</c:choose>

<c:set var="entryStock" value="${product.stock.stockLevelStatus.code}"/>
<div>
	<c:choose>
    	<c:when test="${not empty entryStock and entryStock eq 'inStock'}">
        	<spring:theme code="basket.page.availability"/>: <span class="stock"><spring:theme code="product.variants.in.stock"/></span>
        </c:when>
        <c:when test="${not empty entryStock and entryStock eq 'lowStock'}">
        	<spring:theme code="basket.page.availability"/>: <span class="stock"><spring:theme code="product.variants.low.stock"/></span>
        </c:when>
        <c:otherwise>
 	       <spring:theme code="basket.page.availability"/>: <span class="stock"><spring:theme code="product.variants.out.of.stock"/></span>
    	</c:otherwise>
    </c:choose>
</div>
</div>