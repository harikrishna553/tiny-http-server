package com.sample.app.http.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sample.app.util.ClassInstanceCreator;
import com.sample.app.util.StringUtil;

public class HttpRequestHandlersUtil {
	private static Map<String, String> REQUEST_MAPPING_CLASSES = new ConcurrentHashMap<>();

	public static void addMappingClassForRequestPrefix(String requestPrefix, String fullQualififedClassName) {
		REQUEST_MAPPING_CLASSES.put(requestPrefix, fullQualififedClassName);
	}

	public static Object newInstance(String requestUrl) {
		List<String> possiblePrefixes = getPrefixes(requestUrl);

		String fullyQualifiedClassName = null;

		for (String prefix : possiblePrefixes) {
			if (REQUEST_MAPPING_CLASSES.containsKey(prefix)) {
				fullyQualifiedClassName = REQUEST_MAPPING_CLASSES.get(prefix);
				break;
			}
		}

		if (StringUtil.isNullOrEmpty(fullyQualifiedClassName)) {
			throw new RuntimeException("No class name mapped to requestUrl");
		}

		return ClassInstanceCreator.createInstance(fullyQualifiedClassName);
	}

	/**
	 * For the path /v1/employees/by-city, it return [/v1/employees/by-city,
	 * /v1/employees, /v1, ]
	 * 
	 * @param path
	 * @return
	 */
	private static List<String> getPrefixes(String path) {
		List<String> prefixes = new ArrayList<>();
		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == '/') {
				prefixes.add(path.substring(0, i));
			}
		}

		prefixes.add(path);

		Collections.reverse(prefixes);
		return prefixes;
	}

}
