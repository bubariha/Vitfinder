<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:url value="${urlLink}" var="encodedUrl" />


<c:choose>
	<c:when test="${empty encodedUrl || encodedUrl eq '#'}">
		<img title="${media.altText}" alt="${media.altText}"
			src="${media.url}" class="img-responsiv">
	</c:when>

	<c:when test="${not empty subContent}">
		<figure>
			<div class="col-xs-12 col-sm-12 col-md-4 product-add-vtf">
				<div class="row home-product-add-vtf">
					<img title="${media.altText}" alt="${media.altText}"
						src="${media.url}" class="img-responsive img-fullwidth">
					<figcaption>
						<a href="${encodedUrl}"><span>${subContent}<i
								class="fa fa-angle-right" aria-hidden="true"></i>
						</span></a>
					</figcaption>
				</div>
			</div>
		</figure>
	</c:when>

	<c:otherwise>
		<a href="${encodedUrl}"><img title="${media.altText}"
			alt="${media.altText}" src="${media.url}"></a>
	</c:otherwise>
</c:choose>

