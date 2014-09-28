package com.b5m.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DESHelper {
	private static SecretKey key;
	static {
		setKey("izensoft www.b5m.com");
	}

	private DESHelper() {

	}

	/**
	 * 根据参数生成KEY
	 */
	private static void setKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(strKey.getBytes()));
			key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public final static String encodeAppId(String content) {
		return PWCode.getPassWordCode(content)+ "-" + content;
	}

	public final static String decodeAppId(String content) {
		String key = content.split("-")[0];
		String value = content.split("-")[1];

		if (key.equals(PWCode.getPassWordCode(value))) {
			return value;
		} else {
			return null;
		}
	}
	
	public static String getUrlEncString(String strMing) {
		return encodeAppId(strMing);
	}
	
	public static String getUrlDesString(String strMing) {
		return decodeAppId(strMing);
	}
	
	/**
	 * 加密String明文输入,String密文输出
	 */
	public static String getEncString(String strMing) {
		return encodeAppId(strMing);
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public static String getDesString(String strMi) {
		return decodeAppId(strMi);
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	@SuppressWarnings("unused")
	private static byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	@SuppressWarnings("unused")
	private static byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	public static void main(String args[]) {

		String str1 = "1235123x";
		// DES加密
		System.out.println("原文:" + str1);
		String str2 = getEncString(str1);
		String deStr = getDesString(str2);
		System.out.println("密文:" + str2 + " " + str2.length());
		// DES解密
		System.out.println("解密:" + deStr);
	}
}
