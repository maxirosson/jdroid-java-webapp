package com.jdroid.javaweb.config;

import com.jdroid.java.repository.CacheWrapperRepository;
import com.jdroid.java.repository.InMemoryRepository;
import com.jdroid.java.repository.Pair;
import com.jdroid.java.repository.PairRepository;
import com.jdroid.java.repository.Repository;
import com.jdroid.java.utils.StringUtils;
import com.jdroid.java.utils.TypeUtils;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.context.BuildConfigUtils;

import java.util.List;

public class ConfigHelper {
	
	private static Repository<Pair> configRepository;

	private static Pair getPairFromRepository(ConfigParameter configParameter) {
		Pair pair = configRepository.get(configParameter.getKey());
		return pair != null && pair.getValue() != null ? pair : null;
	}
	
	public static Object getObjectValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = getPairFromRepository(configParameter);
		if (pair != null) {
			return pair.getValue();
		} else {
			return BuildConfigUtils.getBuildConfigValue(configParameter.getKey(), configParameter.getDefaultValue());
		}
	}

	public static String getStringValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = getPairFromRepository(configParameter);
		if (pair != null) {
			return pair.getValue();
		} else {
			return BuildConfigUtils.getBuildConfigString(configParameter.getKey(), configParameter.getDefaultValue() != null ? configParameter.getDefaultValue().toString() : null);
		}
	}
	
	public static List<String> getStringListValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = getPairFromRepository(configParameter);
		String value = null;
		if (pair != null) {
			value = pair.getValue();
		} else {
			value = BuildConfigUtils.getBuildConfigString(configParameter.getKey(), (String)configParameter.getDefaultValue());
		}
		return StringUtils.splitWithCommaSeparator(value);
	}

	public static Boolean getBooleanValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = getPairFromRepository(configParameter);
		if (pair != null) {
			return TypeUtils.getBoolean(pair.getValue());
		} else {
			return BuildConfigUtils.getBuildConfigBoolean(configParameter.getKey(), (Boolean)configParameter.getDefaultValue());
		}
	}

	public static Integer getIntegerValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = getPairFromRepository(configParameter);
		if (pair != null) {
			return TypeUtils.getInteger(pair.getValue());
		} else {
			return BuildConfigUtils.getBuildConfigInteger(configParameter.getKey(), (Integer)configParameter.getDefaultValue());
		}
	}

	public static Long getLongValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = getPairFromRepository(configParameter);
		if (pair != null) {
			return TypeUtils.getLong(pair.getValue());
		} else {
			return BuildConfigUtils.getBuildConfigLong(configParameter.getKey(), (Long)configParameter.getDefaultValue());
		}
	}
	
	public static Double getDoubleValue(ConfigParameter configParameter) {
		initConfigRepository();
		Pair pair = getPairFromRepository(configParameter);
		if (pair != null) {
			return TypeUtils.getDouble(pair.getValue());
		} else {
			return BuildConfigUtils.getBuildConfigDouble(configParameter.getKey(), (Double)configParameter.getDefaultValue());
		}
	}
	
	public static void saveConfigParameter(String key, Object value) {
		initConfigRepository();
		if (value != null) {
			configRepository.add(new Pair(key, value.toString()));
		} else {
			configRepository.remove(key);
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
			PairRepository pairRepository = Application.get().getBean(PairRepository.class);
			if (pairRepository != null) {
				configRepository = new CacheWrapperRepository<>(pairRepository);
			} else {
				configRepository = new CacheWrapperRepository<>(new InMemoryRepository<Pair>());
			}
			reloadConfig();
		}
	}
}
