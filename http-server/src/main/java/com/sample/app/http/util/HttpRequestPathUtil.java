package com.sample.app.http.util;

import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class HttpRequestPathUtil {

	public static String path(HttpExchange exchange) {
		return exchange.getRequestURI().getPath();
	}

	public static String resolvePath(String templatePath, Map<String, String> pathParameters) {
		String resolvedPath = templatePath;

		// Iterate through the path parameters and replace placeholders in the template
		for (Map.Entry<String, String> entry : pathParameters.entrySet()) {
			String placeholder = "{" + entry.getKey() + "}";
			String value = entry.getValue();
			resolvedPath = resolvedPath.replace(placeholder, value);
		}

		return resolvedPath;
	}

	/**
	 * String templatePath = "/employees/by-city/{cityName}/by-age/{age}"; String
	 * actualPath = "/employees/by-city/Bangalore/by-age/34";
	 * 
	 * 
	 * @param templatePath
	 * @param actualPath
	 * @return
	 */

	public static Map<String, String> extractPathVariables(String templatePath, String actualPath) {
		Map<String, String> pathVariables = new HashMap<>();
		String[] templatePathSegments = templatePath.split("/");
		String[] actualPathSegments = actualPath.split("/");

		for (int i = 0; i < templatePathSegments.length; i++) {
			if (templatePathSegments[i].startsWith("{") && templatePathSegments[i].endsWith("}")) {
				String pathVariableName = templatePathSegments[i].substring(1, templatePathSegments[i].length() - 1);
				pathVariables.put(pathVariableName, actualPathSegments[i]);
			}
		}

		return pathVariables;
	}
}
