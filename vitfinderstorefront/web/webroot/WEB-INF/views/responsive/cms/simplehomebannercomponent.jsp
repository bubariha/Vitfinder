<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<!-- 	col-xs-12 col-sm-12 col-md-4 -->
<!-- <div class="row promotion-block-vtf"> -->
<a href="${url}"><div class="col-xs-12 col-sm-12 col-md-4" style="background-color: ${component.bgColor};height: 320px;color: white">
	<div class="row promotion-block-a-vtf">
			<h3>${component.title}<br> <span>${component.subContent}</span></h3>
			<span><p>${component.content}</p></span>
			<h2><i class="${component.styleAttributes}" aria-hidden="true"></i>
			<i class="${component.styleHeartAttributes}" aria-hidden="true"></i></h2>
	</div>
</div></a>

<!-- </div> -->
