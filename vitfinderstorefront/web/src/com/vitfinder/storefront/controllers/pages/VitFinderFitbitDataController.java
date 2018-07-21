/**
 *
 */
package com.vitfinder.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.ResourceBreadcrumbBuilder;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vitfinder.core.service.VitFinderFitBitBandDataService;
import com.vitfinder.storefront.controllers.ControllerConstants;



@Controller
@Scope("tenant")
@RequestMapping("/fitbit")
public class VitFinderFitbitDataController extends AbstractPageController
{
	private static final String STUDY_MY_BAND_DETAILS_PAGE = "studymyband-detailspage";

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "vitFinderFitBitBandDataService")
	private VitFinderFitBitBandDataService vitFinderFitBitBandDataService;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@RequestMapping(value = "/banddata", method = RequestMethod.GET)
	public String getFitBitBandData(@RequestParam("type") final String type, final Model model, final HttpServletRequest request,
			final HttpServletResponse response) throws CMSItemNotFoundException
	{
		if (getSessionService().getAttribute("redirectURI") != null)
		{
			getSessionService().removeAttribute("redirectURI");
		}
		if ((getSessionService().getAttribute("access_token") != null && customerFacade.getCurrentCustomer().getUid() != null)
				&& customerFacade.getCurrentCustomer().getUid().equals(getSessionService().getAttribute("customerId")))
		{
			System.out.println("access_token : exist");

			if (type.equalsIgnoreCase("activity"))
			{
				final Map<String, Map<String, String>> activity = vitFinderFitBitBandDataService.getActivityData();
				if (activity != null)
				{
					model.addAttribute("activity", activity);
					model.addAttribute("type", type);
				}

			}

			if (type.equalsIgnoreCase("body"))
			{
				final Map<String, Map<String, String>> bodyAndWeight = vitFinderFitBitBandDataService.getBodyAndWeightData();
				if (bodyAndWeight != null)
				{
					model.addAttribute("bodyAndWeight", bodyAndWeight);
					model.addAttribute("type", type);
				}
			}

			if (type.equalsIgnoreCase("food"))
			{
				final Map<String, Map<String, String>> food = vitFinderFitBitBandDataService.getFoodLogingData();
				if (food != null)
				{
					model.addAttribute("food", food);
					model.addAttribute("type", type);
				}
			}

			if (type.equalsIgnoreCase("friends"))
			{
				final Map<String, Map<String, String>> friends = vitFinderFitBitBandDataService.getFriendsData();
				if (friends != null)
				{
					model.addAttribute("friends", friends);
					model.addAttribute("type", type);
				}
			}

			if (type.equalsIgnoreCase("heart"))
			{
				final Map<String, Map<String, String>> heart = vitFinderFitBitBandDataService.getHeartRate();
				if (heart != null)
				{
					model.addAttribute("heart", heart);
					model.addAttribute("type", type);
				}
			}

			if (type.equalsIgnoreCase("sleep"))
			{
				final Map<String, Map<String, String>> sleep = vitFinderFitBitBandDataService.getSleep();
				if (sleep != null)
				{
					model.addAttribute("sleep", sleep);
					model.addAttribute("type", type);
				}
			}

			if (type.equalsIgnoreCase("subscriptions"))
			{
				final Map<String, Map<String, String>> subscriptions = vitFinderFitBitBandDataService.getSubscriptions();
				if (subscriptions != null)
				{
					model.addAttribute("subscriptions", subscriptions);
					model.addAttribute("type", type);
				}
			}

			if (type.equalsIgnoreCase("user"))
			{
				final Map<String, Map<String, String>> user = vitFinderFitBitBandDataService.getUser();
				if (user != null)
				{
					model.addAttribute("user", user);
					model.addAttribute("type", type);
				}
			}
			if (type.equalsIgnoreCase("devices"))
			{
				ArrayList<String> devices = vitFinderFitBitBandDataService.getDevices();
				if (devices != null)
				{
					model.addAttribute("devices", devices);
					model.addAttribute("type", type);
				}
			}

		}
		else
		{
			System.out.println("access_token : not-exist");
			getSessionService().setAttribute("redirectURI", "/fitbit/banddata?type=" + type);
			return REDIRECT_PREFIX + "/fitbit/requestForAthorizationCode";

		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(STUDY_MY_BAND_DETAILS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(STUDY_MY_BAND_DETAILS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getBreadcrumbs("text.account.profile.studymyband.details"));
		return ControllerConstants.Views.Pages.Account.StudyMyBandDetialsPage;
	}

	@RequestMapping(value = "/requestForAthorizationCode", method = RequestMethod.GET)
	public String requestForAthorizationCode(final HttpServletRequest request, final HttpServletResponse response)
	{

		final String loginURL = Config.getParameter("vitfinder.fitbit.authorization.uri");
		final String clientID = Config.getParameter("vitfinder.fitbit.clientId");
		final String redirectURI = Config.getParameter("vitfinder.fitbit.redirect.uri");
		final String scope = Config.getParameter("vitfinder.fitbit.scope");
		final StringBuffer fitbitAthenticationURL = new StringBuffer(loginURL);
		fitbitAthenticationURL.append("?response_type=code");
		fitbitAthenticationURL.append("&redirect_uri=");
		fitbitAthenticationURL.append(redirectURI);
		fitbitAthenticationURL.append("&client_id=");
		fitbitAthenticationURL.append(clientID);
		fitbitAthenticationURL.append("&scope=");
		fitbitAthenticationURL.append(scope);
		fitbitAthenticationURL.append("&expires_in=");
		fitbitAthenticationURL.append("604800");

		return REDIRECT_PREFIX + fitbitAthenticationURL.toString();

	}


	@RequestMapping(value = "/accessToken", method = RequestMethod.GET)
	public String getAccessToken(@RequestParam("code") final String code, final Model model, final HttpServletRequest request,
			final HttpServletResponse response) throws ClientProtocolException, IOException, JSONException
	{
		final String accessTokenURL = Config.getParameter("vitfinder.fitbit.accesstoken.uri");
		final String clientID = Config.getParameter("vitfinder.fitbit.clientId");
		final String clientSecret = Config.getParameter("vitfinder.fitbit.clientsecret");
		final String redirectURI = Config.getParameter("vitfinder.fitbit.redirect.uri");
		final String str = clientID + ":" + clientSecret;
		final String encodedStr = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));

		final HttpClient httpclient = new DefaultHttpClient();
		final HttpPost httppost = new HttpPost(accessTokenURL);

		final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("client_id", clientID));
		nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
		nameValuePairs.add(new BasicNameValuePair("redirect_uri", redirectURI));
		nameValuePairs.add(new BasicNameValuePair("code", code));
		nameValuePairs.add(new BasicNameValuePair("client_secret", clientSecret));

		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		httppost.addHeader("Authorization", "Basic " + encodedStr);
		httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		final HttpResponse respons1 = httpclient.execute(httppost);
		if (respons1.getStatusLine().getStatusCode() == 200)
		{
			final String json = EntityUtils.toString(respons1.getEntity());
			final JSONObject jsonObj = new JSONObject(json);
			getSessionService().setAttribute("access_token", jsonObj.get("access_token"));
			getSessionService().setAttribute("customerId", getCustomerFacade().getCurrentCustomer().getUid());
			getSessionService().setAttribute("refresh_token", jsonObj.get("refresh_token"));
			getSessionService().setAttribute("user_id", jsonObj.get("user_id"));


		}

		return REDIRECT_PREFIX + getSessionService().getAttribute("redirectURI");
	}



	/**
	 * @return the accountBreadcrumbBuilder
	 */
	public ResourceBreadcrumbBuilder getAccountBreadcrumbBuilder()
	{
		return accountBreadcrumbBuilder;
	}

	/**
	 * @param accountBreadcrumbBuilder
	 *           the accountBreadcrumbBuilder to set
	 */
	public void setAccountBreadcrumbBuilder(ResourceBreadcrumbBuilder accountBreadcrumbBuilder)
	{
		this.accountBreadcrumbBuilder = accountBreadcrumbBuilder;
	}
}
