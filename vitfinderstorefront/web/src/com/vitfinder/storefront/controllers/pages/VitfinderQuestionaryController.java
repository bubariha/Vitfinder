package com.vitfinder.storefront.controllers.pages;


import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorstorefrontcommons.annotations.RequireHardLogIn;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.util.MetaSanitizerUtil;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vitfinder.core.QuestionaryData;
import com.vitfinder.facades.facades.VitfinderQuestionaryFacade;
import com.vitfinder.facades.product.data.GenderData;
import com.vitfinder.storefront.CustomerCommonQuestionaireForm;
import com.vitfinder.storefront.QuestionaireForm;
import com.vitfinder.storefront.controllers.ControllerConstants;


@Controller
@RequestMapping(value = "/questionary")
public class VitfinderQuestionaryController extends CategoryPageController
{
	private static final String CUSTOMER_COMMON_QUESTIONAIRE = "customerCommonQuestionaire";

	@Resource(name = "vitfinderQuestionaryFacade")
	private VitfinderQuestionaryFacade vitfinderQuestionaryFacade;
	@Resource(name = "categoryConverter")
	private Converter<CategoryModel, CategoryData> categoryConverter;

	private static final String QUIZ_RESULTS_CMS_PAGE = "accountQuizResultsPage";

	@RequestMapping(value = "/symptoms/{categoryCode:.*}", method = RequestMethod.GET)
	public String getQuestionaryCategory(@PathVariable("categoryCode") final String categoryCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException
	{

		boolean isAvailable = isCustomerAvailable(request, model);
		CategoryModel category = null;
		isAvailable = true;
		if (isAvailable)
		{
			category = getCommerceCategoryService().getCategoryForCode(categoryCode);
			final CategoryData questionCategoryData = new CategoryData();
			getCategoryConverter().convert(category, questionCategoryData);
			final List<CategoryData> symptomCategories = new ArrayList<>();
			final List<CategoryData> questionCategories = new ArrayList<>();
			for (final CategoryData categoryData : questionCategoryData.getSubCategories())
			{
				if (categoryData.isSymptoms())
				{
					symptomCategories.add(categoryData);
					continue;
				}
				questionCategories.add(categoryData);
			}
			loadOptionsForQuestions(questionCategories);
			model.addAttribute("symptoms", symptomCategories.isEmpty() ? null : symptomCategories);
			model.addAttribute("questions", questionCategories);
			model.addAttribute("categoryData", questionCategoryData);

		}
		final CategoryPageModel categoryPage = getCategoryPage(category);
		storeCmsPageInModel(model, categoryPage);
		model.addAttribute("pageType", PageType.CATEGORY.name());
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(category.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(category.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);
		return ControllerConstants.Views.Pages.Product.Questionary;
	}

	/**
	 * @param questionCategories
	 */
	private void loadOptionsForQuestions(final List<CategoryData> questionCategories)
	{

		for (final CategoryData question : questionCategories)
		{
			final CategoryData catData = getVitfinderQuestionaryFacade().getCategoryForCode(question.getCode());
			question.setSubCategories(catData.getSubCategories());
		}
	}

	@RequestMapping(value = "/symptoms/{categoryCode:.*}/next", method = RequestMethod.POST)
	public String getNextQuestionaryCategory(@PathVariable("categoryCode") final String categoryCode,
			final QuestionaireForm questionaireForm, final Model model, final HttpServletRequest request,
			final HttpServletResponse response) throws UnsupportedEncodingException
	{
		final CategoryData questionCategoryData = getVitfinderQuestionaryFacade().getCategoryForCode(questionaireForm.getAnswer());
		final List<CategoryData> questionCategories = new ArrayList<>();
		for (final CategoryData categoryData : questionCategoryData.getSubCategories())
		{
			if (categoryData.isSymptoms())
			{
				continue;
			}
			questionCategories.add(categoryData);
			model.addAttribute("isLastQuestion", categoryData.isLast());
		}
		getQuestionaryCategory(categoryCode, model, request, response);
		model.addAttribute("questions", questionCategories);
		loadOptionsForQuestions(questionCategories);

		getVitfinderQuestionaryFacade().saveQAMetric(categoryCode, questionCategoryData.getDescription());

		return ControllerConstants.Views.Pages.Product.Questionary;
	}

	@RequestMapping(value = "/symptoms/{categoryCode:.*}/commonstoreandcontinue", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String getCommonQuestionaryData(@PathVariable("categoryCode") final String categoryCode,
			final CustomerCommonQuestionaireForm questionaryForm, final Model model, final HttpServletRequest request,
			final HttpServletResponse response) throws UnsupportedEncodingException
	{
		request.getSession().setAttribute(CUSTOMER_COMMON_QUESTIONAIRE, questionaryForm);
		return getQuestionaryCategory(categoryCode, model, request, response);
	}

	@RequestMapping(value = "/symptoms/{categoryCode:.*}/common", method = RequestMethod.GET)
	public String getHomePage(@PathVariable("categoryCode") final String categoryCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException
	{
		return getQuestionaryCategory(categoryCode, model, request, response);
	}

	/**
	 * @param request
	 * @param model
	 * @return
	 */
	private boolean isCustomerAvailable(final HttpServletRequest request, final Model model)
	{
		model.addAttribute("questionaireForm", new QuestionaireForm());
		final HttpSession session = request.getSession();
		final Object commonQuestionaire = session.getAttribute(CUSTOMER_COMMON_QUESTIONAIRE);
		if (commonQuestionaire == null)
		{
			model.addAttribute("isAvailable", false);
			model.addAttribute("customerCommonQuestionaireForm", new CustomerCommonQuestionaireForm());
			final List<GenderData> genderOptions = new ArrayList<GenderData>();
			final GenderData male = new GenderData();
			male.setCode("male");
			male.setName("Male");

			genderOptions.add(male);
			final GenderData female = new GenderData();
			female.setCode("female");
			female.setName("Female");
			genderOptions.add(female);

			final GenderData undisputed = new GenderData();
			undisputed.setCode("Undisputed");
			undisputed.setName("Undisputed");
			genderOptions.add(undisputed);

			model.addAttribute("genders", genderOptions);
			return false;
		}

		model.addAttribute("isAvailable", true);
		return true;
	}


	@RequestMapping(value = "/symptoms/{categoryCode:.*}/submit", method =
	{ RequestMethod.POST })
	public String storeCustomerData(@PathVariable("categoryCode") final String categoryCode,
			final QuestionaireForm questionaireForm, final Model model, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException
	{
		if (questionaireForm == null)
		{

			return REDIRECT_PREFIX + "/en_GB/questionary/symptoms/" + categoryCode + "/common";

		}

		boolean flag = false;
		final CustomerCommonQuestionaireForm commonDetails = (CustomerCommonQuestionaireForm) request.getSession()
				.getAttribute(CUSTOMER_COMMON_QUESTIONAIRE);
		if (commonDetails != null)
		{
			if (commonDetails.isStoreData() && !getCustomerFacade().getCurrentCustomer().getName().equalsIgnoreCase("Anonymous"))
			{
				flag = true;
				//store customer data
				getVitfinderQuestionaryFacade().storeCustomerQuiz(questionaireForm, commonDetails, flag, categoryCode);

			}
			else if (questionaireForm.isStoreQuizResults()
					&& !getCustomerFacade().getCurrentCustomer().getName().equalsIgnoreCase("Anonymous"))
			{

				//store quiz results only
				getVitfinderQuestionaryFacade().storeCustomerQuiz(questionaireForm, commonDetails, flag, categoryCode);
			}
		}
		else
		{
			LOG.debug("There is nothing to save " + commonDetails);
		}

		getVitfinderQuestionaryFacade().saveQAMetric(categoryCode,
				getVitfinderQuestionaryFacade().getCategoryForCode(questionaireForm.getAnswer()).getDescription());

		return REDIRECT_PREFIX + "/c/" + questionaireForm.getAnswer() + "?isQuestionary=true";

	}

	@RequestMapping(value = "/allSymptoms/{categoryCode:.*}", method = RequestMethod.GET)
	public String getSymptoms(@PathVariable("categoryCode") final String categoryCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException
	{

		CategoryModel category = null;
		category = getCommerceCategoryService().getCategoryForCode(categoryCode);
		final CategoryData symptomCategoryData = new CategoryData();
		getCategoryConverter().convert(category, symptomCategoryData);
		final List<CategoryData> symptomCategories = new ArrayList<>();
		for (final CategoryData categoryData : symptomCategoryData.getSubCategories())
		{
			if (categoryData.isSymptoms())
			{
				symptomCategories.add(categoryData);
			}
		}
		model.addAttribute("rootCategory", symptomCategoryData);
		model.addAttribute("symptoms", symptomCategories);

		final CategoryPageModel categoryPage = getCategoryPage(category);
		storeCmsPageInModel(model, categoryPage);
		model.addAttribute("pageType", PageType.CATEGORY.name());
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(category.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(category.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);
		return ControllerConstants.Views.Pages.Product.SymptomsCategory;
	}

	@RequestMapping(value = "/isSubCategoryAvailable", method = RequestMethod.POST)
	public @ResponseBody boolean isSubCategoryAvailable(@RequestParam("categoryCode") final String categoryCode)
			throws UnsupportedEncodingException
	{
		boolean isAvailable = false;
		try
		{
			CategoryModel category = null;
			category = getCommerceCategoryService().getCategoryForCode(categoryCode);
			if (category != null)
			{
				if (category.getCategories() != null && !CollectionUtils.isEmpty(category.getCategories()))
				{

					isAvailable = true;
				}
				else
				{
					isAvailable = false;
				}
			}
			else
			{
				isAvailable = false;
			}

		}
		catch (final Exception e)
		{
			LOG.debug("Error :" + e.getMessage());
			isAvailable = false;
		}
		return isAvailable;
	}


	/**
	 * @return the vitfinderQuestionaryFacade
	 */
	public VitfinderQuestionaryFacade getVitfinderQuestionaryFacade()
	{
		return vitfinderQuestionaryFacade;
	}

	/**
	 * @param vitfinderQuestionaryFacade
	 *           the vitfinderQuestionaryFacade to set
	 */
	public void setVitfinderQuestionaryFacade(final VitfinderQuestionaryFacade vitfinderQuestionaryFacade)
	{
		this.vitfinderQuestionaryFacade = vitfinderQuestionaryFacade;
	}

	/**
	 * @return the categoryConverter
	 */
	public Converter<CategoryModel, CategoryData> getCategoryConverter()
	{
		return categoryConverter;
	}

	/**
	 * @param categoryConverter
	 *           the categoryConverter to set
	 */
	public void setCategoryConverter(final Converter<CategoryModel, CategoryData> categoryConverter)
	{
		this.categoryConverter = categoryConverter;
	}


	//vtf-144
	@RequestMapping(value = "/quizResults", method = RequestMethod.GET)
	@RequireHardLogIn
	public String showQuizResults(final Model model) throws CMSItemNotFoundException
	{

		final List<QuestionaryData> questioanry = getVitfinderQuestionaryFacade().getQuizResults();
		model.addAttribute("questioanry", questioanry);

		storeCmsPageInModel(model, getContentPageForLabelOrId(QUIZ_RESULTS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(QUIZ_RESULTS_CMS_PAGE));

		return ControllerConstants.Views.Pages.Account.AccountQuizResultsPage;
	}

	//vtf-1023
	@RequestMapping(value = "/generalConditions/{categoryCode}", method = RequestMethod.GET)
	//@RequireHardLogIn
	public String generalConditions(@PathVariable("categoryCode") final String categoryCode, final Model model)
			throws CMSItemNotFoundException
	{

		CategoryModel category = null;
		category = getCommerceCategoryService().getCategoryForCode(categoryCode);

		final CategoryData conditionCategoryData = new CategoryData();
		getCategoryConverter().convert(category, conditionCategoryData);
		/*
		 * final List<CategoryData> conditionCategories = new ArrayList<>(); for (final CategoryData categoryData :
		 * conditionCategoryData.getSubCategories()) { final CategoryModel subCatModel =
		 * getCommerceCategoryService().getCategoryForCode(categoryData.getCode()); final CategoryData subCatData = new
		 * CategoryData(); getCategoryConverter().convert(subCatModel, subCatData);
		 * categoryData.setSubCategories(subCatData.getSubCategories());
		 *
		 * conditionCategories.add(categoryData); }
		 */

		final List<CategoryData> conditionCategories = conditionCategoryData.getSubCategories();

		model.addAttribute("rootCategory", conditionCategoryData);
		model.addAttribute("conditions", conditionCategories);

		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getSearchBreadcrumbBuilder().getBreadcrumbs(categoryCode, new ProductCategorySearchPageData()));

		final CategoryPageModel categoryPage = getCategoryPage(category);
		storeCmsPageInModel(model, categoryPage);
		model.addAttribute("pageType", PageType.CATEGORY.name());
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(category.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(category.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);
		model.addAttribute("pageTitle", "Conditions | Vitfinder Site");
		return ControllerConstants.Views.Pages.Misc.GeneralConditionsPage;
	}

	@RequestMapping(value = "/condition/{categoryCode}", method = RequestMethod.GET)
	//@RequireHardLogIn
	public String condition(@PathVariable("categoryCode") final String categoryCode, final Model model)
			throws CMSItemNotFoundException
	{

		CategoryModel category = null;
		category = getCommerceCategoryService().getCategoryForCode(categoryCode);

		final CategoryData conditionCategoryData = new CategoryData();
		getCategoryConverter().convert(category, conditionCategoryData);
		final List<CategoryData> conditionCategories = new ArrayList<>();
		for (final CategoryData categoryData : conditionCategoryData.getSubCategories())
		{
			conditionCategories.add(categoryData);
		}

		model.addAttribute("rootCategory", conditionCategoryData);
		model.addAttribute("conditions", conditionCategories);

		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				getSearchBreadcrumbBuilder().getBreadcrumbs(categoryCode, new ProductCategorySearchPageData()));
		storeCmsPageInModel(model, getContentPageForLabelOrId("conditionPage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("conditionPage"));
		model.addAttribute("pageTitle", conditionCategoryData.getName() + " | Vitfinder Site");
		return ControllerConstants.Views.Pages.Misc.ConditionPage;
	}

	@RequestMapping(value = "/removeEntry/{code}", method = RequestMethod.GET)
	//@RequireHardLogIn
	public String removeQuestionary(@PathVariable("code") final String code)
	{
		getVitfinderQuestionaryFacade().removeQuestionary(Long.parseLong(code));
		return REDIRECT_PREFIX + "/questionary/quizResults";
	}

}
