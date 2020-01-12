package com.jdroid.javaweb.sample.api.controller

import com.jdroid.java.concurrent.ExecutorUtils
import com.jdroid.java.exception.UnexpectedException
import com.jdroid.java.utils.LoggerUtils
import com.jdroid.javaweb.api.AbstractController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/error")
class ErrorController : AbstractController() {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(ErrorController::class.java)
    }

    @RequestMapping(value = ["/log"], method = [RequestMethod.GET])
    fun log(
        @RequestParam(required = true) errorLevel: Boolean?,
        @RequestParam(required = true) message: String,
        @RequestParam(required = true) newThread: Boolean,
        @RequestParam(required = true) exception: Boolean?
    ) {
        if (newThread) {
            ExecutorUtils.execute(Runnable {
                if (errorLevel!!) {
                    if (exception!!) {
                        LOGGER.error("Log message", UnexpectedException(message))
                    } else {
                        LOGGER.error(message)
                    }
                } else {
                    if (exception!!) {
                        LOGGER.warn("Log message", UnexpectedException(message))
                    } else {
                        LOGGER.warn(message)
                    }
                }
            })
        } else {
            if (errorLevel!!) {
                if (exception!!) {
                    LOGGER.error("Log message", UnexpectedException(message))
                } else {
                    LOGGER.error(message)
                }
            } else {
                if (exception!!) {
                    LOGGER.warn("Log message", UnexpectedException(message))
                } else {
                    LOGGER.warn(message)
                }
            }
        }
    }
}
