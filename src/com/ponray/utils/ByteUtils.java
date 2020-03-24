package com.ponray.utils;

import java.nio.ByteBuffer;
import java.util.Locale;

/**
 * Byte转换工具
 * 
 * @author yangle
 */
public class ByteUtils {

	/**
	 * 十六进制字符串转byte[]
	 * 
	 * @param hex
	 *            十六进制字符串
	 * @return byte[]
	 */
	public static byte[] hexStr2Byte(String hex) {
		if (hex == null) {
			return new byte[] {};
		}

		// 奇数位补0
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}

		int length = hex.length();
		ByteBuffer buffer = ByteBuffer.allocate(length / 2);
		for (int i = 0; i < length; i++) {
			String hexStr = hex.charAt(i) + "";
			i++;
			hexStr += hex.charAt(i);
			byte b = (byte) Integer.parseInt(hexStr, 16);
			buffer.put(b);
		}
		return buffer.array();
	}

	/**
	 * byte[]转十六进制字符串
	 * 
	 * @param array
	 *            byte[]
	 * @return 十六进制字符串
	 */
	public static String byteArrayToHexString(byte[] array) {
		if (array == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			buffer.append(byteToHex(array[i]));
		}
		return buffer.toString();
	}

	/**
	 * byte转十六进制字符
	 * 
	 * @param b
	 *            byte
	 * @return 十六进制字符
	 */
	public static String byteToHex(byte b) {
		String hex = Integer.toHexString(b & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex.toUpperCase(Locale.getDefault());
	}

	/**
	 * byte转10进制
	 * @param b
	 * @return
	 */
	public static int byteToInt(byte b) {
		int x = b & 0xff;
		return x;
	}

	/**
	 * byte数组转换为整数
	 * 第0个byte与上0xff,生成整数,在右移24位，取得一个整数
	 * 第1个byte与上0xff,生成整数,在右移16位，取得一个整数
	 * 第2个byte与上0xff,生成整数,在右移8位，取得一个整数
	 * 第3个byte与上0xff,生成整数
	 * 把四个整数做或操作,转换为已整数
	 */
	public static int byteArrToInt(byte[] arr){
		int x = ((arr[0] & 0xff) << 24 )|((arr[1]& 0xff) <<16 )|((arr[2] & 0xff)<<8)|(arr[3] & 0xff);
		return x;
	}

}
