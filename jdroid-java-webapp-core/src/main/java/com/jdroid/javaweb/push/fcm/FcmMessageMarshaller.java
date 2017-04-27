package com.jdroid.javaweb.push.fcm;

import com.jdroid.java.json.JsonMap;
import com.jdroid.java.marshaller.Marshaller;
import com.jdroid.java.marshaller.MarshallerMode;

import java.util.Map;

public class FcmMessageMarshaller implements Marshaller<FcmMessage, JsonMap> {

	public static final String COLLAPSE_KEY = "collapse_key";
	public static final String REGISTRATION_IDS = "registration_ids";
	public static final String TO = "to";
	public static final String PRIORITY = "priority";
	public static final String TIME_TO_LIVE = "time_to_live";
	public static final String DATA = "data";

	@Override
	public JsonMap marshall(FcmMessage fcmMessage, MarshallerMode mode, Map<String, String> extras) {
		JsonMap jsonMap = new JsonMap(mode, extras);
		jsonMap.put(TO, fcmMessage.getTo());
		jsonMap.put(REGISTRATION_IDS, fcmMessage.getRegistrationIds());
		jsonMap.put(COLLAPSE_KEY, fcmMessage.getCollapseKey());
		jsonMap.put(PRIORITY, fcmMessage.getPriority().getParameter());
		jsonMap.put(TIME_TO_LIVE, fcmMessage.getTimeToLive());
		jsonMap.put(DATA, fcmMessage.getParameters());
		return jsonMap;
	}
}