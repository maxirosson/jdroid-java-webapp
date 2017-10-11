package com.jdroid.javaweb.sample.firebase;

import com.jdroid.javaweb.firebase.firestore.FirestoreRepository;
import com.jdroid.javaweb.sample.context.ServerApplication;

public class SampleFirestoreRepository extends FirestoreRepository<SampleEntity> {
	
	@Override
	protected String getProjectId() {
		return ServerApplication.get().getFirebaseProjectId();
	}

	@Override
	protected String getPath() {
		return "samples";
	}

	@Override
	protected Class<SampleEntity> getEntityClass() {
		return SampleEntity.class;
	}
}
