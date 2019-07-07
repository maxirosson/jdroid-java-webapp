package com.jdroid.javaweb.firebase.fcm;

import com.google.firebase.messaging.Message;


public interface PushService {
	
	void addDevice(Device device, Boolean updateLastActiveTimestamp);

	void removeDevice(String instanceId, DeviceType deviceType);
	
	void send(Message message);

}