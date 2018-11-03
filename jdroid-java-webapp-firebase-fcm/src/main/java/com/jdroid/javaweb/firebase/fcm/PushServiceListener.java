package com.jdroid.javaweb.firebase.fcm;

public interface PushServiceListener {

	void onAddDevice(String instanceId, DeviceType deviceType);

	void onUpdateDevice(String instanceId, DeviceType deviceType);

	void onRemoveDevice(String instanceId, DeviceType deviceType);
}
