package com.jdroid.javaweb.sentry;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentryLoggerFactory implements ILoggerFactory {

	@Override
	public Logger getLogger(String name) {
		return new SentryLogger(LoggerFactory.getLogger(name));
	}
}
