package com.jdroid.javaweb.commons

import com.jdroid.javaweb.application.TestApplication

import org.testng.annotations.BeforeClass

open class AbstractTest {

    @BeforeClass
    fun onBeforeClass() {
        val testApplication = TestApplication()
        testApplication.init()
    }
}
