plugins {
    id("com.gradle.enterprise").version("3.1.1")
}

include(":jdroid-java-webapp-core")
include(":jdroid-java-webapp-csv")
include(":jdroid-java-webapp-facebook")
include(":jdroid-java-webapp-twitter")
include(":jdroid-java-webapp-push")
include(":jdroid-java-webapp-firebase-fcm")
include(":jdroid-java-webapp-sample")

apply(from = File(settingsDir, "buildCacheSettings.gradle"))
