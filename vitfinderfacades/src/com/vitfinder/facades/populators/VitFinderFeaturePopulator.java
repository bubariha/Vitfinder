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

import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.classification.features.Feature;
import de.hybris.platform.commercefacades.product.converters.populator.FeaturePopulator;
import de.hybris.platform.commercefacades.product.data.FeatureData;


/**
 */
public class VitFinderFeaturePopulator extends FeaturePopulator
{

	@Override
	public void populate(final Feature source, final FeatureData target)
	{
		super.populate(source, target);
		final ClassAttributeAssignmentModel classAttributeAssignment = source.getClassAttributeAssignment();
		target.setRi(classAttributeAssignment.getClassificationAttribute().getRi());
		target.setSafeUpperLimit(classAttributeAssignment.getClassificationAttribute().getSafeUpperLimit());

		target.setName(
				source.getName() != null ? source.getName() : classAttributeAssignment.getClassificationAttribute().getCode());
	}
}
