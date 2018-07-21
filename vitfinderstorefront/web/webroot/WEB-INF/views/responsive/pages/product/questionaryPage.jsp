<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/responsive/formElement"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/responsive/common" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <template:page pageTitle="${pageTitle}">
	<jsp:body>
		<div id="globalMessages">
			<common:globalMessages/>
		</div> 
<div class="questionnaire-vtf">	
	<div class="questionnaire-vtf-hedng row">
	<div class="col-xs-12 col-md-8 col-lg-8">
		<h3>Health Questionnaire</h3>
		<p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.</p>
	</div>	
	<div class="col-xs-12 col-md-4 col-lg-4"> 	</div>	
	</div>
	
<%-- <c:forEach var="symptom" items="${symptoms}">
	<a href="/questionary/allSymptoms/${symptom.code}">${symptom.description }</a><br/>
</c:forEach> --%>
	<c:choose>
		<c:when test="${!isAvailable}">
		<div class="col-xs-12 col-md-8 col-lg-8">
		 <div class="row">
		<ycommerce:testId code="checkoutSteps">
			<div class="checkout-steps checkout-steps-vtf">			
				<form:form method="post" commandName="customerCommonQuestionaireForm" id="customerCommonQuestionaireForm" action="/questionary/symptoms/${categoryData.code}/commonstoreandcontinue" onsubmit="return validate()">					
				
				<div class="questn-from-vit"> 
				<div class="questn-select-vit" id="questn-select-vit">
					<formElement:formSelectBox idKey="question.gender" 
						labelKey="question.gender" selectCSSClass="form-control"
						path="gender" mandatory="true" skipBlank="false"
						skipBlankMessageKey="form.select.empty" items="${genders}" />
					<div id = "questn-select-vit-error" style="display:none;">Please Select Gender!</div>	
				</div>
			   
			     <div class="age-vit" id="age-vit">	
					<formElement:formInputBox idKey="question.age"
						labelKey="question.age" path="age"
						inputCSS="form-control" mandatory="true" />
					<div id = "age-vit-error" style="display:none;">Please Enter A Value For Age(0-130)!</div>		
				 </div>	
				  <div class="height-vit" id="height-vit">
					<formElement:formInputBox idKey="question.height"
						labelKey="question.height" path="height" inputCSS="form-control"
						mandatory="true" />
					<div id = "height-vit-error" style="display:none;">Please Enter A Value For Height(0-250)!</div>	
			      </div>
			       <div class="weight-vit" id="weight-vit">
					<formElement:formInputBox idKey="question.weight"
						labelKey="question.weight" path="weight" inputCSS="form-control"
						mandatory="true" />
					<div id = "weight-vit-error" style="display:none;">Please Enter A Value For weight(0-300)!</div>	
					</div>	
					
					<div class="checkbox"> 
					<label><input type="checkbox" name="storeData" >Store Data</label> 
					</div>
					
					<%--<formElement:formCheckbox idKey="question.storeData"
						labelKey="question.storeData" path="storeData" 
						mandatory="false" inputCSS="store_data_validation"/> --%>
					
					<%-- <div class="exercise-vit" id="exercise-vit">
					<formElement:formInputBox idKey="question.exercise"
						labelKey="question.exercise" path="exercise" inputCSS="form-control"/>
					<div id = "exercise-vit-error" style="display:none;">Please Enter A Value For Exercise!</div>	
					</div>
					
					<div class="headaches-vit" id="headaches-vit">	
					<formElement:formInputBox idKey="question.headaches"
						labelKey="question.headaches" path="headaches" inputCSS="form-control"
						mandatory="true" />
					<div id = "headaches-vit-error" style="display:none;">Please Enter A Value For Headaches!</div>
					</div>	
					
					<div class="drink-vit" id="drink-vit">
					<formElement:formInputBox idKey="question.drink"
						labelKey="question.drink" path="drink" inputCSS="form-control"
						mandatory="true" />
					<div id = "drink-vit-error" style="display:none;">Please Enter A Value For Drinks!</div>	
					</div>	 --%>
		</div>			
					
					<div class="col-xs-12 col-xs-offset-0 col-md-4 col-md-offset-4">
					<input type="submit" class="btn btn-default btn-block addtocart-btn-vtf" id="submitType" name="submitType" value="commonQuestionaire" onsubmit="return validate()"/>
					</div>
					
			</form:form>
				
			</div>
		</ycommerce:testId>
		</div>
	</div>
		
		</c:when>
		<c:otherwise>

	<div class="col-xs-12 col-md-8 col-lg-8">
	  <div class="row">
			<form:form modelAttribute="questionaireForm" method="post" action="/en_GB/questionary/symptoms/${categoryData.code}/${isLastQuestion?'submit':'next' }" id="questionaireForm" onsubmit="return validateQuestionaireForm()">			
			<div> 
			<c:choose>
				<c:when test="${symptoms!=null}">
						${categoryData.description}</div><br/>
						<form:select path="answer" class="form-control" id="category_option_selection">
							<c:if test="${skipBlank == null || skipBlank == false}">
								<option value="" disabled="disabled" ${empty selectedValue ? 'selected="selected"' : ''}>
									<spring:theme code='${skipBlankMessageKey}'/>
								</option>
							</c:if>
							<c:forEach var="option" items="${symptoms}">
							<c:if test="${option.symptoms}">
							<c:set var="isLast" value="${option.last}"/>
								<form:option value="${option.code}">${option.description}</form:option>
								</c:if>
							</c:forEach>	
						</form:select>
				</c:when>
				<c:otherwise>
					<c:forEach items="${questions}" var="question">
							${question.description}</div><br/>
							<form:select path="answer" class="form-control" id="category_option_selection">
							
								<c:if test="${skipBlank == null || skipBlank == false}">
									<option value="" disabled="disabled" ${empty selectedValue ? 'selected="selected"' : ''}>
										<spring:theme code='${skipBlankMessageKey}'/>
									</option>
								</c:if>
								<c:forEach var="option" items="${question.subCategories}">
								<c:if test="${!option.symptoms}">
								<c:set var="isLast" value="${option.last}"/>
									<form:option value="${option.code}">${option.description}</form:option>
									</c:if>
								</c:forEach>	
							</form:select>
					</c:forEach>
					</c:otherwise>
			</c:choose>
			<div id = "category_option_selection-error" style="display:none;">Please Select!</div>
			<br/>
				
			
			<div id = "recommendation-terms">
			
			<form:label path="termsCheckbox" >
				<form:checkbox id="recommendation_terms_accept-check" path="termsCheckbox" />&nbsp;
				<spring:theme code="recommendation.termsandconditions.checkbox"
										arguments="/login/termsAndConditions"
										text="Terms and Conditions" />
			</form:label>
			
			<br>
			<form:label path="storeQuizResults" >
				<form:checkbox id="recommendation_terms_storequiz-check" path="storeQuizResults" />&nbsp;
				<spring:theme code="recommendation.storequiz.checkbox"/>
			</form:label>

			<div id = "recommendation_terms_accept-error" style="display:none;">Please accept the Terms and Conditions!</div>
			</br>
			</div>
			<input type="submit" disabled="disabled" name="submitType" value="Next"  class="btn addtocart-btn-vtf col-xs-12 col-xs-offset-0 col-md-4 col-md-offset-4 question_form_selection" id="questionary_action_button"/>
			
			</form:form>
		</div>
	</div>		
		</c:otherwise>		
	</c:choose>
	
	<div class="col-xs-12 col-md-4">
		<div class="row">
			<div class="col-xs-5 col-md-4">
				<div class="questionnaire-progres-vtf">
				<img src="/_ui/responsive/theme-blue/images/user_icon.svg" alt="" title="" class="img-responsive img-fullwidth">
					<div class="progress">
					    <div class="progress-bar-vtf progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
					    </div>
					</div>
				</div>
			</div>
			<div class="col-xs-7 col-md-8" >
				<form role="form" id="progressInputVTF" >
					<div class="checkbox"> <label><input id="progressGender" type="checkbox" value="25" name="Gender" disabled="disabled">Gender</label> </div>
					<div class="checkbox"> <label><input id="progressAge" type="checkbox" value="25" name="Age" disabled="disabled">Age</label> </div>
					<div class="checkbox"> <label><input id="progressHeight" type="checkbox" value="25" name="Height" disabled="disabled">Height</label> </div>
					<div class="checkbox"> <label><input id="progressWeight" type="checkbox" value="25" name="Weight" disabled="disabled">Weight</label> </div>
				</form>
			</div>
		</div>
  </div>
  
