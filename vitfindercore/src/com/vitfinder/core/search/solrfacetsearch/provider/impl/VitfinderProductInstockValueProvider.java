/**
 *
 */
package com.vitfinder.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.ProductInStockFlagValueProvider;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.store.BaseStoreModel;

import com.vitfinder.core.model.VitfinderEANProductModel;


/**
 * @author bhavadi
 *
 */
public class VitfinderProductInstockValueProvider extends ProductInStockFlagValueProvider
{
	@Override
	protected boolean isInStock(final ProductModel product, final BaseStoreModel baseStore)
	{
		if (product instanceof VitfinderEANProductModel)
		{
			final VitfinderEANProductModel vitfinderProductModel = (VitfinderEANProductModel) product;
			return isInStock(vitfinderProductModel.getStockLevelStatus());
		}
		return super.isInStock(product, baseStore);
	}

}
