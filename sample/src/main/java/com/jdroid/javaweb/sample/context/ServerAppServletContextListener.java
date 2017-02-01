package com.jdroid.javaweb.sample.context;

import com.jdroid.javaweb.context.AppServletContextListener;
import com.jdroid.javaweb.rollbar.RollBarLoggerFactory;

import org.slf4j.ILoggerFactory;

public class ServerAppServletContextListener extends AppServletContextListener {

	@Override
	protected ILoggerFactory createLoggerFactory() {
		return new RollBarLoggerFactory();
	}
}
