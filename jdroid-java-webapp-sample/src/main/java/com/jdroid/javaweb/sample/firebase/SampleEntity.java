package com.jdroid.javaweb.sample.firebase;

import com.google.cloud.firestore.GeoPoint;
import com.jdroid.java.domain.Entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SampleEntity extends Entity {
	
	private String stringField;
	private Long longField;
	private Float floatField;
	private Date timestamp;
	private SampleInnerEntity composite;
	private List<String> stringArray;
	private GeoPoint geoPoint;
	private List<SampleEntity> subCollection;
	private Map<String, String> stringMap;
	private Map<String, Object> objectMap;
	
	public Long getLongField() {
		return longField;
	}
	
	public void setLongField(Long longField) {
		this.longField = longField;
	}

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
	
	public SampleInnerEntity getComposite() {
		return composite;
	}
	
	public void setComposite(SampleInnerEntity composite) {
		this.composite = composite;
	}
	
	public List<SampleEntity> getSubCollection() {
		return subCollection;
	}
	
	public void setSubCollection(List<SampleEntity> subCollection) {
		this.subCollection = subCollection;
	}
	
	public List<String> getStringArray() {
		return stringArray;
	}
	
	public void setStringArray(List<String> stringArray) {
		this.stringArray = stringArray;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Float getFloatField() {
		return floatField;
	}
	
	public void setFloatField(Float floatField) {
		this.floatField = floatField;
	}
	
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}
	
	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}
	
	public Map<String, Object> getObjectMap() {
		return objectMap;
	}
	
	public void setObjectMap(Map<String, Object> objectMap) {
		this.objectMap = objectMap;
	}
	
	public Map<String, String> getStringMap() {
		return stringMap;
	}
	
	public void setStringMap(Map<String, String> stringMap) {
		this.stringMap = stringMap;
	}
}
