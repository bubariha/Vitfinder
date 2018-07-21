/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2015 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.vitfinder.core.strategies.impl;

import de.hybris.platform.commerceservices.order.CommerceCartMergingException;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCartMergingStrategy;
import de.hybris.platform.core.model.order.CartModel;

import java.util.List;


public class VitfinderCommerceCartMergingStrategy extends DefaultCommerceCartMergingStrategy
{


	@Override
	public void mergeCarts(final CartModel fromCart, final CartModel toCart, final List<CommerceCartModification> modifications)
			throws CommerceCartMergingException
	{
		//		super.mergeCarts(fromCart, toCart, modifications);
		//		final List<CartModel> actualRetailerBaskets = fromCart.getRetailerBaskets();
		//		final List<CartModel> oldRetailerCarts = toCart.getRetailerBaskets();
		//		for (final CartModel oldRetailerCart : oldRetailerCarts)
		//		{
		//			boolean isRetailerBasketThere = false;
		//			if (oldRetailerCart.getRetailer().size() > 0)
		//			{
		//				final String oldRetailer = oldRetailerCart.getRetailer().get(0).getCode();
		//				for (final CartModel actualRetailerBasket : actualRetailerBaskets)
		//				{
		//
		//					if (actualRetailerBasket.getRetailer().size() > 0)
		//					{
		//
		//						if (oldRetailer.equalsIgnoreCase(actualRetailerBasket.getRetailer().get(0).getCode()))
		//						{
		//							isRetailerBasketThere = true;
		//							super.mergeCarts(oldRetailerCart, actualRetailerBasket, modifications);
		//						}
		//					}
		//				}
		//				if (!isRetailerBasketThere)
		//				{
		//					toCart.getRetailerBaskets().add(oldRetailerCart);
		//					getModelService().save(toCart);
		//					getModelService().refresh(toCart);
		//				}
		//			}
		//		}

	}


}
