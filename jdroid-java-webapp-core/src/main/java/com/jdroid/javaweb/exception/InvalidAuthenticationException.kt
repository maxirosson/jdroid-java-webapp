package com.jdroid.javaweb.exception

import com.jdroid.java.exception.ErrorCode
import com.jdroid.java.exception.ErrorCodeException

class InvalidAuthenticationException(errorCode: ErrorCode) : ErrorCodeException(errorCode)
