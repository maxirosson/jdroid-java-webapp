package com.jdroid.javaweb.sentry;

import com.getsentry.raven.Raven;
import com.getsentry.raven.RavenFactory;
import com.jdroid.javaweb.application.AbstractAppModule;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.CoreConfigParameter;

public class SentryAppModule extends AbstractAppModule {

	public static final String MODULE_NAME = SentryAppModule.class.getName();

	public static SentryAppModule get() {
		return (SentryAppModule)Application.get().getAppModule(MODULE_NAME);
	}

	private Raven raven;

	public Raven getRaven() {
		if (raven == null) {
			raven = RavenFactory.ravenInstance(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.SENTRY_DSN));
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
