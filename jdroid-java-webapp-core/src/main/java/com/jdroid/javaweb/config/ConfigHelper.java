package com.jdroid.javaweb.config;

import com.jdroid.java.remoteconfig.RemoteConfigLoader;
import com.jdroid.java.remoteconfig.RemoteConfigParameter;
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

public class ConfigHelper implements RemoteConfigLoader {
	
	private Repository<Pair> configRepository;
	
	public ConfigHelper() {
		PairRepository pairRepository = Application.get().getBean(PairRepository.class);
		if (pairRepository != null) {
			configRepository = new CacheWrapperRepository<>(pairRepository);
		} else {
			configRepository = new CacheWrapperRepository<>(new InMemoryRepository<Pair>());
		}
		configRepository.getAll();
	}

	private Pair getPairFromRepository(RemoteConfigParameter remoteConfigParameter) {
		Pair pair = configRepository.get(remoteConfigParameter.getKey());
		return pair != null && pair.getValue() != null ? pair : null;
	}
	
	@Override
	public Object getObject(RemoteConfigParameter remoteConfigParameter) {
		Pair pair = getPairFromRepository(remoteConfigParameter);
		if (pair != null) {
			return pair.getValue();
		} else {
			return BuildConfigUtils.getBuildConfigValue(remoteConfigParameter.getKey(), remoteConfigParameter.getDefaultValue());
		}
	}
	
	@Override
	public String getString(RemoteConfigParameter remoteConfigParameter) {
		Pair pair = getPairFromRepository(remoteConfigParameter);
		if (pair != null) {
			return pair.getValue();
		} else {
			return BuildConfigUtils.getBuildConfigString(remoteConfigParameter.getKey(), remoteConfigParameter.getDefaultValue() != null ? remoteConfigParameter.getDefaultValue().toString() : null);
		}
	}
	
	@Override
	public List<String> getStringList(RemoteConfigParameter remoteConfigParameter) {
		return StringUtils.splitWithCommaSeparator(getString(remoteConfigParameter));
	}
	
	@Override
	public Boolean getBoolean(RemoteConfigParameter remoteConfigParameter) {
		Pair pair = getPairFromRepository(remoteConfigParameter);
		if (pair != null) {
			return TypeUtils.getBoolean(pair.getValue());
		} else {
			return BuildConfigUtils.getBuildConfigBoolean(remoteConfigParameter.getKey(), (Boolean)remoteConfigParameter.getDefaultValue());
		}
	}
	
	@Override
	public Long getLong(RemoteConfigParameter remoteConfigParameter) {
		Pair pair = getPairFromRepository(remoteConfigParameter);
		if (pair != null) {
			return TypeUtils.getLong(pair.getValue());
		} else {
			Long defaultValue;
			if (remoteConfigParameter.getDefaultValue() instanceof Integer) {
				defaultValue = ((Integer)remoteConfigParameter.getDefaultValue()).longValue();
			} else {
				defaultValue = (Long)remoteConfigParameter.getDefaultValue();
			}
			return BuildConfigUtils.getBuildConfigLong(remoteConfigParameter.getKey(), defaultValue);
		}
	}
	
	@Override
	public Double getDouble(RemoteConfigParameter remoteConfigParameter) {
		Pair pair = getPairFromRepository(remoteConfigParameter);
		if (pair != null) {
			return TypeUtils.getDouble(pair.getValue());
		} else {
			Double defaultValue;
			if (remoteConfigParameter.getDefaultValue() instanceof Integer) {
				defaultValue = ((Float)remoteConfigParameter.getDefaultValue()).doubleValue();
			} else {
				defaultValue = (Double)remoteConfigParameter.getDefaultValue();
			}
			return BuildConfigUtils.getBuildConfigDouble(remoteConfigParameter.getKey(), defaultValue);
		}
	}
	
	public void saveRemoteConfigParameter(String key, Object value) {
		if (value != null) {
			configRepository.add(new Pair(key, value.toString()));
		} else {
			configRepository.remove(key);
		}
	}
	
	@Override
	public void fetch() {
		((CacheWrapperRepository)configRepository).clearCache();
		configRepository.getAll();
	}
}
