<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true" type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<c:set var="hideDescription" value="checkout.login.loginAndCheckout" />

<div class="headline">
	<spring:theme code="login.title" />
</div>

<c:if test="${actionNameKey ne hideDescription}">
	<p>
		<spring:theme code="login.description" />
	</p>
</c:if>

<form:form action="${action}" method="post" commandName="loginForm">
	<c:if test="${not empty message}">
		<span class="has-error"> <spring:theme code="${message}" />
		</span>
	</c:if>	
	
		<formElement:formInputBox idKey="j_username" labelKey="login.email"
			path="j_username" mandatory="true" />
		<formElement:formPasswordBox idKey="j_password"
			labelKey="login.password" path="j_password" inputCSS="form-control"
			mandatory="true" />
	
			<div class="forgotten-password">
				<ycommerce:testId code="login_forgotPassword_link">
					<a href="<c:url value='/login/pw/request'/>" class="js-password-forgotten" data-cbox-title="<spring:theme code="forgottenPwd.title"/>">
						<spring:theme code="login.link.forgottenPwd" />
					</a>
				</ycommerce:testId>
			</div>
		<ycommerce:testId code="loginAndCheckoutButton">
			<button type="submit" class="btn btn-primary btn-block addtocart-btn-vtf">
				<spring:theme code="${actionNameKey}" />
			</button>
		</ycommerce:testId>

</form:form>

