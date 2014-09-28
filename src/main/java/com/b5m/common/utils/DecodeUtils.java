package com.b5m.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 解密工具
 */
public class DecodeUtils {

	public static char[] DECODE_TABLES = new char[] { 0x7a, 0x5a, 0x65, 0x6a, 0x3f, 0x2e, 0x20, 0x30, 0x05, 0x71, 0x47, 0x5e, 0x3b, 0x1e, 0x24, 0x23, 0x28, 0x1a, 0x29, 0x6c, 0x07, 0x58, 0x53, 0x61, 0x4a, 0x40, 0x3a, 0x64, 0x1d, 0x49, 0x4c, 0x27, 0x42, 0x06, 0x3d, 0x7c, 0xf, 0x39, 0x70, 0x4e, 0x14, 0x4b, 0x7e, 0xc, 0x5d, 0x5f, 0x16, 0x2a, 0x26, 0x60, 0x6e, 0x76, 0x15, 0xe, 0x6d, 0x68, 0x25, 0x57, 0x59, 0x1c, 0x69, 0x43, 0x31, 0x7b, 0x08, 0x21, 0x79, 0x78, 0x32, 0x33, 0x4d, 0xb, 0x44, 0x37, 0x3e, 0x17, 0x19, 0x22, 0x41, 0x6f, 0x51, 0x77, 0xd, 0x02, 0x13, 0x67, 0x55, 0x50, 0x38, 0x35, 0x1f, 0x6b, 0x34, 0x63, 0x62, 0x12, 0x52, 0x18, 0x75, 0x04, 0x48, 0x03, 0x46, 0x66, 0x74, 0x10, 0x1b, 0x7d, 0x00, 0xa, 0x5b, 0x2c, 0x72, 0x2b, 0x3c, 0x73, 0x45, 0x5c, 0x2d, 0x7f, 0x2f, 0x4f, 0x01, 0x54, 0x36,
			0x56, 0x09, 0x11 };

	/**
	 * 使用自定义编码
	 * 
	 * @param decodeTables
	 *            解密表
	 * @param content
	 *            ASCII字符串
	 */
	public static String decodeStr(String content) {
		StringBuilder sb = new StringBuilder();
		char[] buffer = content.toCharArray();
		try {
			for (int i = 0; i < buffer.length; i++) {
				char ec = buffer[i];
				char dc = DECODE_TABLES[ec];
				sb.append(dc);
			}

			return sb.toString();
		} catch (Exception e) {
		}

		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String details = "8tX8X%253D8XE8tI8%2BH8%2525I8tY8NE8N%2B8tf8%2B%253D8XXf%2502%251D%2513OQ%2502s8tf8%2B%253D8X%2525q%253D%2560%253DX%2507E%2525%2525q8tI8XD8N%2B8tY8%2BH8N%25258t%255C8%2BN8%2BN8t%255C8%2BH8%2525E8tY8%2BI8NY8tY8NH8N%257C8t%257C8N%255C8XY8tX8XE8X%253D8t%25258%2525H8N%25078tY8%2525t8N%2B8tX8%2Bf8%2525%2B8tY8X%253D8%2525%257C8tI8X%25258XXq8t%257C8%2B%253E8%2BH8tX8%2BH8N%257C8t%257C8N%255C8XY8tX8XE8X%253D8t%25258%2525H8N%25078tY8%2525t8N%2Bq8tX8XY8%2B%25078tY8%2525t8N%2Bq8tX8XY8%2B%25078t%257C8X%25258%2525Xq8t%25258%2B%2B8%2525%253E8tX8X%25258%2BD0%253E%253E%2525%2505%2507%250707hh%25268EN8Df8Df%253C6U%253ED%2505E%257C%2507%255EbB%253C6U%2505%255DO68Df2%253E8DfU%253E%255C8DfF%2507%253E8Df%2507H8Df%2507%25078Dfp%2Bt7%257D%2513%251D%255D%255D3N%251DNNNNNN%251E%2509d%250AB%253E%253D%253DFNN%253D%253CbUy%253CYFFNNbO%2507DY%253E%2505%2503%2526U0%255D7pO6%250207hh%25268EN8Df8Df%253Ch%25026%2505%2503%251B%2505%255DO68Df%2525%257C%257CXYI%25057h6%2513";

		String decodeStr = decodeStr(URLDecoder.decode(details, "utf-8"));
		String[] split = decodeStr.split("&");

		String title = split[0];
		String price = split[1];
		String picture = split[2];
		String source = split[3];
		String url = split[4];
		for (int i = 0; i < split.length; i++) {
			System.out.println(split[i]);
		}
//		System.out.println(String.format(" title: %s\n price: %s\n pic: %s\n source: %s\n url: %s\n", URLDecoder.decode(title, "utf-8"), price, URLDecoder.decode(picture, "utf-8"), source, URLDecoder.decode(url, "utf-8")));

	}
}
