package com.sample.app.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sample.app.http.enums.HttpContentType;
import com.sample.app.http.enums.HttpStatus;
import com.sample.app.http.request.handlers.HttpRequestHandler;
import com.sample.app.http.util.HttpResponseUtil;
import com.sample.app.util.ClassInstanceCreator;
import com.sample.app.util.StringUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class TinyHttpServer {
	private final Map<String, String> REQUEST_MAPPING_CLASSES = new ConcurrentHashMap<>();

	private HttpServer httpServer = null;
	private int port;

	public TinyHttpServer(int port) throws IOException {
		this.port = port;

		// Create an HttpServer that listens on port.
		httpServer = HttpServer.create(new InetSocketAddress(port), 0);

		// Create a context for the server, specifying the path and the handler to use.
		httpServer.createContext("/", new RootHandler());

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("Stopping server safely");
					httpServer.stop(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void start() {
		// Start the server.
		httpServer.start();

		System.out.println("Server is running on port " + this.port + " .......");
	}

	public void addMappingClassForRequestPrefix(String requestPrefix, String fullQualififedClassName) {
		REQUEST_MAPPING_CLASSES.put(requestPrefix, fullQualififedClassName);
	}

	private Object newInstance(String requestUrl) {
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

	private class RootHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String requestMethod = exchange.getRequestMethod();
			URI uri = exchange.getRequestURI();
			String requestUrl = uri.getPath();

			try {
				HttpRequestHandler requestHandler = (HttpRequestHandler) newInstance(requestUrl);
				switch (requestMethod) {
				case "GET":
					requestHandler.handleGetRequest(exchange);
					break;
				case "POST":
					requestHandler.handlePostRequest(exchange);
					break;
				case "PUT":
					requestHandler.handlePutRequest(exchange);
					break;
				case "OPTIONS":
					requestHandler.handleOptionsRequest(exchange);
					break;
				case "DELETE":
					requestHandler.handleDeleteRequest(exchange);
					break;
				default:
					HttpResponseUtil.sendResponse(exchange, HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed",
							HttpContentType.PLAIN_TEXT);
					break;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

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
