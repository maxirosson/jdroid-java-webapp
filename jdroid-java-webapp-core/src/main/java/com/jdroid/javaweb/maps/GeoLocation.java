package com.jdroid.javaweb.maps;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class GeoLocation implements Serializable {
	
	private static final long serialVersionUID = -2822993513206651288L;
	
	private Double longitude;
	private Double latitude;
	
	public GeoLocation(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public GeoLocation() {
		this(null, null);
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@JsonIgnore
	public boolean isValid() {
		return (latitude != null) && (longitude != null);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GeoLocation that = (GeoLocation)o;
		return Objects.equals(longitude, that.longitude) &&
				Objects.equals(latitude, that.latitude);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(longitude, latitude);
	}
	
	@Override
	public String toString() {
		return Double.toString(getLatitude()) + "," + Double.toString(getLongitude());
	}
	
	public double distance(GeoLocation geoLocation) {
		return distance(geoLocation.getLatitude(), geoLocation.getLongitude());
	}
	
	/*
	 * @return: Distance in meters between this location and the specified
	 */
	public double distance(Double lat, Double lon) {
		return distanceBetween(latitude, longitude, lat, lon);
	}
	
	/**
	 * Computes the approximate distance in meters between two locations.
	 * Distance is defined using the WGS84 ellipsoid.
	 *
	 * @param startLatitude the starting latitude
	 * @param startLongitude the starting longitude
	 * @param endLatitude the ending latitude
	 * @param endLongitude the ending longitude
	 */
	public static float distanceBetween(double startLatitude, double startLongitude,
												  double endLatitude, double endLongitude) {
		// Based on http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf
		// using the "Inverse Formula" (section 4)
		
		int MAXITERS = 20;
		// Convert lat/long to radians
		startLatitude *= Math.PI / 180.0;
		endLatitude *= Math.PI / 180.0;
		startLongitude *= Math.PI / 180.0;
		endLongitude *= Math.PI / 180.0;
		
		double a = 6378137.0; // WGS84 major axis
		double b = 6356752.3142; // WGS84 semi-major axis
		double f = (a - b) / a;
		double aSqMinusBSqOverBSq = (a * a - b * b) / (b * b);
		
		double L = endLongitude - startLongitude;
		double A = 0.0;
		double U1 = Math.atan((1.0 - f) * Math.tan(startLatitude));
		double U2 = Math.atan((1.0 - f) * Math.tan(endLatitude));
		
		double cosU1 = Math.cos(U1);
		double cosU2 = Math.cos(U2);
		double sinU1 = Math.sin(U1);
		double sinU2 = Math.sin(U2);
		double cosU1cosU2 = cosU1 * cosU2;
		double sinU1sinU2 = sinU1 * sinU2;
		
		double sigma = 0.0;
		double deltaSigma = 0.0;
		double cosSqAlpha;
		double cos2SM;
		double cosSigma;
		double sinSigma;
		double cosLambda;
		double sinLambda;
		
		double lambda = L; // initial guess
		for (int iter = 0; iter < MAXITERS; iter++) {
			double lambdaOrig = lambda;
			cosLambda = Math.cos(lambda);
			sinLambda = Math.sin(lambda);
			double t1 = cosU2 * sinLambda;
			double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
			double sinSqSigma = t1 * t1 + t2 * t2; // (14)
			sinSigma = Math.sqrt(sinSqSigma);
			cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda; // (15)
			sigma = Math.atan2(sinSigma, cosSigma); // (16)
			double sinAlpha = (sinSigma == 0) ? 0.0 :
					cosU1cosU2 * sinLambda / sinSigma; // (17)
			cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
			cos2SM = (cosSqAlpha == 0) ? 0.0 :
					cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha; // (18)
			
			double uSquared = cosSqAlpha * aSqMinusBSqOverBSq; // defn
			A = 1 + (uSquared / 16384.0) * // (3)
					(4096.0 + uSquared *
							(-768 + uSquared * (320.0 - 175.0 * uSquared)));
			double B = (uSquared / 1024.0) * // (4)
					(256.0 + uSquared *
							(-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
			double C = (f / 16.0) *
					cosSqAlpha *
					(4.0 + f * (4.0 - 3.0 * cosSqAlpha)); // (10)
			double cos2SMSq = cos2SM * cos2SM;
			deltaSigma = B * sinSigma * // (6)
					(cos2SM + (B / 4.0) *
							(cosSigma * (-1.0 + 2.0 * cos2SMSq) -
									(B / 6.0) * cos2SM *
											(-3.0 + 4.0 * sinSigma * sinSigma) *
											(-3.0 + 4.0 * cos2SMSq)));
			
			lambda = L +
					(1.0 - C) * f * sinAlpha *
							(sigma + C * sinSigma *
									(cos2SM + C * cosSigma *
											(-1.0 + 2.0 * cos2SM * cos2SM))); // (11)
			
			double delta = (lambda - lambdaOrig) / lambda;
			if (Math.abs(delta) < 1.0e-12) {
				break;
			}
		}
		
		return  (float) (b * A * (sigma - deltaSigma));
	}
}