package com.jdroid.javaweb.firebase.fcm;

public interface PushServiceListener {

	public void onAddDevice(String instanceId, DeviceType deviceType);

	public void onUpdateDevice(String instanceId, DeviceType deviceType);

	public void onRemoveDevice(String instanceId, DeviceType deviceType);
}
