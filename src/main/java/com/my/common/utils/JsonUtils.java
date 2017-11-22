package com.my.common.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	public static <T> T fromJson(String json, Class<T> cls) {
		try {
			return mapper.readValue(json, cls);
		} catch (IOException e) {
			return null;
		}
	}
	
}
