package com.vitfinder.facades.populators;

import de.hybris.platform.commercefacades.product.PriceDataFactory;
import de.hybris.platform.commercefacades.product.converters.populator.AbstractProductPopulator;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.price.CommercePriceService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Required;


/**
 * Populate the product data with the price information
 */
public class VitFinderProductPricePopulator<SOURCE extends ProductModel, TARGET extends ProductData>
		extends AbstractProductPopulator<SOURCE, TARGET>
{
	private CommercePriceService commercePriceService;
	private PriceDataFactory priceDataFactory;

	protected CommercePriceService getCommercePriceService()
	{
		return commercePriceService;
	}

	@Required
	public void setCommercePriceService(final CommercePriceService commercePriceService)
	{
		this.commercePriceService = commercePriceService;
	}

	protected PriceDataFactory getPriceDataFactory()
	{
		return priceDataFactory;
	}

	@Required
	public void setPriceDataFactory(final PriceDataFactory priceDataFactory)
	{
		this.priceDataFactory = priceDataFactory;
	}

	@Override
	public void populate(final SOURCE productModel, final TARGET productData) throws ConversionException
	{

		final PriceDataType priceType;
		final PriceInformation info;
		if (CollectionUtils.isEmpty(productModel.getVariants()))
		{
			priceType = PriceDataType.BUY;
			info = getCommercePriceService().getWebPriceForProduct(productModel);
		}
		else
		{
			priceType = PriceDataType.FROM;
			info = getCommercePriceService().getFromPriceForProduct(productModel);
		}

		if (info != null)
		{
			final PriceData priceData = getPriceDataFactory().create(priceType, BigDecimal.valueOf(info.getPriceValue().getValue()),
					info.getPriceValue().getCurrencyIso());

			final PriceRowModel prm = productModel.getEurope1Prices().iterator().next();

			if (prm.getWasPrice() != null)
			{
				priceData.setWasPrice(new BigDecimal(prm.getWasPrice().doubleValue()));

				final PriceData wasPriceData = getPriceDataFactory().create(priceType,
						BigDecimal.valueOf(prm.getWasPrice().doubleValue()), info.getPriceValue().getCurrencyIso());
				priceData.setFormattedWasPrice(wasPriceData.getFormattedValue());

				final double saveprice = prm.getWasPrice().doubleValue() - info.getPriceValue().getValue();

				final PriceData savePriceData = getPriceDataFactory().create(priceType, BigDecimal.valueOf(saveprice),
						info.getPriceValue().getCurrencyIso());
				priceData.setFormattedSavePrice(savePriceData.getFormattedValue());

				priceData.setDiscountValue(prm.getDiscountValue());
			}

			productData.setPrice(priceData);
		}
		else
		{
			productData.setPurchasable(Boolean.FALSE);
		}
	}

}



