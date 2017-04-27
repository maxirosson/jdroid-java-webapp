package com.jdroid.javaweb.push.fcm;

public enum FcmMessagePriority {

	NORMAL("normal"),
	HIGH("high");

	private String parameter;

	FcmMessagePriority(String parameter) {
		this.parameter = parameter;
	}

	public String getParameter() {
		return parameter;
	}

	public static FcmMessagePriority findByParameter(String parameter) {
		for (FcmMessagePriority each : values()) {
			if (each.getParameter().equals(parameter)) {
				return each;
			}
		}
		return null;
	}
}
