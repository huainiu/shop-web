package com.b5m.service.daigou.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.dto.OntimePriceBean;
import com.b5m.bean.dto.goodsdetail.GoodsDetailDataSetDto;
import com.b5m.bean.dto.goodsdetail.ShopInfoDto;
import com.b5m.common.utils.MemCachedUtils;
import com.b5m.service.daigou.DaigouLogUtils;
import com.b5m.service.daigou.DaigouService;
import com.b5m.service.ontimeprice.OntimeService;
import com.b5m.service.www.CategoryDaigouService;

@Service
public class DaigouServiceImpl implements DaigouService {
	@Resource(name = "properties")
	private Properties properties;
	@Autowired
	private CategoryDaigouService categoryDaigouService;
	final String TAOBAO_STRING = "taobao.com";
	@Autowired
	private OntimeService ontimeService;

	@Override
	public JSONObject returnDaigouShop(JSONArray jsonArray) {
		JSONObject lowestSource = null;
		int length = jsonArray.size();
		BigDecimal highPrice = null;
		BigDecimal lowestPrice = null;
		int i = -1;
		for (int index = 0; index < length; index++) {
			JSONObject jsonObject = jsonArray.getJSONObject(index);
			String source = jsonObject.getString("Source");
			BigDecimal price = jsonObject.getBigDecimal("Price");
			if (price == null)
				continue;
			if (highPrice == null) {
				highPrice = price;
			}
			if ((lowestPrice == null || lowestPrice.compareTo(price) > 0)) {
				// 不含有淘宝 天猫 或者含有淘宝天猫的 并且价格低于淘宝条猫的最低价
				lowestPrice = price;
				lowestSource = jsonObject;
				i = index;
			}
			if (highPrice.compareTo(price) < 0) {
				highPrice = price;
			}
		}
		if (i == -1)
			return null;
		lowestSource.put("index", i);
		lowestSource.put("HighPrice", highPrice);
		return lowestSource;
	}

	public boolean isOntimeFetchPriceSit(String url) {
		if (StringUtils.isEmpty(url))
			return false;
		// String ontimeFetchPriceSourcesStr =
		// properties.getProperty("ontime.fetch.price.sources");
		// String[] ontimeFetchPriceSources =
		// StringUtils.split(ontimeFetchPriceSourcesStr, ",");
		// for(String ontimeFetchPriceSource : ontimeFetchPriceSources){
		if (url.indexOf(TAOBAO_STRING) >= 0) {
			return false;
		}
		// }
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canDaigou(Map<String, Object> produce, String col) {
		Map<String, Object> goods = (Map<String, Object>) produce.get("daigouSource");
		JSONObject daigouSource = new JSONObject(goods);
		if (daigouSource.get("originUrl") != null) {
			daigouSource.put("originUrl", goods.get("originUrl"));
		} else {
			daigouSource.put("originUrl", goods.get("Url"));
		}
		daigouSource.put("Url", "/daigoucart/center.htm?docId=" + daigouSource.get("DOCID") + "&priceAvg=" + produce.get("highPrice") + "&col=" + col);
		return ontimeQueryPrice(daigouSource, produce);
	}

	public boolean canDaigouAndSetFlag(Map<String, String> produce) {
		String subDocsStr = produce.get("SubDocs");
		String url = "";
		produce.put("canDaigou", "false");
		if (subDocsStr == null) {
			url = produce.get("Url");
		}
		boolean isFetch = isOntimeFetchPriceSit(url);
		boolean isLowest = "1".equals(produce.get("isLowCompPrice"));
		if (isFetch && isLowest) {
			produce.put("canDaigou", "true");
		}
		return isFetch && isLowest;
	}

	protected boolean ontimeQueryPrice(JSONObject daigouSource, Map<String, Object> produce) {
		OntimePriceBean ontimePriceBean = new OntimePriceBean();
		ontimePriceBean.addDocId(daigouSource.getString("DOCID"), daigouSource.getString("originUrl"));
		JSONArray priceArray = ontimeService.queryPrices(ontimePriceBean);
		int length = priceArray.size();
		if (length >= 1) {
			try {
				JSONObject jsonObject = priceArray.getJSONObject(0);
				Integer status = jsonObject.getInteger("status");
				if (status == 0) {
					daigouSource.put("isDone", true);
				} else if (status == 1) {
					String price = jsonObject.getString("price");
					if (!"-1.0".equals(price)) {
						daigouSource.put("Price", price);
						DaigouLogUtils.info("query from data price:[" + daigouSource.getString("Price") + "] for docid[" + daigouSource.getString("DOCID") + "]");
					}
				} else {
					DaigouLogUtils.error("query from data price error,result is[" + priceArray.toJSONString() + "] for docid[" + daigouSource.getString("DOCID") + "]");
				}
			} catch (Exception e) {
				DaigouLogUtils.error(e.getMessage(), e);
			}
		}
		produce.putAll(daigouSource);
		produce.remove("daigouSource");
		DaigouLogUtils.info("into memcache price[" + daigouSource.getString("Price") + "] for docid[" + daigouSource.getString("DOCID") + "]");
		MemCachedUtils.setCache(daigouSource.getString("DOCID") + "_ontimeprice", daigouSource.getString("Price"), 86400);
		return true;
	}

	protected BigDecimal canDaigou(ShopInfoDto shopInfoDto, BigDecimal lowestPrice, BigDecimal highPrice) {
		if (shopInfoDto == null)
			return null;
		BigDecimal temp = BigDecimal.ZERO;
		List<Map<String, String>> sources = shopInfoDto.getShopList();
		for (Map<String, String> source : sources) {
			String priceStr = source.get("Price");
			BigDecimal price = new BigDecimal(priceStr);
			if (lowestPrice.compareTo(price) > 0) {
				return null;
			}
			if (highPrice == null) {
				if (temp.compareTo(price) < 0) {
					temp = price;
				}
			}
		}
		if (highPrice != null)
			return highPrice;
		return temp;
	}
}
