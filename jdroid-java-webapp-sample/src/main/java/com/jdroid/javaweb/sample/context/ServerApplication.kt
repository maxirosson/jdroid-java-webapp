package com.jdroid.javaweb.sample.context

import com.jdroid.java.domain.Entity
import com.jdroid.java.marshaller.MarshallerProvider
import com.jdroid.javaweb.application.AppModule
import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.context.BuildConfigUtils
import com.jdroid.java.firebase.admin.FirebaseAdminSdkHelper
import com.jdroid.javaweb.sample.api.controller.SampleResponse
import com.jdroid.javaweb.sample.api.controller.SampleResponseMarshaller
import com.jdroid.javaweb.sentry.SentryAppModule

class ServerApplication : Application<Entity>() {

    val firebaseUrl: String
        get() = BuildConfigUtils.getBuildConfigValue("FIREBASE_URL")

    val firebaseAuthToken: String
        get() = BuildConfigUtils.getBuildConfigValue("FIREBASE_AUTH_TOKEN")

    override fun onCreateApplication() {
        super.onCreateApplication()

        FirebaseAdminSdkHelper.init(BuildConfig.FIREBASE_SERVICE_ACCOUNT)
        MarshallerProvider.get().addMarshaller(SampleResponse::class.java, SampleResponseMarshaller())
    }

    override fun getBuildConfigClass(): Class<*> {
        return BuildConfig::class.java
    }

    override fun initAppModule(appModulesMap: MutableMap<String, AppModule>) {
        appModulesMap[SentryAppModule.MODULE_NAME] = SentryAppModule()
    }

    companion object {

        fun get(): ServerApplication {
            return Application.get() as ServerApplication
        }
    }
}
