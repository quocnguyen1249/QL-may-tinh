package com.qlmaytinh.Util;

import java.io.BufferedReader;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtil {
	private static final Logger LOGGER = Logger.getLogger(HttpUtil.class.getName());
	private String line;

	public HttpUtil(String line) {
		this.line = line;
	}
	
	public <T> T toModel(Class<T> tClass) {
		try {
			return new ObjectMapper().readValue(line, tClass);
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			return null;
		}
	}
	public static HttpUtil of(BufferedReader reader) { 
		StringBuilder builder = new StringBuilder();
		try {
			String line;
			while((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return new HttpUtil(builder.toString());
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
			return null;
		}
	}
}
