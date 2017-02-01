package com.jdroid.javaweb.api;

import com.jdroid.java.http.api.AbstractApiService;
import com.jdroid.java.http.mock.AbstractMockHttpService;
import com.jdroid.java.http.mock.JsonMockHttpService;
import com.jdroid.javaweb.application.Application;

public abstract class ServerApiService extends AbstractApiService {
	
	@Override
	protected Boolean isHttpMockEnabled() {
		return Application.get().getAppContext().isHttpMockEnabled();
	}
	
	@Override
	protected AbstractMockHttpService getAbstractMockHttpServiceInstance(Object... urlSegments) {
		return new JsonMockHttpService(urlSegments) {
			
			@Override
			protected Integer getHttpMockSleepDuration(Object... urlSegments) {
				return Application.get().getAppContext().getHttpMockSleepDuration();
			}
		};
	}
}
