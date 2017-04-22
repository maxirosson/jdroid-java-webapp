package com.jdroid.javaweb.application;

public class TestApplication extends Application {
	@Override
	public Class<?> getBuildConfigClass() {
		return TestBuildConfig.class;
	}
}
