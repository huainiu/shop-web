package com.b5m.web.controller.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.b5m.common.env.Constants;
import com.b5m.common.log.B5MLogger;

/**
 * 控制器的基类
 * 
 * @author Wiley
 * 
 */
public abstract class AbstractBaseController {
	protected B5MLogger logger = new B5MLogger(AbstractBaseController.class.getName());

	private ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	private ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	private ThreadLocal<PrintWriter> out = new ThreadLocal<PrintWriter>();

	private MultipartHttpServletRequest multipartRequest = null;
	private boolean blMultipart = false;

	public AbstractBaseController() {
		super();
		logger = new B5MLogger(this.getClass().getName());
	}

	public final void _setServlet(HttpServletRequest request, HttpServletResponse response) {
		this.request.set(this.parseMultipart(request));// 自动解析是否有文件上传
		this.request.set(request);
		this.response.set(response);
		response.setCharacterEncoding(Constants.DEFAULT_ENCODING);
		response.setContentType(Constants.HTML_CONTENT_TYPE);
	}

	protected final boolean isMultipart() {
		return blMultipart;
	}

	/**
	 * 分析是否上传文件请求
	 * 
	 * @param request as HttpServletRequest
	 * @return HttpServletRequest
	 */
	protected final HttpServletRequest parseMultipart(HttpServletRequest request) {
		this.blMultipart = ServletFileUpload.isMultipartContent(request);
		if (!this.blMultipart) {
			try {
				request.setCharacterEncoding(Constants.DEFAULT_ENCODING);
			} catch (UnsupportedEncodingException uee) {
				logger.logError(uee, "parseMultipart");
			}
			return request;
		}
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding(Constants.DEFAULT_ENCODING);
		try {
			this.multipartRequest = resolver.resolveMultipart(request);
		} catch (MultipartException me) {
			logger.logError(me, "parseMultipart");
		}
		return this.multipartRequest;
	}

	protected final HttpServletRequest getMultipartRequest() {
		return (this.multipartRequest == null) ? this.getRequest() : this.multipartRequest;
	}

	/**
	 * 
	 * @return Iterator&lt;Input.name&gt;
	 */
	public final String[] getUploadNames(String prefix) {
		if (!this.blMultipart)
			return null;
		Iterator<String> ite = multipartRequest.getFileNames();// input.name
		List<String> names = new ArrayList<String>();
		String tmpString = null;
		while (ite.hasNext()) {
			tmpString = ite.next();
			if (tmpString.startsWith(prefix))
				names.add(tmpString);
		}
		return names.toArray(new String[names.size()]);
	}

	/**
	 * //根据getUploadNames()的得到文件对象
	 * 
	 * @param fname as String
	 * @return MultipartFile
	 */
	public final MultipartFile getUploadFile(String fname) {
		if (!this.blMultipart)
			return null;
		return this.multipartRequest.getFile(fname);
	}

	protected final String getString(String pName) {
		return _getParam(request.get(), pName);
	}

	protected final String getString(String pName, String defVal) {
		return _getParam(request.get(), pName, defVal);
	}

	private final String _getParam(HttpServletRequest pReq, String pName, String defVal) {
		String tmp = pReq.getParameter(pName);
		return StringUtils.isBlank(tmp) ? defVal : tmp;
	}

	private final String _getParam(HttpServletRequest pReq, String pName) {
		String tmp = "";
		if (StringUtils.isBlank(pName))
			return tmp;
		tmp = pReq.getParameter(pName);
		return StringUtils.isBlank(tmp) ? "" : tmp;
	}

