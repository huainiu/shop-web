package com.b5m.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.WebTools;
import com.b5m.common.env.GlobalInfo;
/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-10-22
 * @email wuming@b5m.com
 */
public class RedirectFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*String b5mServer = GlobalInfo.getStr("LOCAL_SERVER");
		if(StringUtils.isEmpty(b5mServer)) b5mServer = "search.b5m.com";
		
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(!StringUtils.isEmpty(getUtmSource(req))){
        	saveOrModifyCookie(req, res, b5mServer);
        	chain.doFilter(request, response);
        	return;
        }
        Cookie[] cks = req.getCookies();
        String server = req.getParameter("server");
        if(cks != null && StringUtils.isEmpty(server)){
        	server = WebTools.getCooKieValue("_newserver_", cks);
        }
        String oldServer = GlobalInfo.getStr("OLD_SERVER");
        String newServer = GlobalInfo.getStr("NEW_SERVER");
        
        int percent = getPercent();
        if(req.getRequestURI().indexOf("search/s/") < 0){
        	chain.doFilter(request, response);
        }else{
        	if(StringUtils.isEmpty(server) || "null".equals(server)){
        		int hashCode = HashCodeBuilder.reflectionHashCode(UUID.randomUUID().toString());
    			hashCode = hashCode > 0 ? hashCode : -hashCode;
    			if(hashCode % 10 > (percent - 1)){
    				server = oldServer;
    			}else{
    				server = newServer;
    			}
        	}
        	saveOrModifyCookie(req, res, server);
    		if(b5mServer.equals(server)){
    			chain.doFilter(request, response);
    		}else{
    			res.sendRedirect(path(server, req));
    		}
        }*/
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Cookie[] cks = req.getCookies();
        String abstr = WebTools.getCooKieValue("_b5mab", cks);
		if(StringUtils.isEmpty(abstr)){
			JSONObject jsonObject = new JSONObject();
			saveOrModifyCookie(req, res, getDomain(req), jsonObject);
		}else{
			abstr = URLDecoder.decode(abstr, "UTF-8");
			JSONObject jsonObject = JSONObject.parseObject(abstr);
			String ab = jsonObject.getString("s");
			if(StringUtils.isEmpty(ab)){
				saveOrModifyCookie(req, res, getDomain(req), jsonObject);
			}
		}
    	chain.doFilter(request, response);
	}
	
	private void saveOrModifyCookie(HttpServletRequest req, HttpServletResponse res, String domain, JSONObject jsonObject) throws UnsupportedEncodingException{
		int percent = getPercent();
        String value = "B";
        int hashCode = HashCodeBuilder.reflectionHashCode(UUID.randomUUID().toString());
		hashCode = hashCode > 0 ? hashCode : -hashCode;
		if(hashCode % 10 > (percent - 1)){
			value = "A";
		}
		jsonObject.put("s", value);
		Cookie cookie = null;
		Cookie[] cks = req.getCookies();
		if(cks != null){
			for(Cookie item : cks) {
				if("_b5mab".equals(item.getName())){
					cookie = item;
					break;
				}
			}
		}
    	if(cookie == null) {
    		cookie = new Cookie("_b5mab", URLEncoder.encode(jsonObject.toJSONString(), "UTF-8"));
    	}
    	cookie.setDomain(domain);
    	cookie.setValue(URLEncoder.encode(jsonObject.toJSONString(), "UTF-8"));
    	cookie.setMaxAge(-1);//3个月
    	res.addCookie(cookie);
	}
	
	private String getDomain(HttpServletRequest req){
		String server = req.getServerName();
		String suffixServer = ".b5m.com";
		if (server.indexOf("prod") >= 0) {
			suffixServer = ".bang5mai.com";
		} else if (server.indexOf("stage") >= 0) {
			suffixServer = ".bang5mai.com";
		} else if (server.indexOf("b5m") < 0) {
			suffixServer = ".bang5mai.com";
		}
		return suffixServer;
	}
	
	private String path(String server, HttpServletRequest request){
		StringBuilder sb = new StringBuilder();
		int port = request.getServerPort();
		sb.append(request.getScheme()).append("://").append(server).append(":").append(port == 80 ? "" : port).append(request.getContextPath()).append(request.getRequestURI());
		return sb.toString();
	}
	
	public String getUtmSource(HttpServletRequest req){
		return req.getParameter("utm_source");
	}
	
	public int getPercent(){
		Integer percent = GlobalInfo.getInt("APTEST_PERCENT");
		if(percent == null) percent = 5;
		return percent;
	}

	@Override
	public void destroy() {
		
	}

}
