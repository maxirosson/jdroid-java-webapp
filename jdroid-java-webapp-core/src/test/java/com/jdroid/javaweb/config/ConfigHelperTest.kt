package com.jdroid.javaweb.config

import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.application.TestBuildConfig
import com.jdroid.javaweb.commons.AbstractTest
import org.junit.Assert
import org.junit.Test

class ConfigHelperTest : AbstractTest() {

    @Test
    fun test() {
        Assert.assertEquals(Application.get().remoteConfigLoader.getString(CoreConfigParameter.APP_NAME), TestBuildConfig.APP_NAME)
    }
}
