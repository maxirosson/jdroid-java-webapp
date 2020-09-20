package com.jdroid.javaweb.sample.firebase;

import com.jdroid.java.firebase.firestore.FirestoreRepository;

public class SampleFirestoreRepository extends FirestoreRepository<SampleEntity> {
	
	@Override
	protected String getPath() {
		return "samples";
	}

	@Override
	protected Class<SampleEntity> getEntityClass() {
		return SampleEntity.class;
	}
}
