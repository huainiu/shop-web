package com.b5m.common.utils.shoplist;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UrlSpecialHandler {
	
	public static Map<String, String> rules = new HashMap<String, String>();
	
	public static Map<String, String> outbounds = new HashMap<String, String>();
	
	static{
		rules.put("+", "%2B");
		
		outbounds.put("%2B", "+");
	}
	
	public static String filterUrl(String url){
		/*Set<String> ruleKeys = rules.keySet();
		String temp = url;
		for(String ruleKey : ruleKeys){
			if(temp.contains(ruleKey)){
				temp = temp.replaceAll(ruleKey, rules.get(ruleKey));
			}
		}
		return temp;*/
		if(!url.contains("+"))
			return url;
		return url.replaceAll("\\+", "%2B");
	}
	
	public static String replaceValue(String value){
		Set<String> outboundKeys = outbounds.keySet();
		String temp = value;
		for(String outboundKey : outboundKeys){
			if(temp.contains(outboundKey)){
				temp = temp.replaceAll(outboundKey, outbounds.get(outboundKey));
			}
		}
		return temp;
	}

}
