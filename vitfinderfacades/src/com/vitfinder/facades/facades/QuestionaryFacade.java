/**
 *
 */
package com.vitfinder.facades.facades;

import de.hybris.platform.commercefacades.product.data.CategoryData;


/**
 * @author nandakishore
 *
 */
public interface QuestionaryFacade
{
	CategoryData getCategoryForCode(String code);

}
