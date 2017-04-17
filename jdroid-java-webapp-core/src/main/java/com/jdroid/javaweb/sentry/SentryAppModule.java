package com.jdroid.javaweb.sentry;

import com.getsentry.raven.Raven;
import com.getsentry.raven.RavenFactory;
import com.google.common.collect.Maps;
import com.jdroid.javaweb.application.AbstractAppModule;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.ConfigHelper;
import com.jdroid.javaweb.config.CoreConfigParameter;

import java.util.Map;

public class SentryAppModule extends AbstractAppModule {

	public static final String MODULE_NAME = SentryAppModule.class.getName();

	public static SentryAppModule get() {
		return (SentryAppModule)Application.get().getAppModule(MODULE_NAME);
	}

	private Raven raven;

	public Raven getRaven() {
		if (raven == null) {
			raven = RavenFactory.ravenInstance(ConfigHelper.getStringValue(CoreConfigParameter.SENTRY_DSN));
		}
		return raven;
	}

	@Override
	public void onContextDestroyed() {
		if (raven != null) {
			getRaven().closeConnection();
		}
	}
}
