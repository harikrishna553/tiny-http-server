package com.sample.app.request.handlers;

import com.sample.app.http.enums.HttpStatus;
import com.sample.app.http.exceptions.HttpRequestException;
import com.sample.app.http.request.handlers.HttpRequestHandler;
import com.sample.app.http.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;

public class RootHandler implements HttpRequestHandler {
	@Override
	public void handleGetRequest(HttpExchange exchange) throws HttpRequestException {
		HttpResponseUtil.sendResponse(exchange, HttpStatus.OK);
	}
}
