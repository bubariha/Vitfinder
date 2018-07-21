/**
 *
 */
package com.vitfinder.core.sort;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.core.model.order.CartModel;


/**
 * @author nandakishore
 *
 */
public interface BasketSort
{
	boolean sort(CartData cartData);

	CartModel updateGenericCartEntry() throws CommerceCartModificationException;
}
