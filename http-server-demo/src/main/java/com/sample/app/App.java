package com.sample.app;

import java.io.IOException;

import com.sample.app.http.server.TinyHttpServer;
import com.sample.app.http.util.HttpRequestHandlersUtil;
import com.sample.app.request.handlers.EmployeeRequestHandler;
import com.sample.app.request.handlers.HealthHandler;
import com.sample.app.request.handlers.RootHandler;

public class App {

	public static void main(String[] args) throws IOException {
		HttpRequestHandlersUtil.addMappingClassForRequestPrefix("/v1/employees", EmployeeRequestHandler.class.getName());
		HttpRequestHandlersUtil.addMappingClassForRequestPrefix("/", RootHandler.class.getName());
		HttpRequestHandlersUtil.addMappingClassForRequestPrefix("/health", HealthHandler.class.getName());

		TinyHttpServer myServer = new TinyHttpServer(8080);
		myServer.start();

	}

}
