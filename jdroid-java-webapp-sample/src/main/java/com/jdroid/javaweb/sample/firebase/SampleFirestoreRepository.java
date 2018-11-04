package com.jdroid.javaweb.sample.firebase;

import com.jdroid.javaweb.firebase.firestore.FirestoreRepository;

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
