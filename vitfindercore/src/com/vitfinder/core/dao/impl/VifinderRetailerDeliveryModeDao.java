// vtf-40

package com.vitfinder.core.dao.impl;

import de.hybris.platform.commerceservices.delivery.dao.impl.DefaultCountryZoneDeliveryModeDao;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.retailer.RetailerModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeModel;
import de.hybris.platform.deliveryzone.model.ZoneDeliveryModeValueModel;
import de.hybris.platform.jalo.link.Link;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VifinderRetailerDeliveryModeDao extends DefaultCountryZoneDeliveryModeDao
{

	private static final String RETAILER_DELIVERY_MODE_RELATION = "Retailer2DeliveryModeRel";
	private static final String ZONE_COUNTRY_RELATION = "ZoneCountryRelation";
	private static final String STORE_TO_DELIVERY_MODE_RELATION = "BaseStore2DeliveryModeRel";


	public ZoneDeliveryModeValueModel getDeliveryPriceForAnonymousUser(final DeliveryModeModel deliveryModeModel)
	{
		final StringBuilder query = new StringBuilder("SELECT {");
		query.append(ItemModel.PK);
		query.append("} FROM {");
		query.append(ZoneDeliveryModeValueModel._TYPECODE);
		query.append("} WHERE {");
		query.append(ZoneDeliveryModeValueModel.DELIVERYMODE);
		query.append("}=?deliveryMode");

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("deliveryMode", deliveryModeModel);

		return doSearch(query.toString(), params, ZoneDeliveryModeValueModel.class).iterator().next();
	}

	public Collection<DeliveryModeModel> findDeliveryModes(final AbstractOrderModel abstractOrder, final String retailer)
	{
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {zdm:").append(ItemModel.PK).append("}");
		query.append(" FROM { ").append(ZoneDeliveryModeValueModel._TYPECODE).append(" AS val");
		query.append(" JOIN ").append(ZoneDeliveryModeModel._TYPECODE).append(" AS zdm");
		query.append(" ON {val:").append(ZoneDeliveryModeValueModel.DELIVERYMODE).append("}={zdm:").append(ItemModel.PK)
				.append('}');
		query.append(" JOIN ").append(ZONE_COUNTRY_RELATION).append(" AS z2c");
		query.append(" ON {val:").append(ZoneDeliveryModeValueModel.ZONE).append("}={z2c:").append(Link.SOURCE).append('}');
		query.append(" JOIN ").append(STORE_TO_DELIVERY_MODE_RELATION).append(" AS s2d");
		query.append(" ON {val:").append(ZoneDeliveryModeValueModel.DELIVERYMODE).append("}={s2d:").append(Link.TARGET).append('}');

		query.append(" JOIN ").append(RETAILER_DELIVERY_MODE_RELATION).append(" AS r2d");
		query.append(" ON {val:").append(ZoneDeliveryModeValueModel.DELIVERYMODE).append("}={r2d:").append(Link.TARGET).append('}');

		query.append(" } WHERE {val:").append(ZoneDeliveryModeValueModel.CURRENCY).append("}=?currency");
		query.append(" AND {z2c:").append(Link.TARGET).append("}=?deliveryCountry");
		query.append(" AND {s2d:").append(Link.SOURCE).append("}=?store");
		query.append(" AND {zdm:").append(ZoneDeliveryModeModel.NET).append("}=?net");
		query.append(" AND {zdm:").append(ZoneDeliveryModeModel.ACTIVE).append("}=?active");

		final Map<String, Object> params = new HashMap<String, Object>();

		params.put("currency", abstractOrder.getCurrency());
		params.put("net", abstractOrder.getNet());
		params.put("active", Boolean.TRUE);
		params.put("store", abstractOrder.getStore());

		if (abstractOrder.getDeliveryAddress() != null)
		{
			params.put("deliveryCountry", abstractOrder.getDeliveryAddress().getCountry());
		}
		else
		{
			params.put("deliveryCountry", abstractOrder.getStore().getDeliveryCountries());
		}

		abstractOrder.setRetailer(findRetailers(retailer));

		if (abstractOrder.getRetailer().isEmpty() || abstractOrder.getRetailer() == null)
		{
			return new ArrayList<DeliveryModeModel>();
		}

		query.append(" AND {r2d:").append(Link.SOURCE).append("}=?retailer");
		params.put("retailer", abstractOrder.getRetailer());

		return doSearch(query.toString(), params, DeliveryModeModel.class);

	}

	public List<RetailerModel> findRetailers(final String retailer)
	{
		final StringBuilder query = new StringBuilder("SELECT {PK} FROM {RETAILER} WHERE {CODE} = ?retailer");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("retailer", retailer);
		return doSearch(query.toString(), params, RetailerModel.class);
	}

}
