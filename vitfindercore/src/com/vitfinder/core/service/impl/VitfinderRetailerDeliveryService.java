// vtf-40
package com.vitfinder.core.service.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.commerceservices.delivery.impl.DefaultDeliveryService;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeValueModel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.vitfinder.core.dao.impl.VifinderRetailerDeliveryModeDao;


public class VitfinderRetailerDeliveryService extends DefaultDeliveryService
{

	private VifinderRetailerDeliveryModeDao countryZoneDeliveryModeDao;


	/**
	 * @return the countryZoneDeliveryModeDao
	 */
	public VifinderRetailerDeliveryModeDao getCountryZoneDeliveryModeDao()
	{
		return countryZoneDeliveryModeDao;
	}


	/**
	 * @param countryZoneDeliveryModeDao
	 *           the countryZoneDeliveryModeDao to set
	 */
	@Required
	public void setCountryZoneDeliveryModeDao(final VifinderRetailerDeliveryModeDao countryZoneDeliveryModeDao)
	{
		this.countryZoneDeliveryModeDao = countryZoneDeliveryModeDao;
	}

	public ZoneDeliveryModeValueModel getDeliveryPriceForAnonymousUser(final DeliveryModeModel deliveryModeModel)
	{
		return getCountryZoneDeliveryModeDao().getDeliveryPriceForAnonymousUser(deliveryModeModel);
	}

	public List<DeliveryModeModel> getSupportedDeliveryModeListForOrder(final AbstractOrderModel abstractOrder,
			final String retailer)
	{
		validateParameterNotNull(abstractOrder, "abstractOrder model cannot be null");
		final List<DeliveryModeModel> deliveryModes = new ArrayList<DeliveryModeModel>();
		deliveryModes.addAll(getCountryZoneDeliveryModeDao().findDeliveryModes(abstractOrder, retailer));
		sortDeliveryModes(deliveryModes, abstractOrder);
		return deliveryModes;
	}
}
