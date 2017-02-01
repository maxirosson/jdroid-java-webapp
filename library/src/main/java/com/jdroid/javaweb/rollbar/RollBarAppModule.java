package com.jdroid.javaweb.rollbar;

import com.google.common.collect.Maps;
import com.jdroid.javaweb.application.AbstractAppModule;
import com.jdroid.javaweb.application.Application;

import java.util.Map;

public class RollBarAppModule extends AbstractAppModule {

	public static final String MODULE_NAME = RollBarAppModule.class.getName();

	public static RollBarAppModule get() {
		return (RollBarAppModule)Application.get().getAppModule(MODULE_NAME);
	}

	private RollBarContext rollBarContext;

	public RollBarAppModule() {
		rollBarContext = new RollBarContext();
	}

	public RollBarContext getRollBarContext() {
		return rollBarContext;
	}

	@Override
	public Map<String, String> createAppInfoParameters() {
		Map<String, String> parameters = Maps.newHashMap();
		parameters.put("RollBar Enabled", RollBarAppModule.get().getRollBarContext().isRollBarEnabled().toString());
		parameters.put("RollBar Access Token", RollBarAppModule.get().getRollBarContext().getRollBarAccessToken());
		return parameters;
	}
}
