package com.jdroid.javaweb.sample.context;

import com.jdroid.java.domain.Entity;
import com.jdroid.javaweb.application.AppModule;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.context.AppContext;
import com.jdroid.javaweb.rollbar.RollBarAppModule;

import java.util.Map;

public class ServerApplication extends Application<Entity> {
	
	public static ServerApplication get() {
		return (ServerApplication)Application.get();
	}
	
	@Override
	public ServerAppContext getAppContext() {
		return (ServerAppContext)super.getAppContext();
	}

	@Override
	protected AppContext createAppContext() {
		return new ServerAppContext();
	}

	@Override
	public Class<?> getBuildConfigClass() {
		return BuildConfig.class;
	}

	@Override
	protected void initAppModule(Map<String, AppModule> appModulesMap) {
		appModulesMap.put(RollBarAppModule.MODULE_NAME, new RollBarAppModule());
	}
}
