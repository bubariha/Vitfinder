<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<div class="container">
<footer class="main-footer main-footer-vtf">

	<cms:pageSlot position="Footer" var="feature">
		 <cms:component component="${feature}"/>
	</cms:pageSlot>
</footer>
</div>
