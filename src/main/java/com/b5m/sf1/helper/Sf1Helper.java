package com.b5m.sf1.helper;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.Constants;
import com.b5m.base.common.Lang;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.sf1.dto.req.AttrSearchBean;
import com.b5m.sf1.dto.req.CondSearchBean;
import com.b5m.sf1.dto.req.GetAttr;
import com.b5m.sf1.dto.req.GroupBean;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.dto.req.SelectSearchBean;
import com.b5m.sf1.dto.req.SortSearchBean;
import com.b5m.sf1.log.Sf1Log;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-11-22
 * @email wuming@b5m.com
 */
public class Sf1Helper {
	public static final Log LOG = LogFactory.getLog(Sf1Helper.class);
	public static final String CONTEXT_COLLECTION = "b5mo";

	@SuppressWarnings("finally")
	public static JSONObject doSearch(String url, String jsonString) {
		Sf1Log.info(new StringBuilder("请求地址").append(url).toString() + "\n" + new StringBuilder("发送请求").append(jsonString).toString());
		LOG.info(new StringBuilder("请求地址").append(url).toString());
		LOG.info(new StringBuilder("发送请求").append(jsonString).toString());
		long timeStart = System.currentTimeMillis();
		JSONObject resultObj = null;
		try {
			String resultMsg = doInternalSearch(url, jsonString);
			resultObj = JSONObject.parseObject(resultMsg);
			resultObj.put("requestJson", jsonString);
			if (!isQuerySuccess(resultObj)) {
				String errMsg = getErrMsgFromJson(resultObj);
				Sf1Log.info("请求失败 : " + errMsg + " ---   request : " + jsonString);
				throw new RuntimeException(errMsg);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			long timeEnd = System.currentTimeMillis();
			double sf1rTime = Sf1Helper.getSpeedTime(resultObj);
			Sf1Log.info(new StringBuilder("---请求花费时间,sf1r内部查询时间:").append(timeEnd - timeStart).append("毫秒,").append(sf1rTime * 1000).append("毫秒.url:").append(url).toString());
			LOG.info(new StringBuilder("---请求花费时间,sf1r内部查询时间:").append(timeEnd - timeStart).append("毫秒,").append(sf1rTime * 1000).append("毫秒.url:").append(url).toString());
		}
		return resultObj;
	}

	public static String buildGetJson(SF1SearchBean bean) {
		JSONObject jsonObject = new JSONObject();
		buildCondition(jsonObject, bean);
		buildSelect(jsonObject, bean);
		if (StringTools.isEmpty(bean.getKeywords())) {
			jsonObject.put("search_session", new JSONObject().put("search_session", bean.getKeywords()));
		}
		jsonObject.put("collection", bean.getCollection());
		buildHeader(jsonObject, bean);
		return jsonObject.toJSONString();
	}

	public static String buildSearchCountJson(SF1SearchBean bean) {
		JSONObject jsonObject = new JSONObject();
		// 查询总数 就将limit和offset都设置到最小
		bean.setLimit(1);
		bean.setOffset(0);
		buildJsonLimit(jsonObject, bean);
		buildCondition(jsonObject, bean);
		buildSearch(jsonObject, bean);
		jsonObject.put("analyzer_result", true);
		jsonObject.put("remove_duplicated_result", false);
		jsonObject.put("collection", bean.getCollection());
		buildHeader(jsonObject, bean);
		return jsonObject.toJSONString();
	}

	public static String buildForecastJson(JSONArray forecastString) {
		JSONObject jsonObject = new JSONObject();
		// 查询总数 就将limit和offset都设置到最小
		jsonObject.put("collection", "aggregator");
		jsonObject.put("price_history", forecastString);
		return jsonObject.toJSONString();
	}

	public static String buildSearchJson(SF1SearchBean bean) {
		JSONObject jsonObject = new JSONObject();
		buildJsonLimit(jsonObject, bean);
		buildCondition(jsonObject, bean);
		buildSelect(jsonObject, bean);
		buildJsonOfSort(jsonObject, bean);
		buildSearch(jsonObject, bean);
		jsonObject.put("analyzer_result", true);
		buildJsonGetAttr(jsonObject, bean);
		jsonObject.put("remove_duplicated_result", false);
		jsonObject.put("collection", bean.getCollection());
		buildJsonGroup(jsonObject, bean);
		buildHeader(jsonObject, bean);
		return jsonObject.toJSONString();
	}

	public static JSONObject buildSummarizationJson(String collection, String docid) {
		JSONObject summarizationJson = new JSONObject();
		buildJsonOfHeader(summarizationJson);
		summarizationJson.put("collection", collection);
		JSONObject resource = new JSONObject();
		resource.put("DOCID", docid);
		summarizationJson.put("resource", resource);
		return summarizationJson;
	}

	/**
	 * 生成用于字符纠正的json
	 * 
	 * @return 字符纠正的json
	 */
	public static String buildRefinedJson(String collection, String keyword) {
		JSONObject ivrJSON = new JSONObject();
		ivrJSON.put("keywords", keyword);
		ivrJSON.put("collection", collection);
		buildJsonOfHeader(ivrJSON);
		return ivrJSON.toJSONString();
	}

	/**
	 * 通过结果json，判断请求是否成果
	 * 
	 * @param 结果Json
	 * @return true|false 成功返回true
	 */
	protected static boolean isQuerySuccess(JSONObject resultJsonObj) {
		boolean isSuccess = false;
		if ("true".equals(resultJsonObj.getJSONObject("header").getString("success"))) {
			isSuccess = true;
		}
		return isSuccess;
	}

	/**
	 * 从结果Json中获取错误信息
	 * 
	 * @param 结果Json
	 * @return 错误信息
	 */
	protected static String getErrMsgFromJson(JSONObject resultJsonObj) {
		String errMsg = resultJsonObj.getJSONArray("errors").toString();
		return errMsg;
	}

	protected static String doInternalSearch(String url, String jsonString) {
		PostMethod method = null;
		// 创建请求方法
		try {
			MonitorUtils.latencyCollecterBegin();
			HttpClient httpClient = HttpClientFactory.getHttpClient();
			method = createPostMethod(url, jsonString.toString());
			// 执行请求
			int statusCode = httpClient.executeMethod(method);
			httpClient.getParams().setSoTimeout(5000);
			httpClient.getParams().setConnectionManagerTimeout(5000);
			if (statusCode == HttpStatus.SC_OK) {
				// 获得返回串
				String resultMsg = method.getResponseBodyAsString().trim();
				LOG.debug(new StringBuilder("收到返回").append(resultMsg).toString());
				return resultMsg;
			} else {
				throw new RuntimeException("Connect fail, url:" + url + " code:" + statusCode);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (HttpException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				MonitorUtils.latencyCollecterCommit();
			} catch (Exception e) {
			}
			if (null != method) {
				method.releaseConnection();
			}
		}

	}

	protected static PostMethod createPostMethod(String URL, String content) throws UnsupportedEncodingException {
		PostMethod method = new PostMethod(URL);
		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, Constants.DEFAULT_ENCODING);
		method.getParams().setParameter(HttpMethodParams.BUFFER_WARN_TRIGGER_LIMIT, 5242880);// 警报限制设置为5M
		method.setRequestEntity(new StringRequestEntity(content, "application/json", Constants.DEFAULT_ENCODING));
		method.setRequestHeader("Connection", "Keep-Alive");
		return method;
	}

	public static class HttpClientFactory {
		private final static ThreadLocal<HttpClient> threadLocal = new ThreadLocal<HttpClient>();
		private final static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		static {
			connectionManager.getParams().setConnectionTimeout(5000);
			connectionManager.getParams().setSoTimeout(5000);
			// 最大连接主机数
			connectionManager.getParams().setDefaultMaxConnectionsPerHost(1000);
			// 最大活动连接数
			connectionManager.getParams().setMaxTotalConnections(1000);
		}

		/**
		 * 获得httpCilent 每个请求（request）都会取得同样的httpclient,不同请求会获得不同的httpclient
		 * 
		 * @return
		 */
		public static HttpClient getHttpClient() {
			HttpClient httpClient = (HttpClient) threadLocal.get();
			if (httpClient == null) {
				httpClient = new HttpClient(connectionManager);
				threadLocal.set(httpClient);
			}
			return httpClient;
		}
	}

	protected static void buildSelect(JSONObject jsonObject, SF1SearchBean bean) {
		List<SelectSearchBean> selectList = bean.getSelectList();
		if (selectList.isEmpty())
			return;
		JSONArray jsonArray = new JSONArray();
		for (SelectSearchBean selectSearchBean : selectList) {
			JSONObject select = new JSONObject();
			select.put("property", selectSearchBean.getScdName());
			jsonArray.add(select);
		}
		jsonObject.put("select", jsonArray);
	}

	protected static void buildJsonGroup(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		List<GroupBean> groupBeans = sf1SearchBean.getGroupBeans();
		if (groupBeans.isEmpty())
			return;
		JSONArray jsonArray = new JSONArray();
		for (GroupBean groupBean : groupBeans) {
			JSONObject group = new JSONObject();
			group.put("property", groupBean.getProperty());
			group.put("range", groupBean.isRange());
			jsonArray.add(group);
		}
		jsonObject.put("group", jsonArray);
	}

	protected static void buildJsonGetAttr(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		GetAttr getAttr = sf1SearchBean.getGetAttr();
		if (getAttr == null)
			return;
		JSONObject attr = new JSONObject();
		attr.put("attr_result", getAttr.isGetAttr());
		if (getAttr.isGetAttr()) {
			attr.put("attr_top", getAttr.getTop());
		}
		jsonObject.put("attr", attr);
	}

	protected static void buildJsonLimit(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		jsonObject.put("limit", sf1SearchBean.getLimit());
		jsonObject.put("offset", sf1SearchBean.getOffset());
	}

	protected static void buildSearch(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		JSONObject jsonSearch = new JSONObject();
		buildGroupLabel(jsonSearch, sf1SearchBean);
		buildAttr(jsonSearch, sf1SearchBean);
		jsonSearch.put("keywords", sf1SearchBean.getKeywords());
		jsonSearch.put("ranking_model", "bm25");
		jsonSearch.put("is_require_related", sf1SearchBean.isRequireRelated());
		jsonSearch.put("log_keywords", sf1SearchBean.isLogKeywords());
		if (sf1SearchBean.getQueryAbbreviation() != null) {
			jsonSearch.put("query_abbreviation", sf1SearchBean.getQueryAbbreviation());
		}
		buildAnalyzer(jsonSearch, sf1SearchBean);
		buildIn(jsonSearch, sf1SearchBean);
		buildMode(jsonSearch, sf1SearchBean);
		jsonObject.put("search", jsonSearch);
	}

	/*
	 * protected static void buildStartWith(JSONArray cnds, SF1SearchBean
	 * sf1SearchBean){ JSONObject jsonObject = new JSONObject(); JSONArray
	 * values = new JSONArray(); values.add(sf1SearchBean.getCategory());
	 * jsonObject.put("value", values); jsonObject.put("property", "Category");
	 * jsonObject.put("operator", "starts_with"); cnds.add(jsonObject); }
	 */

	protected static void buildGroupLabel(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		JSONArray jsonArray = new JSONArray();
		String category = sf1SearchBean.getCategory();
		if (!Lang.isEmpty(category)) {
			JSONObject categoryJson = new JSONObject();
			String[] cs = StringTools.split(category, ">");
			JSONArray array = new JSONArray();
			array.addAll(Lang.newList(cs));
			categoryJson.put("value", array);
			categoryJson.put("property", "Category");
			jsonArray.add(categoryJson);
		}
		if (!StringTools.isEmpty(sf1SearchBean.getSources())) {
			String[] sources = StringTools.split(sf1SearchBean.getSources(), ",");
			for (String source : sources) {
				JSONObject sourceObject = new JSONObject();
				JSONArray sourceArray = new JSONArray();
				sourceArray.addAll(CollectionTools.newList(source));
				sourceObject.put("property", "Source");
				sourceObject.put("value", sourceArray);
				jsonArray.add(sourceObject);
			}
		}
		if (!StringTools.isEmpty(sf1SearchBean.getSprice()) || !StringTools.isEmpty(sf1SearchBean.getEprice())) {
			JSONObject sourceObject = new JSONObject();
			sourceObject.put("property", "Price");
			JSONArray sourceArray = new JSONArray();
			sourceArray.add(sf1SearchBean.getSprice() + "-" + sf1SearchBean.getEprice());
			sourceObject.put("value", sourceArray);
			jsonArray.add(sourceObject);
		}
		jsonObject.put("group_label", jsonArray);
	}

	protected static void buildAttr(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		List<AttrSearchBean> attrList = sf1SearchBean.getAttrList();
		if (attrList.isEmpty())
			return;
		JSONArray jsonArray = new JSONArray();
		for (AttrSearchBean attrSearchBean : attrList) {
			JSONObject attr = new JSONObject();
			if (!StringUtils.isEmpty(attrSearchBean.getValue())) {
				attr.put("attr_value", attrSearchBean.getValue());
				attr.put("attr_name", attrSearchBean.getName());
			}
			jsonArray.add(attr);
		}
		jsonObject.put("attr_label", jsonArray);
	}

	protected static void buildAnalyzer(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		JSONObject analyzer = new JSONObject();
		jsonObject.put("analyzer", analyzer);
		analyzer.put("apply_la", true);
		analyzer.put("use_original_keyword", false);
		analyzer.put("use_synonym_extension", true);
	}

	protected static void buildIn(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		if (CollectionTools.isEmpty(sf1SearchBean.getSearchIn()))
			return;
		JSONArray array = new JSONArray();
		List<String> searchIns = sf1SearchBean.getSearchIn();
		if (searchIns == null || searchIns.isEmpty())
			return;
		if (searchIns.size() == 1 && "Title".equals(searchIns.get(0)))
			return;
		for (String in : searchIns) {
			if ("Title".equals(in))
				continue;
			JSONObject properties = new JSONObject();
			properties.put("property", in);
			array.add(properties);
		}
		jsonObject.put("in", array);
	}

	protected static void buildMode(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		if (sf1SearchBean.isNeedSearchMode()) {
			if (sf1SearchBean.getSearchMode() != null) {
				jsonObject.put("searching_mode", sf1SearchBean.getSearchMode());
			} else {
				JSONObject properties = new JSONObject();
				properties.put("mode", "zambezi");
				jsonObject.put("searching_mode", properties);
			}
		}
	}

	protected static void buildHeader(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		JSONObject properties = new JSONObject();
		properties.put("check_time", "true");
		jsonObject.put("header", properties);
	}

	protected static void buildCondition(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		JSONArray cnds = new JSONArray();
		List<CondSearchBean> condSearchBeans = sf1SearchBean.getCondLst();
		for (CondSearchBean condSearchBean : condSearchBeans) {
			JSONObject cnd = new JSONObject();
			cnd.put("property", condSearchBean.getName());
			cnd.put("operator", condSearchBean.getOperator());
			JSONArray valueArray = new JSONArray();
			for (String value : condSearchBean.getParams()) {
				valueArray.add(value);
			}
			cnd.put("value", valueArray);
			cnds.add(cnd);
		}
		jsonObject.put("conditions", cnds);
	}

	protected static void buildJsonOfSort(JSONObject jsonObject, SF1SearchBean sf1SearchBean) {
		JSONArray sortJson = new JSONArray();
		List<SortSearchBean> sortLst = sf1SearchBean.getSortList();
		for (SortSearchBean sortSearchBean : sortLst) {
			JSONObject sortChildJson = new JSONObject();
			String softfield = sortSearchBean.getName();
			String sortType = sortSearchBean.getType();
			// 若排序字段为空，则不对该字段进行排序
			if (StringTools.isEmpty(softfield)) {
				continue;
			}
			sortChildJson.put("property", softfield);
			sortChildJson.put("order", sortType);
			sortJson.add(sortChildJson);
		}
		jsonObject.put("sort", sortJson);
	}

	/**
	 * 拼装json对象的header部分
	 * 
	 * @param documentSearchJson
	 *            需要拼装的主Json对象
	 */
	public static void buildJsonOfHeader(JSONObject documentSearchJson) {
		JSONObject headerJson = new JSONObject();
		headerJson.put("check_time", "true");
		documentSearchJson.put("header", headerJson);
	}

	/**
	 * 从json对象中获取所用时间
	 * 
	 * @param JSONObject
	 *            结果json对象
	 * @return 服务器端花费时间
	 */
	public static double getSpeedTime(JSONObject resultJsonObj) {
		double result;
		try {
			JSONObject timerJson = resultJsonObj.getJSONObject("timers");
			double speedTime = timerJson.getDouble("process_time");
			// 将double 截取到小数后3位
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(3);
			result = Double.valueOf(nf.format(speedTime));
		} catch (Exception e) {
			// 如果获得时间失败,则时间为-1
			result = -1;
		}
		return result;
	}

	public static void main(String[] args) {
		SF1SearchBean sf1SearchBean = new SF1SearchBean();
		sf1SearchBean.setCollection("cpc");
		sf1SearchBean.setLimit(10);
		sf1SearchBean.setOffset(0);
		sf1SearchBean.setKeywords("iphone");
		System.out.println(buildSearchJson(sf1SearchBean));
	}

}