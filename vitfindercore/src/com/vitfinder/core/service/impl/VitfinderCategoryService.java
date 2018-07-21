/**
 *
 */
package com.vitfinder.core.service.impl;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.category.impl.DefaultCommerceCategoryService;

import java.util.Collection;

import javax.annotation.Resource;

import com.vitfinder.core.dao.impl.VitfinderCategoryDao;


public class VitfinderCategoryService extends DefaultCommerceCategoryService
{

	@Resource(name = "vitfinderCategoryDao")
	private VitfinderCategoryDao vitfinderCategoryDao;

	public Collection<CategoryModel> findSubCategoriesByCode(final CategoryModel category)
	{
		return vitfinderCategoryDao.findSubCategoriesByCode(category);
	}


}
