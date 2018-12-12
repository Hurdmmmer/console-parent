package com.tdh.gps.console.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @ClassName: MD5ExtractTokenKeyDigester
 * @Description: (提取MD5 TokenKey工具类)
 * @author wxf
 * @date 2018年12月6日 下午3:13:11
 *
 */
public class MD5ExtractTokenKeyDigesterUtils {

	/**
	 * 
	 * @Title: digest  
	 * @Description: (MD5加密)  
	 * @param value
	 * @return String 返回类型 
	 * @throws
	 */
	public static String digest(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}
}
