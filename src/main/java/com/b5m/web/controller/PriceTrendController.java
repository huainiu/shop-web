package com.b5m.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.service.pricetrend.PricePerDay;
import com.b5m.service.pricetrend.PriceTrend;
import com.b5m.service.pricetrend.PriceTrendService;
import com.b5m.service.pricetrend.PriceTrendUtils;
import com.b5m.sf1.Sf1Query;
import com.b5m.web.controller.base.AbstractBaseController;
import com.mchange.v1.util.StringTokenizerUtils;

@Controller
@RequestMapping("/pricehistory")
public class PriceTrendController extends AbstractBaseController {

	@Resource
	private PriceTrendService priceTrendService;

	@Autowired
	private Sf1Query sf1Query;

	@RequestMapping("/priceTrend")
	@ResponseBody
	public void priceTrend(String docId, String source) throws Exception {
		Map<String, String> docidSourceMapping = new HashMap<String, String>();
		docidSourceMapping.put(docId, source);
		JSONArray array = priceTrendService.priceTrend(90, docidSourceMapping, false, docId);
		output(0, "success", array);
	}
	
	//cms访问
	@RequestMapping("/only/priceTrend")
	@ResponseBody
	public PriceTrend onlyPriceTrend(String docId, Integer day) throws Exception {
		if(day == null) day = 90;
		PriceTrend priceTrend = priceTrendService.singlePriceTrend(day, docId, false, null);
		return priceTrend;
	}

	@RequestMapping("/goodsDetail")
	@ResponseBody
	public void singlePriceTrendForGoodsDetail(String docId, String source, Boolean fill, String price) throws Exception {
		if (fill == null) fill = false;
		Map<String, String> docidSourceMapping = new HashMap<String, String>();
		docidSourceMapping.put(docId, source);
		PriceTrend priceTrend = priceTrendService.singlePriceTrend(90, docId, fill, price);
		if (priceTrend == null)
			output(0, "success", null);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(PriceTrendUtils.convert(priceTrend, source));
		jsonObject.put("averiage", jsonArray);
		JSONArray priceArray = PriceTrendUtils.convert(jsonArray.getJSONObject(0).getJSONArray("prices"), "MM");
		JSONObject rs = sf1Query.forecastPrice(priceArray);
		if (rs.getString("forecast_result") != null && !StringUtils.isEmpty(price)) {
			BigDecimal forecast = new BigDecimal(rs.getString("forecast_result"));
			BigDecimal nowPrice = new BigDecimal(price);
			jsonObject.put("forecastTrend", forecast.compareTo(nowPrice));
			jsonObject.put("forecast", forecast.setScale(2, RoundingMode.HALF_EVEN));
		}
		jsonObject.put("averiageType", PriceTrendUtils.getPriceType(priceTrend, price));
		PricePerDay pricePerDay = PriceTrendUtils.getPrePrice(priceTrend, priceTrend.getPricePerDays().get(priceTrend.getPricePerDays().size() - 1));
		jsonObject.put("changePrice", pricePerDay);
		jsonObject.put("nowPrice", priceTrend.getPricePerDays().get(priceTrend.getPricePerDays().size() - 1));
		output(0, "success", jsonObject);
	}

	@RequestMapping("/priceTrendTypes")
	@ResponseBody
	public String priceTrendTypes(String docIds) {
		if (StringUtils.isEmpty(docIds))
			return "";
		return priceTrendService.priceTrendTyp(StringTokenizerUtils.tokenizeToArray(docIds, ","), 90);
	}
	
	
	

	@RequestMapping("/sourcePriceHistory")
	@ResponseBody
	public void getSourcePriceHistory(ModelMap model, String docId) throws Exception {
		List<String> removeSources = new ArrayList<String>();
		JSONObject jsonObject = priceTrendService.getSourcePriceHistory(docId, 90, removeSources);
		output(0, "success", jsonObject);
	}

	@RequestMapping("/allSourcePriceHistory")
	@ResponseBody
	public void getAllSourcePriceHistory(ModelMap model, String docId) throws Exception {
		List<String> removeSources = new ArrayList<String>();
		removeSources.add("淘宝网");
		removeSources.add("天猫");
		JSONArray jsonArray = priceTrendService.getAllSourcePriceHistory(docId, 90, removeSources);
		output(0, "success", jsonArray);
	}
}
