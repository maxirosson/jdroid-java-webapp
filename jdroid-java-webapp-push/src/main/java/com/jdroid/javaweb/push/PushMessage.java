package com.jdroid.javaweb.push;

import java.util.Map;

public interface PushMessage {
	
	public Map<String, String> getParameters();
	
	public void addParameter(String key, Object value);

	public DeviceType getDeviceType();
	
}
