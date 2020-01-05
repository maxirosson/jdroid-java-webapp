package com.jdroid.javaweb.filter

import com.jdroid.java.utils.LoggerUtils
import com.jdroid.javaweb.api.ApiExceptionHandler
import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.config.CoreConfigParameter
import com.jdroid.javaweb.exception.CommonErrorCode
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class APIVersionFilter : OncePerRequestFilter() {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(APIVersionFilter::class.java)
        private const val API_VERSION_HEADER = "api-version"
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val minApiVersion = Application.get().remoteConfigLoader.getString(CoreConfigParameter.MIN_API_VERSION)!!.replace(".", "").toLong()
        val clientApiVersion = request.getHeader(API_VERSION_HEADER)?.replace(".", "")?.toLong()
        if (clientApiVersion == null) {
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED)
            LOGGER.warn("Missing [$API_VERSION_HEADER] header")
        } else if (clientApiVersion >= minApiVersion) {
            response.setHeader(ApiExceptionHandler.STATUS_CODE_HEADER, ApiExceptionHandler.OK_STATUS_CODE_HEADER_VALUE)
            response.setStatus(HttpServletResponse.SC_OK)
            filterChain.doFilter(request, response)
        } else {
            response.setHeader(ApiExceptionHandler.STATUS_CODE_HEADER, CommonErrorCode.INVALID_API_VERSION.getStatusCode())
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED)
            LOGGER.warn("Invalid  [$API_VERSION_HEADER] header")
        }
    }
}
