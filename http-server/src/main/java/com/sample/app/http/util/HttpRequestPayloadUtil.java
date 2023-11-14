package com.sample.app.http.util;

import java.io.IOException;

import com.sample.app.util.IOUtil;
import com.sun.net.httpserver.HttpExchange;

public class HttpRequestPayloadUtil {

	public static String readRequestBody(HttpExchange exchange) throws IOException {
		return IOUtil.readContentAsString(exchange.getRequestBody());
	}

}
