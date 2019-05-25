package com.jdroid.javaweb.utils

import org.springframework.util.FileCopyUtils
import java.io.File
import java.io.IOException

/**
 * This class contains functions for working with files within the application.
 */
object FileUtils : com.jdroid.java.utils.FileUtils() {

	/**
	 * @param src The source file
	 * @param dest The destination file
	 * @throws IOException if an I/O error occurs
	 */
	@Throws(IOException::class)
	fun copy(src: File, dest: File) {
		FileCopyUtils.copy(src, dest)
	}

	/**
	 * @param src a source folder
	 * @param dest a destination folder
	 * @throws IOException if an I/O error occurs
	 */
	@Throws(IOException::class)
	fun copyDir(src: File, dest: File) {
		if (src.isDirectory) {
			if (!dest.exists()) {
				dest.mkdirs()
			}
			val files = src.list()
			for (fileName in files!!) {
				copyDir(File(src, fileName), File(dest, fileName))
			}
		} else {
			copy(src, dest)
		}
	}
}
