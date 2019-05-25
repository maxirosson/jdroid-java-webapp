package com.jdroid.javaweb.application

interface AppModule {

	fun getServerInfoMap(): Map<String, String>?

	fun onCreateApplication()

	fun onContextDestroyed()
}
