package com.jdroid.javaweb.push.fcm;

import java.util.List;

public class FcmResponse {

	// Required. Number of messages that were processed without an error.
	private Integer success;

	// Required. Number of messages that could not be processed.
	private Integer failure;

	// Required. Number of results that contain a canonical registration token.
	private Integer canonicalIds;

	// Required. Unique ID (number) identifying the multicast message.
	private Integer multicastId;

	// List representing the status of the messages processed. The objects are listed in the same order
	// as the request (i.e., for each registration ID in the request, its result is listed in the same index in the response).
	private List<FcmResult> results;

	public Boolean isOk() {
		return failure != null && failure == 0 && canonicalIds != null && canonicalIds == 0;
	}

	public List<FcmResult> getResults() {
		return results;
	}

	public Integer getMulticastId() {
		return multicastId;
	}

	public Integer getSuccess() {
		return success;
	}

	public Integer getCanonicalIds() {
		return canonicalIds;
	}

	public Integer getFailure() {
		return failure;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	public void setCanonicalIds(Integer canonicalIds) {
		this.canonicalIds = canonicalIds;
	}

	public void setMulticastId(Integer multicastId) {
		this.multicastId = multicastId;
	}

	public void setResults(List<FcmResult> results) {
		this.results = results;
	}
}
