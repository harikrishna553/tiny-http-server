package com.sample.app.request.handlers;

import com.sample.app.dto.Employee;
import com.sample.app.http.enums.HttpContentType;
import com.sample.app.http.enums.HttpStatus;
import com.sample.app.http.exceptions.HttpRequestException;
import com.sample.app.http.request.handlers.HttpRequestHandler;
import com.sample.app.http.util.HttpRequestPathUtil;
import com.sample.app.http.util.HttpRequestPayloadUtil;
import com.sample.app.http.util.HttpResponseUtil;
import com.sample.app.repository.EmployeeRepository;
import com.sample.app.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import java.util.*;

public class EmployeeRequestHandler implements HttpRequestHandler {

	private static final String EMPLOYEES_BY_CITY_TEMPLATE = "/v1/employees/by-city/{empCity}";

	@Override
	public void handleGetRequest(HttpExchange exchange) throws HttpRequestException {
		String path = HttpRequestPathUtil.path(exchange);
		System.out.println("Received GET request for the path : " + path);

		try {
			if ("/v1/employees".equals(path)) {
				List<Employee> emps = EmployeeRepository.emps();
				String json = JsonUtil.marshal(emps);
				HttpResponseUtil.sendResponse(exchange, HttpStatus.OK, json, HttpContentType.JSON);
				return;
			}

			if (path.startsWith("/v1/employees/by-city")) {
				Map<String, String> pathParams = HttpRequestPathUtil.extractPathVariables(EMPLOYEES_BY_CITY_TEMPLATE,
						path);
				String city = pathParams.get("empCity");
				List<Employee> emps = EmployeeRepository.empsByCity(city);
				String json = JsonUtil.marshal(emps);
				HttpResponseUtil.sendResponse(exchange, HttpStatus.OK, json, HttpContentType.JSON);
				return;
			}

			throw new HttpRequestException("No handler found for the path " + path);
		} catch (Exception e) {
			throw new HttpRequestException(e);
		}

	}

	public void handlePostRequest(HttpExchange exchange) throws HttpRequestException {
		String path = HttpRequestPathUtil.path(exchange);
		System.out.println("Received GET request for the path : " + path);

		try {
			if ("/v1/employees".equals(path)) {
				String requestPayload = HttpRequestPayloadUtil.readRequestBody(exchange);
				Employee emp = JsonUtil.unmarshal(Employee.class, requestPayload);
				EmployeeRepository.addEmployeeToCityMap(emp);
				HttpResponseUtil.sendResponse(exchange, HttpStatus.CREATED, requestPayload, HttpContentType.JSON);
				return;
			}
		} catch (Exception e) {
			throw new HttpRequestException(e);
		}

	}

}
