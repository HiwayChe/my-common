package com.my.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtils {

	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if(requestAttributes == null) {
			return null;
		}
		return ((ServletRequestAttributes)requestAttributes).getRequest();
	}
	
	public static HttpServletResponse getResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if(requestAttributes == null) {
			return null;
		}
		return ((ServletRequestAttributes)requestAttributes).getResponse();
	}
	
	public static void redirect(String url, Map<String, ?> params) throws IOException {
		getResponse().sendRedirect(MapUtils.joinUrl(url, MapUtils.join(params, "&")));
	}
	
	public static void addCookie(String name, String value, String domain, Integer age, Boolean httponly) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath("/");
		cookie.setMaxAge(age);
		cookie.setHttpOnly(httponly);
		getResponse().addCookie(cookie);
	}
	
	public static String getCompleteUrl() {
		HttpServletRequest request = getRequest();
		String queryString  =request.getQueryString();
		return StringUtils.isEmpty(queryString) ? request.getRequestURL().toString() : (request.getRequestURL().append("?").append(queryString).toString());
	}
	
	public static String EncodeUrlComponent(String url) {
		try {
			return URLEncoder.encode(url, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}
	
	public static String decodeUrlComponent(String encodedUrl) {
		try {
			return URLDecoder.decode(encodedUrl, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return encodedUrl;
		}
	}
	
	public static Map<String, String> parseQueryString(String query) {
		if(query == null || "".equals(query)) {
			return Collections.emptyMap();
		}
		Map<String, String> data = new HashMap<String, String>();
		String[] array = query.split("&", 0);
		int index = 0;
		for (String str : array) {
			index = str.indexOf("=");
			if (index != -1) {
				data.put(str.substring(0, index), str.substring(index + 1));
			} else {
				data.put(str, null);//null instead of empty string
			}
		}
		return data;
	}
}
