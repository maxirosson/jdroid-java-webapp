package com.jdroid.javaweb.repository;

import com.jdroid.java.repository.Repository;
import com.jdroid.javaweb.domain.FileEntity;

public interface FileEntityRepository extends Repository<FileEntity> {
	
	public FileEntity getByName(String name);
	
}
