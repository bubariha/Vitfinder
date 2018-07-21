
package com.vitfinder.core.servicelayer;

import de.hybris.platform.site.BaseSiteService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

//import java.util.Base64;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class SSOMessageGenerator
{

	@Resource(name = "baseSiteService")
	private BaseSiteService baseSiteService;

	public static List<String> getPayload(final String id, final String name, final String email, final String secretKey)
			throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, JsonGenerationException, JsonMappingException,
			IOException
	{
		final List<String> sign = new ArrayList<String>();

		/*
		 * This script will calculate the Disqus SSO payload package Please see the Integrating SSO guide to find out how
		 * to configure your account first: http://help.disqus.com/customer/portal/articles/236206
		 *
		 * This example uses the Jackson JSON processor: http://jackson.codehaus.org/Home
		 */
		//final String DISQUS_SECRET_KEY = secretKey; // Your Disqus secret key from http://disqus.com/api/applications/

		// User data, replace values with authenticated user data
		final HashMap<String, String> message = new HashMap<String, String>();
		message.put("id", id);
		message.put("username", name);
		message.put("email", email);
		//message.put("avatar","http://example.com/path-to-avatar.jpg"); // User's avatar URL (optional)
		//message.put("url","http://example.com/"); // User's website or profile URL (optional)

		// Encode user data
		final ObjectMapper mapper = new ObjectMapper();

		final String jsonMessage = mapper.writeValueAsString(message);

		final String base64EncodedStr = new String(Base64.encodeBase64(jsonMessage.getBytes()));

		//String base64EncodedStr1 = Base64.getEncoder().encodeToString(jsonMessage.getBytes());

		// Get the timestamp
		final long timestamp = System.currentTimeMillis() / 1000;

		// Assemble the HMAC-SHA1 signature
		final String signature = calculateRFC2104HMAC(base64EncodedStr + " " + timestamp, secretKey);

		// Output string to use in remote_auth_s3 variable
		sign.add(base64EncodedStr);
		sign.add(signature);
		sign.add("" + timestamp);

		return sign;
		//return (base64EncodedStr + " " + signature + " " + timestamp);
	}



	private static String toHexString(final byte[] bytes)
	{
		final Formatter formatter = new Formatter();
		for (final byte b : bytes)
		{
			formatter.format("%02x", b);
		}

		return formatter.toString();
	}

	private static String calculateRFC2104HMAC(final String data, final String key)
			throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
	{

		final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
		final SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
		final Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);
		return toHexString(mac.doFinal(data.getBytes()));
	}



}
