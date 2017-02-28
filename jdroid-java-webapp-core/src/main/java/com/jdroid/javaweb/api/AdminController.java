package com.jdroid.javaweb.api;

import com.google.common.collect.Maps;
import com.jdroid.java.date.DateUtils;
import com.jdroid.java.http.MimeType;
import com.jdroid.javaweb.application.AppModule;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.ConfigHelper;
import com.jdroid.javaweb.config.CoreConfigParameter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

public class AdminController extends AbstractController {

	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = MimeType.TEXT)
	@ResponseBody
	public String getAppInfo() {

		Map<String, Object> infoMap = Maps.newLinkedHashMap();
		infoMap.put("App Name", ConfigHelper.getStringValue(CoreConfigParameter.APP_NAME));
		infoMap.put("App Version", ConfigHelper.getStringValue(CoreConfigParameter.APP_VERSION));
		infoMap.put("Build Type", ConfigHelper.getStringValue(CoreConfigParameter.BUILD_TYPE));
		infoMap.put("Build Time", ConfigHelper.getStringValue(CoreConfigParameter.BUILD_TIME));
		infoMap.put("Git Branch", ConfigHelper.getStringValue(CoreConfigParameter.GIT_BRANCH));
		infoMap.put("Git Sha", ConfigHelper.getStringValue(CoreConfigParameter.GIT_SHA));
		infoMap.put("Http Mock Enabled", ConfigHelper.getBooleanValue(CoreConfigParameter.HTTP_MOCK_ENABLED));
		infoMap.put("Http Mock Sleep Duration", ConfigHelper.getIntegerValue(CoreConfigParameter.HTTP_MOCK_SLEEP_DURATION));
		infoMap.put("Default Charset", Charset.defaultCharset());
		infoMap.put("File Encoding", System.getProperty("file.encoding"));

		infoMap.put("Time Zone", TimeZone.getDefault().getID());
		infoMap.put("Current Time", DateUtils.now());

		// Twitter
		infoMap.put("Twitter Enabled", ConfigHelper.getBooleanValue(CoreConfigParameter.TWITTER_ENABLED));
		infoMap.put("Twitter Oauth Consumer Key", ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_KEY));
		infoMap.put("Twitter Oauth Consumer Secret", ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_CONSUMER_SECRET));
		infoMap.put("Twitter Oauth Access Token", ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN));
		infoMap.put("Twitter Oauth Access Token Secret", ConfigHelper.getStringValue(CoreConfigParameter.TWITTER_OAUTH_ACCESS_TOKEN_SECRET));

		// Google
		infoMap.put("Google Server API Key", ConfigHelper.getStringValue(CoreConfigParameter.GOOGLE_SERVER_API_KEY));

		for (AppModule appModule : Application.get().getAppModules()) {
			Map<String, String> params = appModule.createAppInfoParameters();
			if (params != null) {
				infoMap.putAll(params);
			}
		}

		infoMap.putAll(getCustomInfoMap());

		StringBuilder builder = new StringBuilder();
		for (Entry<String, Object> entry : infoMap.entrySet()) {
			builder.append("\n");
			builder.append(entry.getKey());
			builder.append(": ");
			builder.append(entry.getValue());
		}

		return builder.toString();
	}

	protected Map<String, Object> getCustomInfoMap() {
		return Maps.newHashMap();
	}

	@RequestMapping(value = "/config/reload", method = RequestMethod.GET)
	public void reloadConfig() {
		ConfigHelper.reloadConfig();
	}
}
