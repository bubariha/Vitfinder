/**
 *
 */
package com.vitfinder.core.dao.impl;

import de.hybris.platform.category.daos.impl.DefaultCategoryDao;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.jalo.link.Link;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class VitfinderCategoryDao extends DefaultCategoryDao
{

	private static final String SUPER_SUB_CATEGORY_RELATION = "CategoryCategoryRelation";

	public Collection<CategoryModel> findSubCategoriesByCode(final CategoryModel category)
	{
		//final StringBuilder query = new StringBuilder("select {target} from {CategoryCategoryRelation} where {source}=?category");

		final StringBuilder query = new StringBuilder("SELECT {");
		query.append(Link.TARGET);
		query.append("} ");
		query.append("FROM {");
		query.append(SUPER_SUB_CATEGORY_RELATION);
		query.append("} ");
		query.append("WHERE {");
		query.append(Link.SOURCE);
		query.append("} =?category");

		final Map<String, Object> params = (Map) Collections.singletonMap("category", category);

		final SearchResult<CategoryModel> searchRes = search(query.toString(), params);
		return searchRes.getResult();
	}
}
