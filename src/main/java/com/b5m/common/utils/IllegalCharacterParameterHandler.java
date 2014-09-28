package com.b5m.common.utils;

public class IllegalCharacterParameterHandler {

	/**
	 * 过滤掉搜索请求参数中的keyword的非法关键字
	 * @param keyword
	 * @return
	 */
	public static String filterIllegalCharacterInKeyword(String keyword){
		if(isLegalCharacters(keyword))
			return keyword;
		return keyword.replaceAll(",\\", "").trim();
	}
	
	/**
	 * 判断字符串中是否有非法字符
	 * @param keyword
	 * @return
	 */
	public static boolean isLegalCharacters(String keyword){
		if(keyword.contains(",\\")){
			return false;
		}
		return true;
	}
}
