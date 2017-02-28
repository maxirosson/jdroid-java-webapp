package com.jdroid.javaweb.sample.api.controller;

import com.jdroid.java.concurrent.ExecutorUtils;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.javaweb.api.AbstractController;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class ErrorController extends AbstractController {

	private static final Logger LOGGER = LoggerUtils.getLogger(ErrorController.class);

	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public void log(@RequestParam(required = true) final Boolean errorLevel,
					 @RequestParam(required = true) final String message,
					 @RequestParam(required = true) Boolean newThread,
					 @RequestParam(required = true) final Boolean exception) {
		if (newThread) {
			ExecutorUtils.execute(new Runnable() {
				@Override
				public void run() {
					if (errorLevel) {
						if (exception) {
							LOGGER.error("Log message", new UnexpectedException(message));
						} else {
							LOGGER.error(message);
						}
					} else {
						if (exception) {
							LOGGER.warn("Log message", new UnexpectedException(message));
						} else {
							LOGGER.warn(message);
						}
					}
				}
			});
		} else {
			if (errorLevel) {
				if (exception) {
					LOGGER.error("Log message", new UnexpectedException(message));
				} else {
					LOGGER.error(message);
				}
			} else {
				if (exception) {
					LOGGER.warn("Log message", new UnexpectedException(message));
				} else {
					LOGGER.warn(message);
				}
			}
		}
	}
}