package com.jdroid.javaweb.firebase.fcm;

import com.jdroid.java.repository.Repository;

public interface DeviceRepository extends Repository<Device> {
	
	public Device findByRegistrationToken(String registrationToken, DeviceType deviceType);
	
	@Deprecated
	public Device findByInstanceId(String instanceId, DeviceType deviceType);
	
}