/**
 *
 */
package com.vitfinder.facades.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.vitfinder.core.dao.VitfinderGenericProductDao;
import com.vitfinder.core.model.VitfinderGenericProductModel;
import com.vitfinder.core.service.VitfinderGenericProductService;


/**
 * @author Nandakishore
 *
 */
public class DefaultVitfinderGenericProductService implements VitfinderGenericProductService
{

	private VitfinderGenericProductDao vitfinderGenericProductDao;
	private static final Logger LOG = Logger.getLogger(DefaultVitfinderGenericProductService.class);


	@Override
	public VitfinderGenericProductModel getProductForCode(final String code)
	{
		final List<VitfinderGenericProductModel> products = getVitfinderGenericProductDao().findProductsByCode(code);
		if (products != null && products.size() > 0)
		{
			return products.get(0);
		}
		return null;
	}


	/**
	 * @return the vitfinderGenericProductDao
	 */
	public VitfinderGenericProductDao getVitfinderGenericProductDao()
	{
		return vitfinderGenericProductDao;
	}


	/**
	 * @param vitfinderGenericProductDao
	 *           the vitfinderGenericProductDao to set
	 */
	public void setVitfinderGenericProductDao(final VitfinderGenericProductDao vitfinderGenericProductDao)
	{
		this.vitfinderGenericProductDao = vitfinderGenericProductDao;
	}



}
