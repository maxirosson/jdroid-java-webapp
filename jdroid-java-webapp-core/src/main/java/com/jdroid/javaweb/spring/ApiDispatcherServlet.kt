package com.jdroid.javaweb.spring

import com.jdroid.javaweb.api.BadRequestException

import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.util.UrlPathHelper

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiDispatcherServlet : DispatcherServlet() {

	@Throws(Exception::class)
	override fun noHandlerFound(request: HttpServletRequest, response: HttpServletResponse) {
		val requestUri = UrlPathHelper().getRequestUri(request)
		throw BadRequestException(requestUri, request.parameterMap, request.method, servletName)
	}
}
