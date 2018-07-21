/**
 *
 */
package com.vitfinder.facades.service;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.core.model.product.ProductModel;


/**
 * @author nandakishore
 *
 */
public interface EanProductResolverFromGenericProduct
{
	ProductData resolveEanProduct(PageableData pageableData, SearchStateData searchState, ProductModel productModel);
}
