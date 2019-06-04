package com.jdroid.javaweb.repository

import com.jdroid.java.repository.Repository
import com.jdroid.javaweb.domain.FileEntity

interface FileEntityRepository : Repository<FileEntity> {

    fun getByName(name: String): FileEntity
}
