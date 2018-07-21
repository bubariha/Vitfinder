/**
 *
 */
package com.vitfinder.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.c2l.CurrencyModel;
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
import java.util.List;

import com.vitfinder.core.model.VitfinderEANProductModel;


public class VitfinderStrengthValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}


	@SuppressWarnings("deprecation")
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{

		final Collection fieldValues = new ArrayList();
		try
		{
			List<String> rangeNameList = null;
			ProductModel product = null;
			if (model instanceof ProductModel)
			{
				product = (ProductModel) model;
			}
			else
			{
				throw new FieldValueProviderException("Cannot evaluate strength of non-product item");
			}

			if (product instanceof VitfinderEANProductModel)
			{
				final Double strength = ((VitfinderEANProductModel) product).getStrength();
				if (indexConfig.getCurrencies().isEmpty())
				{
					if (strength != null)
					{
						final Double value = strength;
						rangeNameList = getRangeNameList(indexedProperty, value);
						final Collection<String> fieldNames = this.fieldNameProvider.getFieldNames(indexedProperty, "GBP");
						for (final String fieldName : fieldNames)
						{
							if (rangeNameList.isEmpty())
							{
								fieldValues.add(new FieldValue(fieldName, value));
							}
							else
							{
								for (final String rangeName : rangeNameList)
								{
									fieldValues.add(new FieldValue(fieldName, (rangeName == null) ? value : rangeName));
								}
							}
						}

					}
				}
				for (final CurrencyModel currency : indexConfig.getCurrencies())
				{
					final CurrencyModel sessionCurrency = this.i18nService.getCurrentCurrency();
					try
					{
						this.i18nService.setCurrentCurrency(currency);
						if (strength == null)
						{
							break;
						}
						final Double value = strength;
						rangeNameList = getRangeNameList(indexedProperty, value, currency.getIsocode());
						final Collection<String> fieldNames = this.fieldNameProvider.getFieldNames(indexedProperty,
								currency.getIsocode().toLowerCase());
						for (final String fieldName : fieldNames)
						{
							if (rangeNameList.isEmpty())
							{
								fieldValues.add(new FieldValue(fieldName, value));
							}
							else
							{
								for (final String rangeName : rangeNameList)
								{
									fieldValues.add(new FieldValue(fieldName, (rangeName == null) ? value : rangeName));
								}
							}
						}

					}
					finally
					{
						this.i18nService.setCurrentCurrency(sessionCurrency);
					}
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error(e);
			throw new FieldValueProviderException(
					"Cannot evaluate " + indexedProperty.getName() + " using " + super.getClass().getName(), e);
		}
		return fieldValues;


	}

}
