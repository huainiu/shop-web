package com.b5m.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DataUtils {
	public static Map<String, String> attrSpecialChar = new HashMap<String, String>();
	
	static{
		//下划线用@@代替
		//&用@@代替
		//+用@*@代替
		//,用@^@代替
		attrSpecialChar.put("@^@", ",");
		attrSpecialChar.put("@@", "_");
		attrSpecialChar.put("@$@", "&");
		attrSpecialChar.put("@**", "+");
	}
	
	public static boolean containsChinese(String str){
		if(StringUtils.isEmpty(str)) return false;
		return str.getBytes().length != str.length();
	}
	
	public static String replace(String text, String replace, String replaceWith){
		if(text.indexOf(replace) < 0) return text;
		return StringUtils.replace(text, replace, replaceWith);
	}
	
	public static String specialCharEn(String str){
		for(Entry<String, String> entry : attrSpecialChar.entrySet()){
			str = replace(str, entry.getValue(), entry.getKey());
		}
		return str;
	}
	
	public static String specialCharDe(String str){
		for(Entry<String, String> entry : attrSpecialChar.entrySet()){
			str = replace(str, entry.getKey(), entry.getValue());
		}
		return str;
	}
	
	public static String base64Encode(String chinese){
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode(chinese.getBytes());
	}
	
	public static String base64Decode(String str){
		BASE64Decoder base64Decoder = new BASE64Decoder();
		try {
			return new String(base64Decoder.decodeBuffer(str));
		} catch (IOException e) {
			return null;
		}
	}
	
	public static String strEncode(String str){
		int length = str.length();
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index < length; index++){
			int asciiNum = (int)str.charAt(index);
			String oneEncode = fourPlaceFill(Integer.toString(asciiNum, 35));//转化为35进制
			sb.append(oneEncode);
		}
		return sb.toString().toUpperCase();
	}
	
	public static String strDecode(String str){
		str = str.toLowerCase();
		int length = str.length();
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index < length; index = index + 4){
			String oneEncode = str.substring(index, index + 4);
			int asciiNum = Integer.valueOf(oneEncode, 35);//35进制转化为10进制
			char c = (char)asciiNum;
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static String fourPlaceFill(String str){
		int length = str.length();
		if(length >= 4) return str;
		if(length == 0) return "0000";
		if(length == 1) return "000" + str;
		if(length == 2) return "00" + str;
		if(length == 3) return "0" + str;
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(strEncode("@^@唐泰全包围脚垫宝马7系5系加长3系脚垫宝马专车专用汽车脚垫 温馨米 120女士大码雪纺衫针织拼接时尚款lkfajvflkd2339049834===-=9\\|1121783#%^%&*&(kfve923934/*<>?/{}[]+=《来自Running星的你》HAHA守护“簪子女大海列车(바다열차)”"));
		System.out.println(strDecode("001T002O001T0HR60MQS0H0F0HC80I600QYA0IB80J530W9H001K0Q45001I0Q450H9C0V8G001G0Q450QYA0IB80J530W9H0GB20TXU0GB20OGW0MNI0TXU0QYA0IB8000W0N0Q0W3V0Q09000W001E001F001D0IO90IKD0IM30P2Q0VIT0QGW0SHS0V1E0QHA0KO00KST0LAR0J8N0MEE00330032002W002R0031003D002W00330032002U001F001G001G001M001D001H001M001L001G001H001Q001Q001Q001A001Q001M002M003J001E001E001F001E001K001L001G00100012002O001200130017001300150032002W003D002V001M001F001G001M001G001H001C0017001P001R001S001C003I003K002L002N0018001Q0A1D0LL90R58002C003C0035003500300035002X0LBX0OQU0GKK0A1E0022001U0022001U0J4H0KLH06OU0PX60J2V0IO90IM30MUN0H5F0TXU001514AN12DG16CV17N9001606OV"));
//		"女士大码雪纺衫针织拼接时尚款";
		/*System.out.println(base64Encode("女士大码雪纺衫针织拼接时尚款"));
		System.out.println(UrlTools.urlEncode(base64Encode("女士大码雪纺衫针织拼接时尚款")));
		System.out.println(UrlTools.urlDecode(base64Encode("女士大码雪纺衫针织拼接时尚款")));*/
//		ZZZZ
		//0123456789 ABCDEFGHIJKLMNOPQRSTUVWXYZ 36 
//		System.out.println(Integer.toString(796, 35));
//		int i = 45796;
//		System.out.println(Integer.valueOf("00" + Integer.toString(796, 35), 35));
//		45796
		//22899唐泰全包围脚垫宝马7系5系加长3系脚垫宝马专车专用汽车脚垫 温馨米 120
		/*String str = "@^@唐泰全包围脚垫宝马7系5系加长3系脚垫宝马专车专用汽车脚垫 温馨米 120女士大码雪纺衫针织拼接时尚款lkfajvflkd2339049834===-=9\\|1121783#%^%&*&(kfve923934/*<>?/{}[]+=《来自Running星的你》HAHA守护“簪子女大海列车(바다열차)”";
		for(int i = 0; i < str.length(); i++){
			System.out.println((int)str.charAt(i));
		}*/
	}
	
}
