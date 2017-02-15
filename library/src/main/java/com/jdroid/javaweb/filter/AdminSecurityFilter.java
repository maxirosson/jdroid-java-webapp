package com.jdroid.javaweb.filter;

import com.jdroid.java.utils.LoggerUtils;
import com.jdroid.javaweb.config.ConfigHelper;
import com.jdroid.javaweb.config.CoreConfigParameter;

import org.slf4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminSecurityFilter extends OncePerRequestFilter {
	
	private static final Logger LOGGER = LoggerUtils.getLogger(AdminSecurityFilter.class);
	
	public static final String TOKEN_PARAMETER = "token";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getParameter(TOKEN_PARAMETER);
		if (getAdminToken().equals(token)) {
			filterChain.doFilter(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED);

			String queryString = request.getQueryString();
			LOGGER.warn("Invalid or missing admin security token. " + request.getMethod() + " " + request.getRequestURI()
					+ (queryString != null ? "?" + request.getQueryString() : ""));
		}
	}
	
	protected String getAdminToken() {
		return ConfigHelper.getStringValue(CoreConfigParameter.ADMIN_TOKEN);
	}
}
