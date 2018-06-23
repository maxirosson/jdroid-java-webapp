package com.jdroid.javaweb.twitter;

import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.CoreConfigParameter;

import org.slf4j.Logger;

import java.io.File;
import java.util.List;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.StatusUpdate;
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
		cb.setOAuthConsumerKey(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_KEY));
		cb.setOAuthConsumerSecret(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_SECRET));
		cb.setOAuthAccessToken(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN));
		cb.setOAuthAccessTokenSecret(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN_SECRET));
		twitterFactory = new TwitterFactory(cb.build());
	}
	
	public void tweet(String text) {
		tweet(text, (File)null);
	}
	
	public void tweet(String text, String mediaPath) {
		tweet(text, mediaPath != null ? new File(mediaPath) : null);
	}
	
	public void tweet(String text, File media) {
		try {
			if (Application.get().getRemoteConfigLoader().getBoolean(CoreConfigParameter.TWITTER_ENABLED)) {
				StatusUpdate statusUpdate = new StatusUpdate(text);
				if (media != null) {
					statusUpdate.setMedia(media);
				}
				Status status = twitterFactory.getInstance().updateStatus(statusUpdate);
				LOGGER.info("Successfully updated the status to [" + status.getText() + "].");
			} else {
				LOGGER.info("Ignored tweet status [" + text + "]" + (media != null ? (" with media: " + media.getAbsolutePath()) : ""));
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
		return searchTweets(new Query(queryText));
	}
	
	public List<Status> searchTweets(Query query) {
		Twitter twitter = twitterFactory.getInstance();
		try {
			return twitter.search(query).getTweets();
		} catch (TwitterException e) {
			throw new UnexpectedException(e);
		}
	}
	
	public void retweetStatus(long tweetId) {
		Twitter twitter = twitterFactory.getInstance();
		try {
			twitter.retweetStatus(tweetId);
		} catch (TwitterException e) {
			throw new UnexpectedException(e);
		}
	}
}
