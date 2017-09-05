package com.twitter.service;

import java.util.List;

import com.twitter.dto.Tweet;

public interface TweetService {

	public List<Tweet> getTweets(String screenName);
}
