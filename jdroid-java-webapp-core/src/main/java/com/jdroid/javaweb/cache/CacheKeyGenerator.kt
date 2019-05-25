package com.jdroid.javaweb.cache

import org.springframework.cache.interceptor.DefaultKeyGenerator

import java.lang.reflect.Method

class CacheKeyGenerator : DefaultKeyGenerator() {

	override fun generate(target: Any?, method: Method?, vararg params: Any): Any {
		return method?.name + super.generate(target, method, *params).toString()
	}
}
