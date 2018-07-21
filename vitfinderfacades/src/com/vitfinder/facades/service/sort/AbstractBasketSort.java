/**
 *
 */
package com.vitfinder.facades.service.sort;

import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.order.CommerceCartModification;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.model.ModelService;

import com.vitfinder.core.sort.BasketSort;
import com.vitfinder.facades.facades.VitfinderCartFacade;
import com.vitfinder.facades.service.EanProductResolverFromGenericProduct;


/**
 * @author nandakishore
 *
 */
public abstract class AbstractBasketSort implements BasketSort
{
	private EanProductResolverFromGenericProduct eanProductResolverFromGenericProduct;
	private ModelService modelService;
	private CartService cartService;
	private ProductService productService;
	private CommerceCartService commerceCartService;
	private CartFacade cartFacade;


	protected PageableData createPageableData(final int pageNumber, final int pageSize, final String sortCode)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(pageNumber);
		pageableData.setSort(sortCode);
		pageableData.setPageSize(pageSize);
		return pageableData;
	}

	protected void updateCartEntries(final CartModel cartModel, final String sort, final String searchQuery)
			throws CommerceCartModificationException
	{
		for (final AbstractOrderEntryModel cartEntry : cartModel.getEntries())
		{
			if (cartEntry.getGenericProduct() != null)
			{
				//G000000001:price-asc:retailer:Myprotein
				final PageableData pageableData = createPageableData(0, 10, sort);
				final SearchStateData searchState = new SearchStateData();
				final SearchQueryData searchQueryData = new SearchQueryData();
				searchQueryData.setValue(cartEntry.getGenericProduct().getCode() + searchQuery);
				searchState.setQuery(searchQueryData);
				final ProductData productData = getEanProductResolverFromGenericProduct().resolveEanProduct(pageableData, searchState,
						cartEntry.getGenericProduct());
				if (productData != null)
				{
					final ProductModel productModel = getProductService().getProductForCode(productData.getCode());
					replaceWithNewEanProduct(productModel, cartEntry, cartModel);
				}
			}
		}
	}

	protected void replaceWithNewEanProduct(final ProductModel productModel, final AbstractOrderEntryModel cartEntry,
			final CartModel cartModel) throws CommerceCartModificationException
	{
		if (!cartEntry.getProduct().getCode().equals(productModel.getCode()))
		{
			final CommerceCartParameter parameter = new CommerceCartParameter();
			final Long quantity = cartEntry.getQuantity();
			parameter.setEnableHooks(true);
			parameter.setCart(cartModel);
			parameter.setQuantity(quantity);
			parameter.setProduct(productModel);
			parameter.setUnit(productModel.getUnit());
			parameter.setCreateNewEntry(false);
			final CommerceCartModification modification = getCommerceCartService().addToCart(parameter);
			final AbstractOrderEntryModel retailerCartEntry = modification.getEntry();
			retailerCartEntry.setGenericProduct(cartEntry.getGenericProduct());
			getModelService().save(retailerCartEntry);
			getModelService().save(cartModel);
			((VitfinderCartFacade) getCartFacade()).updateGenericCartEntry(cartEntry.getEntryNumber(), 0, cartModel,
					cartEntry.getGenericProduct());
		}
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
	 * @return the cartService
	 */
	public CartService getCartService()
	{
		return cartService;
	}


	/**
	 * @param cartService
	 *           the cartService to set
	 */
	public void setCartService(final CartService cartService)
	{
		this.cartService = cartService;
	}


	/**
	 * @return the productService
	 */
	public ProductService getProductService()
	{
		return productService;
	}


	/**
	 * @param productService
	 *           the productService to set
	 */
	public void setProductService(final ProductService productService)
	{
		this.productService = productService;
	}


	/**
	 * @return the commerceCartService
	 */
	public CommerceCartService getCommerceCartService()
	{
		return commerceCartService;
	}


	/**
	 * @param commerceCartService
	 *           the commerceCartService to set
	 */
	public void setCommerceCartService(final CommerceCartService commerceCartService)
	{
		this.commerceCartService = commerceCartService;
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
