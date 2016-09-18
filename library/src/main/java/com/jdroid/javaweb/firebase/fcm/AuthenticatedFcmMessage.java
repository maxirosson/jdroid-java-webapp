package com.jdroid.javaweb.firebase.fcm;

public abstract class AuthenticatedFcmMessage extends FcmMessage {
	
	private static final String USER_ID_KEY = "userIdKey";
	
	public AuthenticatedFcmMessage(Long userId) {
		addParameter(USER_ID_KEY, userId);
	}
}
