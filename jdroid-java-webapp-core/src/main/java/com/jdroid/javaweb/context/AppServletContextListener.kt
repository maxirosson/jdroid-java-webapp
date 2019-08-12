package com.jdroid.javaweb.context

import com.jdroid.java.utils.LoggerUtils
import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.exception.DefaultExceptionHandler

import org.apache.log4j.helpers.LogLog
import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.springframework.util.ClassUtils
import org.springframework.util.Log4jConfigurer

import java.io.FileNotFoundException

import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

/**
 * Listener to load from the classpath the log4j configuration file.
 */
open class AppServletContextListener : ServletContextListener {

    companion object {
        private lateinit var LOGGER: Logger
    }

    override fun contextInitialized(event: ServletContextEvent) {

        LoggerUtils.setEnabled(true)
        val loggerFactory = createLoggerFactory()
        if (loggerFactory != null) {
            LoggerUtils.setDefaultLoggerFactory(loggerFactory)
        }

        try {
            // If the file log4j.deployment.xml is present in the classpath, it is used
            // else the file log4j.xml is used
            var log4jPath = "classpath:log4j.xml"
            if (ClassUtils.getDefaultClassLoader().getResource("log4j.xml") == null) {
                log4jPath = "classpath:log4j.deployment.xml"
            }
            Log4jConfigurer.initLogging(log4jPath)
            LOGGER = LoggerUtils.getLogger(AppServletContextListener::class.java)
            LOGGER.info("Starting Logging.")
        } catch (ex: FileNotFoundException) {
            LogLog.error(ex.message)
        }

        Thread.setDefaultUncaughtExceptionHandler(DefaultExceptionHandler())
    }

    override fun contextDestroyed(arg0: ServletContextEvent) {
        LOGGER.info("Shutdown Logging.")
        Log4jConfigurer.shutdownLogging()

        Application.get().onContextDestroyed()
    }

    protected open fun createLoggerFactory(): ILoggerFactory? {
        return null
    }
}
