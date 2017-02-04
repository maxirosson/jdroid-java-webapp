package com.jdroid.javaweb.sentry;

import com.google.common.collect.Maps;
import com.jdroid.javaweb.application.AbstractAppModule;
import com.jdroid.javaweb.application.Application;

import java.util.Map;

public class SentryAppModule extends AbstractAppModule {

	public static final String MODULE_NAME = SentryAppModule.class.getName();

	public static SentryAppModule get() {
		return (SentryAppModule)Application.get().getAppModule(MODULE_NAME);
	}

	private SentryContext sentryContext;

	public SentryAppModule() {
		sentryContext = new SentryContext();
	}

	public SentryContext getSentryContext() {
		return sentryContext;
	}

	@Override
	public Map<String, String> createAppInfoParameters() {
		Map<String, String> parameters = Maps.newHashMap();
		parameters.put("Sentry Enabled", SentryAppModule.get().getSentryContext().isSentryEnabled().toString());
		parameters.put("Sentry DSN", SentryAppModule.get().getSentryContext().getSentryDsn());
		return parameters;
	}
}
