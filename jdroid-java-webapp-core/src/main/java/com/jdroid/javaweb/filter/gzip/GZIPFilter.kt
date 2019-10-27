package com.jdroid.javaweb.filter.gzip

import com.jdroid.java.http.HttpService

import java.io.IOException

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GZIPFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (request is HttpServletRequest) {
            val acceptEncoding = request.getHeader(HttpService.ACCEPT_ENCODING_HEADER)
            if (acceptEncoding != null && acceptEncoding.contains(HttpService.GZIP_ENCODING)) {
                val wrappedResponse = GZIPResponseWrapper(response as HttpServletResponse)
                chain.doFilter(request, wrappedResponse)
                wrappedResponse.close()
                return
            }
        }
        chain.doFilter(request, response)
    }

    override fun init(filterConfig: FilterConfig) {
        // Do nothing
    }

    override fun destroy() {
        // Do nothing
    }
}
