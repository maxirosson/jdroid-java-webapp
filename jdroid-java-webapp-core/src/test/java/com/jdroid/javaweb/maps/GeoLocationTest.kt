package com.jdroid.javaweb.maps

import org.junit.Assert.assertEquals
import org.junit.Test

class GeoLocationTest {

    @Test
    fun distance() {
        val geoLocation = GeoLocation(-34.5696907, -58.4192862)
        assertEquals(0.0, geoLocation.distance(GeoLocation(-34.5696907, -58.4192862)), 0.0)
        assertEquals(701.8848876953125, geoLocation.distance(GeoLocation(-34.57496, -58.415052)), 0.0)
    }
}
