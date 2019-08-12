package com.jdroid.javaweb.context

import com.jdroid.java.domain.Entity
import com.jdroid.java.utils.LoggerUtils
import com.jdroid.javaweb.exception.InvalidAuthenticationException

/**
 * A context holding per session values
 *
 * @param <T>
 */
abstract class AbstractSecurityContext<T : Entity> {

    companion object {
        private val LOGGER = LoggerUtils.getLogger(AbstractSecurityContext::class.java)
    }

    /**
     * @return the logged user
     */
    var user: T? = null
        private set

    val isAuthenticated: Boolean
        get() = user != null

    /**
     * @param email The user's email
     * @param password The user's password
     * @return The User
     * @throws InvalidAuthenticationException Thrown if the email and/or the password are invalid
     */
    @Throws(InvalidAuthenticationException::class)
    fun authenticateUser(email: String, password: String): T {
        user = verifyPassword(email, password)
        LOGGER.info("User [id: " + user!!.getId() + ", email: " + email + "] authenticated.")
        return user!!
    }

    @Throws(InvalidAuthenticationException::class)
    fun authenticateUser(userToken: String): T {
        user = verifyToken(userToken)
        LOGGER.info("User [id: " + user!!.getId() + ", userToken: " + userToken + "] authenticated.")
        return user!!
    }

    /**
     * @param email The email to verify
     * @param password The password to verify
     * @return The user
     * @throws InvalidAuthenticationException Thrown if the email and/or the password are invalid
     */
    @Throws(InvalidAuthenticationException::class)
    protected abstract fun verifyPassword(email: String, password: String): T

    /**
     * @param userToken The userToken to verify
     * @return The user
     * @throws InvalidAuthenticationException Thrown if the userToken is invalid
     */
    @Throws(InvalidAuthenticationException::class)
    protected abstract fun verifyToken(userToken: String): T

    fun invalidate() {
        this.user = null
    }
}
