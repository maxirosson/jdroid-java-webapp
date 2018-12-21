package com.jdroid.javaweb.spring;

import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;
import org.springframework.core.style.StylerUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class IgnoreAttacksFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerUtils.getLogger(IgnoreAttacksFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String requestUri = new UrlPathHelper().getRequestUri(request);
		if (shouldIgnoreRequest(request)) {
			LOGGER.info("Ignoring request [URI '" + requestUri + "', method '" + request.getMethod() + "', parameters "
				+ StylerUtils.style(request.getParameterMap()) + "]");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	protected abstract boolean shouldIgnoreRequest(HttpServletRequest request);
}