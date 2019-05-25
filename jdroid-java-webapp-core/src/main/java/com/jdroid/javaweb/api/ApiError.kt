package com.jdroid.javaweb.api

import org.springframework.http.HttpStatus

class ApiError @JvmOverloads constructor(private val status: HttpStatus, val code: String? = "500", val developerMessage: String? = null, val message: String? = null) {

	fun getStatus(): Int {
		return status.value()
	}

	override fun toString(): String {
		return ("ApiError [status=$status, code=$code, message=$message, developerMessage=$developerMessage]")
	}

}