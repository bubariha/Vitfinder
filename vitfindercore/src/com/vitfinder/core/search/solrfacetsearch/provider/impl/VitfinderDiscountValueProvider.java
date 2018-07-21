/**
 *
 */
package com.vitfinder.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.c2l.CurrencyModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.DiscountRowModel;
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


public class VitfinderDiscountValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
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

			final Collection<DiscountRowModel> discounts = product.getEurope1Discounts();
			Double discountValue = 0.00d;
			for (final DiscountRowModel discount : discounts)
			{
				if (discount.getAbsolute() != null && !discount.getAbsolute())
				{
					discountValue = discountValue + discount.getValue();
				}
			}
			if (indexConfig.getCurrencies().isEmpty())
			{
				if (discountValue != 0)
				{
					final Double value = discountValue;
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
					if (discountValue == 0.00)
					{
						break;
					}
					final Double value = discountValue;
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
		catch (final Exception e)
		{
			LOG.error(e);
			throw new FieldValueProviderException(
					"Cannot evaluate " + indexedProperty.getName() + " using " + super.getClass().getName(), e);
		}
		return fieldValues;


	}

}
