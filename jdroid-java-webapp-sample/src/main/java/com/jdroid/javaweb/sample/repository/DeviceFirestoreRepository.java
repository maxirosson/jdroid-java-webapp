package com.jdroid.javaweb.sample.repository;

import com.google.cloud.firestore.Query;
import com.jdroid.java.firebase.firestore.FirestoreRepository;
import com.jdroid.javaweb.firebase.fcm.Device;
import com.jdroid.javaweb.firebase.fcm.DeviceRepository;
import com.jdroid.javaweb.firebase.fcm.DeviceType;

public class DeviceFirestoreRepository extends FirestoreRepository<Device> implements DeviceRepository {
	
	@Override
	protected String getPath() {
		return "devices";
	}
	
	@Override
	protected Class<Device> getEntityClass() {
		return Device.class;
	}
	
	@Override
	public Device findByRegistrationToken(String registrationToken, DeviceType deviceType) {
		Query query = createCollectionReference().whereEqualTo("registrationToken", registrationToken).whereEqualTo("deviceType", deviceType);
		return getItemByQuery(query);
	}
}