	protected final int getInt(String pName) {
		int val = 0;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Integer.parseInt(tmp);
		} catch (Exception e) {
			val = 0;
		}
		return val;
	}

	protected final long getLong(String pName) {
		long val = 0;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Long.parseLong(tmp);
		} catch (Exception e) {
			val = 0;
		}
		return val;
	}

	// protected final Date getDate(String pName) {
	// String tmp=_getParam(request.get(), pName);
	// return null;
	// }

	protected final boolean getBoolean(String pName) {
		String tmp = _getParam(request.get(), pName);
		return (tmp.equalsIgnoreCase("true") || tmp.equalsIgnoreCase("y") || tmp.equalsIgnoreCase("1"));
	}

	protected final double getDouble(String pName) {
		double val = 0.0d;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Double.parseDouble(tmp);
		} catch (Exception e) {
			val = 0.0d;
		}
		return val;
	}

	protected final float getFloat(String pName) {
		float val = 0.0f;
		String tmp = _getParam(request.get(), pName);
		try {
			if (StringUtils.isNotBlank(tmp))
				val = Float.parseFloat(tmp);
		} catch (Exception e) {
			val = 0.0f;
		}
		return val;
	}

	protected final String[] getArrays(String pName) {
		String[] vals = null;
		vals = request.get().getParameterValues(pName);
		if (null == vals)
			vals = new String[] {};
		return vals;
	}

	protected final String getInputString() {
		String str = null;
		InputStream in = null;
		try {
			in = request.get().getInputStream();
			str = IOUtils.toString(in, request.get().getCharacterEncoding());
			in.close();
		} catch (IOException ioe) {
			logger.logError(ioe, "getInputString");
			str = null;
		} finally {
			IOUtils.closeQuietly(in);
		}
		return str;
	}

	/*************************/
	/*
	 * @RequestMapping() public abstract void DefaultAction(PrintWriter out);
	 * //public abstract void DefaultAction(ModelMap map)
	 * 
	 * @RequestMapping(params = "action=json") public abstract void
	 * JsonAction(PrintWriter out);
	 */
	protected final PrintWriter getOut() {
		if (this.out.get() == null) {
			try {
				this.out.set(this.response.get().getWriter());
			} catch (IOException ioe) {
				logger.logError(ioe, "getOut");
			}
		}
		return this.out.get();
	}

	protected final HttpServletRequest getRequest() {
		return this.request.get();
	}

	protected final HttpServletResponse getResponse() {
		return this.response.get();
	}
	
	protected final void output(Object val) throws Exception {
		output(0, "success", val);
	}
	
	protected final void outputError(Object val) throws Exception {
		output(-1, "failure", val);
	}
	
	protected final void output(int code, String msg, Object val) throws Exception {
		HttpServletResponse response = getResponse();
		HttpServletRequest request = getRequest();
		setExpires(response, null);
		response.setCharacterEncoding("UTF-8");
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		json.put("code", code);
		json.put("msg", msg);
		json.put("val", val);
		String jsonstr = json.toJSONString();
		String jsonCallback = request.getParameter("jsonCallback");
		if (!StringUtils.isEmpty(jsonCallback)) {
			response.setContentType("application/x-javascript");
			jsonstr = jsonCallback + "(" + jsonstr + ")";
		} else {
			response.setContentType("application/json");
		}
		String unicode = request.getParameter("unicode");
		byte[] bs = null;
		if (!StringUtils.isEmpty(unicode)) {
			bs = chinaToUnicode(jsonstr).getBytes();
		} else {
			bs = jsonstr.getBytes("UTF-8");
		}

		response.setContentLength(bs.length);
		response.getOutputStream().write(bs);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	protected final void outputVal(Object val) throws Exception {
		HttpServletResponse response = getResponse();
		HttpServletRequest request = getRequest();
		setExpires(response, null);
		response.setCharacterEncoding("UTF-8");
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		json.put("val", val);
		String jsonstr = JSON.toJSONString(val, SerializerFeature.DisableCircularReferenceDetect);
		String jsonCallback = request.getParameter("jsonCallback");
		if (!StringUtils.isEmpty(jsonCallback)) {
			response.setContentType("application/x-javascript");
			jsonstr = jsonCallback + "(" + jsonstr + ")";
		} else {
			response.setContentType("application/json");
		}
		String unicode = request.getParameter("unicode");
		byte[] bs = null;
		if (!StringUtils.isEmpty(unicode)) {
			bs = chinaToUnicode(jsonstr).getBytes();
		} else {
			bs = jsonstr.getBytes("UTF-8");
		}

		response.setContentLength(bs.length);
		response.getOutputStream().write(bs);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	protected final void output(HttpServletResponse response, HttpServletRequest request, int code, String msg, Object val, boolean isAjax) throws Exception {
		if (isAjax) {
			setExpires(response, null);
			response.setCharacterEncoding("UTF-8");
			com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
			json.put("code", code);
			json.put("msg", msg);
			json.put("val", val);
			String jsonstr = json.toString();
			response.setContentType("application/json");
			byte[] bs = jsonstr.getBytes("UTF-8");
			response.setContentLength(bs.length);
			response.getOutputStream().write(bs);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} else {
			PrintWriter out = response.getWriter();
			out.write("<script>");
			out.write("alert('" + msg + "');");
			out.write("window.location.href='" + request.getHeader("referer")+"';");
			out.write("</script>");
			out.flush();
			out.close();
		}
	}

	private String chinaToUnicode(String str) {
		StringBuilder result = new StringBuilder(2000);
		int length = str.length();
		for (int i = 0; i < length; i++) {
			int chr1 = str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result.append("\\u").append(Integer.toHexString(chr1));
			} else {
				result.append(chr1);
			}
		}
		return result.toString();
	}

	private void setExpires(HttpServletResponse response, Integer expiresTime) {
		if (expiresTime != null && expiresTime > 0) {
			long now = System.currentTimeMillis();
			response.setHeader("Cache-Control", "max-age=" + expiresTime);
			response.setHeader("Cache-Control", "must-revalidate");
			response.setDateHeader("Last-Modified", now);
			response.setDateHeader("Expires", now + expiresTime * 1000);
		} else {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
		}
	}
}
