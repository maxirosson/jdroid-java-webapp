package com.jdroid.javaweb.commons

import com.jdroid.javaweb.application.TestApplication
import org.junit.BeforeClass

open class AbstractTest {

    companion object {

        @JvmStatic
        @BeforeClass
        fun onBeforeClass() {
            val testApplication = TestApplication()
            testApplication.init()
        }
    }
}
