<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="actionNameKey" required="true"
	type="java.lang.String"%>
<%@ attribute name="action" required="true" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>


<div class="headline">
	<spring:theme code="register.new.customer" />
</div>
<p>
	<spring:theme code="register.description" />
</p>

<form:form method="post" commandName="registerForm" id="registerForm"
	action="${action}">
	<div class="form_field-elements js-recaptcha-captchaaddon">
		<formElement:formSelectBox idKey="register.title"
			labelKey="register.title" selectCSSClass="form-control"
			path="titleCode" mandatory="true" skipBlank="false"
			skipBlankMessageKey="form.select.empty" items="${titles}" />
		<formElement:formInputBox idKey="register.firstName"
			labelKey="register.firstName" path="firstName"
			inputCSS="form-control" mandatory="true" />
		<formElement:formInputBox idKey="register.lastName"
			labelKey="register.lastName" path="lastName" inputCSS="form-control"
			mandatory="true" />

		<formElement:formInputBox idKey="address.line1"
			labelKey="address.line1" path="line1" inputCSS="form-control"
			mandatory="true" />
		<formElement:formInputBox idKey="address.line2"
			labelKey="address.line2" path="line2" inputCSS="form-control"
			mandatory="false" />
		<formElement:formInputBox idKey="address.townCity"
			labelKey="address.townCity" path="townCity" inputCSS="form-control"
			mandatory="true" />
		<formElement:formInputBox idKey="address.postcode"
			labelKey="address.postcode" path="postcode" inputCSS="form-control"
			mandatory="true" />
		<formElement:formSelectBox idKey="address.country"
			labelKey="address.country" path="countryIso" mandatory="true" selectCSSClass="form-control"
			skipBlank="false" skipBlankMessageKey="address.selectCountry"
			items="${countryData}" itemValue="isocode"
			selectedValue="${registerForm.countryIso}" />
		<formElement:formInputBox idKey="address.phone"
			labelKey="address.phone" path="phone" inputCSS="form-control"
			mandatory="true" />

		<formElement:formInputBox idKey="register.email"
			labelKey="register.email" path="email" inputCSS="form-control"
			mandatory="true" />
		<formElement:formPasswordBox idKey="password" labelKey="register.pwd"
			path="pwd" inputCSS="form-control password-strength" mandatory="true" />
		<formElement:formPasswordBox idKey="register.checkPwd"
			labelKey="register.checkPwd" path="checkPwd" inputCSS="form-control"
			mandatory="true" />

		<form:label path="communicationPreferences">
			<spring:theme code="communication.preferences" />
		</form:label>
		<c:forEach items="${communicationPreferences}" var="commPrep">
			<formElement:formCheckbox idKey="communication.preferences"
				labelKey="${commPrep}" path="communicationPreferences"
				value="${commPrep}" mandatory="true" />
		</c:forEach>

		<form:label path="registerCheckbox">
			<form:checkbox id="Terms1" path="registerCheckbox" />&nbsp;
			<spring:theme code="register.termsandconditions.checkbox"
				arguments="/login/termsAndConditions" text="Terms and Conditions" />
		</form:label>

		<input type="hidden" id="recaptchaChallangeAnswered"
			value="${requestScope.recaptchaChallangeAnswered}" />
	</div>

	<div class="form-actions clearfix form-register-btn-vtf">
		<ycommerce:testId code="register_Register_button">
			<button type="submit" id="register_button"
				class="btn btn-default btn-block addtocart-btn-vtf ">
				<spring:theme code='${actionNameKey}' />
			</button>
		</ycommerce:testId>
	</div>
</form:form>
<script type="text/javascript">
	var registerErrorMessage = [
			"<spring:theme text='Please select title' code='address.title.invalid'/>",
			"<spring:theme text='Please enter first name' code='register.firstName.invalid'/>",
			"<spring:theme text='Please enter last name' code='register.lastName.invalid'/>",
			"<spring:theme text='Please enter address line1' code='address.line1.invalid'/>",
			"<spring:theme text='Please enter city' code='address.townCity.invalid'/>",
			"<spring:theme text='Please select country' code='address.country.invalid'/>",
			"<spring:theme text='Please enter postcode' code='address.postcode.invalidCode'/>",
			"<spring:theme text='Please enter a valid phone number' code='address.phone.invalid'/>",
			"<spring:theme text='Please enter an email address' code='existing.customer.email.error.message'/>",
			"<spring:theme text='Please enter a password with atleast 7 characters' code='password.strength.minchartext'/>",
			"<spring:theme text='passwords dont match' code='validation.checkPwd.equals'/>" ]
</script>