/**
 *
 */
package com.vitfinder.core.interceptors;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import com.vitfinder.core.model.BasketSortsModel;


/**
 * @author nandakishore
 *
 */
public class BasketSortsValidator implements ValidateInterceptor<BasketSortsModel>
{

	@Override
	public void onValidate(final BasketSortsModel basketSort, final InterceptorContext interceptorContext)
			throws InterceptorException
	{
		//		if (interceptorContext.isModified(basketSort, BasketSortsModel.BEANID))
		//		{
		try
		{
			Registry.getApplicationContext().getBean(basketSort.getBeanId());
		}
		catch (final Exception e)
		{
			throw new InterceptorException("bean not identified with the id ::" + basketSort.getBeanId());
		}

	}
	//	}

}
