/**
 *
 */
package com.vitfinder.facades.service.impl;


import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.core.model.product.ProductModel;

import com.vitfinder.facades.service.EanProductResolverFromGenericProduct;


/**
 * @author nandakishore
 *
 */
public class VitfinderEanProductResolverFromGenericProduct implements EanProductResolverFromGenericProduct
{

	private ProductSearchFacade<ProductData> productSearchFacade;

	@Override
	public ProductData resolveEanProduct(PageableData pageableData, SearchStateData searchState, final ProductModel productModel)
	{
		if (pageableData == null && searchState == null)
		{
			pageableData = createPageableData(0, 10, "price-asc");
			searchState = new SearchStateData();
			final SearchQueryData searchQueryData = new SearchQueryData();
			searchQueryData.setValue(productModel.getCode());
			searchState.setQuery(searchQueryData);
		}
		final ProductSearchPageData<SearchStateData, ProductData> searchResults = getProductSearchFacade().textSearch(searchState,
				pageableData);
		for (final ProductData result : searchResults.getResults())
		{
			return result;
		}
		return null;
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
	 * @return the productSearchFacade
	 */
	public ProductSearchFacade<ProductData> getProductSearchFacade()
	{
		return productSearchFacade;
	}

	/**
	 * @param productSearchFacade
	 *           the productSearchFacade to set
	 */
	public void setProductSearchFacade(final ProductSearchFacade<ProductData> productSearchFacade)
	{
		this.productSearchFacade = productSearchFacade;
	}

}
