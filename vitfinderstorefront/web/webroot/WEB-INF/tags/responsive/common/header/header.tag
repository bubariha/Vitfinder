<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/responsive/nav"%>

<cms:pageSlot position="TopHeaderSlot" var="component" element="div"
	class="container">
	<cms:component component="${component}" />
</cms:pageSlot>

<header class="main-header main-header-md main-header-vtf">
	<div class="container">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-3 col-lg-3 col-fullwidth">
				<div class="site-logo site-logo-vtf">
					<cms:pageSlot position="SiteLogo" var="logo" limit="1">
						<cms:component component="${logo}" />
					</cms:pageSlot>
				</div>
			</div>

			
			<div class="col-xs-12 col-sm-12 col-md-2 pull-right col-fullwidth">
				<ul class="header_icon-vtf">

					<li class="header-search-vtf-new">
						<div class="header-search-input-vtf">
							<cms:pageSlot position="SearchBox" var="component">
								<cms:component component="${component}" />
							</cms:pageSlot>
						</div> <span class="fa fa-search header-search-icon-vtf"
						aria-hidden="true"></span>
					</li>

					<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
						<li class="liOffcanvas header-login-vtf"><ycommerce:testId
								code="header_Login_link">
								<a href="<c:url value="/login"/>"><span class="fa fa-user"
									aria-hidden="true"></span> <span class=" hidden-md hidden-lg"><spring:theme
											code="header.link.login" /></span> </a>
							</ycommerce:testId></li>
					</sec:authorize>
					<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
						<li class="liOffcanvas header-logout-vtf"><ycommerce:testId
								code="header_signOut">
								<a href="<c:url value='/logout'/>"><span
									class="fa fa-unlock-alt" aria-hidden="true"></span><span
									class=" hidden-md hidden-lg"><spring:theme
											code="header.link.logout" /> </span></a>
							</ycommerce:testId></li>
					</sec:authorize>

					<li class="header-store-vtf"><c:if
							test="${empty hideHeaderLinks}">
							<ycommerce:testId code="header_StoreFinder_link">
								<a href="<c:url value="/store-finder"/>"
									class="btn btn-default header-store-icon-vtf"> <span
									class="fa fa-star" aria-hidden="true"></span>
								</a>
							</ycommerce:testId>
						</c:if></li>

					<li id="miniCartSlot" class="header-cart-vtf"><cms:pageSlot
							position="MiniCart" var="cart" limit="1">
							<cms:component component="${cart}" />
						</cms:pageSlot></li>
				</ul>



				<div class="sign-in-vtf">
					<div class="md-secondary-navigation header-sign-icon-vtf">
						<ul>
							<c:if test="${empty hideHeaderLinks}">
								<c:if test="${uiExperienceOverride}">
									<li class="backToMobileLink"><c:url
											value="/_s/ui-experience?level=" var="backToMobileStoreUrl" />
										<a href="${backToMobileStoreUrl}"> <spring:theme
												code="text.backToMobileStore" />
									</a></li>
								</c:if>


								<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
									<c:set var="maxNumberChars" value="25" />
									<c:if test="${fn:length(user.firstName) gt maxNumberChars}">
										<c:set target="${user}" property="firstName"
											value="${fn:substring(user.firstName, 0, maxNumberChars)}..." />
									</c:if>

									<li class="logged_in"><ycommerce:testId
											code="header_LoggedUser">
											<spring:theme code="header.welcome"
												arguments="${user.firstName},${user.lastName}"
												htmlEscape="true" />
										</ycommerce:testId></li>
								</sec:authorize>

								<cms:pageSlot position="HeaderLinks" var="link">
									<cms:component component="${link}" element="li" />
								</cms:pageSlot>

							</c:if>
						</ul>
					</div>
				</div>


				<%-- a hook for the my account links in desktop/wide desktop--%>
				<div class=""
					class="hidden-xs hidden-sm col-md-12 accNavComponentDesktop collapse accNavComponentDesktop-vtf "
					id="accNavComponentDesktop">
					<ul class=""></ul>
				</div>


				<div class="sm-navigation sm-navigation-vtf">
					<div class=" visible-xs visible-sm">
						<button class="btn btn-default js-toggle-sm-navigation"
							type="button">
							<span class="fa fa-bars"></span>
						</button>
						<ycommerce:testId code="header_search_activation_button ">
							<button class="btn btn-default js-toggle-xs-search "
								type="button">
								<span class="fa fa-search"></span>
							</button>
						</ycommerce:testId>
					</div>
				</div>





			</div>
		</div>

		<div class="container">
			<div  id="mainNavVtf" class="main-navigation js-enquire-offcanvas-navigation main-navigation-vtf" role="navigation">
				<nav:topNavigation />
				</div>
		</div>

	</div>
	<a id="skiptonavigation"></a>

</header>


<cms:pageSlot position="BottomHeaderSlot" var="component">
	<cms:component component="${component}" />
</cms:pageSlot>
