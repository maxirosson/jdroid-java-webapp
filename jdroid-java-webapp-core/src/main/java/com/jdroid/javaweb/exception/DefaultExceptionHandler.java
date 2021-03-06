package com.jdroid.javaweb.exception;

import com.jdroid.java.http.exception.ConnectionException;
import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.lang.Thread.UncaughtExceptionHandler;

public class DefaultExceptionHandler implements UncaughtExceptionHandler {
	
	private final static Logger LOGGER = LoggerUtils.getLogger(DefaultExceptionHandler.class);
	
	private UncaughtExceptionHandler wrappedExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {

		if (throwable instanceof ConnectionException) {
			LOGGER.warn("Connection error", throwable);
		} else {
			LOGGER.error("Uncaught Exception", throwable);
		}
		
		wrappedExceptionHandler.uncaughtException(thread, throwable);
	}
}
