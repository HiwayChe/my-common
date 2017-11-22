package com.my.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class EncryptionUtils {
	
	/**
	 * md5 hash, not recommend. use sha-256 instead.32 bits
	 */
	@Deprecated
	public static final String MD5 = "MD5";
	/**
	 * sha256 hash, 64 bits
	 */
	public static final String SHA256 = "SHA-256";

	public static String hash(String algorithm, String message, String salt) {
		if (null == message) {
			return null;
		}
		message = message + (salt == null ? "" : salt);
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(message.getBytes(), 0, message.length());
			return new BigInteger(1, digest.digest()).toString(16).toUpperCase();
		} catch (Exception e) {
			// swallow exception
		}
		return "";
	}

	public static void main(String... args) {
		String str = "admin";
		System.out.println(hash(MD5, str, ""));
		System.out.println(hash(SHA256, "123456", ""));
	}
}
