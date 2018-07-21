
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<template:page pageTitle="${pageTitle}">

	<ul class="condition-listing-vtf">

		<c:forEach items="${subCategories}" var="entry">
			<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
				<div class="condition-item-vtf">

					<c:set var="style" value="fa fa-shield"></c:set>

					<div class="condition-item-icon">
						<i class="${style}" aria-hidden="true"></i>
					</div>
					<a href="/c/${entry.code}?isQuickTips=true"><h4>${entry.name}</h4></a>
					<!-- <a href="/c/${entry.code}?isQuickTips=true"> <img
						src="${entry.thumbnail.URL}" /></a> -->

				</div>
			</li>
		</c:forEach>
	</ul>

</template:page>

