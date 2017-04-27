package com.jdroid.javaweb.push.fcm;

import com.jdroid.java.http.parser.json.JsonParser;
import com.jdroid.java.json.JSONObject;

import java.util.List;

public class FcmResponseParser extends JsonParser<JSONObject> {

	@Override
	public Object parse(JSONObject json) {
		FcmResponse fcmResponse = new FcmResponse();
		fcmResponse.setSuccess(json.optInt("success"));
		fcmResponse.setFailure(json.optInt("failure"));
		fcmResponse.setCanonicalIds(json.optInt("canonical_ids"));
		fcmResponse.setMulticastId(json.optInt("multicast_id"));
		List<FcmResult> results = parseList(json, "results", new FcmResultParser());
		fcmResponse.setResults(results);
		return fcmResponse;
	}

	private class FcmResultParser extends JsonParser<JSONObject> {

		@Override
		public Object parse(JSONObject json) {
			FcmResult fcmResult = new FcmResult();
			fcmResult.setMessageId(json.optString("message_id"));
			fcmResult.setRegistrationId(json.optString("registration_id"));
			fcmResult.setError(json.optString("error"));
			return fcmResult;
		}
	}
}