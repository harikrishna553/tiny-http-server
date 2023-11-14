package com.sample.app.request.handlers;

import java.util.HashMap;

import com.sample.app.http.enums.HttpContentType;
import com.sample.app.http.enums.HttpStatus;
import com.sample.app.http.exceptions.HttpRequestException;
import com.sample.app.http.request.handlers.HttpRequestHandler;
import com.sample.app.http.util.HttpResponseUtil;
import com.sample.app.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import java.util.*;

public class HealthHandler implements HttpRequestHandler {

	private static Map<String, Object> RESPONSE = new HashMap<>();

	static {
		RESPONSE.put("status", "ok");
	}

	@Override
	public void handleGetRequest(HttpExchange exchange) throws HttpRequestException {
		try {

			String json = JsonUtil.marshal(RESPONSE);
			HttpResponseUtil.sendResponse(exchange, HttpStatus.OK, json, HttpContentType.JSON);
		} catch (Exception e) {
			throw new HttpRequestException(e);
		}

	}
}
