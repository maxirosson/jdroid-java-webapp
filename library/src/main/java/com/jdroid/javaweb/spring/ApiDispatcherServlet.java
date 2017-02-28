package com.jdroid.javaweb.spring;

import com.jdroid.javaweb.api.BadRequestException;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiDispatcherServlet extends DispatcherServlet {
	
	@Override
	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestUri = new UrlPathHelper().getRequestUri(request);
		throw new BadRequestException("No mapping found for HTTP request", requestUri, request.getParameterMap(),
				request.getMethod(), getServletName());
	}
}
