package com.jdroid.javaweb.sample.api.controller

import com.jdroid.java.http.AbstractHttpService
import com.jdroid.java.http.MimeType
import com.jdroid.java.utils.LoggerUtils
import com.jdroid.javaweb.api.AbstractController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/sample")
class SampleController : AbstractController() {

    @RequestMapping(value = ["/get"], method = [RequestMethod.GET], produces = [MimeType.JSON_UTF8])
    @ResponseBody
    operator fun get(@RequestParam param1: String, @RequestHeader header1: String, @RequestHeader(value = "User-Agent") userAgent: String): String {
        LOGGER.info("GET. param1 = $param1. header1 = $header1. userAgent = $userAgent")
        return marshall(SampleResponse("response1"))
    }

    @RequestMapping(value = ["/post"], method = [RequestMethod.POST])
    fun post(@RequestParam param1: String, @RequestHeader header1: String, @RequestHeader(value = "User-Agent") userAgent: String) {
        LOGGER.info("POST. param1 = $param1. header1 = $header1. userAgent = $userAgent")
    }

    @RequestMapping(value = ["/put"], method = [RequestMethod.PUT])
    fun put(@RequestParam param1: String, @RequestHeader header1: String, @RequestHeader(value = "User-Agent") userAgent: String) {
        LOGGER.info("PUT. param1 = $param1. header1 = $header1. userAgent = $userAgent")
    }

    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@RequestParam param1: String, @RequestHeader header1: String, @RequestHeader(value = "User-Agent") userAgent: String) {
        LOGGER.info("DELETE. param1 = $param1. header1 = $header1. userAgent = $userAgent")
    }

    @RequestMapping(value = ["/patch"], method = [RequestMethod.PATCH])
    fun patch(@RequestParam param1: String, @RequestHeader header1: String, @RequestHeader(value = "User-Agent") userAgent: String) {
        LOGGER.info("PATCH. param1 = $param1. header1 = $header1. userAgent = $userAgent")
    }

    companion object {
        private val LOGGER = LoggerUtils.getLogger(AbstractHttpService::class.java)
    }
}