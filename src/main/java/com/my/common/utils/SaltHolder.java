package com.my.common.utils;

public abstract class SaltHolder {
	private static ThreadLocal<String> saltHolder = new ThreadLocal<String>();
	
	public static void setSalt(String salt) {
		saltHolder.set(salt);
	}
	
	public static String getSalt() {
		return saltHolder.get();
	}
}
