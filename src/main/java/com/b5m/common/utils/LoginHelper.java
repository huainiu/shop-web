package com.b5m.common.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.b5m.base.common.utils.WebTools;
import com.b5m.frame.pojo.UserCenter;

public class LoginHelper {
	/**
	 * 根据cookie数组获得Map集合
	 */
	public static Map<String, String> getCookieMap(Cookie[] cks) {
		Map<String, String> mapCookie = null;
		if (cks != null && cks.length > 0) {
			mapCookie = new HashMap<String, String>();
			for (int i = 0; i < cks.length; i++) {
				mapCookie.put(cks[i].getName(), cks[i].getValue());
			}
		}
		return mapCookie;
	}

	public static UserCenter getUserCenter(HttpServletRequest request) {
		Cookie[] cks = request.getCookies();
		if (null != cks && cks.length > 0) {
			String userId = WebTools.getCooKieValue("token", cks);//"7e1e75fca78740bcb79c924ceaf69aa6";
			Object obj = UcMemCachedUtils.getCache("usercenter_key_" + userId);
			if (obj != null) {
				return JSON.parseObject(obj.toString(), UserCenter.class);
			}
		}
		return null;
	}
	
	public static boolean isLogin(HttpServletRequest request){
		Cookie[] cks = request.getCookies();
		if (null != cks && cks.length > 0) {
			String userId = WebTools.getCooKieValue("token", cks);//"7e1e75fca78740bcb79c924ceaf69aa6";
			Object obj = UcMemCachedUtils.getCache("usercenter_status_" + userId);
			if (obj != null && "true".equals(obj.toString())) {
				return true;
			}
		}
		return false;
	}
	
	public static String getUserId(HttpServletRequest request){
		Cookie[] cks = request.getCookies();
		if (null != cks && cks.length > 0) {
			String userId = WebTools.getCooKieValue("token", cks);//"7e1e75fca78740bcb79c924ceaf69aa6";
			Object obj = UcMemCachedUtils.getCache("usercenter_status_" + userId);
			if (obj != null && "true".equals(obj.toString())) {
				return userId;
			}
		}
		return null;
	}

}
