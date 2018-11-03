package com.jdroid.javaweb.firebase.fcm.instanceid;


import org.apache.log4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web Filter to add information to Log4J for logging
 */
public class InstanceIdLog4jFilter extends OncePerRequestFilter {

	public static final String INSTANCE_ID_HEADER = "instanceId";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String instanceId = request.getHeader(INSTANCE_ID_HEADER);
		if (instanceId != null) {
			MDC.put(INSTANCE_ID_HEADER,  " - " + instanceId);
		}
		
		try {
			// Continue processing the rest of the filter chain.
			filterChain.doFilter(request, response);
		} finally {
			// Remove the added elements - only if added.
			MDC.remove(INSTANCE_ID_HEADER);
		}
	}
}
