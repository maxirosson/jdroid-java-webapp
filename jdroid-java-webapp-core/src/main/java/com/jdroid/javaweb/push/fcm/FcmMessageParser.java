package com.jdroid.javaweb.push.fcm;

import com.jdroid.java.http.parser.json.JsonParser;
import com.jdroid.java.json.JSONObject;

public class FcmMessageParser extends JsonParser<JSONObject> {

	@Override
	public Object parse(JSONObject json) {
		FcmMessage fcmMessage = new FcmMessage();
		fcmMessage.setTo(json.optString(FcmMessageMarshaller.TO));
		fcmMessage.setRegistrationIds(json.optList(FcmMessageMarshaller.REGISTRATION_IDS));
		fcmMessage.setCollapseKey(json.optString(FcmMessageMarshaller.COLLAPSE_KEY));
		FcmMessagePriority priority = FcmMessagePriority.findByParameter(json.optString(FcmMessageMarshaller.PRIORITY));
		if (priority != null) {
			fcmMessage.setPriority(priority);
		}
		fcmMessage.setTimeToLive(json.optInt(FcmMessageMarshaller.TIME_TO_LIVE));
		JSONObject data = json.getJSONObject(FcmMessageMarshaller.DATA);
		for (Object each : data.keySet()) {
			fcmMessage.addParameter(each.toString(), data.getString(each.toString()));
		}
		return fcmMessage;
	}
}
