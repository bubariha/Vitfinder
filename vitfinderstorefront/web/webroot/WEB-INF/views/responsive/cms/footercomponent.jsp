<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="footer"
	tagdir="/WEB-INF/tags/responsive/common/footer"%>

<div class="container">
	<div class="row footer-one-vtf">
		<div class="row">
			<c:forEach items="${navigationNodes}" var="node">
				<div class="links">
					<c:choose>
						<c:when test="${node.uid eq 'SocialNavNode' }">
							<div class="title">${node.title}</div>
								<h1><div class="col-xs-12 " >
										<ul class="social-icon-vtf"> 
												<c:forEach items="${node.links}" var="childlink">
													<cms:component component="${childlink}"
														evaluateRestriction="true" element="li" />
												</c:forEach>
										<ul>
									</div></h1>
						</c:when>
						<c:otherwise>
						<div class="title">${node.title}</div>
							<ul>
								<c:forEach items="${node.links}" var="childlink">
									<cms:component component="${childlink}"
										evaluateRestriction="true" element="li" />
								</c:forEach>
							<ul>
						</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
	</div>
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


<div class="copyright copyright-vtf">
	<div class="container">
		<h6>${notice}.com</h6>
	</div>
</div>