package com.sample.app.util;

public class ClassInstanceCreator {
	public static Object createInstance(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	
}
