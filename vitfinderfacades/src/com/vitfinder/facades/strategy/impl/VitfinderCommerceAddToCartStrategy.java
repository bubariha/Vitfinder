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
package com.vitfinder.facades.strategy.impl;

import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceAddToCartStrategy;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import com.vitfinder.facades.facades.VitfinderCartFacade;


public class VitfinderCommerceAddToCartStrategy extends DefaultCommerceAddToCartStrategy
{

	private CartFacade cartFacade;

	/**
	 * Adds an item to the cart for pickup in a given location
	 *
	 * @param parameter
	 *           Cart parameters
	 * @return Cart modification information
	 * @throws de.hybris.platform.commerceservices.order.CommerceCartModificationException
	 *
	 */
	@Override
	public CommerceCartModification addToCart(final CommerceCartParameter parameter) throws CommerceCartModificationException
	{
		final CommerceCartModification modification = super.addToCart(parameter);
		final AbstractOrderEntryModel cartEntry = modification.getEntry();
		if (cartEntry != null)
		{
			cartEntry.setGenericProduct(parameter.getGenericProduct());
			getModelService().save(cartEntry);
		}

		((VitfinderCartFacade) getCartFacade()).createOrUpdateRetailerBaskets(cartEntry, parameter.getCart(),
				parameter.getQuantity());
		return modification;


	}


	public CommerceCartModification addToRetailerCart(final CommerceCartParameter parameter)
			throws CommerceCartModificationException
	{
		final CommerceCartModification modification = super.addToCart(parameter);
		final AbstractOrderEntryModel cartEntry = modification.getEntry();
		if (cartEntry != null)
		{
			cartEntry.setGenericProduct(parameter.getGenericProduct());
			getModelService().save(cartEntry);
		}

		return modification;


	}

	@Override
	protected long getAvailableStockLevel(final ProductModel productModel, final PointOfServiceModel pointOfServiceModel)
	{
		final BaseStoreModel baseStore = getBaseStoreService().getCurrentBaseStore();
		if (!getCommerceStockService().isStockSystemEnabled(baseStore)
				|| "instock".equalsIgnoreCase(productModel.getStockLevelStatus().toString()))
		{
			return getForceInStockMaxQuantity();
		}
		else
		{
			final Long availableStockLevel;

			if (pointOfServiceModel == null)
			{
				availableStockLevel = getCommerceStockService().getStockLevelForProductAndBaseStore(productModel, baseStore);
			}
			else
			{
				availableStockLevel = getCommerceStockService().getStockLevelForProductAndPointOfService(productModel,
						pointOfServiceModel);
			}

			if (availableStockLevel == null)
			{
				return getForceInStockMaxQuantity();
			}
			else
			{
				return availableStockLevel.longValue();
			}
		}
	}

	/**
	 * @return the cartFacade
	 */
	public CartFacade getCartFacade()
	{
		return cartFacade;
	}

	/**
	 * @param cartFacade
	 *           the cartFacade to set
	 */
	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

}
