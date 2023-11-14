package com.sample.app.http.exceptions;

public class HttpRequestException extends Exception{
	
	public HttpRequestException() {
		
	}

	public HttpRequestException(Throwable t) {
		super(t);
	}
	
	public HttpRequestException(String message) {
		super(message);
	}
}
