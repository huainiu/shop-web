package com.b5m.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.b5m.base.common.Lang;
import com.b5m.base.common.utils.StringTools;
/**
 * description 
 *
 * @Company b5m
 * @author echo
 * @since 2013-5-27
 */
public class UrlReloadFilter implements Filter{
	private static final String[] params = {"categoryValue","sortField","sortType","showType","sourceValue","brandValue","attrNames","attrValue","log_group_label","priceFrom","priceTo","splServiceValue","searchType","currPageNo","filterField","kaviFilter","isFreeDelivery","isCOD","isGenuine","keyWord"};

    @Override
    public void destroy(){
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException{
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURL().toString();
        
        String url = needRedirect(req);
        if(!StringUtils.isEmpty(url)){
        	res.setStatus(301);
    		res.setHeader("Location", url); 
    		res.setHeader("Connection", "close");
    		return;
        }
        
        String pathVar = "search/s/";
        if (path.indexOf("/clothing/") > 0)
        	pathVar = "clothing/";
        if(path.endsWith("html") && sum(path) > 0 && sum(path) == 16){
        	String prefix = path.substring(0, path.indexOf("_"));
        	String suffix = path.substring(path.lastIndexOf("_") + 1);
        	if(suffix.indexOf("/") > 0){	
        		String newPath = prefix + pathVar + "___________________" + suffix;
        		res.setStatus(301);
                res.setHeader("Location", newPath); 
             	res.setHeader("Connection", "close");
        	}else{
        		String newPath = prefix + suffix;
        		res.setStatus(301);
                res.setHeader("Location", StringTools.replace(newPath, pathVar, "")); 
             	res.setHeader("Connection", "close");
        	}
        }else if(sum(path) == 19 && !noCache(req)){
        	if(StringTools.split(path, pathVar)[1].indexOf("/") > 0){
        		filterChain.doFilter(req, res);
        	}else{
        		String newPath = path;
        		StringBuilder sb = new StringBuilder();
            	String[] splits = StringTools.split(path, pathVar);
            	String prefix = splits[0];
            	String paramStr = splits[1];
            	sb.append(prefix);
            	paramStr = StringTools.split(paramStr, ".html")[0];
            	String[] paramValues = paramStr.split("_", 20);
            	boolean change = true;
            	for(int i = 0; i< params.length; i++){
            		if(!StringTools.isEmpty(paramValues[i]) && (i==1 || i==2 || i==4 || i==5 || i==9 || i==10 || i==12 || i==13 || i==14 || i==16 || i==17 || i==18)){
            			change = false;
            			break;
            		}
            	}
            	if(StringTools.isEmpty(paramValues[19])){
            		filterChain.doFilter(req, res);
            		return;
            	}
            	if(change){
            		String category = paramValues[0];
            		if(!StringTools.isEmpty(category)){
            			if(category.indexOf("/") > 0){
            				category = StringTools.replace(category, "/", "^");
            			}
            			sb.append(category);
            			sb.append("/");
            		}
            		//添加keyword   http://localhost/keyword.html
            		sb.append(paramValues[19]);
            		sb.append(".html");
            		newPath = sb.toString();
            		res.setStatus(301);
            		res.setHeader("Location", newPath); 
            		res.setHeader("Connection", "close");
            	}else{
            		filterChain.doFilter(req, res);
            	}
        	}
        }else{
        	filterChain.doFilter(req, res);
        }
    }
    
    public String needRedirect(HttpServletRequest req){
		String requestUrl = req.getRequestURL().toString();
		if(requestUrl.indexOf("item/_") > 0){
			return StringUtils.replace(requestUrl, "item/_", "item/");
		}
		if(requestUrl.indexOf("item/") > 0 && requestUrl.charAt(requestUrl.length() - 7) == '_'){
			return requestUrl.substring(0, requestUrl.length() - 7) + ".html";
		}
		return "";
	}
    
    protected boolean noCache(HttpServletRequest req){
    	String compareParam = req.getParameter("compare");
    	String refine = req.getParameter("refine");
    	if(!Lang.isEmpty(refine)) return true;
    	if(!Lang.isEmpty(compareParam) && "true".equals(compareParam)) return true;
    	return false;
    }
    
    public int sum(String str){
        int num = 0;
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == 95){//95是下划线
                num++;
            }
        }
        return num;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException{
    }
    
    
}
