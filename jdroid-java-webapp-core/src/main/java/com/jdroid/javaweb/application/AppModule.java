package com.jdroid.javaweb.application;

import java.util.Map;

public interface AppModule {

	public Map<String, String> getServerInfoMap();

	public void onCreateApplication();

	public void onContextDestroyed();
}
