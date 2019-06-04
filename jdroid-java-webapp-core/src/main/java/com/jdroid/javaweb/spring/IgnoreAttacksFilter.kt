package com.jdroid.javaweb.spring

import com.jdroid.java.utils.LoggerUtils
import org.springframework.core.style.StylerUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.UrlPathHelper
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class IgnoreAttacksFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val requestUri = UrlPathHelper().getRequestUri(request)
        if (shouldIgnoreRequest(request)) {
            LOGGER.info("Ignoring request [URI '" + requestUri + "', method '" + request.method + "', parameters " +
                    StylerUtils.style(request.parameterMap) + "]")
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        } else {
            filterChain.doFilter(request, response)
        }
    }

    protected abstract fun shouldIgnoreRequest(request: HttpServletRequest): Boolean

    companion object {

        private val LOGGER = LoggerUtils.getLogger(IgnoreAttacksFilter::class.java)
    }
}