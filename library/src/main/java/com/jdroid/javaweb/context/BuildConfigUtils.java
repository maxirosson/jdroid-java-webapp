package com.jdroid.javaweb.context;

import com.jdroid.java.utils.ReflectionUtils;
import com.jdroid.javaweb.application.Application;

public class BuildConfigUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getBuildConfigValue(String property) {
		return (T)ReflectionUtils.getStaticFieldValue(Application.get().getBuildConfigClass(), property);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBuildConfigValue(String property, Object defaultValue) {
		return (T)ReflectionUtils.getStaticFieldValue(Application.get().getBuildConfigClass(), property, defaultValue);
	}

	public static String getBuildConfigBoolean(String property, String defaultValue) {
		return (String)getBuildConfigValue(property, defaultValue);
	}

	public static Boolean getBuildConfigBoolean(String property, Boolean defaultValue) {
		return (Boolean)getBuildConfigValue(property, defaultValue);
	}

	public static Integer getBuildConfigInteger(String property, Integer defaultValue) {
		return (Integer)getBuildConfigValue(property, defaultValue);
	}

	public static Long getBuildConfigLong(String property, Long defaultValue) {
		return (Long)getBuildConfigValue(property, defaultValue);
	}
}
