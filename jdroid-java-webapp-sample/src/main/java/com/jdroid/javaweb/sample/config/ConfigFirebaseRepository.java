package com.jdroid.javaweb.sample.config;

import com.jdroid.java.firebase.database.PairFirebaseRepository;
import com.jdroid.java.firebase.database.auth.CustomTokenFirebaseAuthenticationStrategy;
import com.jdroid.java.firebase.database.auth.FirebaseAuthenticationStrategy;
import com.jdroid.javaweb.sample.context.ServerApplication;

public class ConfigFirebaseRepository extends PairFirebaseRepository {

	@Override
	protected FirebaseAuthenticationStrategy createFirebaseAuthenticationStrategy() {
		return new CustomTokenFirebaseAuthenticationStrategy() {
			@Override
			protected String getAuthToken() {
				return ServerApplication.get().getFirebaseAuthToken();
			}
		};
	}

	@Override
	protected String getFirebaseUrl() {
		return ServerApplication.get().getFirebaseUrl();
	}

	@Override
	protected String getPath() {
		return "config";
	}
}
