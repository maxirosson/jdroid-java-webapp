package com.jdroid.javaweb.api;

public class ConfigParameterInfo {
	
	private String key;
	private Object value;
	private Object defaultValue;
	
	public ConfigParameterInfo(String key, Object value, Object defaultValue) {
		this.key = key;
		this.value = value;
		this.defaultValue = defaultValue;
	}
	
	public String getKey() {
		return key;
	}
	
	public Object getValue() {
		return value;
	}
	
	public Object getDefaultValue() {
		return defaultValue;
	}
}
