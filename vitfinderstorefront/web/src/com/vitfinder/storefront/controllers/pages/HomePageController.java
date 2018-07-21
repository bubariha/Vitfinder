/*
# * [y] hybris Platform
 *
 * Copyright (c) 2000-2015 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.vitfinder.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.disqus.DisqusConfigParamsModel;
import de.hybris.platform.site.BaseSiteService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vitfinder.core.service.impl.VitfinderCategoryService;
import com.vitfinder.core.servicelayer.SSOMessageGenerator;
import com.vitfinder.storefront.controllers.ControllerConstants;


/**
 * Controller for home page
 */
@Controller
@Scope("tenant")
@RequestMapping("/")
public class HomePageController extends AbstractPageController
{

	@Resource(name = "vitfinderCategoryService")
	private VitfinderCategoryService vitfinderCategoryService;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;


	@RequestMapping(method = RequestMethod.GET)
	public String home(@RequestParam(value = "logout", defaultValue = "false") final boolean logout, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (logout)
		{
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER, "account.confirmation.signout.title");
			return REDIRECT_PREFIX + ROOT;
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));

		return getViewForPage(model);
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	//vtf-206
	@RequestMapping(value = "/quicktips/{rootCategory}", method = RequestMethod.GET)
	public String getSubCategories(@PathVariable("rootCategory") final String rootCategory, final Model model)

	{

		final CategoryModel category = vitfinderCategoryService.getCategoryForCode(rootCategory);
		if (category != null && category.getGenericCategory() != null && category.getGenericCategory().booleanValue())
		{
			model.addAttribute("isGenericCategory", Boolean.TRUE);
			final Collection<CategoryModel> subCats = vitfinderCategoryService.findSubCategoriesByCode(category);
			if (!subCats.isEmpty())
			{
				model.addAttribute("subCategories", subCats);
				CategoryPageModel categoryPage;
				try
				{
					categoryPage = getCmsPageService().getPageForCategory(category);
					storeCmsPageInModel(model, categoryPage);
					storeContentPageTitleInModel(model, getPageTitleResolver().resolveCategoryPageTitle(category));
					return ControllerConstants.Views.Pages.Misc.QuickTipsPage;
				}
				catch (final CMSItemNotFoundException e)
				{
					return REDIRECT_PREFIX + ROOT;
				}

			}
		}

		return REDIRECT_PREFIX + ROOT;
	}

	@RequestMapping(value = "/articles/{pageCode}", method = RequestMethod.GET)
	public String article(@PathVariable("pageCode") final String pageCode, final Model model) throws CMSItemNotFoundException
	{

		try
		{
			populateModel(model, pageCode);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return REDIRECT_PREFIX + ROOT;
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(pageCode));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(pageCode));

		return ControllerConstants.Views.Pages.Misc.ArticlesPage;

	}

	/**
	 * @param model
	 * @throws CMSItemNotFoundException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws InvalidKeyException
	 */
	private void populateModel(final Model model, final String pageCode) throws CMSItemNotFoundException, InvalidKeyException,
			JsonGenerationException, JsonMappingException, SignatureException, NoSuchAlgorithmException, IOException
	{
		final CustomerData customerData = customerFacade.getCurrentCustomer();
		model.addAttribute("customerData", customerData);
		model.addAttribute("pageID", getContentPageForLabelOrId(pageCode).getPk());
		model.addAttribute("pageCode", getContentPageForLabelOrId(pageCode).getUid());

		final DisqusConfigParamsModel disqusConfig = baseSiteService.getCurrentBaseSite().getDisqusParams();
		if (disqusConfig != null)
		{
			final String publicKey = disqusConfig.getPublicApiKey();
			final String forumName = disqusConfig.getForumName();
			final String secretKey = disqusConfig.getSecretKey();
			model.addAttribute("publicApiKey", publicKey);
			model.addAttribute("forumName", forumName);
			final List<String> payloadMsg = SSOMessageGenerator.getPayload(customerData.getUid(), customerData.getName(),
					customerData.getDisplayUid(), secretKey);

			model.addAttribute("base64EncodedStr", payloadMsg.get(0));
			model.addAttribute("signature", payloadMsg.get(1));
			model.addAttribute("timestamp", payloadMsg.get(2));
		}

	}
}
