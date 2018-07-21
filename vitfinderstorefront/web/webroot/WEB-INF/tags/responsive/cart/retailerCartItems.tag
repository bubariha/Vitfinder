<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div class="prodcut-brkdown-heading-vtf ">
	<table class="table">
		<thead>
			<tr>
				<td> Items </td>					
				<td> Description </td>					
				<td> Item Price </td>					
				<td> Quantity </td>
				<td> Total Price </td>
			</tr>
		</thead>
		<c:forEach items="${cartData.entries}" var="entry">
			<c:url value="${entry.product.url}" var="productUrl" />
				<tr class="product-item product-item-vtf">
					<td>
					<div class="thumb hidden"> <a href="${productUrl}"><product:productPrimaryImage product="${entry.product}" format="thumbnail" /></a> </div>
					 <div class="details"> 
					 <ycommerce:testId code="cart_product_name"> <a href="${productUrl}"><div class="name product-name-vtf">${entry.product.name}</div></a></ycommerce:testId>
					<div class="item-sku item-sku-vtf hidden">${entry.product.code}</div>
					 <c:if
											test="${ycommerce:doesPotentialPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
											<c:forEach items="${cartData.potentialProductPromotions}"
												var="promotion">
												<c:set var="displayed" value="false" />
												<c:forEach items="${promotion.consumedEntries}"
													var="consumedEntry">
													<c:if
														test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber && not empty promotion.description}">
														<c:set var="displayed" value="true" />
	
														<div class="promo hidden">
															<ycommerce:testId code="cart_potentialPromotion_label">
			                                             ${promotion.description}
			                                         </ycommerce:testId>
														</div>
													</c:if>
												</c:forEach>
											</c:forEach>
										</c:if>
					 <c:if test="${not empty entry.product.promotionalMessage}">
											<p>${entry.product.promotionalMessage}</p>
										</c:if>
					 <c:if
											test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
											<c:forEach items="${cartData.appliedProductPromotions}"
												var="promotion">
												<c:set var="displayed" value="false" />
												<c:forEach items="${promotion.consumedEntries}"
													var="consumedEntry">
													<c:if
														test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
														<c:set var="displayed" value="true" />
														<div class="promo">
															<ycommerce:testId code="cart_appliedPromotion_label">
		                                            ${promotion.description}
		                                        </ycommerce:testId>
														</div>
													</c:if>
												</c:forEach>
											</c:forEach>
										</c:if>
	 				<c:set var="entryStock" value="${entry.product.stock.stockLevelStatus.code}" />
	
										<c:forEach items="${entry.product.baseOptions}" var="option">
											<c:if
												test="${not empty option.selected and option.selected.url eq entry.product.url}">
												<c:forEach items="${option.selected.variantOptionQualifiers}"
													var="selectedOption">
													<div class="hidden">
														<strong>${selectedOption.name}:</strong> <span>${selectedOption.value}</span>
													</div>
													<c:set var="entryStock"
														value="${option.selected.stock.stockLevelStatus.code}" />
												</c:forEach>
											</c:if>
										</c:forEach>
	
										<div class="hidden">
											<c:choose>
												<c:when
													test="${not empty entryStock and entryStock ne 'outOfStock'}">
													<spring:theme code="basket.page.availability" />: <span
														class="stock"><spring:theme
															code="product.variants.in.stock" /></span>
												</c:when>
												<c:otherwise>
													<spring:theme code="basket.page.availability" />: <span
														class="stock"><spring:theme
															code="product.variants.out.of.stock" /></span>
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</td>
					<td>
								<c:if test="${entry.product.description ne null}">
															<c:set var="prodDesc" value="${entry.product.description}"></c:set>
															<c:set var="productURL" value="${entry.product.url}"></c:set>
															<c:choose>
																<c:when test="${fn:length(prodDesc) gt 25}">
																	<p> 
																	<c:set var="conditionVariable" value="true" />
																	<c:forEach var="i" begin="30" end="${fn:length(prodDesc)}" step="1">
																		<c:if test="${conditionVariable eq 'true'}">
																		<c:if test="${fn:substring(prodDesc,i,i+1) eq ' '}">
																			<c:out value="${fn:substring(prodDesc,0,i)}..."	escapeXml="false" /><a href="${productURL}">Read more</a>
																			<c:set var="conditionVariable" value="false" />
																		</c:if>
																		</c:if>
																	</c:forEach>														
																	</p>
																</c:when>
																<c:otherwise>
																	<p><c:out value="${prodDesc}" escapeXml="false"/><a href="${productURL}">Read more</a></p>
																</c:otherwise>
															</c:choose>
														</c:if>
								</td>
					<td>
									<span class="price price-vtf"> <ins>
											<format:price priceData="${entry.basePrice}"
												displayFreeForZero="true" />
										</ins>
									</span>
								</td>
					<td class="qty">
								<c:url value="/cart/retailer/update" var="cartUpdateFormAction" />
								<form:form id="updateCartForm${entry.entryNumber}" class="brkdown-qty-vtf"
										action="${cartUpdateFormAction}" method="post"
										commandName="updateQuantityForm${entry.entryNumber}">
										<input type="hidden" name="entryNumber"
											value="${entry.entryNumber}" />
											<input type="hidden" name="retailerCartId"
											value="${cartData.code}" />
										<input type="hidden" name="productCode"
											value="${entry.product.code}" />
										<input type="hidden" name="initialQuantity"
											value="${entry.quantity}" />
										<input type="hidden" name="productPrice"
											value="${entry.totalPrice.value}" />
										<input type="hidden" name="productName"
											value="${entry.product.name}" />
										<input type="hidden" name="cartId" value="${cartData.code}">
										<input type="hidden" name="ean" value="${entry.product.ean}">
										<ycommerce:testId code="cart_product_quantity">
											<c:set var="form"
												value="updateQuantityForm${entry.entryNumber}" />
											<form:label cssClass="" path="quantity" class="hidden"
												for="quantity${entry.entryNumber}">
												<spring:theme code="basket.page.qty" />:</form:label>
											<input type="number"
												class="form-control update-entry-quantity-input"
												name="quantity" value="${entry.quantity}"
												${not entry.updateable?' disabled':' '} />
										</ycommerce:testId>
										
									</form:form>
									</td>
					<td>
									<div class="item-price">
										<ycommerce:testId code="cart_totalProductPrice_label">
											<format:price priceData="${entry.totalPrice}"
												displayFreeForZero="true" />
										</ycommerce:testId>
									</div>
								</td>
				</tr>
		</c:forEach>
	</table>
