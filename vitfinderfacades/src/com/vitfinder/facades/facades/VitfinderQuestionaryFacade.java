
/**
 *
 */
package com.vitfinder.facades.facades;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
//import de.hybris.platform.core.model.QuestionnaireMetricModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vitfinder.core.QuestionaryData;
import com.vitfinder.core.model.CommonQuestionaryModel;
import com.vitfinder.core.model.QuestionaryModel;
import com.vitfinder.storefront.CustomerCommonQuestionaireForm;
import com.vitfinder.storefront.QuestionaireForm;


/**
 * @author nandakishore
 *
 */
public class VitfinderQuestionaryFacade implements QuestionaryFacade
{
	private ModelService modelService;
	private UserService userService;
	private Converter<CategoryModel, CategoryData> categoryConverter;
	private CommerceCategoryService commerceCategoryService;
	private Converter<ProductModel, ProductData> productConverter;

	@Override
	public CategoryData getCategoryForCode(final String categoryCode)
	{
		final CategoryModel category = getCommerceCategoryService().getCategoryForCode(categoryCode);
		final CategoryData questionaire = new CategoryData();
		return getCategoryConverter().convert(category, questionaire);
	}

	/**
	 * @return the commerceCategoryService
	 */
	public CommerceCategoryService getCommerceCategoryService()
	{
		return commerceCategoryService;
	}

	/**
	 * @param commerceCategoryService
	 *           the commerceCategoryService to set
	 */
	public void setCommerceCategoryService(final CommerceCategoryService commerceCategoryService)
	{
		this.commerceCategoryService = commerceCategoryService;
	}

	/**
	 * @return the categoryConverter
	 */
	public Converter<CategoryModel, CategoryData> getCategoryConverter()
	{
		return categoryConverter;
	}

	/**
	 * @return the productConverter
	 */
	public Converter<ProductModel, ProductData> getProductConverter()
	{
		return productConverter;
	}

	/**
	 * @param productConverter
	 *           the productConverter to set
	 */
	public void setProductConverter(final Converter<ProductModel, ProductData> productConverter)
	{
		this.productConverter = productConverter;
	}

	/**
	 * @param categoryConverter
	 *           the categoryConverter to set
	 */
	public void setCategoryConverter(final Converter<CategoryModel, CategoryData> categoryConverter)
	{
		this.categoryConverter = categoryConverter;
	}

	/**
	 * @param questionaireForm
	 * @param commonDetails
	 * @param flag
	 * @param categoryCode
	 */
	public void storeCustomerQuiz(final QuestionaireForm questionaireForm, final CustomerCommonQuestionaireForm commonDetails,
			final boolean flag, final String categoryCode)
	{

		final UserModel user = getUserService().getCurrentUser();
		if (user instanceof CustomerModel)
		{
			final CustomerModel customer = (CustomerModel) user;
			final QuestionaryModel questionary = getModelService().create(QuestionaryModel.class);
			final CategoryModel answer = getCommerceCategoryService().getCategoryForCode(questionaireForm.getAnswer());
			questionary.setAnswers(answer);
			final CategoryModel question = getCommerceCategoryService().getCategoryForCode(categoryCode);
			questionary.setQuestions(question);

			if (question.getLast().booleanValue() && questionaireForm.isStoreQuizResults())
			{
				final List<ProductModel> product = ((CategoryModel) getModelService().get(answer.getPk())).getProducts();
				if (!product.isEmpty())
				{
					questionary.setRecommendedProducts(product);
				}

				final Collection<CategoryModel> allSuperCatsModel = answer.getAllSupercategories();
				for (final CategoryModel superCatModel : allSuperCatsModel)
				{
					if (superCatModel.getSymptoms().booleanValue())
					{
						questionary.setSymptomCategory(superCatModel);
					}
				}
			}
			final List<QuestionaryModel> questionaries = new ArrayList<QuestionaryModel>();
			questionaries.addAll(((CustomerModel) getUserService().getCurrentUser()).getQuestionary()); // To prevent overriding the previous questionary
			questionaries.add(questionary);
			customer.setQuestionary(questionaries);
			getModelService().saveAll();
			if (flag)
			{
				final CommonQuestionaryModel commonQuestions = getModelService().create(CommonQuestionaryModel.class);
				populateCommonQuestions(commonDetails, commonQuestions);
				customer.setCommonQuestionary(commonQuestions);
				getModelService().saveAll();
			}
		}
	}

	/**
	 * @param commonDetails
	 * @param commonQuestions
	 */
	private void populateCommonQuestions(final CustomerCommonQuestionaireForm commonDetails,
			final CommonQuestionaryModel commonQuestions)
	{
		// YTODO Auto-generated method stub
		commonQuestions.setAge(commonDetails.getAge());
		commonQuestions.setHeight(commonDetails.getHeight());
		Integer weight = null;
		try
		{
			weight = Integer.valueOf(commonDetails.getWeight());

		}
		catch (final Exception exception)
		{
			//
		}
		commonQuestions.setWeight(weight);
		commonQuestions.setStoreData(commonDetails.isStoreData());
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


	public List<QuestionaryData> getQuizResults()
	{
		final List<QuestionaryModel> questionary = ((CustomerModel) getUserService().getCurrentUser()).getQuestionary();
		final List<QuestionaryData> questionaryDataList = new ArrayList();
		if (!questionary.isEmpty())
		{
			for (final QuestionaryModel questionaryModel : questionary)
			{
				final QuestionaryData questionaryData = populateQuestionaryData(questionaryModel);
				questionaryDataList.add(questionaryData);
			}
		}
		return questionaryDataList;
	}

	/**
	 * @param last
	 * @param questionaryData
	 */
	private QuestionaryData populateQuestionaryData(final QuestionaryModel questionaryModel)
	{
		final QuestionaryData questionaryData = new QuestionaryData();

		final CategoryData symptomCategory = new CategoryData();
		getCategoryConverter().convert(questionaryModel.getSymptomCategory(), symptomCategory);
		questionaryData.setSymptoms(symptomCategory);

		final List<ProductModel> products = questionaryModel.getRecommendedProducts();
		final List<ProductData> recommendedProducts = new ArrayList<ProductData>();

		for (final ProductModel productModel : products)
		{
			final ProductData product = new ProductData();
			getProductConverter().convert(productModel, product);
			recommendedProducts.add(product);
		}

		questionaryData.setRecommendedProducts(recommendedProducts);
		questionaryData.setRecommendedDate(questionaryModel.getCreationtime());
		questionaryData.setPk(questionaryModel.getPk().getLongValueAsString());

		return questionaryData;
	}

	public void removeQuestionary(final Long pk)
	{

		final List<QuestionaryModel> questionaryList = ((CustomerModel) getUserService().getCurrentUser()).getQuestionary();

		for (final QuestionaryModel questionary : questionaryList)
		{
			if (questionary.getPk().getLongValue() == pk.longValue())
			{
				getModelService().remove(questionary.getPk());
			}
		}
		getModelService().saveAll();
	}

	public void saveQAMetric(final String answerCategory, final String answer)
	{

		//		final QuestionnaireMetricModel questionnaire = getModelService().create(QuestionnaireMetricModel.class);

		//		final String question = getCommerceCategoryService().getCategoryForCode(answerCategory).getSupercategories().get(0)
		//				.getDescription();
		//
		//		questionnaire.setQuestion(question);
		//		questionnaire.setAnswer(answer);
		//
		//		getModelService().save(questionnaire);
		//		getModelService().refresh(questionnaire);

	}

}
