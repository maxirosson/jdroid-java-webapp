package com.jdroid.javaweb.api;

import com.jdroid.java.http.api.AbstractApiService;
import com.jdroid.java.http.mock.AbstractMockHttpService;
import com.jdroid.java.http.mock.JsonMockHttpService;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.CoreConfigParameter;

public abstract class ServerApiService extends AbstractApiService {
	
	@Override
	protected Boolean isHttpMockEnabled() {
		return Application.get().getRemoteConfigLoader().getBoolean(CoreConfigParameter.HTTP_MOCK_ENABLED);
	}
	
	@Override
	protected AbstractMockHttpService getAbstractMockHttpServiceInstance(Object... urlSegments) {
		return new JsonMockHttpService(urlSegments) {
			
			@Override
			protected Integer getHttpMockSleepDuration(Object... urlSegments) {
				return Application.get().getRemoteConfigLoader().getLong(CoreConfigParameter.HTTP_MOCK_SLEEP_DURATION).intValue();
			}
		};
	}
}
