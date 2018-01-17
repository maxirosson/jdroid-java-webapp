package com.jdroid.javaweb.twitter;

import com.jdroid.javaweb.config.ConfigHelper;
import com.jdroid.javaweb.config.CoreConfigParameter;

import twitter4j.FilterQuery;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamConnector {
	
	private TwitterStreamFactory twitterStreamFactory;
	private TwitterStream twitterStream;
	
	public TwitterStreamConnector() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_KEY));
		cb.setOAuthConsumerSecret(ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_SECRET));
		cb.setOAuthAccessToken(ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN));
		cb.setOAuthAccessTokenSecret(ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN_SECRET));
		twitterStreamFactory = new TwitterStreamFactory(cb.build());
	}
	
	public void listenTweets(FilterQuery tweetFilterQuery, StatusListener statusListener) {
		twitterStream = twitterStreamFactory.getInstance();
		twitterStream.addListener(statusListener);
		twitterStream.filter(tweetFilterQuery);
	}
	
	/**
	 * Stop listening to tweets
	 */
	public void cleanUp() {
		if (twitterStream != null) {
			twitterStream.cleanUp();
		}
	}
	
	/**
	 * Cancel received tweets but not notified
	 */
	public void shutdown() {
		if (twitterStream != null) {
			twitterStream.shutdown();
		}
	}
	
}
