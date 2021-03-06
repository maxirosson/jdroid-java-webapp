package com.jdroid.javaweb.sample.firebase;

import com.jdroid.java.firebase.database.FirebaseDatabaseRepository;
import com.jdroid.java.firebase.database.auth.CustomTokenFirebaseAuthenticationStrategy;
import com.jdroid.java.firebase.database.auth.FirebaseAuthenticationStrategy;
import com.jdroid.javaweb.sample.context.ServerApplication;

public class SampleFirebaseRepository extends FirebaseDatabaseRepository<SampleEntity> {

	@Override
	protected FirebaseAuthenticationStrategy createFirebaseAuthenticationStrategy() {
		return new CustomTokenFirebaseAuthenticationStrategy() {
			@Override
			protected String getAuthToken() {
				return ServerApplication.Companion.get().getFirebaseAuthToken();
			}
		};
	}

	@Override
	protected String getFirebaseUrl() {
		return ServerApplication.Companion.get().getFirebaseUrl();
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
