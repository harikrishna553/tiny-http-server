package com.sample.app.http.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sample.app.http.enums.HttpContentType;
import com.sample.app.http.enums.HttpStatus;
import com.sample.app.http.request.handlers.HttpRequestHandler;
import com.sample.app.http.util.HttpRequestHandlersUtil;
import com.sample.app.http.util.HttpResponseUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class TinyHttpServer {
	private HttpServer httpServer = null;
	private int port;

	public TinyHttpServer(int port) throws IOException {
		this.port = port;

		// Create an HttpServer that listens on port 8080.
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

	private static class RootHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String requestMethod = exchange.getRequestMethod();
			URI uri = exchange.getRequestURI();
			String requestUrl = uri.getPath();

			try {
				HttpRequestHandler requestHandler = (HttpRequestHandler) HttpRequestHandlersUtil.newInstance(requestUrl);
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
					HttpResponseUtil.sendResponse(exchange, HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed", HttpContentType.PLAIN_TEXT);
					break;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

	}

}
