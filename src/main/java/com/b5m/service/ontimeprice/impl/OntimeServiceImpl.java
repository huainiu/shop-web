package com.b5m.service.ontimeprice.impl;

import java.math.BigDecimal;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.dto.OntimePriceBean;
import com.b5m.bean.dto.SkuRequest;
import com.b5m.bean.dto.goodsdetail.GoodsDetailDataSetDto;
import com.b5m.client.ontimeprice.OntimeClient;
import com.b5m.common.utils.MemCachedUtils;
import com.b5m.service.goodsdetail.GoodsDetailService;
import com.b5m.service.ontimeprice.OntimeLogUtils;
import com.b5m.service.ontimeprice.OntimeService;
import com.b5m.service.ontimeprice.SkuBean;
import com.b5m.service.ontimeprice.SkuProp;

@Service("ontimeService")
public class OntimeServiceImpl implements OntimeService {

	@Autowired
	private OntimeClient ontimeClient;

	@Autowired
	private GoodsDetailService goodsDetailServiceImpl;

	@Resource(name = "sysConfig")
	private Properties properties;

	@Override
	public JSONArray queryPrices(OntimePriceBean ontimePriceBean) {
		long start = System.currentTimeMillis();
		try {
			JSONArray jsonArray = ontimeClient.queryPrices(ontimePriceBean.toJsonString());
			return jsonArray;
		} catch (Exception e) {
			OntimeLogUtils.error("error --- > on time query price time is[" + (System.currentTimeMillis() - start) + "ms] for request[" + ontimePriceBean.toJsonString() + "]", e);
			return new JSONArray();
		}
	}

	@Override
	public JSONObject queryDetail(String url) {
		long start = System.currentTimeMillis();
		SkuRequest skuRequest = new SkuRequest("", url);
		skuRequest.addKeys("Source", "OriginalPicture", "Url", "Title");
		try {
			JSONObject result = ontimeClient.querySku(skuRequest.toJsonString());
			return result;
		} catch (Exception e) {
			OntimeLogUtils.error("error --- > on time query detail time is[" + (System.currentTimeMillis() - start) + "ms] for request[" + skuRequest.toJsonString() + "]", e);
			return null;
		}
	}

	@Override
	public SkuBean querySkuProp(SkuRequest skuRequest, String docId, HttpServletRequest req) {
		for (int i = 0; i < 3; i++) {
			long start = System.currentTimeMillis();
			try {
				SkuBean skuBean = new SkuBean();
				JSONArray skus;
				String url = skuRequest.getUrl().replaceFirst("http://", "");
				String host = url.substring(0, url.indexOf("/"));
				if (properties.getProperty(host + ".sku") != null) {
					skus = sf1Data(docId, req, properties.getProperty(host + ".sku"));
				} else {
					skus = spiderData(skuRequest, skuBean);
				}
				skuAssemble(docId, skuBean, skus);
			//	skusCache(docId, skus);
				return skuBean;
			} catch (Exception e) {
				OntimeLogUtils.error("error --- > on time query sku time is[" + (System.currentTimeMillis() - start) + "ms] for request[" + skuRequest.toJsonString() + "]", e);
			}
		}
		return null;
	}
	
	@Override
	public JSONArray querySkuAll(SkuRequest skuRequest, String docId, HttpServletRequest req) {
		for (int i = 0; i < 3; i++) {
			long start = System.currentTimeMillis();
			try {
				SkuBean skuBean = new SkuBean();
				JSONArray skus;
				String url = skuRequest.getUrl().replaceFirst("http://", "");
				String host = url.substring(0, url.indexOf("/"));
				if (properties.getProperty(host + ".sku") != null) {
					skus = sf1Data(docId, req, properties.getProperty(host + ".sku"));
				} else {
					skus = spiderData(skuRequest, skuBean);
				}
				return skus;
			} catch (Exception e) {
				OntimeLogUtils.error("error --- > on time query sku time is[" + (System.currentTimeMillis() - start) + "ms] for request[" + skuRequest.toJsonString() + "]", e);
			}
		}
		return null;
	}

