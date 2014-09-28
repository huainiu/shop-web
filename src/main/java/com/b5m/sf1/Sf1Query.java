package com.b5m.sf1;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.dto.res.SearchDTO;

public interface Sf1Query {
	
	/**
	 * description
	 * 查询数量
	 * @param searchBean
	 * @return
	 * @author echo weng
	 * @since 2014年2月18日
	 * @mail echo.weng@b5m.com
	 */
	int searchCount(SF1SearchBean searchBean);
	
	/**
	 * description
	 * sf1查询
	 * @param searchBean
	 * @return
	 * @author echo weng
	 * @since 2014年2月18日
	 * @mail echo.weng@b5m.com
	 */
	SearchDTO doSearch(SF1SearchBean searchBean);
	
	/**
	 * description
	 * get查询
	 * @param searchBean
	 * @return
	 * @author echo weng
	 * @since 2014年2月18日
	 * @mail echo.weng@b5m.com
	 */
	SearchDTO doGet(SF1SearchBean searchBean);
	
	JSONObject doGetRtnJson(SF1SearchBean searchBean);
	
	/**
	 * 查询纠错信息
	 * @param collection
	 * @param keywords
	 * @return
	 */
	List<String> getRefined_query(String collection, String keywords);
	
	/**
	 * 获取摘要
	 * @param collection
	 * @param docId
	 * @return
	 */
	List<String[]> getSummarization(String collection, String docId);

	/**
	 * 查询预测价格
	 * @param forecastString 历史价格数组
	 * @return
	 */
	JSONObject forecastPrice(JSONArray forecast);
}
