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
package com.vitfinder.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.classification.ClassificationService;
import de.hybris.platform.classification.features.Feature;
import de.hybris.platform.classification.features.FeatureList;
import de.hybris.platform.classification.features.FeatureValue;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;


public class ClassificationAttributeValueProvider extends AbstractPropertyFieldValueProvider
		implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	private ClassificationService classificationService;

	/**
	 * With this we can get the field values for index properties which are classification attibutes
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		if (model instanceof ProductModel)
		{
			final FeatureList features = classificationService.getFeatures((ProductModel) model);
			Feature feature = null;
			for (final Feature featureOne : features)
			{
				if (featureOne.getCode().toLowerCase().contains(indexedProperty.getName().toLowerCase()))
				{
					feature = featureOne;
					break;
				}
			}

			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			if (feature != null && feature.getValue() != null)
			{
				fieldValues.addAll(createFieldValue(feature, indexedProperty));
			}
			return fieldValues;
		}

		else
		{
			return Collections.emptyList();
		}
	}

	protected List<FieldValue> createFieldValue(final Feature feature, final IndexedProperty indexedProperty)
	{
		final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
		Object value = feature.getValue().getValue();
		//		final boolean isRange = feature.getClassAttributeAssignment().getRange();
		boolean isFirst = true;
		String rangeValue = "";
		if (feature.getValues().size() > 1)
		{
			for (final FeatureValue featureValue : feature.getValues())
			{
				if (isFirst)
				{
					rangeValue = featureValue.getValue().toString();
					isFirst = false;
					continue;
				}
				rangeValue = rangeValue + "-" + featureValue.getValue();
			}
		}
		if (indexedProperty.getType().equalsIgnoreCase("string") && !rangeValue.isEmpty())
		{
			value = rangeValue;
		}
		final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
		return fieldValues;
	}


	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	public ClassificationService getClassificationService()
	{
		return classificationService;
	}

	public void setClassificationService(final ClassificationService classificationService)
	{
		this.classificationService = classificationService;
	}

	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}

}
