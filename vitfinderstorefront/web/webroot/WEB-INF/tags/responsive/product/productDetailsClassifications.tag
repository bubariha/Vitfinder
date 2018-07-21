<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="product-classifications">
	<c:if test="${not empty product.classifications}">
		<c:forEach items="${product.classifications}" var="classification">
			<div class="headline" align="center"><h1><b>${classification.name}</b></h1></div>
				<table class="table">
					<tbody>
					<th><spring:theme code="product.nutrition.ingredient" /></th>
					<th><spring:theme code="product.nutrition.qty" /></th>
					<th><spring:theme code="product.nutrition.ri" /></th>
						<c:forEach items="${classification.features}" var="feature">
							<tr>
								<td class="attrib">${feature.name}</td>

								<td>
									<c:forEach items="${feature.featureValues}" var="value" varStatus="status">
										${value.value} 
										<c:choose>
											<c:when test="${feature.range}">
												${not status.last ? '-' : feature.featureUnit.symbol}
											</c:when>
											<c:otherwise>
												${feature.featureUnit.symbol}
												${not status.last ? '<br/>' : ''}
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</td>
								<td>
								${feature.ri}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</c:forEach>
	</c:if>
</div>