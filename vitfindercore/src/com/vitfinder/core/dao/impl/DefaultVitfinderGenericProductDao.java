/**
 *
 */
package com.vitfinder.core.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import org.apache.log4j.Logger;

import com.vitfinder.core.dao.VitfinderGenericProductDao;
import com.vitfinder.core.model.VitfinderGenericProductModel;


/**
 * @author Nandakishore
 *
 */
public class DefaultVitfinderGenericProductDao extends AbstractItemDao implements VitfinderGenericProductDao
{
	private static final Logger LOG = Logger.getLogger(DefaultVitfinderGenericProductDao.class);

	public static final String PRODUCT_BY_CODE = "select {" + VitfinderGenericProductModel.PK + "} from {"
			+ VitfinderGenericProductModel._TYPECODE + "} where {" + VitfinderGenericProductModel.CODE + "}=?code";

	@Override
	public List<VitfinderGenericProductModel> findProductsByCode(final String code)
	{
		LOG.debug("find products by code" + code);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(PRODUCT_BY_CODE);
		query.addQueryParameter("code", code);
		final SearchResult<VitfinderGenericProductModel> results = getFlexibleSearchService().search(query);
		return results.getResult();
	}

}
