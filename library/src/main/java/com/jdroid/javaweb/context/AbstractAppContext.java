package com.jdroid.javaweb.context;

public abstract class AbstractAppContext {

	@SuppressWarnings("unchecked")
	public <T> T getBuildConfigValue(String property) {
		return getBuildConfigValue(property, null);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBuildConfigValue(String property, Object defaultValue) {
		return BuildConfigUtils.getBuildConfigValue(property, defaultValue);
	}

	public Boolean getBuildConfigBoolean(String property, Boolean defaultValue) {
		return BuildConfigUtils.getBuildConfigValue(property, defaultValue);
	}
}
