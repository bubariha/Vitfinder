<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<template:page pageTitle="${pageTitle}">
	<ul class="condition-listing-vtf">
		<c:forEach var="category" items="${conditions}">
			<div id="condition-${category.code}">
				<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
					<div class="condition-item-vtf">
						<c:set var="style" value="fa fa-shield"></c:set>
						<div class="condition-item-icon">
							<i class="${style}" aria-hidden="true"></i>
						</div>
						<a href="/en_GB/questionary/condition/${category.code}"><h4>${category.name}</h4></a>
						<h6>Show More</h6>
					</div>
				</li>
			</div>
		</c:forEach>
	</ul>
</template:page>