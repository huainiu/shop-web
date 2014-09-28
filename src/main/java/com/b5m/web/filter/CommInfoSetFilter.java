package com.b5m.web.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.entity.SearchHotKey;
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.env.GlobalWebCfg;
import com.b5m.common.utils.ConfigPropUtils;

/**
 * description
 * 
 * @Company b5m
 * @author echo
 * @since 2013-5-27
 */
public class CommInfoSetFilter implements Filter {

	private final static Log LOG = LogFactory.getLog(CommInfoSetFilter.class);

	private final static String DEFAULT_COMMINFO_PATH = "classpath:PageCommInfo.properties";

	public static Properties properties = null;

	public static final String HOTKEY = "hotkey";

	public String prefix = "";

	public List<SearchHotKey> keyList = new ArrayList<SearchHotKey>();
	
	public List<String> includeAction = new ArrayList<String>();
	
	private String[] abs = new String[]{"A","B"};
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;// req.getRequestURI()/index.htm
		HttpServletResponse res = (HttpServletResponse) response;
		// 设置basepath
		setBasePath(req);

		// 设置用户中心url
		setUcenterHttpUrl(req);

		setCommInfo(req);

		setNavType(req, res);

//		setHotkey(req);

		filterChain.doFilter(req, res);

		//setAbTest(req, res);
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		includeAction.add("compare/");
		includeAction.add("item/");
		includeAction.add("clothing/");
		includeAction.add("search/s/");
		
		String commInfoProPath = arg0.getInitParameter("CommInfoProPath");
		if (StringUtils.isEmpty(commInfoProPath)) {
			commInfoProPath = DEFAULT_COMMINFO_PATH;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("---------------------start init properties file[" + commInfoProPath + "]------------------");
		}
		properties = new Properties();
		InputStream inputStream = null;
		if (commInfoProPath.startsWith("classpath:")) {
			inputStream = CommInfoSetFilter.class.getClassLoader().getResourceAsStream(commInfoProPath.substring("classpath:".length()));
		} else {
			String filePath = arg0.getServletContext().getRealPath("/") + commInfoProPath;
			try {
				inputStream = new FileInputStream(filePath);
			} catch (FileNotFoundException e) {
				throw new ServletException("file:[" + commInfoProPath + "] not exists");
			}
		}
		try {
			properties.load(inputStream);
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.debug("---------------------init exception exception type is[" + e.getClass().getSimpleName() + "], error message is [" + e.getMessage() + "]-------------------------");
			}
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("---------------------finished init-------------------------");
		}
	}

	private void setCommInfo(HttpServletRequest req) {
		if (properties != null) {
			for (Entry<Object, Object> entry : properties.entrySet()) {
				if (entry.getKey() != null) {
					req.setAttribute(entry.getKey().toString(), entry.getValue());
				}
			}
		}
		req.setAttribute("adRecordUrl", GlobalInfo.getStr("adRecordUrl"));
	}

	private void setNavType(HttpServletRequest req, HttpServletResponse res) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			req.setAttribute("cookieId", WebTools.getCooKieValue("cookieId", cookies));
		}
	}
	
	/*private void setAbTest(HttpServletRequest req, HttpServletResponse res) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			String abstr = WebTools.getCooKieValue("_b5mab", cookies);
			if(StringUtils.isEmpty(abstr)){
				JSONObject jsonObject = new JSONObject();
				setAbtestCookie(jsonObject, cookies, req, res);
			}else{
				JSONObject jsonObject = JSONObject.parseObject(abstr);
				String ab = jsonObject.getString("s");
				if(StringUtils.isEmpty(ab)){
					setAbtestCookie(jsonObject, cookies, req, res);
				}
			}
			req.setAttribute("cookieId", WebTools.getCooKieValue("cookieId", cookies));
		}
	}
	
	private void setAbtestCookie(JSONObject jsonObject, Cookie[] cookies, HttpServletRequest req, HttpServletResponse res){
		jsonObject.put("s", abs[new Random().nextInt(2)]);
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
    		cookie = new Cookie("_b5mab", jsonObject.toJSONString());
    	}
    	cookie.setDomain(getDomain(req));
    	cookie.setPath("/");
    	cookie.setValue("kafjkvjafklv");
    	cookie.setMaxAge(25920);//3个月
    	res.addCookie(cookie);
	}*/

	private void setBasePath(HttpServletRequest req) {
		req.setAttribute("suffixServer", getSuffixServer(req));
	}
	
	private String getSuffixServer(HttpServletRequest req){
		String server = req.getServerName();
		String suffixServer = "b5m.com";
		if (server.indexOf("prod") >= 0) {
			suffixServer = "prod.bang5mai.com";
		} else if (server.indexOf("stage") >= 0) {
			suffixServer = "stage.bang5mai.com";
		} else if (server.indexOf("b5m") < 0) {
			suffixServer = "stage.bang5mai.com";
		}
		return suffixServer;
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

	private void setUcenterHttpUrl(HttpServletRequest req) {
		req.setAttribute("loginAndRegisterURL", GlobalWebCfg.getUcenterHttpUrl(req));
		req.setAttribute("paysuccessurl", ConfigPropUtils.getUcenterUrl("ucenter.pay.success.url"));
	}

}
