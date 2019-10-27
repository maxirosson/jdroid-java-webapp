package com.jdroid.javaweb.filter

import com.jdroid.java.utils.LoggerUtils
import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.config.CoreConfigParameter
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class AdminSecurityFilter : OncePerRequestFilter() {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(AdminSecurityFilter::class.java)
        const val TOKEN_PARAMETER = "token"
    }

    protected open val adminToken: String?
        get() = Application.get().remoteConfigLoader.getString(CoreConfigParameter.ADMIN_TOKEN)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val token = request.getParameter(TOKEN_PARAMETER)
        if (adminToken == token) {
            filterChain.doFilter(request, response)
        } else {
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED)

            val queryString = request.queryString
            LOGGER.warn("Invalid or missing admin security token. " + request.method + " " + request.requestURI +
                    if (queryString != null) "?" + request.queryString else ""
            )
        }
    }
}
