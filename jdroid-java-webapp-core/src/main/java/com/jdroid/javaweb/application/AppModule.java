package com.jdroid.javaweb.application;

import java.util.Map;

public interface AppModule {

	public Map<String, String> createAppInfoParameters();

	public void onCreateApplication();

	public void onContextDestroyed();
}
