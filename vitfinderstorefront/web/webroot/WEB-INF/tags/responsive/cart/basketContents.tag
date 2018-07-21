<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>


	<h4>Basket contents </h4>
	<ul class="basket-content-ul-vtf">
		
		<c:forEach items="${cartData.entries}" var="entry">
			<c:url value="${entry.product.url}" var="productUrl" />
					<li class="basket-colum-vtf">
						<input type="checkbox" checked="checked"/>
						<lable>${entry.product.name}</lable>
					</li>
		</c:forEach>
	</ul>


	<%-- <storepickup:pickupStorePopup /> --%>