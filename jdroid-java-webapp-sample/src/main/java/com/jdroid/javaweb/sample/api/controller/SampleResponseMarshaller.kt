package com.jdroid.javaweb.sample.api.controller

import com.jdroid.java.json.JsonMap
import com.jdroid.java.marshaller.Marshaller
import com.jdroid.java.marshaller.MarshallerMode

class SampleResponseMarshaller : Marshaller<SampleResponse, JsonMap> {

    override fun marshall(value: SampleResponse?, mode: MarshallerMode?, extras: Map<String, String>?): JsonMap? {
        val jsonMap = JsonMap(mode, extras)
        jsonMap["sampleKey"] = value?.sampleKey
        return jsonMap
    }
}
