package com.jdroid.javaweb.sentry;

import com.getsentry.raven.event.Event;
import com.getsentry.raven.event.EventBuilder;
import com.getsentry.raven.event.interfaces.ExceptionInterface;
import com.getsentry.raven.event.interfaces.MessageInterface;
import com.jdroid.java.concurrent.LowPriorityThreadFactory;
import com.jdroid.javaweb.application.Application;
import com.jdroid.javaweb.config.CoreConfigParameter;

import org.slf4j.Logger;
import org.slf4j.Marker;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SentryLogger implements Logger {

	private Logger wrappedLogger;

	private Executor executor = Executors.newFixedThreadPool(1, new LowPriorityThreadFactory());

	public SentryLogger(Logger wrappedLogger) {
		this.wrappedLogger = wrappedLogger;
	}

	@Override
	public String getName() {
		return wrappedLogger.getName();
	}

	@Override
	public boolean isTraceEnabled() {
		return wrappedLogger.isTraceEnabled();
	}

	@Override
	public void trace(String msg) {
		wrappedLogger.trace(msg);
	}

	@Override
	public void trace(String format, Object arg) {
		wrappedLogger.trace(format, arg);
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		wrappedLogger.trace(format, arg1, arg2);
	}

	@Override
	public void trace(String format, Object[] argArray) {
		wrappedLogger.trace(format, argArray);
	}

	@Override
	public void trace(String msg, Throwable t) {
		wrappedLogger.trace(msg, t);
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return wrappedLogger.isTraceEnabled(marker);
	}

	@Override
	public void trace(Marker marker, String msg) {
		wrappedLogger.trace(marker, msg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		wrappedLogger.trace(marker, format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		wrappedLogger.trace(marker, format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object[] argArray) {
		wrappedLogger.trace(marker, format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		wrappedLogger.trace(marker, msg, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return wrappedLogger.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		wrappedLogger.debug(msg);
	}

	@Override
	public void debug(String format, Object arg) {
		wrappedLogger.debug(format, arg);
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		wrappedLogger.debug(format, arg1, arg2);
	}

	@Override
	public void debug(String format, Object[] argArray) {
		wrappedLogger.debug(format, argArray);
	}

	@Override
	public void debug(String msg, Throwable t) {
		wrappedLogger.debug(msg, t);
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return wrappedLogger.isDebugEnabled(marker);
	}

	@Override
	public void debug(Marker marker, String msg) {
		wrappedLogger.debug(marker, msg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		wrappedLogger.debug(marker, format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		wrappedLogger.debug(marker, format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object[] argArray) {
		wrappedLogger.debug(marker, format, argArray);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		wrappedLogger.debug(marker, msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return wrappedLogger.isInfoEnabled();
	}

	@Override
	public void info(String msg) {
		wrappedLogger.info(msg);
	}

	@Override
	public void info(String format, Object arg) {
		wrappedLogger.info(format, arg);
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		wrappedLogger.info(format, arg1, arg2);
	}

	@Override
	public void info(String format, Object[] argArray) {
		wrappedLogger.info(format, argArray);
	}

	@Override
	public void info(String msg, Throwable t) {
		wrappedLogger.info(msg, t);
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return wrappedLogger.isInfoEnabled(marker);
	}

	@Override
	public void info(Marker marker, String msg) {
		wrappedLogger.info(marker, msg);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		wrappedLogger.info(marker, format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		wrappedLogger.info(marker, format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object[] argArray) {
		wrappedLogger.info(marker, format, argArray);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		wrappedLogger.info(marker, msg, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return wrappedLogger.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		wrappedLogger.warn(msg);
		log(Event.Level.WARNING, msg, null);
	}

	@Override
	public void warn(String format, Object arg) {
		wrappedLogger.warn(format, arg);
		log(Event.Level.WARNING, format, null);
	}

	@Override
	public void warn(String format, Object[] argArray) {
		wrappedLogger.warn(format, argArray);
		log(Event.Level.WARNING, format, null);
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		wrappedLogger.warn(format, arg1, arg2);
		log(Event.Level.WARNING, format, null);
	}

	@Override
	public void warn(String msg, Throwable t) {
		wrappedLogger.warn(msg, t);
		log(Event.Level.WARNING, msg, t);
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return wrappedLogger.isWarnEnabled(marker);
	}

	@Override
	public void warn(Marker marker, String msg) {
		wrappedLogger.warn(marker, msg);
		log(Event.Level.WARNING, msg, null);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		wrappedLogger.warn(marker, format, arg);
		log(Event.Level.WARNING, format, null);
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		wrappedLogger.warn(marker, format, arg1, arg2);
		log(Event.Level.WARNING, format, null);
	}

	@Override
	public void warn(Marker marker, String format, Object[] argArray) {
		wrappedLogger.warn(marker, format, argArray);
		log(Event.Level.WARNING, format, null);
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		wrappedLogger.warn(marker, msg, t);
		log(Event.Level.WARNING, msg, t);
	}

	@Override
	public boolean isErrorEnabled() {
		return wrappedLogger.isErrorEnabled();
	}

	@Override
	public void error(String msg) {
		wrappedLogger.error(msg);
		log(Event.Level.ERROR, msg, null);
	}

	@Override
	public void error(String format, Object arg) {
		wrappedLogger.error(format, arg);
		log(Event.Level.ERROR, format, null);
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		wrappedLogger.error(format, arg1, arg2);
		log(Event.Level.ERROR, format, null);
	}

	@Override
	public void error(String format, Object[] argArray) {
		wrappedLogger.error(format, argArray);
		log(Event.Level.ERROR, format, null);
	}

	@Override
	public void error(String msg, Throwable t) {
		wrappedLogger.error(msg, t);
		log(Event.Level.ERROR, msg, t);
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return wrappedLogger.isErrorEnabled(marker);
	}

	@Override
	public void error(Marker marker, String msg) {
		wrappedLogger.error(marker, msg);
		log(Event.Level.ERROR, msg, null);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		wrappedLogger.error(marker, format, arg);
		log(Event.Level.ERROR, format, null);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		wrappedLogger.error(marker, format, arg1, arg2);
		log(Event.Level.ERROR, format, null);
	}

	@Override
	public void error(Marker marker, String format, Object[] argArray) {
		wrappedLogger.error(marker, format, argArray);
		log(Event.Level.ERROR, format, null);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		wrappedLogger.error(marker, msg, t);
		log(Event.Level.ERROR, msg, t);
	}

	private void log(final Event.Level level, final String message, final Throwable throwable) {
		if (Application.get().getRemoteConfigLoader().getBoolean(CoreConfigParameter.SENTRY_ENABLED)) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					try {
						EventBuilder eventBuilder = new EventBuilder();
						eventBuilder.withLevel(level);
						eventBuilder.withEnvironment(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.BUILD_TYPE));
						eventBuilder.withRelease(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.APP_VERSION));
						eventBuilder.withServerName(Application.get().getRemoteConfigLoader().getString(CoreConfigParameter.APP_NAME));
						eventBuilder.withLogger(getName());
						eventBuilder.withPlatform(EventBuilder.DEFAULT_PLATFORM);
						if (throwable != null) {
							eventBuilder.withMessage(throwable.getMessage());
							eventBuilder.withSentryInterface(new ExceptionInterface(throwable));
						} else {
							eventBuilder.withMessage(message);
							eventBuilder.withSentryInterface(new MessageInterface(message));
						}
						SentryAppModule.get().getRaven().sendEvent(eventBuilder.build());
					} catch (Throwable e) {
						wrappedLogger.error("There was an error notifying the error to Sentry.", e);
					}
				}

			});
		}
	}
}
