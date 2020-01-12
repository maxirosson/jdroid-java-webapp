package com.jdroid.javaweb.cache

import java.lang.reflect.Method
import org.springframework.cache.interceptor.DefaultKeyGenerator

class CacheKeyGenerator : DefaultKeyGenerator() {

    override fun generate(target: Any?, method: Method?, vararg params: Any): Any {
        return method?.name + super.generate(target, method, *params).toString()
    }
}
