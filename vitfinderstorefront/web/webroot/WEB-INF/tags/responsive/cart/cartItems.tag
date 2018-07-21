<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>

<ul class="cart-list cart-list-vtf">
    <c:forEach items="${cartData.entries}" var="entry">
        <c:url value="${entry.product.url}" var="productUrl"/>
    
        <li class="product-item product-item-vtf">
            <c:if test="${entry.updateable}" >
                <ycommerce:testId code="cart_product_removeProduct">
		            <button class="btn  remove-item remove-entry-button remove-item-vtf" id="removeEntry_${entry.entryNumber}">
		                <span class="glyphicon glyphicon-remove"></span>
		            </button>
                </ycommerce:testId>
            </c:if>

            <div class="row">
                <div class="col-md-6">
                    <div class="thumb">
                        <a href="${productUrl}"><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></a>
                    </div>
			        
                    <div class="details">                   
                        <ycommerce:testId code="cart_product_name">
                            <a href="${productUrl}"><div class="name product-name-vtf">${entry.product.name}</div></a>
                        </ycommerce:testId>
                        <div class="item-sku item-sku-vtf">${entry.product.code}</div>
                        <c:if test="${ycommerce:doesPotentialPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
	                        <c:forEach items="${cartData.potentialProductPromotions}" var="promotion">
	                            <c:set var="displayed" value="false"/>
	                            <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
	                                <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber && not empty promotion.description}">
	                                    <c:set var="displayed" value="true"/>
	                                    
	                                        <div class="promo">
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
                        <c:if test="${ycommerce:doesAppliedPromotionExistForOrderEntry(cartData, entry.entryNumber)}">
	                        <c:forEach items="${cartData.appliedProductPromotions}" var="promotion">
	                            <c:set var="displayed" value="false"/>
	                            <c:forEach items="${promotion.consumedEntries}" var="consumedEntry">
	                                <c:if test="${not displayed && consumedEntry.orderEntryNumber == entry.entryNumber}">
	                                    <c:set var="displayed" value="true"/>
	                                    <div class="promo">
	                                        <ycommerce:testId code="cart_appliedPromotion_label">
	                                            ${promotion.description}
	                                        </ycommerce:testId>
	                                    </div>
	                                </c:if>
	                            </c:forEach>
	                        </c:forEach>
                        </c:if>
                        
		                <c:set var="entryStock" value="${entry.product.stock.stockLevelStatus.code}"/>
		                        
		                <c:forEach items="${entry.product.baseOptions}" var="option">
		                    <c:if test="${not empty option.selected and option.selected.url eq entry.product.url}">
		                        <c:forEach items="${option.selected.variantOptionQualifiers}" var="selectedOption">
		                            <div>
		                                <strong>${selectedOption.name}:</strong>
		                                <span>${selectedOption.value}</span>
		                            </div>
		                            <c:set var="entryStock" value="${option.selected.stock.stockLevelStatus.code}"/>
		                        </c:forEach>
		                    </c:if>
		                </c:forEach>
                        
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
                        <div>
						    <spring:theme code="basket.page.itemPrice"/>: 
                            <span class="price price-vtf">
                                <ins>
                                    <format:price priceData="${entry.basePrice}" displayFreeForZero="true"/>
                                </ins>
                            </span>
                        </div>
						
                        <div class="qty">
                            <c:url value="/cart/update" var="cartUpdateFormAction" />
                            <form:form id="updateCartForm${entry.entryNumber}" action="${cartUpdateFormAction}" method="post" commandName="updateQuantityForm${entry.entryNumber}">
                                <input type="hidden" name="entryNumber" value="${entry.entryNumber}"/>
                                <input type="hidden" name="productCode" value="${entry.product.code}"/>
                                <input type="hidden" name="initialQuantity" value="${entry.quantity}"/>
                                <input type="hidden" name="productPrice" value="${entry.totalPrice.value}"/>
                                <input type="hidden" name="productName" value="${entry.product.name}"/>
                                <input type="hidden" name="cartId" value="${cartData.code}">
                                <input type="hidden" name="ean" value="${entry.product.ean}">
                                <ycommerce:testId code="cart_product_quantity">
                                <c:set var="form"  value="updateQuantityForm${entry.entryNumber}"/>
                                    <form:label cssClass="" path="quantity" for="quantity${entry.entryNumber}"><spring:theme code="basket.page.qty"/>:</form:label>
                               		<input type="number" class="form-control update-entry-quantity-input" name="quantity"  value="${entry.quantity}" ${not entry.updateable?' disabled':' '}/>
                                </ycommerce:testId>
                            </form:form> 

                            <div class="item-price">
                                <ycommerce:testId code="cart_totalProductPrice_label">
                                    <format:price priceData="${entry.totalPrice}" displayFreeForZero="true"/>
                                </ycommerce:testId>
                            </div>
                        </div>
                    </div>
                </div>
              
                <div class="col-md-6">
                    <div class="pickup pickup-shiping-vtf">
                        <c:choose>
                             <c:when test="${entry.product.purchasable}">
                             	<div class="radio-column">
                           			<c:if test="${not empty entryStock and entryStock ne 'outOfStock'}">
                                        <c:if test="${entry.deliveryPointOfService eq null or not entry.product.availableForPickup}">
									   		<label for="pick0_${entry.entryNumber}">
									   		<span class="glyphicon glyphicon-gift text-gray"></span> 
									   		<span class="name"><spring:theme code="basket.page.shipping.ship"/></span>
									   		</label>
							    		</c:if>
									</c:if>
								    <c:if test="${not empty entry.deliveryPointOfService.name}">
                                        <label for="pick1_${entry.entryNumber}"> 
                                            <span class="glyphicon glyphicon-home"></span> 
                                            <span class="name"><spring:theme code="basket.page.shipping.pickup"/></span>
                                        </label>
								    </c:if>
                                </div>                
                               
                                <div class="store-column">
                                    <c:choose>
                                        <c:when test="${entry.product.availableForPickup}">
                                            <c:choose>
                                             <c:when test="${not empty entry.deliveryPointOfService.name}">
                                                <div class="store-name">${entry.deliveryPointOfService.name}</div>
                                             </c:when>
                                             <c:otherwise>
                                                 <div class="store-name"></div>
                                             </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
        </li>
    </c:forEach>
     
</ul>

<!--  
<div class="cart-product-ingredients-vtf">
                                <h5> Tolerable Upper Intake Levels: </h5>
	                                <ul class="list-unstyled ingredients-details-vtf">
	                                <c:forEach items="${cartData.safeUpperLimits}" var="nutrition">
	                                
	                        			 <c:if test="${ nutrition.cartQty>nutrition.safeUpperLimit}">
	                        			  <li><span>${nutrition.ingredient.name}</span>-${ nutrition.cartQty}/${nutrition.ingredient.featureValues[0].value}</li>
	                        			 </c:if>
	                                
	                                </c:forEach>
	                                </ul>
                               
                                </div>
-->

<!-- vtf-65 -->
<div class="cart-product-ingredients-vtf">
		<h5>Tolerable Upper Intake Levels:</h5>
		
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
<storepickup:pickupStorePopup />
