package com.sample.app;

import java.io.IOException;

import com.sample.app.http.server.TinyHttpServer;
import com.sample.app.request.handlers.EmployeeRequestHandler;
import com.sample.app.request.handlers.HealthHandler;
import com.sample.app.request.handlers.RootHandler;

public class App {

	public static void main(String[] args) throws IOException {

		TinyHttpServer myServer = new TinyHttpServer(8080);

		myServer.addMappingClassForRequestPrefix("/v1/employees", EmployeeRequestHandler.class.getName());
		myServer.addMappingClassForRequestPrefix("/", RootHandler.class.getName());
		myServer.addMappingClassForRequestPrefix("/health", HealthHandler.class.getName());

		myServer.start();

	}

}
