package com.jdroid.javaweb.spring;

import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.javaweb.api.BadRequestException;

import org.slf4j.Logger;
import org.springframework.core.style.StylerUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDispatcherServlet extends DispatcherServlet {

	private static final Logger LOGGER = LoggerUtils.getLogger(ApiDispatcherServlet.class);

	@Override
	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestUri = new UrlPathHelper().getRequestUri(request);
		if (shouldNotThrowBadRequestException(request)) {
			LOGGER.debug("Ignoring request [URI '" + requestUri + "', method '"
					+ request.getMethod() + "', parameters "
					+ StylerUtils.style(request.getParameterMap()) + "] in DispatcherServlet with name '"
					+ getServletName() + "'");
		} else {
			throw new BadRequestException(requestUri, request.getParameterMap(), request.getMethod(), getServletName());
		}
	}

	protected boolean shouldNotThrowBadRequestException(HttpServletRequest request) {
		return false;
	}
}
