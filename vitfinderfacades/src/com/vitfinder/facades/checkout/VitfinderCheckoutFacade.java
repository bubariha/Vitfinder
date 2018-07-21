// vtf-40
package com.vitfinder.facades.checkout;

import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.order.data.ZoneDeliveryModeData;
import de.hybris.platform.commercefacades.order.impl.DefaultCheckoutFacade;
import de.hybris.platform.commercefacades.product.data.PriceDataType;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeValueModel;
import de.hybris.platform.util.PriceValue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.vitfinder.core.service.impl.VitfinderRetailerDeliveryService;


public class VitfinderCheckoutFacade extends DefaultCheckoutFacade
{

	private VitfinderRetailerDeliveryService deliveryService;

	/**
	 * @return the deliveryService
	 */
	@Override
	public VitfinderRetailerDeliveryService getDeliveryService()
	{
		return deliveryService;
	}

	/**
	 * @param deliveryService
	 *           the deliveryService to set
	 */
	@Required
	public void setDeliveryService(final VitfinderRetailerDeliveryService deliveryService)
	{
		this.deliveryService = deliveryService;
	}

	@Override
	protected DeliveryModeData convert(final DeliveryModeModel deliveryModeModel)
	{
		if (deliveryModeModel instanceof ZoneDeliveryModeModel)
		{
			final ZoneDeliveryModeModel zoneDeliveryModeModel = (ZoneDeliveryModeModel) deliveryModeModel;
			final CartModel cartModel = getCart();
			if (cartModel != null)
			{
				final ZoneDeliveryModeData zoneDeliveryModeData = getZoneDeliveryModeConverter().convert(zoneDeliveryModeModel);
				final PriceValue deliveryCost = getDeliveryService().getDeliveryCostForDeliveryModeAndAbstractOrder(deliveryModeModel,
						cartModel);
				if (deliveryCost != null)
				{
					zoneDeliveryModeData.setDeliveryCost(getPriceDataFactory().create(PriceDataType.BUY,
							BigDecimal.valueOf(deliveryCost.getValue()), deliveryCost.getCurrencyIso()));
				}
				else
				{

					final ZoneDeliveryModeValueModel deliveryCostForAnonymousUser = getDeliveryService()
							.getDeliveryPriceForAnonymousUser(deliveryModeModel);
					zoneDeliveryModeData.setDeliveryCost(getPriceDataFactory().create(PriceDataType.BUY,
							BigDecimal.valueOf(deliveryCostForAnonymousUser.getValue().doubleValue()),
							deliveryCostForAnonymousUser.getCurrency().getIsocode()));
				}
				return zoneDeliveryModeData;
			}
		}
		else
		{
			return getDeliveryModeConverter().convert(deliveryModeModel);
		}

		return null;
	}

	/**
	 * This method is responsible to fetch Delivery Modes provided by Retailer
	 *
	 * @param retailerName
	 *           The Retailer
	 * @return Delivery Modes of Retailer
	 */
	public List<? extends DeliveryModeData> getSupportedDeliveryModes(final String retailerName)
	{
		final List<DeliveryModeData> result = new ArrayList<DeliveryModeData>();
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final Collection<DeliveryModeModel> supportedDeliveryModes = getDeliveryService()
					.getSupportedDeliveryModeListForOrder(cartModel, retailerName);

			for (final DeliveryModeModel deliveryModeModel : supportedDeliveryModes)
			{
				result.add(convert(deliveryModeModel));
			}
		}

		return result;
	}

}
