package com.b5m.service.pricetrend;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface PriceTrendService {
	/**
	 * @description
	 * 价格趋势类型
	 * @param docIds
	 * @param range
	 * @return
	 * @author echo
	 * @since 2013-9-5
	 * @email echo.weng@b5m.com
	 */
	String priceTrendTyp(String[] docIds, Integer range);
	
	/**
	 * @description
	 * 价格趋势数据
	 * @param range
	 * @param docidSourceMapping
	 * @param computeAveriage 是否返回平均价格
	 * @param docId
	 * @return
	 * @author echo
	 * @since 2013-9-25
	 * @email echo.weng@b5m.com
	 */
	JSONArray priceTrend(Integer range, Map<String, String> docidSourceMapping, boolean computeAveriage,String ...docId);
	
	JSONArray priceTrend(Integer range, Map<String, String> docidSourceMapping, boolean computeAveriage, boolean formate, String ...docIds);

	/**
	 * @description
	 * 
	 * @param docid
	 * @param range
	 * @param isAll
	 * @param limitSource
	 * @return
	 * @author echo
	 * @since 2013-9-24
	 * @email echo.weng@b5m.com
	 */
	JSONArray getSourcePriceHistoryJSON(String docid, int range, boolean isAll, List<String> limitSource);
	
	/**
	 * @description
	 * 获取平均价格趋势数据及 价格类型
	 * @param docid
	 * @param range
	 * @return
	 * @author echo
	 * @since 2013-9-25
	 * @email echo.weng@b5m.com
	 */
	public JSONObject getSourcePriceHistory(String docid, int range, List<String> removeSources);
	
	/**
	 * @description
	 * 
	 * @param range
	 * @param source
	 * @param docId
	 * @param fill 是否需要全部价格区间都有
	 * @return
	 * @author echo
	 * @since 2013-9-26
	 * @email echo.weng@b5m.com
	 */
	PriceTrend singlePriceTrend(Integer range, String docId, boolean fill, String price);
	
	/**
	 * @description
	 * 获取所有商家的历史价格趋势数据
	 * @param docid
	 * @param range
	 * @param removeSources
	 * @return
	 * @author echo
	 * @since 2013-9-26
	 * @email echo.weng@b5m.com
	 */
	JSONArray getAllSourcePriceHistory(String docid, int range, List<String> removeSources);
}
