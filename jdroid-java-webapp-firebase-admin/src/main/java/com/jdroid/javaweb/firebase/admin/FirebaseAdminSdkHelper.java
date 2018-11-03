package com.jdroid.javaweb.firebase.admin;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.jdroid.java.exception.UnexpectedException;

import java.io.FileInputStream;
import java.io.IOException;


public class FirebaseAdminSdkHelper {

	public static void init(String serviceAccountJsonPath) {
		try {
			FileInputStream serviceAccount = new FileInputStream(serviceAccountJsonPath);
			FirebaseOptions.Builder builder = new FirebaseOptions.Builder();
			builder.setCredentials(GoogleCredentials.fromStream(serviceAccount));
			FirebaseOptions options = builder.build();
			FirebaseApp.initializeApp(options);
		} catch(IOException e) {
			throw new UnexpectedException(e);
		}
	}
}
