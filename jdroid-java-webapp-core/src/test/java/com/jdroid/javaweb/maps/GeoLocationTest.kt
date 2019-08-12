package com.jdroid.javaweb.maps

import org.testng.Assert
import org.testng.annotations.Test

class GeoLocationTest {

    @Test
    fun distance() {
        val geoLocation = GeoLocation(-34.5696907, -58.4192862)
        Assert.assertEquals(geoLocation.distance(GeoLocation(-34.5696907, -58.4192862)), 0.0)
        Assert.assertEquals(geoLocation.distance(GeoLocation(-34.57496, -58.415052)), 701.8848876953125)
    }
}
