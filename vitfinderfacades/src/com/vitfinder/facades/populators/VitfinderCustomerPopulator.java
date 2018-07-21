/**
 *
 */
package com.vitfinder.facades.populators;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import com.vitfinder.core.model.CommunicationPreferenceModel;
import com.vitfinder.facades.user.data.CommunicationPreferenceData;


/**
 * @author Nandakishore
 *
 */
public class VitfinderCustomerPopulator implements Populator<CustomerModel, CustomerData>
{

	private Converter<CommunicationPreferenceModel, CommunicationPreferenceData> communicationPreferenceConverter;

	/*
	 * Created for populating vitfinder specific properties from customer model to data
	 */
	@Override
	public void populate(final CustomerModel source, final CustomerData target) throws ConversionException
	{
		final CommunicationPreferenceModel communicationPreparences = source.getCommunicationPreferences();
		if (communicationPreparences != null)
		{
			final CommunicationPreferenceData communicationPreferenceData = new CommunicationPreferenceData();
			getCommunicationPreferenceConverter().convert(communicationPreparences, communicationPreferenceData);
			target.setCommunicationPreferences(communicationPreferenceData);
		}
	}

	/**
	 * @return the communicationPreferenceConverter
	 */
	public Converter<CommunicationPreferenceModel, CommunicationPreferenceData> getCommunicationPreferenceConverter()
	{
		return communicationPreferenceConverter;
	}

	/**
	 * @param communicationPreferenceConverter
	 *           the communicationPreferenceConverter to set
	 */
	public void setCommunicationPreferenceConverter(
			final Converter<CommunicationPreferenceModel, CommunicationPreferenceData> communicationPreferenceConverter)
	{
		this.communicationPreferenceConverter = communicationPreferenceConverter;
	}


}
