/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2015 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */

package com.vitfinder.facades.customer.form;

import java.util.List;


/**
 * Form object for updating email
 */
public class UpdateCommPrefForm
{
	private List<String> communicationPreferences;
	private String remainder;

	/**
	 * @return the communicationPreferences
	 */
	public List<String> getCommunicationPreferences()
	{
		return communicationPreferences;
	}

	/**
	 * @param communicationPreferences
	 *           the communicationPreferences to set
	 */
	public void setCommunicationPreferences(final List<String> communicationPreferences)
	{
		this.communicationPreferences = communicationPreferences;
	}

	/**
	 * @return the remainder
	 */
	public String getRemainder()
	{
		return remainder;
	}

	/**
	 * @param remainder
	 *           the remainder to set
	 */
	public void setRemainder(final String remainder)
	{
		this.remainder = remainder;
	}


}
