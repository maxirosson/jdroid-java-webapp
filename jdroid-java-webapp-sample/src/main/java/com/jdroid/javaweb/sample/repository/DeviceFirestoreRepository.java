package com.jdroid.javaweb.sample.repository;

import com.google.cloud.firestore.Query;
import com.jdroid.javaweb.firebase.fcm.Device;
import com.jdroid.javaweb.firebase.fcm.DeviceRepository;
import com.jdroid.javaweb.firebase.fcm.DeviceType;
import com.jdroid.javaweb.firebase.firestore.FirestoreRepository;
import com.jdroid.javaweb.sample.context.BuildConfig;
import com.jdroid.javaweb.sample.context.ServerApplication;

public class DeviceFirestoreRepository extends FirestoreRepository<Device> implements DeviceRepository {
	
	@Override
	protected String getServiceAccountJsonPath() {
		return BuildConfig.FIREBASE_SERVICE_ACCOUNT;
	}
	
	@Override
	protected String getProjectId() {
		return ServerApplication.get().getFirebaseProjectId();
	}
	
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
	
	@Override
	public Device findByInstanceId(String instanceId, DeviceType deviceType) {
		Query query = createCollectionReference().whereEqualTo("instanceId", instanceId).whereEqualTo("deviceType", deviceType);
		return getItemByQuery(query);
	}
}