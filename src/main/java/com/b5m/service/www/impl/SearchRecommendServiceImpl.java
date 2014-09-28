package com.b5m.service.www.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.DateTools;
import com.b5m.bean.dto.Recharge;
import com.b5m.bean.entity.SearchRecommend;
import com.b5m.common.utils.OrderUtil;
import com.b5m.common.utils.TradeHttpUtil;
import com.b5m.dao.Dao;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.SearchRecommendService;

@Service("searchRecommendService")
public class SearchRecommendServiceImpl implements SearchRecommendService {

	@Resource(name = "dao")
	private Dao dao;

	@Resource(name = "sysConfig")
	private Properties properties;
	
	private Logger log = Logger.getLogger(getClass());

	@Override
	public JSONObject addSearchRecommend(SearchRecommend searchRecommend) {
//		JSONObject result =  null;
		SearchRecommend res = dao.insert(searchRecommend);
//		String uid = searchRecommend.getUid();
//		if (uid != null && res.getId() != null) {
//			result = TradeHttpUtil.post2Msg(properties.getProperty("trade.url.insert.b5mBean.rechargeQuery"), new Recharge(OrderUtil.getOrderNum(uid), uid, new Double(6), "搜索反馈送帮钻", 9006, 1, "搜索反馈送帮钻"));
//			log.info("搜索反馈送帮钻: " + result.getBooleanValue("ok"));
//		}
		return null;
	}

	@Override
	public PageView<SearchRecommend> getSearchRecommends(String startDate, String endDate, Integer currentPage) {
		if (currentPage == null)
			currentPage = 1;
		return dao.queryPage(SearchRecommend.class, createCnd(startDate, endDate), new PageCnd(currentPage, 10));
	}

	@Override
	public List<SearchRecommend> getSearchRecommendList(String startDate, String endDate) {
		return dao.query(SearchRecommend.class, createCnd(startDate, endDate));
	}

	protected Cnd createCnd(String startDate, String endDate) {
		Cnd cnd = Cnd._new();
		String format = "yyyy-MM-dd HH:mm:ss";
		if (startDate != null)
			cnd.add("createTime", Op.GTE, DateTools.parse(startDate, format));
		if (endDate != null) {
			Date end = DateTools.parse(endDate, format);
			if (end != null) {
				end = DateTools.addDay(end, 1);
			}
			cnd.add("createTime", Op.LTE, end);
		}
		return cnd;
	}

}
