<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<template:page pageTitle="${pageTitle}">
	<jsp:body>
		<div id="globalMessages">
			<common:globalMessages />
		</div> 
		<div> Study My Band Detils</div>
		<c:if test="${type == 'activity' }">
		<h3>Activity :</h3>
			<c:forEach var="activ" items="${activity}">
			
				<b>${activ.key} :<br /></b>
					<c:forEach var="data" items="${activ.value}">
				${data.key} : 
											${data.value}<br />
							</c:forEach>
			</c:forEach>	
		</c:if>

		<c:if test="${type == 'body' }">
		<h3>Body And Weights :</h3>
			<c:forEach var="map" items="${bodyAndWeight}">
			
				<b>${map.key} :<br /></b>
					<c:forEach var="data" items="${map.value}">
				${data.key} : 
											${data.value}<br />
							</c:forEach>
			</c:forEach>	
		</c:if>
		
			<c:if test="${type == 'food' }">
		<h3>Food :</h3>
			<c:forEach var="map" items="${food}">
			
				<b>${map.key} :<br /></b>
					<c:forEach var="data" items="${map.value}">
				${data.key} : 
											${data.value}<br />
							</c:forEach>
			</c:forEach>	
		</c:if>
				<c:if test="${type == 'friends' }">
		<h3>Friends :</h3>
		<c:if test="${friends}">
			<c:forEach var="map" items="${friends}">
			
				<b>${map.key} :<br /></b>
				<c:if test="">
					<c:forEach var="data" items="${map.value}">
				${data.key} : 
							<c:if test="${data.value}">
											${data.value}<br />
								</c:if>
							</c:forEach>
			</c:if>
			</c:forEach>	
		</c:if>
		</c:if>
		<c:if test="${type == 'heart' }">
		<h3>Heart Rate :</h3>
			<c:forEach var="map" items="${heart}">
			
				<b>${map.key} :<br /></b>
					<c:forEach var="data" items="${map.value}">
				${data.key} : 
											${data.value}<br />
							</c:forEach>
			</c:forEach>	
		</c:if>
			<c:if test="${type == 'sleep' }">
		<h3>Sleep :</h3>
			<c:forEach var="map" items="${sleep}">
			
				<b>${map.key} :<br /></b>
					<c:forEach var="data" items="${map.value}">
				${data.key} : 
											${data.value}<br />
							</c:forEach>
			</c:forEach>	
		</c:if>
				<c:if test="${type == 'subscriptions' }">
		<h3>Subscriptions :</h3>
			<c:forEach var="map" items="${subscriptions}">
			
				<b>${map.key} :<br /></b>
					<c:forEach var="data" items="${map.value}">
				${data.key} : 
											${data.value}<br />
							</c:forEach>
			</c:forEach>	
		</c:if>
		
					<c:if test="${type == 'user' }">
		<h3>User :</h3>
			<c:forEach var="map" items="${user}">
			
				<b>${map.key} :<br /></b>
					<c:forEach var="data" items="${map.value}">
				${data.key} : 
											${data.value}<br />
							</c:forEach>
			</c:forEach>	
		</c:if>
					<c:if test="${type == 'devices' }">
		<h3>Devices :</h3>
			<c:forEach var="data" items="${devices}">
			
				${data }<br/>
			</c:forEach>	
		</c:if>
		<a href="/questionary/symptoms/root-category" class="btn btn-default btn-block addtocart-btn-vtf">Health Recommendation</a>
		
		</jsp:body>
</template:page>