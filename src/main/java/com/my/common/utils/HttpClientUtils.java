package com.my.common.utils;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtils {
	private static PoolingHttpClientConnectionManager cm;
	private static RequestConfig requestConfig;

	static {
		cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(50);
		cm.setDefaultMaxPerRoute(5);
		requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000).build();
	}

	private static CloseableHttpClient getHttpClient(CookieStore cookieStore) {
		HttpClientBuilder builder = HttpClients.custom().setConnectionManager(cm)
				.setDefaultRequestConfig(requestConfig);
		if (cookieStore != null) {
			builder.setDefaultCookieStore(cookieStore);
		}
		return builder.build();
	}

	public static Entity doGet(String url, Map<String, ?> params) {
		return doGet(url, params, null, null);
	}

	public static Entity doGet(String url, Map<String, ?> params, Map<String, ?> headers, List<Cookie> cookies) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		try {
			CookieStore cookieStore = null;
			if (cookies != null && !cookies.isEmpty()) {
				cookieStore = new BasicCookieStore();
				for (Cookie cookie : cookies) {
					cookieStore.addCookie(cookie);
				}
			}
			httpclient = getHttpClient(cookieStore);
			URIBuilder builder = new URIBuilder(url);
			if (params != null && !params.isEmpty()) {
				for (Entry<String, ?> entry : params.entrySet()) {
					builder.addParameter(entry.getKey(), entry.getValue().toString());
				}
			}
			HttpGet request = new HttpGet(builder.build());
			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, ?> entry : headers.entrySet()) {
					request.addHeader(entry.getKey(), entry.getValue().toString());
				}
			}
			response = httpclient.execute(request);
			if (response != null) {
				int code = response.getStatusLine().getStatusCode();
				String content = EntityUtils.toString(response.getEntity(), "utf-8");
				return new Entity(code, content);
			} else {
				return new Entity(500, "连接已断开");
			}
		} catch (Exception e) {
			return new Entity(500, e.getMessage());
		} finally {
			org.apache.http.client.utils.HttpClientUtils.closeQuietly(response);
			org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpclient);
		}
	}

	static Entity doPost(String url, Map<String, ?> params, Map<String, ?> headers, List<Cookie> cookies) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		try {
			CookieStore cookieStore = null;
			if (cookies != null && !cookies.isEmpty()) {
				cookieStore = new BasicCookieStore();
				for (Cookie cookie : cookies) {
					cookieStore.addCookie(cookie);
				}
			}
			httpclient = getHttpClient(cookieStore);
			HttpPost request = new HttpPost(new URI(url));
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				for (Entry<String, ?> entry : params.entrySet()) {
					postParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
				}
				request.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
			}
			if (headers != null && !headers.isEmpty()) {
				for (Entry<String, ?> entry : headers.entrySet()) {
					request.addHeader(entry.getKey(), entry.getValue().toString());
				}
			}
			response = httpclient.execute(request);
			if (response != null) {
				int code = response.getStatusLine().getStatusCode();
				String content = EntityUtils.toString(response.getEntity(), "utf-8");
				return new Entity(code, content);
			} else {
				return new Entity(500, "连接已断开");
			}
		} catch (Exception e) {
			return new Entity(500, e.getMessage());
		} finally {
			org.apache.http.client.utils.HttpClientUtils.closeQuietly(response);
			org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpclient);
		}
	}
	
	public static Entity upload(String url, InputStream input, List<Cookie> cookies) {
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		try {
			CookieStore cookieStore = null;
			if (cookies != null && !cookies.isEmpty()) {
				cookieStore = new BasicCookieStore();
				for (Cookie cookie : cookies) {
					cookieStore.addCookie(cookie);
				}
			}
			httpclient = getHttpClient(cookieStore);
			HttpPost request = new HttpPost(new URI(url));
			request.setEntity(new InputStreamEntity(input));
			response = httpclient.execute(request);
			if (response != null) {
				int code = response.getStatusLine().getStatusCode();
				String content = EntityUtils.toString(response.getEntity(), "utf-8");
				return new Entity(code, content);
			} else {
				return new Entity(500, "连接已断开");
			}
		} catch (Exception e) {
			return new Entity(500, e.getMessage());
		} finally {
			org.apache.http.client.utils.HttpClientUtils.closeQuietly(response);
			org.apache.http.client.utils.HttpClientUtils.closeQuietly(httpclient);
		}
	}
	
	public static class Entity {
		int statusCode = 200;
		/**
		 * encoding:utf-8
		 */
		String content = null;

		public Entity(int code, String content) {
			this.statusCode = code;
			this.content = content;
		}

		public int getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}
}


