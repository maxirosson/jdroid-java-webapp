package com.jdroid.javaweb.api

import com.google.gson.GsonBuilder
import com.jdroid.java.gson.GsonBuilderFactory
import com.jdroid.java.marshaller.MarshallerMode
import com.jdroid.java.marshaller.MarshallerProvider
import com.jdroid.java.utils.StringUtils
import com.jdroid.javaweb.application.Application

abstract class AbstractController {

	val userId: String?
		get() = if (Application.get().securityContext != null && Application.get().securityContext.isAuthenticated)
			Application.get().securityContext.user.id
		else
			null

	fun marshallSimple(value: Any): String {
		return marshall(value, MarshallerMode.SIMPLE)
	}

	@JvmOverloads
	fun marshall(value: Any?, mode: MarshallerMode = MarshallerMode.COMPLETE, extras: Map<String, String>? = null): String {
		return if (value != null) MarshallerProvider.get().marshall(value, mode, extras).toString() else StringUtils.EMPTY
	}

	fun autoMarshall(value: Any): String {
		return createGsonBuilder().create().toJson(value)
	}

	protected fun createGsonBuilder(): GsonBuilder {
		return GsonBuilderFactory.createGsonBuilder()
	}
}
