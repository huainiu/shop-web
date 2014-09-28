package com.b5m.service.goodsdetail;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.b5m.base.common.utils.UrlTools;
import com.b5m.base.common.utils.WebTools;

public class GoodsUtils {
	
	   /*public static String redirectUrl(String url){
		  return "http://www.b5m.com/go.php?sid=s&dest=" + UrlTools.urlEncode(url);
	   }*/
	
     public static String redirectUrl(Cookie[] cks, String docId, String url){//1016
    	 String uuid = "";
    	 if(cks != null){
    		 uuid = WebTools.getCooKieValue("token", cks);
    		 if(StringUtils.isEmpty(uuid)){
    			 WebTools.getCooKieValue("cookieId", cks);
    		 }
    	 }
         return  "http://clicks2.b5m.com/redirect/_c.do?sid=1016&ssub=LV1&uuid=" + uuid + "&docid=" + docId + "&dest=" + UrlTools.urlEncode(url);
     }
     
     public static void main(String[] args) {
		String str = "{\"s\":\"B\"}";
		System.out.println(str);
	}
}
