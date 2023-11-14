package com.sample.app.http.request.handlers;

import com.sample.app.http.enums.HttpStatus;
import com.sample.app.http.exceptions.HttpRequestException;
import com.sample.app.http.util.HttpRequestHeaderUtil;
import com.sample.app.http.util.HttpRequestPathUtil;
import com.sample.app.http.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

/**
 * To set the response headers use 'exchange.getResponseHeaders()' method
 *
 */
public interface HttpRequestHandler {

	public default void handleGetRequest(HttpExchange exchange) throws HttpRequestException {
		throw new HttpRequestException("Functionality is not implemented");
	}

	public default void handlePutRequest(HttpExchange exchange) throws HttpRequestException {
		throw new HttpRequestException("Functionality is not implemented");
	}

	public default void handlePostRequest(HttpExchange exchange) throws HttpRequestException {
		throw new HttpRequestException("Functionality is not implemented");
	}

	public default void handleDeleteRequest(HttpExchange exchange) throws HttpRequestException {
		throw new HttpRequestException("Functionality is not implemented");
	}

	public default void handleOptionsRequest(HttpExchange exchange) throws HttpRequestException {
		System.out.println("Received OPTIONS request for " + HttpRequestPathUtil.path(exchange));
		HttpRequestHeaderUtil.setCorsHeaders(exchange);
		HttpResponseUtil.sendResponse(exchange, HttpStatus.OK);
	}

}
