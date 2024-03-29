<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${component.visible}">
	<div class="container">
		<nav id="mainNavVtf" class="main-navigation js-enquire-offcanvas-navigation main-navigation-vtf"
			role="navigation">
			<ul class="nav nav-pills">
				<%--<li class="hidden-md hidden-lg">
					<a class="sm-back js-toggle-sm-navigation" href="#">Back</a>
				</li>--%>
				<c:forEach items="${components}" var="component">
					<c:if test="${component.navigationNode.visible}">
						<cms:component component="${component}" evaluateRestriction="true"
							navigationType="offcanvas" />
					</c:if>
				</c:forEach>
				
			</ul>
		</nav>
		
		
		
	</div>
</c:if>