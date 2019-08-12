package com.jdroid.javaweb.context

import com.jdroid.java.domain.Entity

/**
 * Provides access to the [AbstractSecurityContext]
 *
 * @param <T>
</T> */
abstract class SecurityContextHolder<T : Entity> {

    /**
     * @return The [AbstractSecurityContext] instance
     */
    abstract val context: AbstractSecurityContext<T>
}