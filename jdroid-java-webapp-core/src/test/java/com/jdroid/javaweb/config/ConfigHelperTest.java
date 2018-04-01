package com.jdroid.javaweb.config;

import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.application.TestBuildConfig;
import com.jdroid.javaweb.commons.AbstractTest;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigHelperTest extends AbstractTest {
	
	@Test
	public void test() {
		Assert.assertEquals(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.APP_NAME), TestBuildConfig.APP_NAME);
	}
}
