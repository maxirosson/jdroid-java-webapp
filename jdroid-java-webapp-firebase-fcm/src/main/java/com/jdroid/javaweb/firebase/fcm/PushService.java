package com.jdroid.javaweb.firebase.fcm;

import com.google.firebase.messaging.Message;


public interface PushService {
	
	public void addDevice(Device device, Boolean updateLastActiveTimestamp);

	public void removeDevice(String instanceId, DeviceType deviceType);
	
	public void send(Message message);

	public void processResponse(MessageSendingResponse messageSendingResponse);
}