<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<template:page pageTitle="${pageTitle}">

	<table class="table">
		<thead>
			<tr>
				<td>Symptoms</td>
				<td>Recommended Product</td>
				<td>Recommended Date</td>
			</tr>
		</thead>
		<c:forEach items="${questioanry}" var="entry">
			<tr>
				<td><p>${entry.symptoms.name}</p> <br></td>
				<td><c:forEach items="${entry.recommendedProducts}"
						var="product">
						<p>${product.name}:${product.code}</p>
					</c:forEach></td>
				<td>${entry.recommendedDate}</td>
				<td><c:set var="separator" value="-" /> <c:forEach
						items="${entry.recommendedProducts}" var="product">
						<c:set var="productCodes"
							value="${productCodes}${product.code}${separator}" />
					</c:forEach></td>
				<td>
					<a href="/questionary/removeEntry/${entry.pk}" class="btn  remove-item remove-entry-button remove-item-vtf"><span class="glyphicon glyphicon-remove"></span></a>
				</td>
				<td>
					<p>
						<form:form method="post" id="addToCartForm"
							class="add_to_cart_form" action="/cart/addAll">
							<input type="hidden" name="productCodes" value="${productCodes}" />

							<button id="addToCartButton" type="submit"
								class="btn btn-primary btn-block js-add-to-cart addtocart-vtf"
								disabled="disabled">
								<spring:theme code="quessionare.result.recompare" />
							</button>
						</form:form>
					</p>
				</td>
			</tr>
		</c:forEach>
	</table>
</template:page>

