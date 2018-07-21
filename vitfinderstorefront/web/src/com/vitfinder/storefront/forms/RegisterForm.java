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
package com.vitfinder.storefront.forms;

import java.util.List;


/**
 * Form object for registration
 */
public class RegisterForm
{

	private String titleCode;
	private String firstName;
	private String lastName;
	private String email;
	private String pwd;
	private String checkPwd;
	private String captcha;
	private String line1;
	private String line2;
	private String townCity;
	private String regionIso;
	private String postcode;
	private String countryIso;
	private String phone;
	private List<String> communicationPreferences;
	private boolean registerCheckbox;

	/**
	 * @return the line1
	 */
	public String getLine1()
	{
		return line1;
	}

	/**
	 * @param line1
	 *           the line1 to set
	 */
	public void setLine1(final String line1)
	{
		this.line1 = line1;
	}

	/**
	 * @return the line2
	 */
	public String getLine2()
	{
		return line2;
	}

	/**
	 * @param line2
	 *           the line2 to set
	 */
	public void setLine2(final String line2)
	{
		this.line2 = line2;
	}

	/**
	 * @return the townCity
	 */
	public String getTownCity()
	{
		return townCity;
	}

	/**
	 * @param townCity
	 *           the townCity to set
	 */
	public void setTownCity(final String townCity)
	{
		this.townCity = townCity;
	}

	/**
	 * @return the regionIso
	 */
	public String getRegionIso()
	{
		return regionIso;
	}

	/**
	 * @param regionIso
	 *           the regionIso to set
	 */
	public void setRegionIso(final String regionIso)
	{
		this.regionIso = regionIso;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode()
	{
		return postcode;
	}

	/**
	 * @param postcode
	 *           the postcode to set
	 */
	public void setPostcode(final String postcode)
	{
		this.postcode = postcode;
	}

	/**
	 * @return the countryIso
	 */
	public String getCountryIso()
	{
		return countryIso;
	}

	/**
	 * @param countryIso
	 *           the countryIso to set
	 */
	public void setCountryIso(final String countryIso)
	{
		this.countryIso = countryIso;
	}

	/**
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone
	 *           the phone to set
	 */
	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return the titleCode
	 */
	public String getTitleCode()
	{
		return titleCode;
	}

	/**
	 * @param titleCode
	 *           the titleCode to set
	 */
	public void setTitleCode(final String titleCode)
	{
		this.titleCode = titleCode;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *           the firstName to set
	 */
	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName
	 *           the lastName to set
	 */
	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *           the email to set
	 */
	public void setEmail(final String email)
	{
		this.email = email;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd()
	{
		return pwd;
	}

	/**
	 * @param pwd
	 *           the pwd to set
	 */
	public void setPwd(final String pwd)
	{
		this.pwd = pwd;
	}

	/**
	 * @return the checkPwd
	 */
	public String getCheckPwd()
	{
		return checkPwd;
	}

	/**
	 * @param checkPwd
	 *           the checkPwd to set
	 */
	public void setCheckPwd(final String checkPwd)
	{
		this.checkPwd = checkPwd;
	}


	public String getCaptcha()
	{
		return captcha;
	}

	public void setCaptcha(final String captcha)
	{
		this.captcha = captcha;
	}

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
	 * @return the registerCheckbox
	 */
	public boolean isRegisterCheckbox()
	{
		return registerCheckbox;
	}

	/**
	 * @param registerCheckbox
	 *           the registerCheckbox to set
	 */
	public void setRegisterCheckbox(final boolean registerCheckbox)
	{
		this.registerCheckbox = registerCheckbox;
	}


}
