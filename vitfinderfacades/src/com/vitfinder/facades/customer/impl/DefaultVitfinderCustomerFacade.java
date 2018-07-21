/**
 *
 */
package com.vitfinder.facades.customer.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;

import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.TitleModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.vitfinder.core.enums.Preference;
import com.vitfinder.core.model.CommunicationPreferenceModel;


/**
 * @author gourab.banerjee
 *
 */
public class DefaultVitfinderCustomerFacade extends DefaultCustomerFacade
{

	@Override
	public void register(final RegisterData registerData) throws DuplicateUidException
	{
		validateParameterNotNullStandardMessage("registerData", registerData);
		Assert.hasText(registerData.getFirstName(), "The field [FirstName] cannot be empty");
		Assert.hasText(registerData.getLastName(), "The field [LastName] cannot be empty");
		Assert.hasText(registerData.getLogin(), "The field [Login] cannot be empty");

		final CustomerModel newCustomer = getModelService().create(CustomerModel.class);
		newCustomer.setName(getCustomerNameStrategy().getName(registerData.getFirstName(), registerData.getLastName()));

		if (StringUtils.isNotBlank(registerData.getFirstName()) && StringUtils.isNotBlank(registerData.getLastName()))
		{
			newCustomer.setName(getCustomerNameStrategy().getName(registerData.getFirstName(), registerData.getLastName()));
		}
		final TitleModel title = getUserService().getTitleForCode(registerData.getTitleCode());
		newCustomer.setTitle(title);
		setUidForRegister(registerData, newCustomer);
		newCustomer.setSessionLanguage(getCommonI18NService().getCurrentLanguage());
		newCustomer.setSessionCurrency(getCommonI18NService().getCurrentCurrency());
		setCommunicationPreferences(registerData, newCustomer);
		getCustomerAccountService().register(newCustomer, registerData.getPassword());
		final AddressModel address = getModelService().create(AddressModel.class);
		address.setLine1(registerData.getAddress().getLine1());
		address.setLine2(registerData.getAddress().getLine2());
		address.setTown(registerData.getAddress().getTown());
		address.setPostalcode(registerData.getAddress().getPostalCode());
		address.setPhone1(registerData.getAddress().getPhone());
		address.setOwner(newCustomer);
		address.setShippingAddress(Boolean.TRUE);
		address.setVisibleInAddressBook(Boolean.TRUE);

		if (registerData.getAddress().getCountry() != null)
		{
			final String isocode = registerData.getAddress().getCountry().getIsocode();
			try
			{
				final CountryModel countryModel = getCommonI18NService().getCountry(isocode);
				address.setCountry(countryModel);
			}
			catch (final UnknownIdentifierException e)
			{
				throw new ConversionException("No country with the code " + isocode + " found.", e);
			}
			catch (final AmbiguousIdentifierException e)
			{
				throw new ConversionException("More than one country with the code " + isocode + " found.", e);
			}
		}

		//getModelService().save(address);

		getCustomerAccountService().saveAddressEntry(newCustomer, address);


	}

	/**
	 * @param registerData
	 * @param newCustomer
	 */
	private void setCommunicationPreferences(final RegisterData registerData, final CustomerModel newCustomer)
	{
		final CommunicationPreferenceModel preferences = new CommunicationPreferenceModel();
		final List<Preference> listOfPreferences = new ArrayList<Preference>();
		for (final String preference : registerData.getCommunicationPreferences() == null ? new ArrayList<String>()
				: registerData.getCommunicationPreferences())
		{
			listOfPreferences.add(Preference.valueOf(preference));
		}
		preferences.setListOfPreference(listOfPreferences);
		newCustomer.setCommunicationPreferences(preferences);
	}

	public boolean updateCommunicationPreferences(final CustomerData customerData)
	{
		final CustomerModel customer = getCurrentSessionCustomer();
		populateCommunicationPref(customerData, customer);

		return true;
	}

	/**
	 * @param customerData
	 * @param customer
	 */
	private void populateCommunicationPref(final CustomerData customerData, final CustomerModel customer)
	{
		CommunicationPreferenceModel commPreferences = customer.getCommunicationPreferences();
		final List<Preference> listOfPreferences = new ArrayList<Preference>();
		//		if (commPreferences == null)
		//		{
		commPreferences = new CommunicationPreferenceModel();
		//		}
		for (final String preference : customerData.getCommunicationPreferences().getListOfPreference())
		{
			listOfPreferences.add(Preference.valueOf(preference));
		}
		commPreferences.setRemainder(customerData.getCommunicationPreferences().getRemainder() != null
				? customerData.getCommunicationPreferences().getRemainder() : null);
		commPreferences.setListOfPreference(listOfPreferences);
		customer.setCommunicationPreferences(commPreferences);
		getModelService().saveAll();

	}

}
