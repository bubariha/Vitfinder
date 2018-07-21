/**
 *
 */
package com.vitfinder.core.servicelayer;

import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.model.attribute.DynamicAttributeHandler;


/**
 * Handler for dynamic attribute discount of PriceRow item
 */
public class DynamicAttributeDiscountHandler implements DynamicAttributeHandler<String, PriceRowModel>
{

	@Override
	public String get(final PriceRowModel model)
	{
		if (model == null)
		{
			throw new IllegalArgumentException("Price Row Model is required");
		}

		final Double price = model.getPrice();
		final Double wasPrice = model.getWasPrice();

		if (wasPrice != null && wasPrice.doubleValue() > 0 && wasPrice.doubleValue() > price.doubleValue())
		{
			return ((int) (((wasPrice.doubleValue() - price.doubleValue()) / wasPrice.doubleValue()) * 100)) + " % Off";
		}
		return "";
	}

	@Override
	public void set(final PriceRowModel item, final String value)
	{
		throw new UnsupportedOperationException();
	}
}

