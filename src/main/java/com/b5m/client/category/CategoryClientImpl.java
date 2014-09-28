package com.b5m.client.category;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.Lang;
import com.b5m.client.AbstractClient;
import com.b5m.client.HttpClientFactory;
import com.b5m.common.utils.UcMemCachedUtils;

public class CategoryClientImpl extends AbstractClient implements CategoryClient {
	private static Map<String, String> mapping;
	private static String currentVersion = null;

	public CategoryClientImpl(String url, HttpClientFactory httpClientFactory) {
		super(url, httpClientFactory);
	}

	/**
	 * @description 获取中英对应关系
	 * @return
	 * @author echo weng
	 * @since 2013年12月18日
	 * @mail echo.weng@b5m.com
	 */
	@Override
	public Map<String, String> queryZYMapping() {
		Map<String, String> mapping = Lang.newMap();
		String version = Lang.toStrNotNull(UcMemCachedUtils.getCache("v_version"));
		if (version.equals(CategoryClientImpl.currentVersion) && !CategoryClientImpl.mapping.isEmpty()) {
			return CategoryClientImpl.mapping;
		}
		HttpClient httpClient = httpClientFactory.getHttpClient();
		HttpMethod method = new GetMethod(url);
		try {
			httpClient.executeMethod(method);
			String response = method.getResponseBodyAsString();
			if (Lang.isEmpty(response))
				return mapping;
			return parseResponse(response);
		} catch (HttpException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		CategoryClientImpl.currentVersion = Lang.toStrNotNull(UcMemCachedUtils.getCache("v_version"));
		CategoryClientImpl.mapping = mapping;
		return mapping;
	}

	protected Map<String, String> parseResponse(String response) {
		Map<String, String> mapping = Lang.newMap();
		String json = response.substring(response.indexOf("{"), response.indexOf(";"));
		JSONObject jsonObject = JSONObject.parseObject(json);
		if (jsonObject == null)
			return mapping;
		JSONArray firstCategorys = jsonObject.getJSONArray("data");
		dealWith(mapping, firstCategorys);
		return mapping;
	}

	protected void dealWith(Map<String, String> mapping, JSONArray categorys) {
		int length = categorys.size();
		for (int index = 0; index < length; index++) {
			JSONObject data = categorys.getJSONObject(index);
			JSONObject cates = data.getJSONObject("cates");
			if (cates != null) {
				Object cn = cates.get("cn");
				if (cn instanceof JSONArray) {
					JSONArray cnArray = (JSONArray) cn;
					JSONArray pyArray = cates.getJSONArray("py");
					int cnLength = pyArray.size();
					cnLength = cnLength < cnArray.size() ? cnLength : cnArray.size();
					for (int cnIndex = 0; cnIndex < cnLength; cnIndex++) {
						mapping.put(pyArray.getString(cnIndex), cnArray.getString(cnIndex));
					}
				} else {
					if (!Lang.isEmpty(Lang.toStrNotNull(cn))) {
						mapping.put(cates.getString("py"), cn.toString());
					}
				}
				JSONObject keywords = data.getJSONObject("keywords");
				mapping.put(keywords.getString("py"), keywords.getString("cn"));
			}
			JSONArray subArray = data.getJSONArray("sub_categories");
			if (subArray != null) {
				dealWith(mapping, subArray);
			}
		}
	}
}
