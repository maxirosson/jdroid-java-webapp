package com.jdroid.javaweb.application

import com.jdroid.java.domain.Entity

class TestApplication : Application<Entity>() {
    override fun getBuildConfigClass(): Class<*> {
        return TestBuildConfig::class.java
    }
}
