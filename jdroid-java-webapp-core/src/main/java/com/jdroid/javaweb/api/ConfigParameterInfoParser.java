package com.jdroid.javaweb.api;

import com.jdroid.java.http.parser.json.JsonParser;
import com.jdroid.java.json.JSONObject;

public class ConfigParameterInfoParser extends JsonParser<JSONObject> {
	
	@Override
	public Object parse(JSONObject json) {
		return new ConfigParameterInfo(json.getString("key"), json.opt("value"), null);
	}
}
