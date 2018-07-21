/**
 *
 */
package com.vitfinder.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.List;

import com.vitfinder.core.enums.Preference;
import com.vitfinder.core.model.CommunicationPreferenceModel;
import com.vitfinder.facades.user.data.CommunicationPreferenceData;


/**
 * @author Nandakishore
 *
 */
public class CommunicationPreferencePopulator implements Populator<CommunicationPreferenceModel, CommunicationPreferenceData>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final CommunicationPreferenceModel source, final CommunicationPreferenceData target)
			throws ConversionException
	{
		// YTODO Auto-generated method stub
		target.setListOfPreference(prepareData(source.getListOfPreference()));
		target.setRemainder(source.getRemainder());
	}

	/**
	 * @param listOfPreference
	 * @return
	 */
	private List<String> prepareData(final List<Preference> listOfPreference)
	{
		// YTODO Auto-generated method stub
		final List<String> preferences = new ArrayList<String>();
		for (final Preference preference : listOfPreference)
		{
			preferences.add(preference.toString());
		}
		return preferences;
	}

}
