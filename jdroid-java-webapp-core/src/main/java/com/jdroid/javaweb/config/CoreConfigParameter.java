package com.jdroid.javaweb.config;

import com.jdroid.java.remoteconfig.RemoteConfigParameter;

import java.util.concurrent.TimeUnit;


public enum CoreConfigParameter implements RemoteConfigParameter {

	APP_NAME,
	APP_VERSION,
	MIN_API_VERSION,
	BUILD_TIME,
	BUILD_TYPE,

	HTTP_MOCK_ENABLED(false),
	HTTP_MOCK_SLEEP_DURATION(10),
	ADMIN_TOKEN,

	@Deprecated
	GOOGLE_SERVER_API_KEY,

	GIT_SHA,
	GIT_BRANCH,
	TWITTER_OAUTH_CONSUMER_KEY,
	TWITTER_OAUTH_CONSUMER_SECRET,
	TWITTER_OAUTH_ACCESS_TOKEN,
	TWITTER_OAUTH_ACCESS_TOKEN_SECRET,
	TWITTER_ENABLED(false),
	SENTRY_DSN,
	SENTRY_ENABLED(false),
	DEVICE_UPDATE_REQUIRED_DURATION(TimeUnit.DAYS.toMillis(7));

	private Object defaultValue;

	CoreConfigParameter(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	CoreConfigParameter() {
		this(null);
	}

	@Override
	public String getKey() {
		return name();
	}

	@Override
	public Object getDefaultValue() {
		return defaultValue;
	}
}