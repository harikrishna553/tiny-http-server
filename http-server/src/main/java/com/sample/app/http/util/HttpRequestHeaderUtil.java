package com.sample.app.http.util;

import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class HttpRequestHeaderUtil {

	public static Map<String, List<String>> requestHeaders(HttpExchange exchange) {
		return exchange.getRequestHeaders();
	}

	public static Headers constructHeaders(Map<String, String> headersMap) {
		Headers headers = new Headers();

		for (Map.Entry<String, String> entries : headersMap.entrySet()) {
			headers.add(entries.getKey(), entries.getValue());
		}

		return headers;
	}

	public static Headers constructHeadersFromMultiValues(Map<String, List<String>> headersMap) {
		Headers headers = new Headers();

		for (Map.Entry<String, List<String>> entry : headersMap.entrySet()) {
			for (String value : entry.getValue()) {
				headers.add(entry.getKey(), value);
			}
		}

		return headers;
	}

	public static void setCorsHeaders(HttpExchange exchange) {
		Headers headers = exchange.getResponseHeaders();

		// Allow all origins
		headers.set("Access-Control-Allow-Origin", "*");

		// Set other CORS headers as needed
		headers.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		headers.set("Access-Control-Allow-Headers", "Content-Type");
	}
}
