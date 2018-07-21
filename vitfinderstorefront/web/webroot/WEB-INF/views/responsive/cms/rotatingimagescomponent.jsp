<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

 <div id="bannerCVtf" class="owl-carousel carousel js-owl-carousel js-owl-carousel-reference home-banner-carousel-vtf"> 
			<c:forEach items="${banners}" var="banner" varStatus="status">
				<c:if test="${ycommerce:evaluateRestrictions(banner)}">
					<c:url value="${banner.urlLink}" var="encodedUrl" />
						<figure><c:if test="${banner.external}"> target="_blank"</c:if><img src="${banner.media.url}" alt="${not empty banner.headline ? banner.headline : banner.media.altText}" title="${not empty banner.headline ? banner.headline : banner.media.altText}" class="img-responsive img-fullwidth"/>
							<figcaption>${banner.headline} <span>${banner.headline}</span><a href="${encodedUrl}" class="btn btn-vtf">${banner.content}<i aria-hidden="true"></i></a></figcaption>
						</figure>
				</c:if>
			</c:forEach>
	</div> 

