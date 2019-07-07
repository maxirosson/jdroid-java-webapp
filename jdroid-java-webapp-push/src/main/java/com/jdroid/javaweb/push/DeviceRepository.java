package com.jdroid.javaweb.push;

import com.jdroid.java.repository.Repository;

public interface DeviceRepository extends Repository<Device> {
	
	Device findByRegistrationToken(String registrationToken, DeviceType deviceType);
	
	@Deprecated
	Device findByInstanceId(String instanceId, DeviceType deviceType);
	
}