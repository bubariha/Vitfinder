/*
 * [y] hybris Platform
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
package com.vitfinder.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import org.apache.commons.lang.LocaleUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;


/**
 * Velocity context for a customer email.
 */
public class CustomerEmailContext extends AbstractEmailContext<StoreFrontCustomerProcessModel>
{
	private Converter<UserModel, CustomerData> customerConverter;
	private CustomerData customerData;

	private static final Logger LOG = Logger.getLogger(CustomerEmailContext.class);

	@Override
	public void init(final StoreFrontCustomerProcessModel storeFrontCustomerProcessModel, final EmailPageModel emailPageModel)
	{
		//super.init(storeFrontCustomerProcessModel, emailPageModel);
		final BaseSiteModel baseSite = getSite(storeFrontCustomerProcessModel);
		if (baseSite == null)
		{
			LOG.error("Failed to lookup Site for BusinessProcess [" + storeFrontCustomerProcessModel + "]");
		}
		else
		{
			put(BASE_SITE, baseSite);
			setUrlEncodingAttributes(getUrlEncoderService().getUrlEncodingPatternForEmail(storeFrontCustomerProcessModel));
			final SiteBaseUrlResolutionService siteBaseUrlResolutionService = getSiteBaseUrlResolutionService();
			// Lookup the site specific URLs
			put(BASE_URL, siteBaseUrlResolutionService.getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), false, ""));
			put(BASE_THEME_URL, siteBaseUrlResolutionService.getWebsiteUrlForSite(baseSite, false, ""));
			put(SECURE_BASE_URL, siteBaseUrlResolutionService.getWebsiteUrlForSite(baseSite, getUrlEncodingAttributes(), true, ""));
			put(MEDIA_BASE_URL, siteBaseUrlResolutionService.getMediaUrlForSite(baseSite, false));
			put(MEDIA_SECURE_BASE_URL, siteBaseUrlResolutionService.getMediaUrlForSite(baseSite, true));

			put(THEME, baseSite.getTheme() != null ? baseSite.getTheme().getCode() : null);
		}

		put(FROM_EMAIL, emailPageModel.getFromEmail());

		final LanguageModel language = getEmailLanguage(storeFrontCustomerProcessModel);
		if (language != null)
		{
			put(EMAIL_LANGUAGE, language);

			String fromName = emailPageModel.getFromName(LocaleUtils.toLocale(language.getIsocode()));

			//fromName = emailPageModel.getFromName();



			if (fromName == null)
			{
				fromName = emailPageModel.getFromName();
			}
			put(FROM_DISPLAY_NAME, fromName);
		}
		else
		{
			put(FROM_DISPLAY_NAME, emailPageModel.getFromName());
		}

		final CustomerModel customerModel = getCustomer(storeFrontCustomerProcessModel);
		if (customerModel != null)
		{
			put(TITLE, (customerModel.getTitle() != null && customerModel.getTitle().getName() != null)
					? customerModel.getTitle().getName() : "");
			put(DISPLAY_NAME, customerModel.getDisplayName());
			put(EMAIL, getCustomerEmailResolutionService().getEmailForCustomer(customerModel));
		}
		customerData = getCustomerConverter().convert(getCustomer(storeFrontCustomerProcessModel));
	}

	@Override
	protected BaseSiteModel getSite(final StoreFrontCustomerProcessModel storeFrontCustomerProcessModel)
	{
		return storeFrontCustomerProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(final StoreFrontCustomerProcessModel storeFrontCustomerProcessModel)
	{
		return storeFrontCustomerProcessModel.getCustomer();
	}

	protected Converter<UserModel, CustomerData> getCustomerConverter()
	{
		return customerConverter;
	}

	@Required
	public void setCustomerConverter(final Converter<UserModel, CustomerData> customerConverter)
	{
		this.customerConverter = customerConverter;
	}

	public CustomerData getCustomer()
	{
		return customerData;
	}

	@Override
	protected LanguageModel getEmailLanguage(final StoreFrontCustomerProcessModel businessProcessModel)
	{
		return businessProcessModel.getLanguage();
	}
}
