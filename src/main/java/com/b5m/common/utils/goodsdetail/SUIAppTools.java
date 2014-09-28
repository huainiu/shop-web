/**
 * AppTools.java
 *
 * 功  能：[demo]工具类
 * 类  名：AppTools
 *
 *   ver     变更日       公司      作者     变更内容
 * ──────────────────────────────────────────────
 *   V1.00  '10-11-26  iZENEsoft  金录       初版
 *
 * Copyright (c) 2009 iZENEsoft Business Software corporation All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.b5m.common.utils.goodsdetail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.b5m.common.env.Sf1Constants;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.service.goodsdetail.GoodsUtils;

/**
 * 本类是工具类。<BR>
 */
public class SUIAppTools {

	/**
	 * 日志处理器
	 */
	private static Log logger = LogFactory.getLog(SUIAppTools.class);

	/**
	 * 设置图片地址
	 */
	public static String getPictureSRC(String picture) {
		String url = "http://cdn.bang5mai.com/b5m_img/";
		if (StringUtils.isBlank(picture)) {
			return "";
		} else if (picture.startsWith("http://")) {
			return picture;
		} else {
			return url + picture;
		}

	}

	private static String getMultiPictureSrc(String[] multiPictureSRCArray) {
		StringBuffer sb = new StringBuffer();
		for (String src : multiPictureSRCArray) {
			if (sb.length() == 0) {
				sb.append(getPictureSRC(src));
			} else {
				sb.append(",").append(getPictureSRC(src));
			}
		}
		return sb.toString();
	}

	/**
	 * 获取商品资源显示的内容列表
	 * 
	 * @param lsResult 检索结果
	 * @param pageNo 结果页面页码
	 * @param rowPerPage 结果页面每页显示的记录数
	 * @return 前页面的内容列表
	 * @throws AppException
	 */
	public static List<Map<String, String>> getGoodsCurrentResultList(List<Map<String, String>> lsResult) {
		// Map<String, CmsSuppliserDto> suppliserMap = GlobalCacheHelper.getCacheSuppliserNameMap();
		List<Map<String, String>> lstPageResultList = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : lsResult) {
			String pictureSRC = map.get(Sf1Constants.FIELD_APP_PICTURE);
			if (StringUtils.isNotBlank(pictureSRC)) {

				String[] multiPictureSRCArray = pictureSRC.split(",");
				// 如果存在多张图片,处理多张图片
				if (pictureSRC.split(",").length > 1) {
					pictureSRC = multiPictureSRCArray[0];
				}
				map.put(Sf1Constants.FIELD_APP_PICTURE, SUIAppTools.getPictureSRC(pictureSRC));
				map.put(Sf1Constants.FIELD_APP_PICTURE_MULTI, getMultiPictureSrc(multiPictureSRCArray));
			}
			// 如果原地址不存在，使用本地图片
			if (StringUtils.isBlank(map.get(Sf1Constants.FIELD_APP_PICTURE_SOURCE))) {
				map.put(Sf1Constants.FIELD_APP_PICTURE_SOURCE, pictureSRC);
			}
			// 如果分享或喜欢或关注不存在,设置为0
			if (StringUtils.isBlank(map.get(Sf1Constants.FIELD_APP_XIHUAN))) {
				map.put(Sf1Constants.FIELD_APP_XIHUAN, "0");
			}

			if (StringUtils.isBlank(map.get(Sf1Constants.FIELD_APP_FENXIANG))) {
				map.put(Sf1Constants.FIELD_APP_FENXIANG, "0");
			}

			if (StringUtils.isBlank(map.get(Sf1Constants.FIELD_APP_GUANZHU))) {
				map.put(Sf1Constants.FIELD_APP_GUANZHU, "0");
			}

			if (StringUtils.isBlank(map.get(Sf1Constants.FIELD_APP_BUY_COUNT))) {
				map.put(Sf1Constants.FIELD_APP_BUY_COUNT, "0");
			}
			String score = map.get(Sf1Constants.FIELD_APP_SCORE);
			try {
				if (StringUtils.isNotBlank(score)) {
					try {
						BigDecimal sc = new BigDecimal(score);
						sc = sc.setScale(1, BigDecimal.ROUND_HALF_UP);
						score = sc.toString();
					} catch (Exception e) {
						score = "3";
					}
					map.put(Sf1Constants.FIELD_APP_SCORE, score);
				}
			} catch (Exception e) {
				logger.debug(e);
			}
			lstPageResultList.add(map);
		}
		return lstPageResultList;
	}

	/**
	 * 取得可以用来显示的时间字符串,精确到日期
	 * 
	 * @param sf1rDate sf1r的日期字符串,yyyy-MM-dd
	 * @return 可以用来显示日期字符串
	 */
	public static String getAppDate(String sf1rDate) {
		String result = sf1rDate;
		try {
			result = sf1rDate.substring(0, 4) + "-" + sf1rDate.substring(4, 6) + "-" + sf1rDate.substring(6, 8);
		} catch (Exception e) {
			// 仅仅记录错误
			logger.debug(e);
		}

		return result;
	}

	/**
	 * 获取商品资源显示的内容列表
	 * 
	 * @param lsResult 检索结果
	 * @param pageNo 结果页面页码
	 * @param rowPerPage 结果页面每页显示的记录数
	 * @return 前页面的内容列表
	 * @throws AppException
	 */
	public static List<Map<String, String>> getCommentCurrentResultList(List<Map<String, String>> lsResult, Map<String, SuppliserDto> suppliserMap) {
		List<Map<String, String>> lstPageResultList = new ArrayList<Map<String, String>>();
		for (Map<String, String> map : lsResult) {
			// 设置评论日期
			String date = map.get("DATE");
			String source = getSource(map.get(Sf1Constants.FIELD_APP_SOURCE), suppliserMap);
			SuppliserDto suppliserDto = suppliserMap.get(source);
			if(suppliserDto != null){
				map.put("Logo", suppliserDto.getLogo());
				map.put("LogoUrl", suppliserDto.getUrl());
			}
//			Source
			String appDate = SUIAppTools.getAppDate(date);
			map.put(Sf1Constants.FIELD_APP_DATE, appDate);
			lstPageResultList.add(map);
		}
		return lstPageResultList;
	}
	
	public static String getSource(String source, Map<String, SuppliserDto> suppliserMap){
    	for(String key : suppliserMap.keySet()){
    		if(key.indexOf(source) >= 0) return key;
    		if(source.indexOf(key) >= 0) return key;
    	}
    	return null;
    }

}