</div>
		<!--  
		<div class="cart-product-ingredients-vtf">
			<h5>Tolerable Upper Intake Levels:</h5>
			<ul class="list-unstyled ingredients-details-vtf">
				<c:forEach items="${cartData.safeUpperLimits}" var="nutrition">

					<c:if test="${ nutrition.cartQty>nutrition.safeUpperLimit}">
						<li><span>${nutrition.ingredient.name}</span>-${ nutrition.cartQty}/${nutrition.ingredient.featureValues[0].value}</li>
					</c:if>

					


				</c:forEach>
			</ul>

		</div>-->
		
<!-- vtf-65 -->
<div class="compare-tolerable-upper-vtf">
	<a class="tolerable-upper-btn-vtf"><i class="fa fa-chevron-circle-down" aria-hidden="true"></i> Tolerable Upper Intake Levels:</a>
		
		<div class="compare-tolerable-table-vtf">
		<table class="table">
					<tbody>
					<th><spring:theme code="product.nutrition.ingredient" /></th>
					<th><spring:theme code="product.nutrition.cartqty" /></th>
					<th><spring:theme code="product.nutrition.safeUpperLimit" /></th>
						<c:forEach items="${cartData.safeUpperLimits}" var="nutrition">
							<tr>
								<td class="attrib">${nutrition.ingredient.name}</td>

								<td>
									<c:forEach items="${nutrition.ingredient.featureValues}" var="value" varStatus="status">
										
										<c:choose>
											<c:when test="${nutrition.cartQty>nutrition.ingredient.safeUpperLimit}">
											<font color="red">${nutrition.cartQty} </font>
											</c:when>
											<c:otherwise>
												${nutrition.cartQty}
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</td>
								<td>
								${nutrition.ingredient.safeUpperLimit}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
		
		</div>  
		
	</ul>


	<%-- <storepickup:pickupStorePopup /> --%>