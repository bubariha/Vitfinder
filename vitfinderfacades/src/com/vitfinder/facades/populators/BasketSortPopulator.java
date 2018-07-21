/**
 *
 */
package com.vitfinder.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.vitfinder.core.BasketSortsData;
import com.vitfinder.core.model.BasketSortsModel;


/**
 * @author nandakishore
 *
 */
public class BasketSortPopulator implements Populator<BasketSortsModel, BasketSortsData>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final BasketSortsModel source, final BasketSortsData target) throws ConversionException
	{
		target.setBeanId(source.getBeanId());
		target.setCode(source.getCode());
		target.setName(source.getName());
	}

}
