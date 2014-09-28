package com.b5m.service.www;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.entity.SearchRecommend;
import com.b5m.dao.domain.page.PageView;

public interface SearchRecommendService {
	
	PageView<SearchRecommend> getSearchRecommends(String startDate, String endDate, Integer currentPage);

	List<SearchRecommend> getSearchRecommendList(String startDate, String endDate);

	JSONObject addSearchRecommend(SearchRecommend searchRecommend);
	
}
