package com.jdroid.javaweb.api

import com.jdroid.java.collections.Maps
import com.jdroid.java.date.DateConfiguration
import com.jdroid.java.date.DateUtils
import com.jdroid.java.http.MimeType
import com.jdroid.java.http.parser.json.GsonParser
import com.jdroid.java.remoteconfig.RemoteConfigParameter
import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.config.ConfigHelper
import com.jdroid.javaweb.config.CoreConfigParameter
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.nio.charset.Charset
import java.util.Date
import java.util.TimeZone

abstract class AdminController : AbstractController() {

    private fun getServerInfoMap(): Map<String, Any?> {
        val infoMap = Maps.newLinkedHashMap<String, Any?>()
        infoMap["Default Charset"] = Charset.defaultCharset()
        infoMap["File Encoding"] = System.getProperty("file.encoding")

        infoMap["Time Zone"] = TimeZone.getDefault().id
        infoMap["Current Time"] = DateUtils.now()
        infoMap["Fake Now"] = DateConfiguration.isFakeNow()
        infoMap["Fake Timestamp"] = DateConfiguration.getFakeNow()

        for (appModule in Application.get().appModules) {
            val params = appModule.getServerInfoMap()
            if (params != null) {
                infoMap.putAll(params)
            }
        }

        infoMap.putAll(getCustomInfoMap())

        return infoMap
    }

    @RequestMapping(value = ["/index"], method = [RequestMethod.GET], produces = [MimeType.HTML])
    @ResponseBody
    fun getIndex(): String {
        val builder = StringBuilder()
        builder.append("<html>")
        builder.append("<body>")
        builder.append("<h2>Server Info</h2>")
        for ((key, value) in getServerInfoMap()) {
            builder.append("<div><b>")
            builder.append(key)
            builder.append("</b>: ")
            builder.append(value)
            builder.append("</div>")
            builder.append("\n")
        }

        builder.append("<h2>Config Parameters</h2>")
        for (remoteConfigParameter in getRemoteConfigParameters()) {
            builder.append("<div><b>")
            builder.append(remoteConfigParameter.getKey())
            builder.append("</b>: ")
            builder.append(Application.get().remoteConfigLoader.getObject(remoteConfigParameter))
            builder.append("</div>")
            builder.append("\n")
        }

        builder.append("</body>")
        builder.append("</html>")
        return builder.toString()
    }

    @RequestMapping(value = ["/info"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun getServerInfo(): String {
        return marshall(getServerInfoMap())
    }

    protected open fun getCustomInfoMap(): Map<String, Any> {
        return Maps.newHashMap()
    }

    @RequestMapping(value = ["/fakeNow/save"], method = [RequestMethod.GET])
    fun saveFakeNow(@RequestParam(required = false) timestamp: Long?) {
        if (timestamp != null) {
            DateConfiguration.setFakeNow(Date(timestamp))
        } else {
            DateConfiguration.setFakeNow(null)
        }
    }

    @RequestMapping(value = ["/fakeNow"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun getFakeNow(): String {
        val map = Maps.newHashMap<String, Any?>()
        map["timestamp"] = if (DateConfiguration.isFakeNow()) DateConfiguration.getFakeNow()?.time else null
        return marshall(map)
    }

    @RequestMapping(value = ["/config/fetch"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun fetch(): String {
        Application.get().remoteConfigLoader.fetch()
        return getRemoteConfigParametersValues()
    }

    @RequestMapping(value = ["/config"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    fun getRemoteConfigParametersValues(): String {
        val configParameterInfos = mutableListOf<ConfigParameterInfo>()
        for (configParameter in getRemoteConfigParameters()) {
            configParameterInfos.add(
                ConfigParameterInfo(
                    configParameter.getKey(),
                    Application.get().remoteConfigLoader.getObject(configParameter)!!,
                    configParameter.getDefaultValue()!!
                )
            )
        }
        return autoMarshall(configParameterInfos)
    }

    @RequestMapping(value = ["/config"], method = [RequestMethod.POST])
    fun saveRemoteConfigParameter(@RequestBody configJSON: String) {
        val configParameterInfo = GsonParser(ConfigParameterInfo::class.java).parse(configJSON) as ConfigParameterInfo
        (Application.get().remoteConfigLoader as ConfigHelper).saveRemoteConfigParameter(configParameterInfo.key, configParameterInfo.value)
    }

    protected open fun getRemoteConfigParameters(): List<RemoteConfigParameter> {
        return listOf<RemoteConfigParameter>(*CoreConfigParameter.values())
    }

    @RequestMapping(value = ["/config/database"], method = [RequestMethod.GET], produces = [MimeType.TEXT])
    @ResponseBody
    fun getConfig(@RequestParam(required = true) key: String): String? {
        return Application.get().remoteConfigLoader.getString(object : RemoteConfigParameter {
            override fun getKey(): String {
                return key
            }

            override fun getDefaultValue(): Any? {
                return null
            }
        })
    }
}
