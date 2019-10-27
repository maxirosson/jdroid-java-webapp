package com.jdroid.javaweb.domain

import com.jdroid.java.utils.Hasher
import com.jdroid.javaweb.exception.CommonErrorCode
import com.jdroid.javaweb.exception.InvalidAuthenticationException

import java.io.Serializable

class Password : Serializable {

    private lateinit var hashedPassword: String

    /**
     * @return the [Hasher]
     */
    protected val hasher: Hasher
        get() = Hasher.SHA_512

    /**
     * Default constructor.
     */
    private constructor() {
        // Do nothing
    }

    /**
     * Create a new password
     *
     * @param password the new password
     */
    constructor(password: String) {
        hashedPassword = hasher.hash(password)
    }

    /**
     * Password is verified by a hash function that compares the result of hashing the parameter with the hash of the
     * saved password.
     *
     * @param password string to be verified
     * @throws InvalidAuthenticationException in case verification fails
     */
    @Throws(InvalidAuthenticationException::class)
    fun verify(password: String) {
        if (hashedPassword != hasher.hash(password)) {
            throw InvalidAuthenticationException(CommonErrorCode.INVALID_CREDENTIALS)
        }
    }
}
