package com.jdroid.javaweb.api;

import com.jdroid.java.collections.Lists;
import com.jdroid.java.collections.Maps;
import com.jdroid.java.date.DateConfiguration;
import com.jdroid.java.date.DateUtils;
import com.jdroid.java.http.MimeType;
import com.jdroid.java.http.parser.json.GsonParser;
import com.jdroid.java.remoteconfig.RemoteConfigParameter;
import com.jdroid.javaweb.application.AppModule;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.ConfigHelper;
import com.jdroid.javaweb.config.CoreConfigParameter;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

public abstract class AdminController extends AbstractController {
	
	private Map<String, Object> getServerInfoMap() {
		Map<String, Object> infoMap = Maps.INSTANCE.newLinkedHashMap();
		infoMap.put("Default Charset", Charset.defaultCharset());
		infoMap.put("File Encoding", System.getProperty("file.encoding"));
		
		infoMap.put("Time Zone", TimeZone.getDefault().getID());
		infoMap.put("Current Time", DateUtils.INSTANCE.now());
		infoMap.put("Fake Now", DateConfiguration.INSTANCE.isFakeNow());
		infoMap.put("Fake Timestamp", DateConfiguration.INSTANCE.getFakeNow());
		
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
			builder.append("<div><b>");
			builder.append(entry.getKey());
			builder.append("</b>: ");
			builder.append(entry.getValue());
			builder.append("</div>");
			builder.append("\n");
		}
		
		builder.append("<h2>Config Parameters</h2>");
		for (RemoteConfigParameter remoteConfigParameter : getRemoteConfigParameters()) {
			builder.append("<div><b>");
			builder.append(remoteConfigParameter.getKey());
			builder.append("</b>: ");
			builder.append(Application.get().getRemoteConfigLoader().getObject(remoteConfigParameter));
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
		return Maps.INSTANCE.newHashMap();
	}
	
	@RequestMapping(value = "/fakeNow/save", method = RequestMethod.GET)
	public void saveFakeNow(@RequestParam(required = false) Long timestamp) {
		if (timestamp != null) {
			DateConfiguration.INSTANCE.setFakeNow(new Date(timestamp));
		} else {
			DateConfiguration.INSTANCE.setFakeNow(null);
		}
	}
	
	@RequestMapping(value = "/fakeNow", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getFakeNow() {
		Map<String, Object> map = Maps.INSTANCE.newHashMap();
		map.put("timestamp", DateConfiguration.INSTANCE.isFakeNow() ? DateConfiguration.INSTANCE.getFakeNow().getTime() : null);
		return marshall(map);
	}

	@RequestMapping(value = "/config/fetch", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String fetch() {
		Application.get().getRemoteConfigLoader().fetch();
		return getRemoteConfigParametersValues();
	}
	
	@RequestMapping(value = "/config", method = RequestMethod.GET, produces = MimeType.JSON_UTF8)
	@ResponseBody
	public String getRemoteConfigParametersValues() {
		List<ConfigParameterInfo> configParameterInfos = Lists.INSTANCE.newArrayList();
		for (RemoteConfigParameter configParameter : getRemoteConfigParameters()) {
			configParameterInfos.add(new ConfigParameterInfo(configParameter.getKey(), Application.get().getRemoteConfigLoader().getObject(configParameter), configParameter.getDefaultValue()));
		}
		return autoMarshall(configParameterInfos);
	}
	
	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public void saveRemoteConfigParameter(@RequestBody String configJSON) {
		ConfigParameterInfo configParameterInfo = (ConfigParameterInfo)new GsonParser(ConfigParameterInfo.class).parse(configJSON);
		((ConfigHelper)Application.get().getRemoteConfigLoader()).saveRemoteConfigParameter(configParameterInfo.getKey(), configParameterInfo.getValue());
	}
	
	protected List<RemoteConfigParameter> getRemoteConfigParameters() {
		return Lists.INSTANCE.newArrayList(CoreConfigParameter.values());
	}
	
	@RequestMapping(value = "/config/database", method = RequestMethod.GET, produces = MimeType.TEXT)
	@ResponseBody
	public String getConfig(@RequestParam(required = true) final String key) {
		return Application.get().getRemoteConfigLoader().getString(new RemoteConfigParameter() {
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
