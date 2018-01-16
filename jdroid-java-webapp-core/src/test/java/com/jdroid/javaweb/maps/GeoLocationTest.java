package com.jdroid.javaweb.maps;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GeoLocationTest {
	
	@Test
	public void distance() {
		GeoLocation geoLocation = new GeoLocation(-34.5696907, -58.4192862);
		Assert.assertEquals(geoLocation.distance(new GeoLocation(-34.5696907, -58.4192862)), 0.0D);
		Assert.assertEquals(geoLocation.distance(new GeoLocation(-34.57496, -58.415052)), 701.8848876953125D);
	}
}
