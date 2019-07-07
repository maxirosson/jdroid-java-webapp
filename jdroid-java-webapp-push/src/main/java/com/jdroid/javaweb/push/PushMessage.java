package com.jdroid.javaweb.push;

import java.util.Map;

public interface PushMessage {
	
	Map<String, String> getParameters();
	
	void addParameter(String key, Object value);

	DeviceType getDeviceType();
	
}
