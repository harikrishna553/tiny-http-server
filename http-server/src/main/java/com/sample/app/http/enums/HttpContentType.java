package com.sample.app.http.enums;

public enum HttpContentType {
	JSON("application/json"), XML("application/xml"), PLAIN_TEXT("text/plain"), HTML("text/html");

	private String type;

	HttpContentType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static HttpContentType fromString(String type) {
		for (HttpContentType contentType : values()) {
			if (contentType.getType().equalsIgnoreCase(type)) {
				return contentType;
			}
		}
		return null;
	}
}
