/**
 *
 */
package com.vitfinder.core.dao;

import java.util.List;

import com.vitfinder.core.model.VitfinderGenericProductModel;


/**
 * @author Nandakishore
 *
 */
public interface VitfinderGenericProductDao
{
	/**
	 *
	 * You can get a product with product code by using following method
	 *
	 */
	List<VitfinderGenericProductModel> findProductsByCode(final String code);
}
