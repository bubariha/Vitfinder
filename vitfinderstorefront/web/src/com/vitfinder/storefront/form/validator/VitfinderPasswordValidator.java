package com.vitfinder.storefront.form.validator;

import de.hybris.platform.acceleratorstorefrontcommons.forms.UpdatePasswordForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.PasswordValidator;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;



@Component("vitfinderPasswordValidator")
public class VitfinderPasswordValidator extends PasswordValidator
{
	@Override
	public void validate(final Object object, final Errors errors)
	{
		final UpdatePasswordForm passwordForm = (UpdatePasswordForm) object;
		final String currPasswd = passwordForm.getCurrentPassword();
		final String newPasswd = passwordForm.getNewPassword();
		final String checkPasswd = passwordForm.getCheckNewPassword();

		if (StringUtils.isEmpty(currPasswd))
		{
			errors.rejectValue("currentPassword", "profile.currentPassword.invalid");
		}

		if (StringUtils.isEmpty(newPasswd))
		{
			errors.rejectValue("newPassword", "password.minimum.length");
		}
		else if (StringUtils.length(newPasswd) < 7 || StringUtils.length(newPasswd) > 255)
		{
			errors.rejectValue("newPassword", "password.minimum.length");
		}

		if (StringUtils.isEmpty(checkPasswd))
		{
			errors.rejectValue("checkNewPassword", "password.minimum.length");
		}
		else if (StringUtils.length(checkPasswd) < 7 || StringUtils.length(checkPasswd) > 255)
		{
			errors.rejectValue("checkNewPassword", "password.minimum.length");
		}
	}
}
