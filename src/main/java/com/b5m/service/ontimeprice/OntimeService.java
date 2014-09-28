package com.b5m.service.ontimeprice;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.dto.OntimePriceBean;
import com.b5m.bean.dto.SkuRequest;

public interface OntimeService {

	JSONArray queryPrices(OntimePriceBean ontimePriceBean);

	SkuBean querySkuProp(SkuRequest skuRequest, String docId, HttpServletRequest req);

	String getPriceFromSku(String docId, String goosSpec);

	JSONObject queryDetail(String url);

	JSONArray querySkuAll(SkuRequest skuRequest, String docId, HttpServletRequest req);

}
