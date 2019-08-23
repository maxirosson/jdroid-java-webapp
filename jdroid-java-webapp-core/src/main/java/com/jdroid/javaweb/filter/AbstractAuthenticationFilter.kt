package com.jdroid.javaweb.filter

import com.jdroid.java.utils.LoggerUtils
import com.jdroid.javaweb.api.ApiExceptionHandler
import com.jdroid.javaweb.application.Application
import com.jdroid.javaweb.exception.CommonErrorCode
import com.jdroid.javaweb.exception.InvalidAuthenticationException
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Filter used to verify that requests come from an authenticated user when accessing to content that requires
 * authentication.
 */
open class AbstractAuthenticationFilter : OncePerRequestFilter() {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(AbstractAuthenticationFilter::class.java)
        private const val USER_TOKEN_HEADER = "x-user-token"
    }

    open val allowedPaths: List<String> = listOf()

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        if (requiresAuthentication(request) && !isAuthenticated(request)) {
            response.setHeader(ApiExceptionHandler.STATUS_CODE_HEADER, CommonErrorCode.INVALID_USER_TOKEN.getStatusCode())
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
        } else {
            filterChain.doFilter(request, response)
            invalidate(request)
        }
    }

    /**
     * Checks whether the request requires user authentication to grant access to the requested content.
     *
     * @param request The [HttpServletRequest] to check.
     * @return If authentication is needed.
     */
    protected open fun requiresAuthentication(request: HttpServletRequest): Boolean {
        for (each in allowedPaths) {
            if (request.pathInfo.startsWith(each)) {
                return false
            }
        }
        return true
    }

    protected open fun isAuthenticated(request: HttpServletRequest): Boolean {
        val userToken = request.getHeader(USER_TOKEN_HEADER)
        return userToken != null && isAuthenticated(userToken)
    }

    protected open fun isAuthenticated(userToken: String): Boolean {
        return try {
            Application.get().securityContext!!.authenticateUser(userToken)
            true
        } catch (e: InvalidAuthenticationException) {
            LOGGER.warn("User with token $userToken NOT authenticated.")
            false
        }
    }

    protected open fun invalidate(request: HttpServletRequest) {
        Application.get().securityContext!!.invalidate()
        request.session.invalidate()
    }
}