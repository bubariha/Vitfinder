<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/responsive/formElement" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>


<div class="account-section-header">Update Communication preferences</div>
<div class="account-section-content account-section-content-small">
	<form:form action="update-comm-pref" method="post" commandName="updateCommPrefForm" class="updateCommPrefForm-vtf">
	
	<form:label path="communicationPreferences" ><spring:theme code="acc.communication.preferences" /></form:label>
		<br/>
		<c:set var="isDisabled" value="true"/>
		<c:forEach items="${communicationPreferences}" var="commPref">
		<c:set var="isDisabled" value="true"/>
			<c:forEach items="${updateCommPrefForm.communicationPreferences}" var="preference">
				<c:if test="${commPref eq preference}"> 
					<c:set var="isDisabled" value="false"/>
				</c:if>
			</c:forEach> 
		<form:checkbox path="communicationPreferences" value="${commPref }" label="${commPref}"/>
		</c:forEach>
		
		<form:label path="remainder" ><spring:theme code="acc.remainder" /></form:label>
		<form:radiobuttons path="remainder" items="${listOfRemainders}"/>
		<input type="hidden" id="recaptchaChallangeAnswered" value="${requestScope.recaptchaChallangeAnswered}"/>	
		<div class="form_field-elements control-group js-recaptcha-captchaaddon"></div>
		<div class="form-actions">
			<div class="container accountActions">
				<div class="col-sm-6 col-sm-push-6 accountButtons">
					<ycommerce:testId code="email_saveEmail_button">
						<button type="submit" class="btn btn-primary btn-block addtocart-btn-vtf">
							<spring:theme code="text.account.profile.saveUpdates" text="Save Updates"/>
						</button>
					</ycommerce:testId>
				</div>
				<div class="col-sm-6 col-sm-pull-6 accountButtons">
					<ycommerce:testId code="email_cancelEmail_button">
						<button type="button" class="btn btn-default btn-block backToHome gray-btn-vtf">
							<spring:theme code="text.account.profile.cancel" text="Cancel"/>
						</button>
					</ycommerce:testId>
				</div>
			</div>
		</div>
	</form:form>
</div>