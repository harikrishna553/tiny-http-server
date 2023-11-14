package com.sample.app.http.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sample.app.util.StringUtil;
import com.sun.net.httpserver.HttpExchange;

public class HttpRequestQueryParameterUtil {

	public static Map<String, List<String>> parseQueryParameters(HttpExchange exchange) {

		URI uri = exchange.getRequestURI();
		String query = uri.getQuery();

		if (StringUtil.isNullOrEmpty(query)) {
			return Collections.emptyMap();
		}

		Map<String, List<String>> queryParams = new HashMap<>();

		String[] pairs = query.split("&");
		for (String pair : pairs) {
			String[] keyValue = pair.split("=");
			if (keyValue.length == 2) {
				String key = keyValue[0];
				String value = keyValue[1];

				// If the key already exists, add the value to the existing list
				queryParams.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
			}
		}

		return queryParams;
	}

}
