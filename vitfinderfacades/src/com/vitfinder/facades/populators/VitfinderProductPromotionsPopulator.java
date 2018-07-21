
package com.vitfinder.facades.populators;

import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.vitfinder.core.model.RetailerVariantProductModel;


public class VitfinderProductPromotionsPopulator<SOURCE extends ProductModel, TARGET extends ProductData>
		extends AbstractProductPopulator<SOURCE, TARGET>
{

	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{
		if (productModel.getPromotionalMessage() != null)
		{
			productData.setPromotionalMessage(productModel.getPromotionalMessage());
		}

		if (productModel.getMerchantURL() != null)
		{
			productData.setMerchantURL(productModel.getMerchantURL());
		}

		if ((productModel instanceof RetailerVariantProductModel)
				&& ((RetailerVariantProductModel) productModel).getRetailer() != null)
		{
			productData.setRetailer(((RetailerVariantProductModel) productModel).getRetailer());
		}

	}
}
