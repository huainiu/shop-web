package com.b5m.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.b5m.base.common.utils.UrlTools;
import com.b5m.bean.entity.SwitchKeywords;
import com.b5m.bean.entity.SwitchType;
import com.b5m.common.utils.SpringContextUtils;
import com.b5m.dao.Dao;

public class RedirectSearchFilter implements Filter {

	private Map<String, String> map = new HashMap<String, String>();

	private Dao dao;
	private long refreshTime = System.currentTimeMillis();
	private final long OUT_TIME = 30 * 60 * 1000;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		getMap();
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI().replaceFirst("/", "");
		StringBuilder sb = new StringBuilder();
		int port = req.getServerPort();
		sb.append(req.getScheme()).append("://").append(req.getServerName()).append(port == 80 ? "" : ":" + port).append(req.getContextPath()).append("/");
		String prefix = sb.toString();

		int _count = sum(uri);
		String symbol = "/";
		if (_count > 0) {
			symbol = "_";
		}
		int symbolIndex = uri.lastIndexOf(symbol);
		String orig_keyword = uri.substring(symbolIndex < 0 ? 0 : symbolIndex+1, uri.lastIndexOf("."));
		String keyword = UrlTools.urlDecode(orig_keyword);
		String map_collection = map.get(keyword);

		int firstSlashIndex = uri.indexOf("/");
		String collection = "";
		if (firstSlashIndex > 0) {
			collection = uri.substring(0, uri.indexOf("/"));
		}

		if (map_collection != null) {
			if (map_collection.equals(collection)) {
				filterChain.doFilter(request, response);
				return;
			} else {
				resp.sendRedirect(new StringBuilder().append(prefix).append(map_collection).append("/").append(orig_keyword).append(".html").toString());
				return;
			}
		} else {
			if (map.containsValue(collection)) {
				resp.sendRedirect(new StringBuilder().append(prefix).append(orig_keyword).append(".html").toString());
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	private Map<String, String> getMap() {
		if ((System.currentTimeMillis() - refreshTime) >= OUT_TIME) {
			map.clear();
		}
		if (map.isEmpty()) {
			dao = SpringContextUtils.getBean("dao", Dao.class);
			List<SwitchKeywords> list = dao.queryAll(SwitchKeywords.class);
			for (int i = 0; i < list.size(); i++) {
				SwitchKeywords sk = list.get(i);
				map.put(sk.getName(), sk.getTypeCode());
			}
			refreshTime = System.currentTimeMillis();
		}
		return map;
	}

	public boolean isCollection(String str) {
		for (SwitchType type : SwitchType.values()) {
			if (type.getValue().equals(str))
				return true;
		}
		return false;
	}

	public int sum(String str) {
		int num = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == 95) {// 95是下划线
				num++;
			}
		}
		return num;
	}

}
