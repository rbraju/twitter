package com.twitter.dao;

import java.util.Base64;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.twitter.dto.BearerToken;
import com.twitter.dto.Tweet;
import com.twitter.util.Constants;
import com.twitter.util.JerseyClientFactory;

public class TweetDAO {

	private Client client = JerseyClientFactory.getClient();

	private Response response = null;

	/**
	 * Retrieve tweets from twitter api.
	 * 
	 * @param screenName
	 *            - Twitter username
	 */
	public List<Tweet> getTweets(String screenName) {
		List<Tweet> tweets = null;
		String consumerKey = "_invalidKey_";
		String secret = "_invalidSecret_";

		try {
			String bearerToken = getBearerToken(consumerKey, secret);

			// Retrieve tweets from user timeline
			response = client.target(Constants.ENDPOINT)
								.path(Constants.TIMELINE_PATH)
								.queryParam("screen_name", screenName).queryParam("exclude_replies", "true")
								.request(MediaType.APPLICATION_JSON)
								.header("Authorization", "Bearer " + bearerToken)
								.get();
			
			tweets = response.readEntity(new GenericType<List<Tweet>>() { });
		} catch (Exception e) {
			System.out.println("** Error occurred while retrieving tweets for " + screenName);
		}
		return tweets;
	}

	/**
	 * Generate and return the access token based on the given key and secret
	 * 
	 * @param consumerKey
	 * @param secret
	 * @return Access token
	 */
	private String getBearerToken(String consumerKey, String secret) {
		String credentials = consumerKey + ":" + secret;
		String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

		Response response = client.target(Constants.ENDPOINT)
									.path(Constants.OAUTH_PATH)
									.queryParam("grant_type", "client_credentials")
									.request(MediaType.APPLICATION_JSON)
									.header("Authorization", "Basic " + encodedCredentials)
									.post(null);
		
		BearerToken bearerToken = response.readEntity(BearerToken.class);
		return bearerToken.getAccessToken();
	}
}
