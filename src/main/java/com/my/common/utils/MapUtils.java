package com.my.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {

	/**
	 * 快速构造map<br/>
	 * 
	 * @param args
	 * @return
	 */
	public static Map<String, Object> build(Object... args) {
		Map<String, Object> pair = new HashMap<String, Object>();
		for (int i = 0; i < args.length; i += 2) {
			pair.put(args[i].toString(), i + 1 >= args.length ? "" : args[i + 1]);
		}
		return pair;
	}
	

	public static String joinUrl(String originalUrl, String queryString) {
		if (originalUrl.indexOf("?") > 0) {
			return originalUrl + "&" + queryString;
		} else {
			return originalUrl + "?" + queryString;
		}
	}

	public static String join(Map<String, ?> params, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, ?> entry : params.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append(delimiter);
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static Integer getInteger(Map<String, ?> params, String key) {
		Object value = params.get(key);
		if (value == null) {
			return 0;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue();
		} else if (value instanceof String) {
			try {
				return Integer.parseInt((String) value);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public static Long getLong(Map<String, ?> params, String key) {
		Object value = params.get(key);
		if (value == null) {
			return 0L;
		}
		if (value instanceof Number) {
			return ((Number) value).longValue();
		} else if (value instanceof String) {
			try {
				return Long.parseLong((String) value);
			} catch (Exception e) {
				return 0L;
			}
		}
		return 0L;
	}

	public static Boolean getBoolean(Map<String, ?> params, String key) {
		Object value = params.get(key);
		if (value == null) {
			return Boolean.FALSE;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue() > 0;
		} else if (value instanceof String) {
			String s = ((String) value).toUpperCase();
			return "TRUE".equals(s) || "T".equals(s) || "YES".equals(s) || "Y".equals(s) || "1".equals(s);
		}
		return Boolean.FALSE;
	}

	public static String getString(Map<String, ?> params, String key) {
		Object value = params.get(key);
		return value == null ? "" : value.toString();
	}
}
