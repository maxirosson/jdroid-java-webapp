package com.jdroid.javaweb.sample.context;


import com.jdroid.javaweb.context.AppContext;

/**
 * The application context
 */
public class ServerAppContext extends AppContext {
	
	public String getFirebaseUrl() {
		return BuildConfig.FIREBASE_URL;
	}

	public String getFirebaseAuthToken() {
		return BuildConfig.FIREBASE_AUTH_TOKEN;
	}
}
