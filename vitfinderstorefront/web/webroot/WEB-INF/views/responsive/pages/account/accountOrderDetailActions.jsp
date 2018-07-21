<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<spring:url value="/my-account/orders" var="orderHistoryUrl"/>

<div class="cancel-panel col-xs-12 col-sm-6 col-md-5 col-lg-4">				
	<ycommerce:testId code="orderDetails_backToOrderHistory_button">
			
			<table class="cancel-panel-vtf pull-right" >
				<tr>
					<td>
						<button type="button" class="btn btn-primary btn-block orderBackBtn addtocart-btn-vtf" data-back-to-orders="${orderHistoryUrl}">
							<spring:theme code="text.account.orderDetails.backToOrderHistory" text="Back To Order History"/>
						</button>
					</td>
					<td>&nbsp;</td>
					<td>
				 <a href="/cart/addOrder/${orderData.code}" class="btn btn-primary btn-block mini-cart-checkout-button addtocart-btn-vtf"> <spring:theme code="text.account.orderDetails.reorder"/></a>
					</td>
				</tr>
			</table>
	</ycommerce:testId>
</div>