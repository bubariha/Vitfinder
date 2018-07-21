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
package com.vitfinder.facades.facades;

import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.impl.DefaultCartFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.retailer.RetailerModel;
import de.hybris.platform.order.CartFactory;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vitfinder.core.model.RetailerVariantProductModel;
import com.vitfinder.core.model.VitfinderGenericProductModel;
import com.vitfinder.core.sort.BasketSort;
import com.vitfinder.core.strategies.impl.VitfinderCommerceUpdateCartEntryStrategy;
import com.vitfinder.facades.service.EanProductResolverFromGenericProduct;
import com.vitfinder.facades.strategy.impl.VitfinderCommerceAddToCartStrategy;


/**
 * Implementation for {@link CartFacade}. Delivers main functionality for cart.
 */
public class VitfinderCartFacade extends DefaultCartFacade
{

	private CartFactory cartFactory;

	private ModelService modelService;

	private FlexibleSearchService flexibleSearchService;

	private EanProductResolverFromGenericProduct eanProductResolverFromGenericProduct;

	private VitfinderCommerceUpdateCartEntryStrategy vitfinderCommerceUpdateCartEntryStrategy;

	private VitfinderCommerceAddToCartStrategy vitfinderCommerceAddToCartStrategy;

	@Override
	public CartModificationData addToCart(final String code, final long quantity) throws CommerceCartModificationException
	{
		ProductModel product = getProductService().getProductForCode(code);
		VitfinderGenericProductModel genericProduct = null;
		if (product instanceof VitfinderGenericProductModel)
		{
			genericProduct = (VitfinderGenericProductModel) product;
			final ProductData productData = getEanProductResolverFromGenericProduct().resolveEanProduct(null, null, product);
			if (productData == null)
			{
				throw new CommerceCartModificationException("no ean product match for this generic product");
			}
			product = getProductService().getProductForCode(productData.getCode());
			final CommerceCartModification cartModification = createOrUpdateGenericProductEntry(product, quantity, genericProduct);
			return getCartModificationConverter().convert(cartModification);
		}
		final CartModel cartModel = getCartService().getSessionCart();
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cartModel);
		parameter.setQuantity(quantity);
		parameter.setProduct(product);
		parameter.setUnit(product.getUnit());
		parameter.setCreateNewEntry(false);
		final CommerceCartModification modification = getCommerceCartService().addToCart(parameter);
		/*
		 * final AbstractOrderEntryModel cartEntry = modification.getEntry(); if (cartEntry != null) {
		 * cartEntry.setGenericProduct(genericProduct); getModelService().save(cartEntry); }
		 * createOrUpdateRetailerBaskets(cartEntry, cartModel, quantity);
		 */
		return getCartModificationConverter().convert(modification);
	}


	/**
	 * @param product
	 * @param quantity
	 * @param genericProduct
	 * @return
	 * @throws CommerceCartModificationException
	 */
	private CommerceCartModification createOrUpdateGenericProductEntry(final ProductModel product, final long quantity,
			final VitfinderGenericProductModel genericProduct) throws CommerceCartModificationException
	{
		final CartModel cartModel = getCartService().getSessionCart();
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cartModel);
		parameter.setQuantity(quantity);
		parameter.setProduct(product);
		parameter.setUnit(product.getUnit());
		parameter.setCreateNewEntry(false);
		final CommerceCartModification modification = getCommerceCartService().addToCart(parameter);
		final AbstractOrderEntryModel cartEntry = modification.getEntry();
		if (cartEntry != null)
		{
			cartEntry.setGenericProduct(genericProduct);
			getModelService().save(cartEntry);
		}
		createOrUpdateRetailerBasketGenericProductEntry(cartModel, cartEntry);
		return modification;
	}

	protected PageableData createPageableData(final int pageNumber, final int pageSize, final String sortCode)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(pageNumber);
		pageableData.setSort(sortCode);
		pageableData.setPageSize(pageSize);
		return pageableData;
	}

	/**
	 * @param cartModel
	 * @param cartEntry
	 */
	private void createOrUpdateRetailerBasketGenericProductEntry(final CartModel cartModel,
			final AbstractOrderEntryModel cartEntry) throws CommerceCartModificationException
	{

		// YTODO Auto-generated method stub
		final List<String> allRetailers = getListOfRetailers();
		final Set<String> retailers = new HashSet<String>();
		List<CartModel> retailerBaskets = cartModel.getRetailerBaskets();
		if (retailerBaskets.isEmpty())
		{
			retailerBaskets = new ArrayList<CartModel>(retailerBaskets);
		}

		if (retailerBaskets.size() > 0)
		{
			for (final CartModel retailerCart : retailerBaskets)
			{
				retailers.add(retailerCart.getRetailer().isEmpty() ? "" : retailerCart.getRetailer().get(0).getCode());
			}
		}

		for (final String retailerOne : allRetailers)
		{
			RetailerVariantProductModel varient = null;

			final PageableData pageableData = createPageableData(0, 10, null);
			final SearchStateData searchState = new SearchStateData();
			final SearchQueryData searchQueryData = new SearchQueryData();
			searchQueryData.setValue(cartEntry.getGenericProduct().getCode() + ":price-asc:retailer:" + retailerOne);
			searchState.setQuery(searchQueryData);


			final ProductData productData = getEanProductResolverFromGenericProduct().resolveEanProduct(pageableData, searchState,
					cartEntry.getGenericProduct());
			if (productData == null)
			{
				continue;
			}
			final ProductModel productModel = getProductService().getProductForCode(productData.getCode());

			if (productModel instanceof RetailerVariantProductModel)
			{
				varient = (RetailerVariantProductModel) productModel;
				final String retailer = varient.getRetailer();
				final long quantity = cartEntry.getQuantity();
				//				retailers.add(retailer);
				if (retailers.contains(retailer))
				{
					// already cart there
					for (final CartModel retailerCart : retailerBaskets)
					{
						if (retailer.equalsIgnoreCase(
								retailerCart.getRetailer().isEmpty() ? "" : retailerCart.getRetailer().get(0).getCode()))
						{
							//retailer basket
							final CommerceCartModification modification = addRetailerCartEntry(retailerCart, productModel, quantity);
							final AbstractOrderEntryModel retailerCartEntry = modification.getEntry();
							retailerCartEntry.setGenericProduct(cartEntry.getGenericProduct());
							getModelService().save(retailerCartEntry);
						}
					}
				}
				else
				{
					// creating new retailer cart
					final CartModel newRetailerCart = getCartFactory().createCart();
					final List<RetailerModel> newRetailer = getRetailer(varient);
					newRetailerCart.setRetailer(newRetailer);
					getModelService().save(newRetailerCart);
					retailerBaskets = new ArrayList<CartModel>(retailerBaskets);
					retailerBaskets.add(newRetailerCart);
					cartModel.setRetailerBaskets(retailerBaskets);
					getModelService().save(cartModel);
					final CommerceCartModification modification = addRetailerCartEntry(newRetailerCart, productModel, quantity);
					final AbstractOrderEntryModel retailerCartEntry = modification.getEntry();
					retailerCartEntry.setGenericProduct(cartEntry.getGenericProduct());
					getModelService().save(retailerCartEntry);
					getModelService().save(cartModel);
					retailers.add(newRetailer.get(0).getCode());
				}
			}
		}


	}


	/**
	 * @return
	 */
	private List<String> getListOfRetailers()
	{
		// YTODO Auto-generated method stub
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(
				"SELECT {" + RetailerModel.CODE + "} FROM  {" + RetailerModel._TYPECODE + "}");
		searchQuery.setResultClassList(Collections.singletonList(String.class));
		final SearchResult searchResult = getFlexibleSearchService().search(searchQuery);
		return searchResult.getResult();
	}


	/**
	 * @param cartEntry
	 * @param cartModel
	 * @param quantity
	 * @throws CommerceCartModificationException
	 */
	public void createOrUpdateRetailerBaskets(final AbstractOrderEntryModel cartEntry, final CartModel cartModel,
			final long quantity) throws CommerceCartModificationException
	{
		// YTODO Auto-generated method stub
		final List<VariantProductModel> retailerProducts = getListOfProductsByEAN(cartEntry.getProduct());
		final Set<String> retailers = new HashSet<String>();
		List<CartModel> retailerBaskets = cartModel.getRetailerBaskets();
		if (retailerBaskets.isEmpty())
		{
			retailerBaskets = new ArrayList<CartModel>(retailerBaskets);
		}

		if (retailerBaskets.size() > 0)
		{
			for (final CartModel retailerCart : retailerBaskets)
			{
				retailers.add(retailerCart.getRetailer().isEmpty() ? "" : retailerCart.getRetailer().get(0).getCode());
			}
		}

		for (final VariantProductModel productModel : retailerProducts)
		{
			RetailerVariantProductModel varient = null;
			if (productModel instanceof RetailerVariantProductModel)
			{
				varient = (RetailerVariantProductModel) productModel;
				final String retailer = varient.getRetailer();
				//				retailers.add(retailer);
				if (retailers.contains(retailer))
				{
					// already cart there
					//					CartModel retailerBasket = null;
					for (final CartModel retailerCart : retailerBaskets)
					{
						if (retailer.equalsIgnoreCase(
								retailerCart.getRetailer().isEmpty() ? "" : retailerCart.getRetailer().get(0).getCode()))
						{
							//retailer basket
							final CommerceCartModification modification = addRetailerCartEntry(retailerCart, productModel, quantity);
							final AbstractOrderEntryModel retailerCartEntry = modification.getEntry();
							retailerCartEntry.setGenericProduct(cartEntry.getGenericProduct());
							getModelService().save(retailerCartEntry);
						}
					}
				}
				else
				{
					// creating new retailer cart
					final CartModel newRetailerCart = getCartFactory().createCart();
					final List<RetailerModel> newRetailer = getRetailer(varient);
					newRetailerCart.setRetailer(newRetailer);
					getModelService().save(newRetailerCart);
					retailerBaskets = new ArrayList<CartModel>(retailerBaskets);
					retailerBaskets.add(newRetailerCart);
					cartModel.setRetailerBaskets(retailerBaskets);
					getModelService().save(cartModel);
					final CommerceCartModification modification = addRetailerCartEntry(newRetailerCart, productModel, quantity);
					final AbstractOrderEntryModel retailerCartEntry = modification.getEntry();
					retailerCartEntry.setGenericProduct(cartEntry.getGenericProduct());
					getModelService().save(retailerCartEntry);
					getModelService().save(cartModel);
					retailers.add(newRetailer.get(0).getCode());
				}
			}
		}
	}


	/**
	 * @param productModel
	 * @return
	 */
	private List<RetailerModel> getRetailer(final RetailerVariantProductModel productModel)
	{
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery("SELECT {" + RetailerModel.PK + "} FROM  {"
				+ RetailerModel._TYPECODE + "} where {" + RetailerModel.CODE + "}=?" + RetailerModel.CODE);
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(RetailerModel.CODE, productModel.getRetailer());
		searchQuery.addQueryParameters(params);
		searchQuery.setResultClassList(Collections.singletonList(ProductModel.class));
		final SearchResult searchResult = getFlexibleSearchService().search(searchQuery);
		return searchResult.getResult();
	}


	/**
	 * @param retailerCart
	 * @param productModel
	 * @param quantity
	 * @throws CommerceCartModificationException
	 */
	private CommerceCartModification addRetailerCartEntry(final CartModel retailerCart, final ProductModel productModel,
			final long quantity) throws CommerceCartModificationException
	{
		// YTODO Auto-generated method stub
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(retailerCart);
		parameter.setQuantity(quantity);
		parameter.setProduct(productModel);
		parameter.setUnit(productModel.getUnit());
		parameter.setCreateNewEntry(false);

		final CommerceCartModification modification = getVitfinderCommerceAddToCartStrategy().addToRetailerCart(parameter);
		return modification;
	}


	/**
	 * @param product
	 * @return
	 */
	private List<VariantProductModel> getListOfProductsByEAN(final ProductModel productModel)
	{

		if (productModel.getEan() != null && !productModel.getEan().isEmpty())
		{
			final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(
					"SELECT {" + RetailerVariantProductModel.PK + "} FROM  {" + RetailerVariantProductModel._TYPECODE + "} where {"
							+ RetailerVariantProductModel.EAN + "}=?" + RetailerVariantProductModel.EAN);
			final Map<String, Object> params = new HashMap<String, Object>();
			params.put(RetailerVariantProductModel.EAN, productModel.getEan());
			searchQuery.addQueryParameters(params);
			searchQuery.setResultClassList(Collections.singletonList(ProductModel.class));
			final SearchResult searchResult = getFlexibleSearchService().search(searchQuery);
			return searchResult.getResult();
		}
		return new ArrayList<VariantProductModel>();

	}


	@Override
	public CartModificationData addToCart(final String code, final long quantity, final String storeId)
			throws CommerceCartModificationException
	{
		return super.addToCart(code, quantity, storeId);
	}

	public CartModificationData updateRetailerCartEntry(final long entryNumber, final long quantity, final String cartId)
			throws CommerceCartModificationException
	{
		final CartModel cartModel = getCartService().getSessionCart();
		CartModel updateRetailerCart = null;
		for (final CartModel retailerCart : cartModel.getRetailerBaskets())
		{
			if (retailerCart.getCode().equalsIgnoreCase(cartId))
			{
				updateRetailerCart = retailerCart;
				break;
			}
		}
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(updateRetailerCart);
		parameter.setEntryNumber(entryNumber);
		parameter.setQuantity(quantity);

		final CommerceCartModification modification = getCommerceCartService().updateQuantityForCartEntry(parameter);

		return getCartModificationConverter().convert(modification);
	}



	public CartModificationData updateCartEntry(final long entryNumber, final long quantity, final CartModel cartModel,
			final VitfinderGenericProductModel product) throws CommerceCartModificationException
	{
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cartModel);
		parameter.setEntryNumber(entryNumber);
		parameter.setQuantity(quantity);
		parameter.setGenericProduct(product);
		final CommerceCartModification modification = getCommerceCartService().updateQuantityForCartEntry(parameter);

		return getCartModificationConverter().convert(modification);
	}


	/**
	 * @param entryNumber
	 * @param i
	 * @param cartModel
	 * @param genericProduct
	 */
	public void updateGenericCartEntry(final Integer entryNumber, final int quantity, final CartModel cartModel,
			final VitfinderGenericProductModel genericProduct) throws CommerceCartModificationException
	{
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cartModel);
		parameter.setEntryNumber(entryNumber);
		parameter.setQuantity(quantity);
		parameter.setGenericProduct(genericProduct);

		final CommerceCartModification modification = getVitfinderCommerceUpdateCartEntryStrategy()
				.updateQuantityForGenericCartEntry(parameter);

		//		return getCartModificationConverter().convert(modification);

	}

	/**
	 * @return the cartFactory
	 */
	public CartFactory getCartFactory()
	{
		return cartFactory;
	}


	/**
	 * @param cartFactory
	 *           the cartFactory to set
	 */
	public void setCartFactory(final CartFactory cartFactory)
	{
		this.cartFactory = cartFactory;
	}


	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}


	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}


	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}


	/**
	 * @param cartData
	 * @param sortBean
	 * @return
	 */
	public CartData doRetailerBasketSort(final CartData cartData, final String sortBeanId)
	{
		final BasketSort sortBean = Registry.getApplicationContext().getBean(sortBeanId, BasketSort.class);
		sortBean.sort(cartData);
		return cartData;
	}


	/**
	 * @return the eanProductResolverFromGenericProduct
	 */
	public EanProductResolverFromGenericProduct getEanProductResolverFromGenericProduct()
	{
		return eanProductResolverFromGenericProduct;
	}


	/**
	 * @param eanProductResolverFromGenericProduct
	 *           the eanProductResolverFromGenericProduct to set
	 */
	public void setEanProductResolverFromGenericProduct(
			final EanProductResolverFromGenericProduct eanProductResolverFromGenericProduct)
	{
		this.eanProductResolverFromGenericProduct = eanProductResolverFromGenericProduct;
	}


	/**
	 * @param sortBeanId
	 * @throws CommerceCartModificationException
	 *
	 */
	public void updateGenericCartEntry(final String sortBeanId) throws CommerceCartModificationException
	{
		final BasketSort sortBean = Registry.getApplicationContext().getBean(sortBeanId, BasketSort.class);
		sortBean.updateGenericCartEntry();
	}


	/**
	 * @return the vitfinderCommerceUpdateCartEntryStrategy
	 */
	public VitfinderCommerceUpdateCartEntryStrategy getVitfinderCommerceUpdateCartEntryStrategy()
	{
		return vitfinderCommerceUpdateCartEntryStrategy;
	}


	/**
	 * @param vitfinderCommerceUpdateCartEntryStrategy
	 *           the vitfinderCommerceUpdateCartEntryStrategy to set
	 */
	public void setVitfinderCommerceUpdateCartEntryStrategy(
			final VitfinderCommerceUpdateCartEntryStrategy vitfinderCommerceUpdateCartEntryStrategy)
	{
		this.vitfinderCommerceUpdateCartEntryStrategy = vitfinderCommerceUpdateCartEntryStrategy;
	}


	/**
	 * @return the vitfinderCommerceAddToCartStrategy
	 */
	public VitfinderCommerceAddToCartStrategy getVitfinderCommerceAddToCartStrategy()
	{
		return vitfinderCommerceAddToCartStrategy;
	}


	/**
	 * @param vitfinderCommerceAddToCartStrategy
	 *           the vitfinderCommerceAddToCartStrategy to set
	 */
	public void setVitfinderCommerceAddToCartStrategy(final VitfinderCommerceAddToCartStrategy vitfinderCommerceAddToCartStrategy)
	{
		this.vitfinderCommerceAddToCartStrategy = vitfinderCommerceAddToCartStrategy;
	}



}
