package com.jdroid.javaweb.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jdroid.java.date.DateUtils;
import com.jdroid.java.http.MimeType;
import com.jdroid.javaweb.application.AppModule;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.ConfigHelper;
import com.jdroid.javaweb.config.ConfigParameter;
import com.jdroid.javaweb.config.CoreConfigParameter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

public class AdminController extends AbstractController {
	
	private Map<String, Object> getInfoMap() {
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
		
		return infoMap;
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = MimeType.HTML)
	@ResponseBody
	public String getAppInfoAsText() {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<body>");
		for (Entry<String, Object> entry : getInfoMap().entrySet()) {
			builder.append("<div>");
			builder.append("\n");
			builder.append(entry.getKey());
			builder.append(": ");
			builder.append(entry.getValue());
			builder.append("</div>");
		}
		builder.append("</body>");
		builder.append("</html>");
		return builder.toString();
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getAppInfoAsJson() {
		return marshall(getInfoMap());
	}

	protected Map<String, Object> getCustomInfoMap() {
		return Maps.newHashMap();
	}

	@RequestMapping(value = "/config/reload", method = RequestMethod.GET)
	public void reloadConfig() {
		ConfigHelper.reloadConfig();
	}
	
	@RequestMapping(value = "/config", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getConfigParametersValues() {
		List<ConfigParameterInfo> configParameterInfos = Lists.newArrayList();
		for (ConfigParameter configParameter : getConfigParameters()) {
			configParameterInfos.add(new ConfigParameterInfo(configParameter.getKey(), ConfigHelper.getObjectValue(configParameter), configParameter.getDefaultValue()));
		}
		return marshall(configParameterInfos);
	}
	
	protected List<ConfigParameter> getConfigParameters() {
		return Lists.<ConfigParameter>newArrayList(CoreConfigParameter.values());
	}
	
	@RequestMapping(value = "/config/database/get", method = RequestMethod.GET, produces = MimeType.TEXT)
	@ResponseBody
	public String getConfig(@RequestParam(required = true) final String key) {
		return ConfigHelper.getStringValue(new ConfigParameter() {
			@Override
			public String getKey() {
				return key;
			}
			
			@Override
			public Object getDefaultValue() {
				return null;
			}
		});
	}
}
