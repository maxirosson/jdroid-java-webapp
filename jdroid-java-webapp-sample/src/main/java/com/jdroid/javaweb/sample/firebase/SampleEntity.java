package com.jdroid.javaweb.sample.firebase;

import com.jdroid.java.domain.Entity;

public class SampleEntity extends Entity {
	
	private String stringField;
	private Long longField;
	private SampleInnerEntity composite;
	
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
}
