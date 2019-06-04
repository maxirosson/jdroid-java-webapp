package com.jdroid.javaweb.api

import com.jdroid.java.http.api.AbstractApiService
import com.jdroid.java.http.mock.AbstractMockHttpService
import com.jdroid.java.http.mock.JsonMockHttpService
import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.config.CoreConfigParameter

abstract class ServerApiService : AbstractApiService() {

    override fun isHttpMockEnabled(): Boolean? {
        return Application.get().remoteConfigLoader.getBoolean(CoreConfigParameter.HTTP_MOCK_ENABLED)
    }

    override fun getAbstractMockHttpServiceInstance(vararg urlSegments: Any): AbstractMockHttpService {
        return object : JsonMockHttpService(*urlSegments) {

            override fun getHttpMockSleepDuration(vararg urlSegments: Any): Int {
                return Application.get().remoteConfigLoader.getLong(CoreConfigParameter.HTTP_MOCK_SLEEP_DURATION).toInt()
            }
        }
    }
}
