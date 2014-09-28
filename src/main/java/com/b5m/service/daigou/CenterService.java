package com.b5m.service.daigou;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.dto.DaigouCart;

public interface CenterService {
	/*
	 * 获取商品信息
	 * @param docId
	 * @param ref
	 * @param url
	 * @param bk
	 * @param col
	 * @param docId
	 * @return JSONObject
	 */
	JSONObject getCacheProduct(String docId, String ref, String url, String bk, String col);
	
	/*
	 * 加入购物车时参数设置
	 * @return 
	 */
	DaigouCart createCart(HttpServletRequest request, String key, String docId, String priceAvg, String goodsSpec, Integer count, String channel, String ref, String bk, String url, String origin, String col, String skuId, Integer act, String newPrice, String title, String imgPath);
	
	/*
	 * 获取商品详情
	 * @param url
	 */
	String getshopDetail(String url);
	
	/*
	 * 中间页面设置商品信息
	 */
	void setShopInfo(HttpServletRequest request, JSONObject product, String newPrice, String priceAvg, String ref, String docId, String url, Integer maxNum);
		
}
