package com.jdroid.javaweb.sample.context;

import com.jdroid.java.domain.Entity;
import com.jdroid.java.marshaller.MarshallerProvider;
import com.jdroid.javaweb.application.AppModule;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.context.BuildConfigUtils;
import com.jdroid.javaweb.sample.api.controller.SampleResponse;
import com.jdroid.javaweb.sample.api.controller.SampleResponseMarshaller;
import com.jdroid.javaweb.sentry.SentryAppModule;

import java.util.Map;

public class ServerApplication extends Application<Entity> {
	
	public static ServerApplication get() {
		return (ServerApplication)Application.get();
	}
	
	@Override
	protected void onCreateApplication() {
		MarshallerProvider.get().addMarshaller(SampleResponse.class, new SampleResponseMarshaller());
	}
	
	@Override
	public Class<?> getBuildConfigClass() {
		return BuildConfig.class;
	}

	@Override
	protected void initAppModule(Map<String, AppModule> appModulesMap) {
		appModulesMap.put(SentryAppModule.MODULE_NAME, new SentryAppModule());
	}

	public String getFirebaseProjectId() {
		return BuildConfigUtils.getBuildConfigValue("FIREBASE_PROJECT_ID");
	}
	
	public String getFirebaseUrl() {
		return BuildConfigUtils.getBuildConfigValue("FIREBASE_URL");
	}

	public String getFirebaseAuthToken() {
		return BuildConfigUtils.getBuildConfigValue("FIREBASE_AUTH_TOKEN");
	}
	public String getFirebaseServiceAccount() {
		return BuildConfigUtils.getBuildConfigValue("FIREBASE_SERVICE_ACCOUNT");
	}

}
