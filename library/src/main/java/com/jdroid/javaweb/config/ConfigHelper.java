package com.jdroid.javaweb.config;

import com.jdroid.java.repository.CacheWrapperRepository;
import com.jdroid.java.repository.Pair;
import com.jdroid.java.repository.PairRepository;
import com.jdroid.java.repository.Repository;
import com.jdroid.java.utils.TypeUtils;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.context.BuildConfigUtils;

public class ConfigHelper {

	private static Repository<Pair> configRepository;

	public static String getStringValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = configRepository.get(configParameter.getKey());
		if (pair != null) {
			return pair.getValue();
		} else {
			return BuildConfigUtils.getBuildConfigValue(configParameter.getKey(), configParameter.getDefaultValue());
		}
	}

	public static Boolean getBooleanValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = configRepository.get(configParameter.getKey());
		if (pair != null) {
			return TypeUtils.getBoolean(pair.getValue());
		} else {
			return BuildConfigUtils.getBuildConfigBoolean(configParameter.getKey(), (Boolean)configParameter.getDefaultValue());
		}
	}

	public static Integer getIntegerValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = configRepository.get(configParameter.getKey());
		if (pair != null) {
			return TypeUtils.getInteger(pair.getValue());
		} else {
			return BuildConfigUtils.getBuildConfigInteger(configParameter.getKey(), (Integer)configParameter.getDefaultValue());
		}
	}

	public static void reloadConfig() {
		if (configRepository == null) {
			initConfigRepository();
		} else {
			((CacheWrapperRepository)configRepository).clearCache();
			configRepository.getAll();
		}
	}

	private static void initConfigRepository() {
		if (configRepository == null) {
			configRepository = new CacheWrapperRepository<>(Application.get().getSpringApplicationContext().getBean(PairRepository.class));
			reloadConfig();
		}
	}
}
