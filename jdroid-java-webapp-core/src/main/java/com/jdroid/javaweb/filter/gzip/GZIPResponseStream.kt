package com.jdroid.javaweb.filter.gzip

import com.jdroid.java.http.HttpService
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.GZIPOutputStream
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

class GZIPResponseStream @Throws(IOException::class)
constructor(protected var response: HttpServletResponse) : ServletOutputStream() {

    protected var baos: ByteArrayOutputStream
    protected var gzipstream: GZIPOutputStream
    protected var closed: Boolean = false
    protected var output: ServletOutputStream

    init {
        closed = false
        output = response.outputStream
        baos = ByteArrayOutputStream()
        gzipstream = GZIPOutputStream(baos)
    }

    @Throws(IOException::class)
    override fun close() {
        if (closed) {
            throw IOException("This output stream has already been closed")
        }
        gzipstream.finish()

        val bytes = baos.toByteArray()

        response.addHeader("Content-Length", Integer.toString(bytes.size))
        response.addHeader(HttpService.CONTENT_ENCODING_HEADER, HttpService.GZIP_ENCODING)
        output.write(bytes)
        output.flush()
        output.close()
        closed = true
    }

    @Throws(IOException::class)
    override fun flush() {
        if (closed) {
            throw IOException("Cannot flush a closed output stream")
        }
        gzipstream.flush()
    }

    @Throws(IOException::class)
    override fun write(b: Int) {
        if (closed) {
            throw IOException("Cannot write to a closed output stream")
        }
        gzipstream.write(b.toByte().toInt())
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray) {
        write(b, 0, b.size)
    }

    @Throws(IOException::class)
    override fun write(b: ByteArray, off: Int, len: Int) {
        if (closed) {
            throw IOException("Cannot write to a closed output stream")
        }
        gzipstream.write(b, off, len)
    }
}
