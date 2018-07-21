<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>
<%@ taglib prefix="storepickup" tagdir="/WEB-INF/tags/responsive/storepickup" %>


<c:forEach items="${cartData.basketSorts}" var="sort">
<script type="text/javascript">
$(document).ready(function(){
	$("#sort-${sort.code}").click(function(){
		window.location.href = "<c:url value='/cart/checkout'/>?sortBean="+$("#sort-${sort.code}").val();
    });
});

</script>

<input type="checkbox" id="sort-${sort.code}" name="sortBean"  value="${sort.beanId}" ${sort.beanId eq sortOption ? 'checked':'' }  onclick="sortOrder()" />${sort.name}<br/>
</c:forEach>
