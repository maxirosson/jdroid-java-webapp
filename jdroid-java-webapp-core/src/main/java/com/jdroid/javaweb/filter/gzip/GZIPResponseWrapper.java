package com.jdroid.javaweb.filter.gzip;

import com.jdroid.java.utils.LoggerUtils;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZIPResponseWrapper extends HttpServletResponseWrapper {

	private static final Logger LOGGER = LoggerUtils.getLogger(GZIPResponseWrapper.class);

	protected HttpServletResponse wrappedResponse;
	protected ServletOutputStream stream;
	protected PrintWriter writer;

	public GZIPResponseWrapper(HttpServletResponse wrappedResponse) {
		super(wrappedResponse);
		this.wrappedResponse = wrappedResponse;
	}

	public void close() {
		try {
			if (writer != null) {
				writer.close();
			} else {
				if (stream != null) {
					stream.close();
				}
			}
		} catch (IOException e) {
			LOGGER.error("IOException when closing GZIPResponseWrapper", e);
		}
	}

	@Override
	public void flushBuffer() throws IOException {
		stream.flush();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (writer != null) {
			throw new IllegalStateException("getWriter() has already been called!");
		}

		if (stream == null) {
			stream = createOutputStream();
		}
		return stream;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer != null) {
			return writer;
		}

		if (stream != null) {
			throw new IllegalStateException("getOutputStream() has already been called!");
		}

		stream = createOutputStream();
		writer = new PrintWriter(new OutputStreamWriter(stream, StandardCharsets.UTF_8));
		return writer;
	}

	@Override
	public void setContentLength(int length) {
	}

	private ServletOutputStream createOutputStream() throws IOException {
		return new GZIPResponseStream(wrappedResponse);
	}
}
