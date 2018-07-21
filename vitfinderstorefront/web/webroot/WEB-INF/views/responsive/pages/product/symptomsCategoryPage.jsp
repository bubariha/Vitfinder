<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>

<template:page pageTitle="${pageTitle}">

	<jsp:body>

		<div id="globalMessages">
			<common:globalMessages />
		</div>
<%-- 		<c:forEach var="symptom" items="${symptoms }"> --%>
<%-- 		<a href="/questionary/allSymptoms/${symptom.code}">${symptom.name }</a><br/> --%>
		
<%-- 		</c:forEach> --%>
		
		
		
		<ul class="condition-listing-vtf">		
<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
	<div class="condition-item-vtf">
		<div class="condition-item-icon"> <i class="fa fa-shield" aria-hidden="true"></i></div>
			<h4>Immune System</h4>
			<div class="details">
			<p>Sub Category #1 </p>
			<p>Sub Category #2 </p>
			</div>
			<a class="show-more-btn-vtf" href="#" title="#"> Show More </a>
	</div>
</li>

<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
	<div class="condition-item-vtf">
		<div class="condition-item-icon"><i class="fa fa-bed" aria-hidden="true"></i> </div>
			<h4>Sleep</h4>
			<div class="details">
			<p>Sub Category #1 </p>
			<p>Sub Category #2 </p>
			</div>
			<a class="show-more-btn-vtf" href="#" title="#"> Show More </a>
	</div>
</li>

<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
	<div class="condition-item-vtf">
		<div class="condition-item-icon"><i class="fa fa-cutlery" aria-hidden="true"></i> </div>
			<h4>Diet</h4>
			<div class="details">
			<p>Sub Category #1 </p>
			<p>Sub Category #2 </p>
			<p>Sub Category #3 </p>
			</div>
			<a class="show-more-btn-vtf" href="#" title="#"> Show More </a>
	</div>
</li>

<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
	<div class="condition-item-vtf">
		<div class="condition-item-icon"> <i class="fa fa-meh-o" aria-hidden="true"></i></div>
			<h4>Mood</h4>
			<div class="details">
			<p>Sub Category #1 </p>
			<p>Sub Category #2 </p>
			</div>
			<a class="show-more-btn-vtf" href="#" title="#"> Show More </a>
	</div>
</li>

<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
	<div class="condition-item-vtf">
		<div class="condition-item-icon"><i class="fa fa-shield" aria-hidden="true"></i> </div>
			<h4>Immune System</h4>
			<div class="details">
			<p>Sub Category #1 </p>
			<p>Sub Category #2 </p>
			</div>
			<a class="show-more-btn-vtf" href="#" title="#"> Show More </a>
	</div>
</li>

<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
	<div class="condition-item-vtf">
		<div class="condition-item-icon"><i class="fa fa-bed" aria-hidden="true"></i> </div>
			<h4>Sleep</h4>
			<div class="details">
			<p>Sub Category #1 </p>
			<p>Sub Category #2 </p>
			</div>
			<a class="show-more-btn-vtf" href="#" title="#"> Show More </a>
	</div>
</li>

<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
	<div class="condition-item-vtf">
		<div class="condition-item-icon"><i class="fa fa-cutlery" aria-hidden="true"></i> </div>
			<h4>Diet</h4>
			<div class="details">
			<p>Sub Category #1 </p>
			<p>Sub Category #2 </p>
			</div>
			<a class="show-more-btn-vtf" href="#" title="#"> Show More </a>
	</div>
</li>

<li class="col-xs-6 col-sm-6 col-md-4 col-lg-3 ">
	<div class="condition-item-vtf">
		<div class="condition-item-icon"> <i class="fa fa-meh-o" aria-hidden="true"></i></div>
			<h4>Mood</h4>
			<div class="details">
			<p>Sub Category #1 </p>
			<p>Sub Category #2 </p>
			</div>
			<a class="show-more-btn-vtf" href="#" title="#"> Show More </a>
	</div>
</li>

</ul>
		
		
		<cms:pageSlot position="Section1" var="feature">
		<cms:component component="${feature}" element="div" class=""/>
	</cms:pageSlot>

	<div class="row">
		<cms:pageSlot position="Section2A" var="feature" element="div" class="col-md-3">
			<cms:component component="${feature}"/>
		</cms:pageSlot>

		<cms:pageSlot position="Section2B" var="feature" element="div" class="col-md-9">
			<cms:component component="${feature}"/>
		</cms:pageSlot>
	</div>

	<cms:pageSlot position="Section3" var="feature" element="div" >
		<cms:component component="${feature}"/>
	</cms:pageSlot>
		<a href="/questionary/symptoms/${rootCategory.code}">Know Me Better.</a>
		</jsp:body>
		
</template:page>