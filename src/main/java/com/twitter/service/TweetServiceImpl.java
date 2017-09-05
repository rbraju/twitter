package com.twitter.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.twitter.dao.TweetDAO;
import com.twitter.dto.Tweet;

@Path("/tweet")
public class TweetServiceImpl implements TweetService {

	private final TweetDAO tweetDAO = new TweetDAO();;

	@GET
	@Path("/test")
	@Produces({ MediaType.APPLICATION_JSON })
	public String test() {
		return "{ \"text:\": \"testResponse\" }";
	}

	@GET
	@Path("/{screenName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Tweet> getTweets(@PathParam("screenName") String screenName) {
		return tweetDAO.getTweets(screenName);
	}
}
