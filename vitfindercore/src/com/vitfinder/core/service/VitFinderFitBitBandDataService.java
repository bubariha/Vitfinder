package com.vitfinder.core.service;


import java.util.ArrayList;
import java.util.Map;


public interface VitFinderFitBitBandDataService
{

	Map<String, Map<String, String>> getActivityData();

	Map<String, Map<String, String>> getBodyAndWeightData();

	ArrayList<String> getDevices();

	Map<String, Map<String, String>> getFoodLogingData();

	Map<String, Map<String, String>> getFriendsData();

	Map<String, Map<String, String>> getHeartRate();

	Map<String, Map<String, String>> getSleep();

	Map<String, Map<String, String>> getSubscriptions();

	Map<String, Map<String, String>> getUser();

}
