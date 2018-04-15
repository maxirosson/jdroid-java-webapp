# Jdroid Java Web App
Tools for Java Web Apps

## Continuous Integration
|Branch|Status|Workflows|Insights|
| ------------- | ------------- | ------------- | ------------- |
|master|[![CircleCI](https://circleci.com/gh/maxirosson/jdroid-java-webapp/tree/master.svg?style=svg)](https://circleci.com/gh/maxirosson/jdroid-java-webapp/tree/master)|[Workflows](https://circleci.com/gh/maxirosson/workflows/jdroid-java-webapp/tree/master)|[Insights](https://circleci.com/build-insights/gh/maxirosson/jdroid-java-webapp/master)|
|staging|[![CircleCI](https://circleci.com/gh/maxirosson/jdroid-java-webapp/tree/staging.svg?style=svg)](https://circleci.com/gh/maxirosson/jdroid-java-webapp/tree/staging)|[Workflows](https://circleci.com/gh/maxirosson/workflows/jdroid-java-webapp/tree/staging)|[Insights](https://circleci.com/build-insights/gh/maxirosson/jdroid-java-webapp/staging)|
|production|[![CircleCI](https://circleci.com/gh/maxirosson/jdroid-java-webapp/tree/production.svg?style=svg)](https://circleci.com/gh/maxirosson/jdroid-java-webapp/tree/production)|[Workflows](https://circleci.com/gh/maxirosson/workflows/jdroid-java-webapp/tree/production)|[Insights](https://circleci.com/build-insights/gh/maxirosson/jdroid-java-webapp/production)|

## Features
### jdroid-java-webapp-core
* Http Filters
  * Authentication filter
  * API version filter
* [Spring MVC integration](http://projects.spring.io/spring-framework/)
* [Sentry integration](http://sentry.io)
* Pagination and filtering support
* [Log4j logging support](http://logging.apache.org/log4j/1.2/)
* Utilities for Collections, CSV, Files, Reflection, [Guava](https://code.google.com/p/guava-libraries/) and more
* A set of useful shell scripts to
  * Start/stop and deploy on [Apache Tomcat](http://tomcat.apache.org/)
  * Automatically restart [Apache Tomcat](http://tomcat.apache.org/)
### jdroid-java-webapp-firebase-firestore
* [Firestore integration](https://firebase.google.com/docs/firestore/)
### jdroid-java-webapp-push
* Generic push framework. 
  * [Google Cloud Messaging implementation](http://developer.android.com/google/gcm/index.html)
### jdroid-java-webapp-twitter
* [Twitter 4j integration](http://twitter4j.org/)
### jdroid-java-webapp-facebook
* [Restfb Facebook integration](http://restfb.com/)

## Setup

Add the following lines to your `build.gradle`:

    repositories {
      jcenter()
    }

    dependencies {
      implementation 'com.jdroidtools:jdroid-java-webapp-core:X.Y.Z'
      implementation 'com.jdroidtools:jdroid-java-webapp-firebase-firestore:X.Y.Z'
      implementation 'com.jdroidtools:jdroid-java-webapp-push:X.Y.Z'
      implementation 'com.jdroidtools:jdroid-java-webapp-twitter:X.Y.Z'
      implementation 'com.jdroidtools:jdroid-java-webapp-facebook:X.Y.Z'
    }

Replace the X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-java-webapp/releases/latest)

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)
