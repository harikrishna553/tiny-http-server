package com.sample.app.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sample.app.http.enums.HttpContentType;
import com.sample.app.http.enums.HttpStatus;
import com.sample.app.http.exceptions.HttpRequestException;
import com.sample.app.util.StringUtil;
import com.sun.net.httpserver.HttpExchange;

public class HttpResponseUtil {
	public static void sendResponse(HttpExchange exchange, HttpStatus httpStatus) throws HttpRequestException {
		sendResponse(exchange, httpStatus, null, HttpContentType.PLAIN_TEXT);
	}

	public static void sendResponse(HttpExchange exchange, HttpStatus httpStatus, String responsePayload,
			HttpContentType contentType) throws HttpRequestException {
		sendResponse(exchange, httpStatus.getCode(), responsePayload, contentType.getType());
	}

	private static void sendResponse(HttpExchange exchange, int statusCode, String responsePayload, String contentType)
			throws HttpRequestException {
		try (OutputStream os = exchange.getResponseBody()) {

			if (responsePayload == null) {
				exchange.sendResponseHeaders(statusCode, -1);
			} else {
				exchange.sendResponseHeaders(statusCode, responsePayload.length());
			}

			if (!StringUtil.isNullOrEmpty(contentType)) {
				exchange.getResponseHeaders().set("Content-Type", contentType);
			}

			os.write(responsePayload.getBytes());
		} catch (Exception e) {
			throw new HttpRequestException(e);
		}
	}

	public static void sendStream(HttpExchange exchange, InputStream stream, HttpStatus httpStatus)
			throws HttpRequestException {

		try (OutputStream os = exchange.getResponseBody()) {
			exchange.sendResponseHeaders(httpStatus.getCode(), stream.available());
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = stream.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new HttpRequestException(e);
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				throw new HttpRequestException(e);
			}
		}

	}

}
