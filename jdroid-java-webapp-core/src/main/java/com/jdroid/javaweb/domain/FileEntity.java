package com.jdroid.javaweb.domain;

import com.jdroid.java.domain.Entity;

public class FileEntity extends Entity {
	
	private String name;
	private byte[] content;
	
	/**
	 * Default constructor.
	 */
	protected FileEntity() {
		// Do nothing, is required by hibernate
	}
	
	public FileEntity(byte[] content, String name) {
		this.content = content;
		this.name = name;
	}
	
	public void modify(byte[] content, String name) {
		this.content = content;
		this.name = name;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public String getName() {
		return name;
	}
	
}
