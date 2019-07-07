package com.jdroid.javaweb.firebase.fcm;

import com.jdroid.java.repository.Repository;

public interface DeviceRepository extends Repository<Device> {
	
	Device findByRegistrationToken(String registrationToken, DeviceType deviceType);
	
}