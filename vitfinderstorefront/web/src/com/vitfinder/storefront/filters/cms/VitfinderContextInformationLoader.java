package com.vitfinder.storefront.filters.cms;

import de.hybris.platform.acceleratorcms.context.impl.DefaultContextInformationLoader;
import de.hybris.platform.acceleratorcms.preview.strategies.PreviewContextInformationLoaderStrategy;
import de.hybris.platform.cms2.model.preview.PreviewDataModel;
import de.hybris.platform.core.model.c2l.LanguageModel;

import java.util.Locale;


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

/**
 * Default context information loader
 */
public class VitfinderContextInformationLoader extends DefaultContextInformationLoader
{

	public static class LoadLanguageStrategy extends VitfinderContextInformationLoader
			implements PreviewContextInformationLoaderStrategy
	{

		@Override
		public void initContextFromPreview(final PreviewDataModel preview)
		{
			if (preview.getLanguage() != null)
			{
				super.loadFakeLanguage(preview.getLanguage());
			}

		}

	}


	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.hybris.platform.acceleratorcms.context.impl.DefaultContextInformationLoader#loadFakeLanguage(de.hybris.platform
	 * .core.model.c2l.LanguageModel)
	 */
	@Override
	protected void loadFakeLanguage(final LanguageModel languageModel)
	{
		if (languageModel != null)
		{

			final String isoCode = languageModel.getIsocode();
			System.out.println(isoCode);
			if (isoCode.equalsIgnoreCase("en_gb"))
			{
				getI18NService().setCurrentLocale(Locale.UK);
				return;
			}
			getI18NService().setCurrentLocale(new Locale(languageModel.getIsocode()));
		}
	}

}
