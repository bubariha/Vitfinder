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
package com.vitfinder.storefront.controllers.misc;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.AbstractController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddToCartForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.AddToCartOrderForm;
import de.hybris.platform.basecommerce.enums.StockLevelStatus;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.OrderFacade;
import de.hybris.platform.commercefacades.order.converters.populator.GroupCartModificationListPopulator;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vitfinder.storefront.controllers.ControllerConstants;


/**
 * Controller for Add to Cart functionality which is not specific to a certain page.
 */
@Controller
@Scope("tenant")
public class AddToCartController extends AbstractController
{
	private static final String TYPE_MISMATCH_ERROR_CODE = "typeMismatch";
	private static final String ERROR_MSG_TYPE = "errorMsg";
	private static final String MULTID_ERROR_MSGS_TYPE = "multidErrorMsgs";
	private static final String QUANTITY_INVALID_BINDING_MESSAGE_KEY = "basket.error.quantity.invalid.binding";
	private static final String SHOWN_PRODUCT_COUNT = "vitfinderstorefront.storefront.minicart.shownProductCount";

	protected static final Logger LOG = Logger.getLogger(AddToCartController.class);

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "groupCartModificationListPopulator")
	private GroupCartModificationListPopulator groupCartModificationListPopulator;

	@Resource(name = "orderFacade")
	private OrderFacade orderFacade;


	@RequestMapping(value = "/cart/add", method = RequestMethod.POST, produces = "application/json")
	public String addToCart(@RequestParam("productCodePost") final String code, final Model model, @Valid final AddToCartForm form,
			final BindingResult bindingErrors)
	{
		if (bindingErrors.hasErrors())
		{
			return getViewWithBindingErrorMessages(model, bindingErrors);
		}

		final long qty = form.getQty();

		if (qty <= 0)
		{
			model.addAttribute(ERROR_MSG_TYPE, "basket.error.quantity.invalid");
			model.addAttribute("quantity", Long.valueOf(0L));
		}
		else
		{
			try
			{
				final CartModificationData cartModification = addProductToCart(code, model, qty);

				//vtf 89
				if (cartModification.getEntry().getProduct().getStock().getStockLevelStatus() != null && cartModification.getEntry()
						.getProduct().getStock().getStockLevelStatus().getCode().equals(StockLevelStatus.OUTOFSTOCK.getCode()))
				{
					model.addAttribute(ERROR_MSG_TYPE, "product.out.of.stock");
				}
				if (cartModification.getQuantityAdded() == 0L)
				{
					model.addAttribute(ERROR_MSG_TYPE, "basket.information.quantity.noItemsAdded." + cartModification.getStatusCode());
				}
				else if (cartModification.getQuantityAdded() < qty)
				{
					model.addAttribute(ERROR_MSG_TYPE,
							"basket.information.quantity.reducedNumberOfItemsAdded." + cartModification.getStatusCode());
				}
				model.addAttribute("product", productFacade.getProductForCodeAndOptions(code, Arrays.asList(ProductOption.BASIC)));

			}
			catch (final CommerceCartModificationException ex)
			{
				model.addAttribute(ERROR_MSG_TYPE, "basket.error.occurred");
				model.addAttribute("quantity", Long.valueOf(0L));
				if (ex.getMessage() != null && ex.getMessage().contains("generic"))
				{
					model.addAttribute(ERROR_MSG_TYPE, ex.getMessage());
				}
			}
		}

		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	protected String getViewWithBindingErrorMessages(final Model model, final BindingResult bindingErrors)
	{
		for (final ObjectError error : bindingErrors.getAllErrors())
		{
			if (isTypeMismatchError(error))
			{
				model.addAttribute(ERROR_MSG_TYPE, QUANTITY_INVALID_BINDING_MESSAGE_KEY);
			}
			else
			{
				model.addAttribute(ERROR_MSG_TYPE, error.getDefaultMessage());
			}
		}
		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	protected boolean isTypeMismatchError(final ObjectError error)
	{
		return error.getCode().equals(TYPE_MISMATCH_ERROR_CODE);
	}

	@RequestMapping(value = "/cart/addGrid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final String addGridToCart(@RequestBody final AddToCartOrderForm form, final Model model)
	{
		final Set<String> multidErrorMsgs = new HashSet<String>();
		final List<CartModificationData> modificationDataList = new ArrayList<CartModificationData>();

		for (final OrderEntryData cartEntry : form.getCartEntries())
		{
			if (!isValidProductEntry(cartEntry))
			{
				LOG.error("Error processing entry");
			}
			else if (!isValidQuantity(cartEntry))
			{
				multidErrorMsgs.add("basket.error.quantity.invalid");
			}
			else
			{
				try
				{
					final long qty = cartEntry.getQuantity().longValue();
					final CartModificationData cartModificationData = cartFacade.addToCart(cartEntry.getProduct().getCode(), qty);
					if (cartModificationData.getQuantityAdded() == 0L)
					{
						multidErrorMsgs.add("basket.information.quantity.noItemsAdded." + cartModificationData.getStatusCode());
					}
					else if (cartModificationData.getQuantityAdded() < qty)
					{
						multidErrorMsgs
								.add("basket.information.quantity.reducedNumberOfItemsAdded." + cartModificationData.getStatusCode());
					}

					modificationDataList.add(cartModificationData);

				}
				catch (final CommerceCartModificationException ex)
				{
					multidErrorMsgs.add("basket.error.occurred");
				}
			}
		}

		if (CollectionUtils.isNotEmpty(modificationDataList))
		{
			groupCartModificationListPopulator.populate(null, modificationDataList);

			model.addAttribute("modifications", modificationDataList);
		}

		if (CollectionUtils.isNotEmpty(multidErrorMsgs))
		{
			model.addAttribute("multidErrorMsgs", multidErrorMsgs);
		}

		model.addAttribute("numberShowing", Integer.valueOf(Config.getInt(SHOWN_PRODUCT_COUNT, 3)));


		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}

	protected boolean isValidProductEntry(final OrderEntryData cartEntry)
	{
		return cartEntry.getProduct() != null && StringUtils.isNotBlank(cartEntry.getProduct().getCode());
	}

	protected boolean isValidQuantity(final OrderEntryData cartEntry)
	{
		return cartEntry.getQuantity() != null && cartEntry.getQuantity().longValue() >= 1L;
	}

	/**
	 * @param code
	 * @param model
	 * @param qty
	 * @throws CommerceCartModificationException
	 */
	private CartModificationData addProductToCart(final String code, final Model model, final long qty)
			throws CommerceCartModificationException
	{
		final CartModificationData cartModification = cartFacade.addToCart(code, qty);
		final String productName = cartModification.getEntry().getProduct().getName().replaceAll("'", "");
		cartModification.getEntry().getProduct().setName(productName);
		model.addAttribute("quantity", Long.valueOf(cartModification.getQuantityAdded()));
		model.addAttribute("entry", cartModification.getEntry());
		model.addAttribute("cartCode", cartModification.getCartCode());
		return cartModification;
	}

	@RequestMapping(value = "/cart/addOrder/{orderCodePost}", method = RequestMethod.GET)
	public String addOrderToCart(@PathVariable("orderCodePost") final String orderCode, final Model model)
	{

		try
		{
			final OrderData orderDetails = orderFacade.getOrderDetailsForCode(orderCode);
			for (final OrderEntryData orderEntryData : orderDetails.getEntries())
			{
				addProductToCart(orderEntryData.getProduct().getCode(), model, orderEntryData.getQuantity().longValue());
				model.addAttribute("product", productFacade.getProductForCodeAndOptions(orderEntryData.getProduct().getCode(),
						Arrays.asList(ProductOption.BASIC)));
			}
		}
		catch (final CommerceCartModificationException ex)
		{
			model.addAttribute(ERROR_MSG_TYPE, "basket.error.occurred");
			model.addAttribute("quantity", Long.valueOf(0L));
		}

		return REDIRECT_PREFIX + "/cart";
	}

	@RequestMapping(value = "/cart/addAll", method = RequestMethod.POST, produces = "application/json")
	public String addToCart(@RequestParam("productCodes") final String productCodes, final Model model)
			throws CommerceCartModificationException
	{
		final String codes[] = productCodes.split("-");
		for (final String code : codes)
		{
			if (!code.isEmpty())
			{
				cartFacade.addToCart(code, 1);
			}
		}
		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}
}
