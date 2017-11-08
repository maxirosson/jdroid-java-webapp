# Jdroid Library for Java Web Apps

## Continuous Integration
|Branch|Status|
| ------------- | ------------- |
|Master|[![Build Status](https://travis-ci.org/maxirosson/jdroid-java-webapp.svg?branch=master)](https://travis-ci.org/maxirosson/jdroid-java-webapp)|
|Staging|[![Build Status](https://api.travis-ci.org/maxirosson/jdroid-java-webapp.svg?branch=staging)](https://travis-ci.org/maxirosson/jdroid-java-webapp)|
|Production|[![Build Status](https://api.travis-ci.org/maxirosson/jdroid-java-webapp.svg?branch=production)](https://travis-ci.org/maxirosson/jdroid-java-webapp)|

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
      implementation 'com.jdroidframework:jdroid-java-webapp-core:X.Y.Z'
      implementation 'com.jdroidframework:jdroid-java-webapp-firebase-firestore:X.Y.Z'
      implementation 'com.jdroidframework:jdroid-java-webapp-push:X.Y.Z'
      implementation 'com.jdroidframework:jdroid-java-webapp-twitter:X.Y.Z'
      implementation 'com.jdroidframework:jdroid-java-webapp-facebook:X.Y.Z'
    }

Replace the X.Y.Z by the [latest version](https://github.com/maxirosson/jdroid-java-webapp/releases/latest)

## Donations
Help us to continue with this project:

[![Donate](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=2UEBTRTSCYA9L)

<a href='https://pledgie.com/campaigns/30030'><img alt='Click here to lend your support to: Jdroid and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/30030.png?skin_name=chrome' border='0' ></a>
