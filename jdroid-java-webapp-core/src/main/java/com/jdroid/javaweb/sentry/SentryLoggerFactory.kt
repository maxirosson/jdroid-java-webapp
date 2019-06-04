package com.jdroid.javaweb.sentry

import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SentryLoggerFactory : ILoggerFactory {

    override fun getLogger(name: String): Logger {
        return SentryLogger(LoggerFactory.getLogger(name))
    }
}
