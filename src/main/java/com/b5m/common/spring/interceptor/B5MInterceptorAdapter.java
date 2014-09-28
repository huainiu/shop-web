package com.b5m.common.spring.interceptor;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.common.utils.ContextUtils;
import com.b5m.common.utils.UcMemCachedUtils;
import com.b5m.web.controller.base.AbstractBaseController;

public class B5MInterceptorAdapter extends HandlerInterceptorAdapter {
	
	private List<String> includeAction;
	
	private static final Log LOG = LogFactory.getLog(B5MInterceptorAdapter.class);
	// SSO用到的用户id 长度为32
	private static String USER_ID_COOKIE = "token";
	// SSO用到的用户名称
	// private static String USER_NAME_COOKIE = "name";
	// SSO用到的登录状态
	private static String LOGIN_FLAG_COOKIE = "login";
	// 登陆保存的email(代替name)
	private static String LOGIN_EMAIL_COOKIE = "showname";
	// SSO用到的登录状态
	private static String STATUS_OK = "true";
	// SSO用到的非登录状态
	private static String STATUS_OFF = "false";
	// SSO用到的userId 长度默认32 位
	private static int USER_ID_LENGTH = 32;
	// SSO用到的memcache 缓存 前缀
	private static String USER_STATUS_PREFIX = "usercenter_status_";
	// SSO用到的memcache user 缓存 前缀
	public static String USER_KEY_PREFIX = "usercenter_key_";
	// SSO用到的session 存储的key
	private static String USER_SESSION_USER_ID = "user_session_key_id";
	// private static String USER_SESSION_USER_NAME = "user_session_name_id";
	// session中标记邮箱名
	private static String USER_SESSION_USER_EMAIL = "user_session_email_id";
	// session中标示用户登陆状态
	static String USER_SESSION_FLAG = "user_session_login";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		ContextUtils.getInstance()._setServlet(request, response);
		
		if (handler != null && handler instanceof AbstractBaseController) {
			// 注入全部的request和response对象
			((AbstractBaseController) handler)._setServlet(request, response);
		}
		String uri=request.getRequestURI();
		String path=request.getContextPath();
		String controllerUri=uri.substring(path.length());
		if(!includeAction.contains(controllerUri)){
			return true;
		}
		try {
			// 过滤cookie memcache
			Cookie[] cks = request.getCookies();
			// 先后从cookie，memcache userId 和 isLogin 判断 用户 是否登陆
			if (null != cks && cks.length > 0) {
				String userId = WebTools.getCooKieValue(USER_ID_COOKIE, cks);
				String isLogin = WebTools.getCooKieValue(LOGIN_FLAG_COOKIE, cks);
				String email = WebTools.getCooKieValue(LOGIN_EMAIL_COOKIE, cks);
				if (null != userId && null != isLogin) {
					if (isLogin.equals(STATUS_OK) && userId.length() == USER_ID_LENGTH && STATUS_OK.equals(UcMemCachedUtils.getCache(USER_STATUS_PREFIX + userId))) {
						request.setAttribute(USER_SESSION_USER_ID, userId);
						request.setAttribute(USER_SESSION_USER_EMAIL, email);
						request.setAttribute(USER_SESSION_FLAG, STATUS_OK);
						/*// ID
						WebUtils.setSessionAttribute(request, USER_SESSION_USER_ID, userId);
						// email作为用户名显示
						WebUtils.setSessionAttribute(request, USER_SESSION_USER_EMAIL, email);
						// 用户登陆状态
						WebUtils.setSessionAttribute(request, USER_SESSION_FLAG, STATUS_OK);*/
						LOG.debug("successed login for userId[" + userId + "], isLogin[" + isLogin + "],showname[" + email + "]");
					} else {
						//WebUtils.setSessionAttribute(request, USER_SESSION_FLAG, STATUS_OFF);
						LOG.debug("failed login for userId[" + userId + "], isLogin[" + isLogin + "],showname[" + email + "]");
					}
				} else {
					//WebUtils.setSessionAttribute(request, USER_SESSION_FLAG, STATUS_OFF);
					LOG.debug("failed login for userId[" + userId + "], isLogin[" + isLogin + "],showname[" + email + "]");
				}
			}

		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("B5MInterceptorAdapter preHandle exception: message is [" + e.getMessage() + "]");
			}
		}
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		if (null == modelAndView)
			return;
		try {
			String today = ContextUtils.getInstance().getToday();
			Object o = UcMemCachedUtils.getCache("v_version");
			if(o != null){
				if (Long.parseLong(today) <  Long.parseLong(o.toString())){
					today = o.toString();
				}
			}else{
				today = DateTools.formate(DateTools.now(), "yyyyMMddHHmmss");
				UcMemCachedUtils.setCache("v_version", today, 864000);
			}
			request.setAttribute("today", today);
			super.postHandle(request, response, handler, modelAndView);
		} catch (Exception e) {
			if (LOG.isErrorEnabled()) {
				LOG.error("B5MInterceptorAdapter postHandle exception: message is [" + e.getMessage() + "]");
			}
		}
	}

	public List<String> getIncludeAction() {
		return includeAction;
	}

	public void setIncludeAction(List<String> includeAction) {
		this.includeAction = includeAction;
	}
	
	

}
