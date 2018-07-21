/**
 *
 */
package com.vitfinder.core.service;

import com.vitfinder.core.model.VitfinderGenericProductModel;


/**
 * @author Nandakishore
 *
 */
public interface VitfinderGenericProductService
{
	/**
	 * You can get product with product code with the help of generic product dao.
	 *
	 */
	VitfinderGenericProductModel getProductForCode(final String code);
}