</div>

<div class="col-sm-6 col-md-7 col-lg-7">
	<ycommerce:testId code="checkoutSteps">
		<div class="checkout-steps checkout-steps-vtf">
			<c:forEach items="${checkoutSteps}" var="checkoutStep" varStatus="status">
				<c:url value="${checkoutStep.url}" var="stepUrl"/>
				<c:choose>
					<c:when test="${progressBarId eq checkoutStep.progressBarId}">
						<c:set scope="page"  var="activeCheckoutStepNumber"  value="${checkoutStep.stepNumber}"/>
						<a href="${stepUrl}" class="step-head js-checkout-step active active-checkout-step-vtf">
							<div class="title"><spring:theme code="checkout.multi.${checkoutStep.progressBarId}"/></div>
						</a>
						<div class="step-body"></div>
					</c:when>
					<c:when test="${checkoutStep.stepNumber > activeCheckoutStepNumber}">
						<a href="${stepUrl}" class="step-head js-checkout-step ">
							<div class="title"><spring:theme code="checkout.multi.${checkoutStep.progressBarId}"/></div>
						</a>
					</c:when>
					<c:otherwise>
						<a href="${stepUrl}" class="step-head js-checkout-step ">
							<div class="title"><spring:theme code="checkout.multi.${checkoutStep.progressBarId}"/></div>
							<div class="edit">
								<span class="glyphicon glyphicon-pencil"></span>
							</div>
						</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
	</ycommerce:testId>
</div>
 </jsp:body>
</template:page>

<script type="text/javascript">
$(document).ready(function() {
$("#recommendation-terms").hide();
});
</script>