	public JSONArray spiderData(SkuRequest skuRequest, SkuBean skuBean) {
		JSONArray skus = null;
		long start = System.currentTimeMillis();
		try {
			JSONObject result = ontimeClient.querySku(skuRequest.toJsonString());
			String msg = "";
			if (result != null)
				msg = result.toJSONString();
			OntimeLogUtils.info("info --- > on time query sku time is[" + (System.currentTimeMillis() - start) + "ms] for request[" + skuRequest.toJsonString() + "],result is[" + msg + "]");
			if (result == null)
				return skus;
			skuBean.setLowestPrice(result.getBigDecimal("price"));
			skus = result.getJSONArray("skus");
		} catch (Exception e) {
			OntimeLogUtils.error("error --- > on time query sku time is[" + (System.currentTimeMillis() - start) + "ms] for request[" + skuRequest.toJsonString() + "]", e);
		}
		return skus;
	}

	public JSONArray sf1Data(String docId, HttpServletRequest req, String col) {
		JSONArray skus = null;
		GoodsDetailDataSetDto dataSet = goodsDetailServiceImpl.getGoodsDetailInfoByDocId(req, docId, col);
		String skuString = (String) dataSet.getGoodsDetailInfoDto().get("skus");
		if (!StringUtils.isEmpty(skuString)) {
			skus = JSON.parseArray(skuString);
		}
		return skus;
	}

	private void skuAssemble(String docId, SkuBean skuBean, JSONArray skus) {
		if (CollectionUtils.isEmpty(skus))
			return;
		int length = skus.size();
		JSONObject _sku = new JSONObject();
		skuBean.setSku(_sku);
		BigDecimal lowestPrice = null;
		BigDecimal highestPrice = null;
		for (int index = 0; index < length; index++) {
			JSONObject sku = skus.getJSONObject(index);
			String property = sku.getString("properties_name");
			if (property.endsWith(";"))
				property = property.substring(0, property.length() - 1);
			BigDecimal price = sku.getBigDecimal("price");
			if (lowestPrice == null || lowestPrice.compareTo(price) > 0) {
				lowestPrice = price;
			}
			if (highestPrice == null || highestPrice.compareTo(price) < 0) {
				highestPrice = price;
			}
			_sku.put(property, price);
			addProperty(skuBean, property);
		}
		if (lowestPrice != null) {
			skuBean.setLowestPrice(lowestPrice);
		}
		if (lowestPrice != null) {
			skuBean.setHighestPrice(highestPrice);
			_sku.put("HighestPrice", highestPrice);
		}
		MemCachedUtils.setCache(docId + "_skuprice", _sku, 86400);
	}
	
	private void skusCache(String docId, JSONArray skus) {
		if (CollectionUtils.isEmpty(skus))
			return;
		MemCachedUtils.setCache(docId + "_skus", skus, 86400);
	}
	

	@Override
	public String getPriceFromSku(String docId, String goosSpec) {
		JSONObject sku = (JSONObject) MemCachedUtils.getCache(docId + "_skuprice");
		if (sku == null)
			return null;
		String price = sku.getString(goosSpec);
		if (!StringUtils.isEmpty(price)) {
			return price;
		}
		return sku.getString("HighestPrice");
	}
	
	private void addProperty(SkuBean skuBean, String property) {
		String[] props = StringUtils.split(property, ";");
		for (String prop : props) {
			String[] p = StringUtils.split(prop, ":");
			SkuProp skuProp = skuBean.getByName(p[0]);
			if (skuProp == null) {
				skuProp = new SkuProp();
				skuProp.setName(p[0]);
				skuBean.addSkuProp(skuProp);
			}
			skuProp.addProp(p[1]);
		}
	}

}
