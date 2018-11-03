package com.jdroid.javaweb.firebase.fcm;

public enum DeviceType {
	ANDROID("android"),
	IOS("iOS");

	private String userAgent;

	DeviceType(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public static DeviceType find(String userAgent) {
		for (DeviceType each : values()) {
			if (each.userAgent.equalsIgnoreCase(userAgent)) {
				return each;
			}
		}
		return null;
	}

	public String getUserAgent() {
		return userAgent;
	}
}
