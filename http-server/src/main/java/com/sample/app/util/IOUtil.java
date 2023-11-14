package com.sample.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil {
	public static String readContentAsString(InputStream inputStream) throws IOException {
		try (InputStreamReader isr = new InputStreamReader(inputStream); BufferedReader br = new BufferedReader(isr)) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}

	}
}
