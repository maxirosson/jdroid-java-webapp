package com.jdroid.javaweb.api;

import com.jdroid.java.json.JsonMap;
import com.jdroid.java.marshaller.Marshaller;
import com.jdroid.java.marshaller.MarshallerMode;

import java.util.Map;

public class ConfigParameterInfoMarshaller implements Marshaller<ConfigParameterInfo, JsonMap> {
	
	@Override
	public JsonMap marshall(ConfigParameterInfo configParameterInfo, MarshallerMode mode, Map<String, String> extras) {
		JsonMap jsonMap = new JsonMap(mode, extras);
		jsonMap.put("key", configParameterInfo.getKey());
		jsonMap.put("value", configParameterInfo.getValue());
		jsonMap.put("defaultValue", configParameterInfo.getDefaultValue());
		return jsonMap;
	}
}
