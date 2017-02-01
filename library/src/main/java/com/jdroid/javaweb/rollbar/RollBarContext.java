package com.jdroid.javaweb.rollbar;

import com.jdroid.javaweb.context.AbstractAppContext;

public class RollBarContext extends AbstractAppContext {

	public String getRollBarAccessToken() {
		return getBuildConfigValue("ROLLBAR_ACCESS_TOKEN");
	}

	public Boolean isRollBarEnabled() {
		return getBuildConfigBoolean("ROLLBAR_ENABLED", false);
	}
}
