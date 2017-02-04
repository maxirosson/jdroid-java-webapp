package com.jdroid.javaweb.sentry;

import com.jdroid.javaweb.context.AbstractAppContext;

public class SentryContext extends AbstractAppContext {

	public String getSentryDsn() {
		return getBuildConfigValue("SENTRY_DSN");
	}

	public Boolean isSentryEnabled() {
		return getBuildConfigBoolean("SENTRY_ENABLED", false);
	}
}
