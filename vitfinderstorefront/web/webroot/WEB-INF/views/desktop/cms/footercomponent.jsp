<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/responsive/common/footer"  %>

<div class="container">
	<div class="row footer-one-vtf">
<!-- 		<div class="col-xs-12 col-sm-3 col-md-3" > -->
<!-- 		<h3>Customer Service</h3>	 -->
<!-- 		<ul> -->
<!-- 		<li> <a href="my-account">My Account</a> </li> -->
<!-- 		<li> <a href="#">Track Order</a></li> -->
<!-- 		<li> <a href="#">Resolution Center </a></li> -->
<!-- 		<li> <a href="#">Site Facebook</a></li> -->
<!-- 		<li> <a href="#">Frequently Asked Question</a></li> -->
<!-- 		</ul>	 -->
<!-- 		</div> -->
		
<!-- 		<div class="col-xs-12 col-sm-3 col-md-3" > -->
<!-- 		<h3>Get To Know Us</h3>	 -->
<!-- 		<ul> -->
<!-- 		<li> <a href="#">Careers</a> </li> -->
<!-- 		<li> <a href="#">About Us</a></li> -->
<!-- 		<li> <a href="#">Our Blog</a></li> -->
<!-- 		</ul>	 -->
<!-- 		</div> -->
		
<!-- 		<div class="col-xs-12 col-sm-3 col-md-3" > -->
<!-- 		<h3>Terms and Conditions</h3>	 -->
<!-- 		<ul> -->
<!-- 		<li> <a href="#">Services</a> </li> -->
<!-- 		<li> <a href="#">The Brand</a></li> -->
<!-- 		</ul>	 -->
<!-- 		</div> -->
		
		
	
	<div class="row" >
		<c:forEach items="${navigationNodes}" var="node">
					<div class="links">
							<div class="title">${node.title}</div>
							<ul>
							<c:forEach items="${node.links}" var="childlink">
								<cms:component component="${childlink}" evaluateRestriction="true" element="li" />
							</c:forEach>
							<ul>
					</div>
				</c:forEach>
				
				
	
	
	</div>
</div>

	<c:if test="${showLanguageCurrency}">
		<div class="pull-right">
			<footer:languageSelector languages="${languages}"
				currentLanguage="${currentLanguage}" />
			<footer:currencySelector currencies="${currencies}"
				currentCurrency="${currentCurrency}" />
		</div>
	</c:if>

</div>

<div class="copyright copyright-vtf">
	<div class="container"><h6>${notice}.com</h6></div>
</div>