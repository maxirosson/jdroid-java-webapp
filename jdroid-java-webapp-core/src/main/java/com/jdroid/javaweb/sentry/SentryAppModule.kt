package com.jdroid.javaweb.sentry

import com.getsentry.raven.Raven
import com.getsentry.raven.RavenFactory
import com.jdroid.javaweb.application.AbstractAppModule
import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.config.CoreConfigParameter

class SentryAppModule : AbstractAppModule() {

    companion object {

        val MODULE_NAME: String = SentryAppModule::class.java.name

        fun get(): SentryAppModule {
            return Application.get().getAppModule(MODULE_NAME) as SentryAppModule
        }
    }

    val raven: Raven by lazy {
        RavenFactory.ravenInstance(Application.get().remoteConfigLoader.getString(CoreConfigParameter.SENTRY_DSN))
    }

    override fun onContextDestroyed() {
        raven.closeConnection()
    }
}
