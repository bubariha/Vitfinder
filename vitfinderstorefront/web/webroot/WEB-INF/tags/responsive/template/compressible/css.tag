<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--  AddOn Common CSS files --%>
<c:forEach items="${addOnCommonCssPaths}" var="addOnCommonCss">
	<link rel="stylesheet" type="text/css" media="all" href="${addOnCommonCss}"/>
</c:forEach>

<!--[if IE 9]>
<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/ie9.css"/>
<![endif]-->

<%-- Theme CSS files --%>
<link rel="stylesheet" type="text/css" media="all" href="${themeResourcePath}/css/style.css"/>

<%--  AddOn Theme CSS files --%>
<c:forEach items="${addOnThemeCssPaths}" var="addOnThemeCss">
	<link rel="stylesheet" type="text/css" media="all" href="${addOnThemeCss}"/>
</c:forEach>

<!--[if lt IE 9]>
<script src="${commonResourcePath}/js/html5shiv.min.js"></script>
<script src="${commonResourcePath}/js/respond.min.js"></script>
<![endif]-->
