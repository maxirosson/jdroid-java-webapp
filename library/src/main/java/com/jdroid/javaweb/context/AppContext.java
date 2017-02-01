package com.jdroid.javaweb.context;

import com.jdroid.java.date.DateUtils;

/**
 * The {@link AppContext}
 */
public class AppContext extends AbstractAppContext {
	
	public String getAppName() {
		return getBuildConfigValue("APP_NAME");
	}
	
	public String getAppVersion() {
		return getBuildConfigValue("APP_VERSION");
	}
	
	public String getMinApiVersion() {
		return getBuildConfigValue("MIN_API_VERSION");
	}
	
	public Boolean isHttpMockEnabled() {
		return getBuildConfigBoolean("HTTP_MOCK_ENABLED", false);
	}
	
	public Integer getHttpMockSleepDuration() {
		return getBuildConfigValue("HTTP_MOCK_SLEEP_DURATION");
	}
	
	public String getGoogleServerApiKey() {
		return getBuildConfigValue("GOOGLE_SERVER_API_KEY");
	}
	
	public String getAdminToken() {
		return getBuildConfigValue("ADMIN_TOKEN");
	}
	
	public String getTwitterOAuthConsumerKey() {
		return getBuildConfigValue("TWITTER_OAUTH_CONSUMER_KEY");
	}
	
	public String getTwitterOAuthConsumerSecret() {
		return getBuildConfigValue("TWITTER_OAUTH_CONSUMER_SECRET");
	}
	
	public String getTwitterOAuthAccessToken() {
		return getBuildConfigValue("TWITTER_OAUTH_ACCESS_TOKEN");
	}
	
	public String getTwitterOAuthAccessTokenSecret() {
		return getBuildConfigValue("TWITTER_OAUTH_ACCESS_TOKEN_SECRET");
	}
	
	public Boolean isTwitterEnabled() {
		return getBuildConfigBoolean("TWITTER_ENABLED", false);
	}
	
	public String getBuildTime() {
		return getBuildConfigValue("BUILD_TIME");
	}

	public String getBuildType() {
		return getBuildConfigValue("BUILD_TYPE");
	}

	public Long getDeviceUpdateRequiredDuration() {
		return DateUtils.MILLIS_PER_DAY * 7;
	}
}
