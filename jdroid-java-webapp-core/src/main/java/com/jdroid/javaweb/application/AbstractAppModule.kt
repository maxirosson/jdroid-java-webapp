package com.jdroid.javaweb.application

open class AbstractAppModule : AppModule {

	override fun getServerInfoMap(): Map<String, String>? {
		return null
	}

	override fun onCreateApplication() {
		// Do Nothing
	}

	override fun onContextDestroyed() {
		// Do Nothing
	}
}
