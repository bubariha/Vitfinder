/**
 *
 */
package com.vitfinder.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.StockPopulator;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;


/**
 * @author bhavadi
 *
 */
public class VitfinderStockPopulator<SOURCE extends ProductModel, TARGET extends StockData> extends StockPopulator<SOURCE, TARGET>
{

	@Override
	public void populate(final SOURCE source, final TARGET target) throws ConversionException
	{
		target.setStockLevelStatus(source.getStockLevelStatus());

	}
}
