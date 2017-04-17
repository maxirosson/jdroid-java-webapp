package com.jdroid.javaweb.application;

import java.util.Map;

public class AbstractAppModule implements AppModule {

	@Override
	public Map<String, String> getServerInfoMap() {
		return null;
	}

	@Override
	public void onCreateApplication() {
		// Do Nothing
	}

	@Override
	public void onContextDestroyed() {
		// Do Nothing
	}
}
