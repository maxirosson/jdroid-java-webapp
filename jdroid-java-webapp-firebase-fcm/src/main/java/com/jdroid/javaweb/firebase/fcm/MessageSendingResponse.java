package com.jdroid.javaweb.firebase.fcm;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;

import java.util.List;
import java.util.Map;

public class MessageSendingResponse {

	private Boolean successful = true;
	private String errorCode;
	private DeviceType deviceType;
	private List<String> registrationTokensToRemove;
	private Map<String, String> registrationTokensToReplace;
	private Boolean retry = false;

	public MessageSendingResponse(DeviceType deviceType) {
		this.deviceType = deviceType;
		registrationTokensToRemove = Lists.newArrayList();
		registrationTokensToReplace = Maps.newHashMap();
	}
	
	public void addRegistrationTokenToRemove(String registrationTokenToRemove) {
		registrationTokensToRemove.add(registrationTokenToRemove);
	}
	
	public void addRegistrationTokenToReplace(String oldRegistrationToken, String newRegistrationToken) {
		registrationTokensToReplace.put(oldRegistrationToken, newRegistrationToken);
	}

	public List<String> getRegistrationTokensToRemove() {
		return registrationTokensToRemove;
	}
	public Map<String, String> getRegistrationTokensToReplace() {
		return registrationTokensToReplace;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public Boolean getRetry() {
		return retry;
	}

	public void setRetry(Boolean retry) {
		this.retry = retry;
	}


	public Boolean getSuccessful() {
		return successful;
	}


	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
		this.successful = false;
	}
}
