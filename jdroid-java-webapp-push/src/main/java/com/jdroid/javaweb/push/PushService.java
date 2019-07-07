package com.jdroid.javaweb.push;

public interface PushService {
	
	void addDevice(Device device, Boolean updateLastActiveTimestamp);

	void removeDevice(String instanceId, DeviceType deviceType);
	
	void send(PushMessage pushMessage);

	void processResponse(PushResponse pushResponse);
}