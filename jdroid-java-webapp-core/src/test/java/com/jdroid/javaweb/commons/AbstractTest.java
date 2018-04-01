package com.jdroid.javaweb.commons;

import com.jdroid.javaweb.application.TestApplication;

import org.testng.annotations.BeforeClass;

public class AbstractTest {
	
	@BeforeClass
	public void onBeforeClass() {
		TestApplication testApplication = new TestApplication();
		testApplication.init();
	}
}
