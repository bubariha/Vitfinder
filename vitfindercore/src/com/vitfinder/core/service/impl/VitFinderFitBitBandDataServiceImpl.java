/**
 *
 */
package com.vitfinder.core.service.impl;

import de.hybris.platform.servicelayer.session.SessionService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.vitfinder.core.service.VitFinderFitBitBandDataService;


public class VitFinderFitBitBandDataServiceImpl implements VitFinderFitBitBandDataService
{

	@Resource(name = "sessionService")
	private SessionService sessionService;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getActivityData()
	 */
	@Override
	public Map<String, Map<String, String>> getActivityData()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}
		final Date currentDate = new Date();
		final SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		final String activityURL = "https://api.fitbit.com/1/user" + "/" + user_id + "/activities/date/" + ft.format(currentDate)
				+ ".json";

		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(activityURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final Map<String, Map<String, String>> activityData = (Map<String, Map<String, String>>) data.getBody();
			System.out.println(activityData);
			return activityData;

		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getBodyAndWeightData()
	 */
	@Override
	public Map<String, Map<String, String>> getBodyAndWeightData()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}
		final Date currentDate = new Date();
		final SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");

		final String bodyAndWeightURL = "https://api.fitbit.com/1/user/" + user_id + "/body/log/fat/date/" + ft.format(currentDate)
				+ ".json";

		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(bodyAndWeightURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final Map<String, Map<String, String>> bodyAndWeightData = (Map<String, Map<String, String>>) data.getBody();
			System.out.println(bodyAndWeightData);
			return bodyAndWeightData;

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getDevices()
	 */
	@Override
	public ArrayList<String> getDevices()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}
		final String devicesURL = "https://api.fitbit.com/1/user/" + user_id + "/devices.json";

		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(devicesURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final ArrayList<String> devicesData = (ArrayList<String>) data.getBody();
			System.out.println(devicesData);
			return devicesData;

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getFoodLogingData()
	 */
	@Override
	public Map<String, Map<String, String>> getFoodLogingData()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}
		final Date currentDate = new Date();
		final SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		final String foodLogingURL = "https://api.fitbit.com/1/user/" + user_id + "/foods/log/date/" + ft.format(currentDate)
				+ ".json";


		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(foodLogingURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final Map<String, Map<String, String>> foodLogingData = (Map<String, Map<String, String>>) data.getBody();
			System.out.println(foodLogingData);
			return foodLogingData;

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getFriendsData()
	 */
	@Override
	public Map<String, Map<String, String>> getFriendsData()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}

		final String friendsURL = "https://api.fitbit.com/1/user/" + user_id + "/friends.json";


		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(friendsURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final Map<String, Map<String, String>> friendsData = (Map<String, Map<String, String>>) data.getBody();
			System.out.println(friendsData);
			return friendsData;

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getHeartRate()
	 */
	@Override
	public Map<String, Map<String, String>> getHeartRate()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}
		final Date currentDate = new Date();
		final SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		final String heartRateURL = "https://api.fitbit.com/1/user/" + user_id + "/activities/heart/date/" + ft.format(currentDate)
				+ "/1d.json";
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(heartRateURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final Map<String, Map<String, String>> heartRateData = (Map<String, Map<String, String>>) data.getBody();
			System.out.println(heartRateData);
			return heartRateData;

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getSleep()
	 */
	@Override
	public Map<String, Map<String, String>> getSleep()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}
		final Date currentDate = new Date();
		final SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		final String sleepURL = "https://api.fitbit.com/1/user/" + user_id + "/sleep/date/" + ft.format(currentDate) + ".json";
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(sleepURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final Map<String, Map<String, String>> sleepData = (Map<String, Map<String, String>>) data.getBody();
			System.out.println(sleepData);
			return sleepData;

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getSubscriptions()
	 */
	@Override
	public Map<String, Map<String, String>> getSubscriptions()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}
		final Date currentDate = new Date();
		final SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		final String sleepURL = "https://api.fitbit.com/1/user/" + user_id + "/sleep/date/" + ft.format(currentDate) + ".json";
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(sleepURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final Map<String, Map<String, String>> sleepData = (Map<String, Map<String, String>>) data.getBody();
			System.out.println(sleepData);
			return sleepData;

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vitfinder.core.service.VitFinderFitBitBandDataService#getUser()
	 */
	@Override
	public Map<String, Map<String, String>> getUser()
	{
		String user_id = null;
		if (sessionService.getAttribute("user_id") != null)
		{
			user_id = sessionService.getAttribute("user_id");
		}
		final String userURL = "https://api.fitbit.com/1/user/" + user_id + "/profile.json";
		final RestTemplate restTemplate = new RestTemplate();
		final HttpHeaders accessTokenHeader = new HttpHeaders();
		accessTokenHeader.add("Authorization", "Bearer " + sessionService.getAttribute("access_token"));
		final HttpEntity<String> accessTokenEntity = new HttpEntity<String>("parameters", accessTokenHeader);
		final ResponseEntity<Object> data = restTemplate.exchange(userURL.toString(), HttpMethod.GET, accessTokenEntity,
				Object.class);
		if (data.getStatusCode().value() == 200 && data.getStatusCode().name().equalsIgnoreCase("ok"))
		{

			final Map<String, Map<String, String>> userData = (Map<String, Map<String, String>>) data.getBody();
			System.out.println(userData);
			return userData;

		}
		return null;
	}

}
