package com.ponray.utils;

import java.nio.ByteBuffer;
import java.util.Locale;

/**
 * Byte转换工具
 * 
 * @author yangle
 */
public class ByteUtils {

	public static final byte[] A55A = hexStringToBytes("A55A");

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

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			        return null;
			    }
		    // toUpperCase将字符串中的所有字符转换为大写
		     hexString = hexString.toUpperCase();
		     int length = hexString.length() / 2;
		     // toCharArray将此字符串转换为一个新的字符数组。
		     char[] hexChars = hexString.toCharArray();
		     byte[] d = new byte[length];
		     for (int i = 0; i < length; i++) {
			         int pos = i * 2;
			         d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
			     }
		    return d;
		 }

	//返回匹配字符
 	private static byte charToByte(char c) {
		     return (byte) "0123456789ABCDEF".indexOf(c);
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


	//将字节数组转换为16进制字符串
	public static String binaryToHexString(byte[] bytes) {
		String hexStr = "0123456789ABCDEF";
		String result = "";
		String hex = "";
		for (byte b : bytes) {
			hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
			hex += String.valueOf(hexStr.charAt(b & 0x0F));
			result += hex;
		}
		return result;
	}




	public static String byteArrayToString(byte[] array){
		if (array == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
			buffer.append(new String(array));
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

	// int转换为byte[4]数组
	public static byte[] getByteArray(int i) {
		byte[] b = new byte[4];
		b[0] = (byte) ((i & 0xff000000) >> 24);
		b[1] = (byte) ((i & 0x00ff0000) >> 16);
		b[2] = (byte) ((i & 0x0000ff00) >> 8);
		b[3] = (byte)  (i & 0x000000ff);
		return b;
	}

	/**
	 * float转byte
	 * @param f
	 * @return
	 */
	public static byte[] float2byte(float f) {

		// 把float转换为byte[]
		int fbit = Float.floatToIntBits(f);

		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (fbit >> (24 - i * 8));
		}

		// 翻转数组
		int len = b.length;
		// 建立一个与源数组元素类型相同的数组
		byte[] dest = new byte[len];
		// 为了防止修改源数组，将源数组拷贝一份副本
		System.arraycopy(b, 0, dest, 0, len);
		byte temp;
		// 将顺位第i个与倒数第i个交换
		for (int i = 0; i < len / 2; ++i) {
			temp = dest[i];
			dest[i] = dest[len - i - 1];
			dest[len - i - 1] = temp;
		}

		return dest;

	}

	//这个函数将byte转换成float
	public static float byte2float(byte[] b, int index) {
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}


	/**
	 * short转换为byte[2]数组  用于crc16校验
	 */
	public static byte[] getByteArray(short s) {
		byte[] b = new byte[2];
		b[0] = (byte) ((s & 0xff00) >> 8);
		b[1] = (byte) (s & 0x00ff);
		return b;
	}

	/**
	 * byte数组合并
	 * @param byte1
	 * @param byte2
	 * @return
	 */
	public static byte[] byteMerger(byte[] byte1,byte[] byte2){
		byte[] byte3 = new byte[byte1.length + byte2.length];
		System.arraycopy(byte1,0,byte3,0,byte1.length);
		System.arraycopy(byte2,0,byte3,byte1.length,byte2.length);
		return byte3;
	}

	/**
	 * 翻转byte
	 * @param bytes
	 * @return
	 */
	public static byte[] byteReversal(byte[] bytes){
		byte[] newBytes = new byte[bytes.length];
		for(int i=0;i<bytes.length;i++){
			newBytes[i] = bytes[bytes.length-1-i];
		}
		return newBytes;
	}

}
