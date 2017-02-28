package com.jdroid.javaweb.api;

import com.jdroid.java.http.api.AbstractApiService;
import com.jdroid.java.http.mock.AbstractMockHttpService;
import com.jdroid.java.http.mock.JsonMockHttpService;
import com.jdroid.javaweb.config.ConfigHelper;
import com.jdroid.javaweb.config.CoreConfigParameter;

public abstract class ServerApiService extends AbstractApiService {
	
	@Override
	protected Boolean isHttpMockEnabled() {
		return ConfigHelper.getBooleanValue(CoreConfigParameter.HTTP_MOCK_ENABLED);
	}
	
	@Override
	protected AbstractMockHttpService getAbstractMockHttpServiceInstance(Object... urlSegments) {
		return new JsonMockHttpService(urlSegments) {
			
			@Override
			protected Integer getHttpMockSleepDuration(Object... urlSegments) {
				return ConfigHelper.getIntegerValue(CoreConfigParameter.HTTP_MOCK_SLEEP_DURATION);
			}
		};
	}
}
