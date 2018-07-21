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
package com.vitfinder.facades.populators;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.converters.populator.CategoryPopulator;
import de.hybris.platform.commercefacades.product.data.CategoryData;

import java.util.ArrayList;
import java.util.List;


/**
 * Converter implementation for {@link de.hybris.platform.category.model.CategoryModel} as source and
 * {@link de.hybris.platform.commercefacades.product.data.CategoryData} as target type.
 */
public class VitfinderCategoryPopulator extends CategoryPopulator
{

	@Override
	public void populate(final CategoryModel source, final CategoryData target)
	{
		super.populate(source, target);
		target.setDescription(source.getDescription());
		target.setLast(source.getLast() != null ? source.getLast() : false);
		target.setSymptoms(source.getSymptoms() != null ? source.getSymptoms() : false);
		final List<CategoryModel> subCategories = source.getCategories();
		final List<CategoryData> subCategoryDatas = new ArrayList<CategoryData>();
		for (final CategoryModel subCategory : subCategories)
		{
			final CategoryData catData = new CategoryData();
			super.populate(subCategory, catData);
			catData.setLast(subCategory.getLast() != null ? subCategory.getLast() : false);
			catData.setSymptoms(subCategory.getSymptoms() != null ? subCategory.getSymptoms() : false);
			catData.setDescription(subCategory.getDescription() != null ? subCategory.getDescription() : "");
			subCategoryDatas.add(catData);
		}
		target.setSubCategories(subCategoryDatas);
	}

}
