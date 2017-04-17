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

public abstract class AdminController extends AbstractController {
	
	private Map<String, Object> getServerInfoMap() {
		Map<String, Object> infoMap = Maps.newLinkedHashMap();
		infoMap.put("Default Charset", Charset.defaultCharset());
		infoMap.put("File Encoding", System.getProperty("file.encoding"));
		
		infoMap.put("Time Zone", TimeZone.getDefault().getID());
		infoMap.put("Current Time", DateUtils.now());
		
		for (AppModule appModule : Application.get().getAppModules()) {
			Map<String, String> params = appModule.getServerInfoMap();
			if (params != null) {
				infoMap.putAll(params);
			}
		}
		
		infoMap.putAll(getCustomInfoMap());
		
		return infoMap;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET, produces = MimeType.HTML)
	@ResponseBody
	public String getIndex() {
		StringBuilder builder = new StringBuilder();
		builder.append("<html>");
		builder.append("<body>");
		builder.append("<h2>Server Info</h2>");
		for (Entry<String, Object> entry : getServerInfoMap().entrySet()) {
			builder.append("<div>");
			builder.append(entry.getKey());
			builder.append(": ");
			builder.append(entry.getValue());
			builder.append("</div>");
			builder.append("\n");
		}
		
		builder.append("<h2>Config Parameters</h2>");
		for (ConfigParameter configParameter : getConfigParameters()) {
			builder.append("<div>");
			builder.append(configParameter.getKey());
			builder.append(": ");
			builder.append(ConfigHelper.getObjectValue(configParameter));
			builder.append("</div>");
			builder.append("\n");
		}
		
		builder.append("</body>");
		builder.append("</html>");
		return builder.toString();
	}
	
	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getServerInfo() {
		return marshall(getServerInfoMap());
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
	
	@RequestMapping(value = "/config/save", method = RequestMethod.GET)
	public void saveConfigParameter(@RequestParam(required = true) String key, @RequestParam String value) {
		ConfigHelper.saveConfigParameter(key, value);
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
