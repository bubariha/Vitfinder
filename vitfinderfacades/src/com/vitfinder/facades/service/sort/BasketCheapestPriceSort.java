/**
 *
 */
package com.vitfinder.facades.service.sort;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.model.order.CartModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



/**
 * @author nandakishore
 *
 */
public class BasketCheapestPriceSort extends AbstractBasketSort
{

	public static final String sort = "price-asc";

	@Override
	public boolean sort(final CartData cartData)
	{
		final List<CartData> sortedByPrice = new ArrayList<CartData>(cartData.getRetailerBaskets());
		Collections.sort(sortedByPrice, new Comparator<CartData>()
		{
			@Override
			public int compare(final CartData cartData1, final CartData cartData2)
			{
				return cartData1.getTotalPrice().getValue().subtract(cartData2.getTotalPrice().getValue()).intValue();
			}
		});
		cartData.setRetailerBaskets(sortedByPrice);
		return true;
	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.sort.BasketSort#updateGenericCartEntry()
	 */
	@Override
	public CartModel updateGenericCartEntry() throws CommerceCartModificationException
	{
		final CartModel cartModel = getCartService().getSessionCart();
		updateCartEntries(cartModel, sort, "");
		final List<CartModel> retailerBaskets = cartModel.getRetailerBaskets();
		getModelService().save(cartModel);
		getModelService().refresh(cartModel);
		for (final CartModel cart : retailerBaskets)
		{
			final String retailer = !cart.getRetailer().isEmpty() ? cart.getRetailer().get(0).getCode() : "";
			if (retailer.isEmpty())
			{
				getModelService().remove(cart);
				return null;
			}
			updateCartEntries(cart, sort, ":price-asc:retailer:" + retailer);
			getModelService().save(cart);
			getModelService().refresh(cart);
		}
		return null;
	}



}
