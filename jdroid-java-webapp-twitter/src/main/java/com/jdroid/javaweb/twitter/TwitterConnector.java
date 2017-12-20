package com.jdroid.javaweb.twitter;

import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.javaweb.config.ConfigHelper;
import com.jdroid.javaweb.config.CoreConfigParameter;

import org.slf4j.Logger;

import java.util.List;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnector {
	
	private static final Logger LOGGER = LoggerUtils.getLogger(TwitterConnector.class);
	
	public static Integer CHARACTERS_LIMIT = 140;
	public static Integer NEW_CHARACTERS_LIMIT = 280;
	public static Integer URL_CHARACTERS_COUNT = 22;
	
	private TwitterFactory twitterFactory;
	
	public TwitterConnector() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_KEY));
		cb.setOAuthConsumerSecret(ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_SECRET));
		cb.setOAuthAccessToken(ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN));
		cb.setOAuthAccessTokenSecret(ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN_SECRET));
		twitterFactory = new TwitterFactory(cb.build());
	}
	
	public void tweet(String text) {
		try {
			if (ConfigHelper.getBooleanValue(CoreConfigParameter.TWITTER_ENABLED)) {
				Status status = twitterFactory.getInstance().updateStatus(text);
				LOGGER.info("Successfully updated the status to [" + status.getText() + "].");
			} else {
				LOGGER.info("Ignored tweet status [" + text + "].");
			}
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void tweetSafe(String text) {
		try {
			tweet(text);
		} catch (Exception e) {
			LOGGER.error("Error when posting on Twitter: [" + text + "]", e);
		}
	}
	
	public List<Status> searchTweets(String queryText) {
		Twitter twitter = twitterFactory.getInstance();
		Query query = new Query(queryText);
		try {
			return twitter.search(query).getTweets();
		} catch (TwitterException e) {
			throw new UnexpectedException(e);
		}
	}
	
}
