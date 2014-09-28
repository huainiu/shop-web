package com.b5m.service.pricetrend.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.DateTools;
import com.b5m.common.env.Sf1Constants;
import com.b5m.common.spring.aop.Cache;
import com.b5m.common.utils.goodsdetail.SUIAppTools;
import com.b5m.service.pricetrend.PricePerDay;
import com.b5m.service.pricetrend.PriceTrend;
import com.b5m.service.pricetrend.PriceTrendService;
import com.b5m.service.pricetrend.PriceTrendUtils;
import com.b5m.sf1.Sf1Query;
import com.b5m.sf1.dto.req.GetAttr;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.dto.res.SearchDTO;
import com.b5m.sf1.helper.Sf1Helper;

@Service("priceTrendService")
public class PriceTrendServiceImpl implements PriceTrendService {

	@Resource(name = "sf1Query")
	private Sf1Query sf1Query;

	@Override
	public String priceTrendTyp(String[] docIds, Integer range) {
		return PriceTrendUtils.getPriceTrendType(docIds, range);
	}

	@Override
	public JSONArray priceTrend(Integer range, Map<String, String> docidSourceMapping, boolean computeAveriage, String... docIds) {
		return priceTrend(range, docidSourceMapping, computeAveriage, false, docIds);
	}

	@Override
	@Cache
	public JSONArray priceTrend(Integer range, Map<String, String> docidSourceMapping, boolean computeAveriage, boolean formate, String... docIds) {
		List<PriceTrend> priceHistoryDTOs = PriceTrendUtils.getPriceHistory(range, false, docIds);
		// 计算平均价格
		if (computeAveriage) {
			priceHistoryDTOs = PriceTrendUtils.dealWithPriceHistoryDTOs(priceHistoryDTOs, docidSourceMapping, range);
		}
		if (priceHistoryDTOs.isEmpty())
			return null;
		JSONArray array = new JSONArray();
		for (PriceTrend priceHistoryDTO : priceHistoryDTOs) {
			array.add(PriceTrendUtils.convert(priceHistoryDTO, docidSourceMapping.get(priceHistoryDTO.getDocId()), formate));
		}
		return array;
	}

	/**
	 * @description 商品详情页面使用
	 * @param range
	 * @param source
	 * @return
	 * @author echo
	 * @since 2013-9-26
	 * @email echo.weng@b5m.com
	 */
	@Override
	public PriceTrend singlePriceTrend(Integer range, String docId, boolean fill, String price) {
		List<PriceTrend> priceHistoryDTOs = PriceTrendUtils.getPriceHistory(range, false, docId);
		PriceTrend priceTrend = null;
		if (!CollectionUtils.isEmpty(priceHistoryDTOs)) {
			priceTrend = priceHistoryDTOs.get(0);
		}
		if (priceTrend == null) {
			if (!StringUtils.isEmpty(price)) {
				priceTrend = new PriceTrend(docId, price, DateTools.now());
			} else {
				return null;
			}
		}
		List<PricePerDay> pricePerDays = priceTrend.getPricePerDays();
		if (fill) {
			PriceTrendUtils.fillEveryDatePrice(priceTrend, range);
		}
		Collections.sort(pricePerDays, new Comparator<PricePerDay>() {
			@Override
			public int compare(PricePerDay o1, PricePerDay o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});
		if (!StringUtils.isEmpty(price)) {
			int size = pricePerDays.size();
			if (DateTools.toMidnightDate(pricePerDays.get(size - 1).getDate()).compareTo(DateTools.getMidnightToday()) >= 0) {
				pricePerDays.set(size - 1, new PricePerDay(DateTools.now(), price));
			} else {
				pricePerDays.add(new PricePerDay(DateTools.now(), price));
			}
		}
		return priceTrend;
	}

	/**
	 * @param docid
	 *            DOCID
	 * @param range
	 *            价格趋势图范围，单位为天
	 * @return
	 */
	@Override
	@Cache
	public JSONArray getSourcePriceHistoryJSON(String docid, int range, boolean isAll, List<String> limitSource) {
		if (isAll) {
			limitSource = new ArrayList<String>();
		}
		Map<String, String> docidSourceMapping = getDocidSourceMapping(docid, limitSource);
		JSONArray json = priceTrend(range, docidSourceMapping, true, true, docidSourceMapping.keySet().toArray(new String[] {}));
		return json;
	}

	@Override
	public JSONObject getSourcePriceHistory(String docid, int range, List<String> removeSources) {
		JSONObject jsonObject = new JSONObject();
		Map<String, String> docidSourceMapping = getDocidSourceMapping(docid, removeSources, true);
		List<PriceTrend> priceHistoryDTOs = PriceTrendUtils.getPriceHistory(range, false, docidSourceMapping.keySet().toArray(new String[] {}));
		String type = PriceTrendUtils.getPriceTrendType(priceHistoryDTOs);
		jsonObject.put("type", type);

		// 获取最低价，averiage等名称未换
		PriceTrend priceTrend = PriceTrendUtils.createPriceTrendLow(priceHistoryDTOs, range);
		JSONArray jsonArray = new JSONArray();
		if (priceTrend == null)
			return jsonObject;
		jsonArray.add(PriceTrendUtils.convert(priceTrend, "最低价"));
		jsonObject.put("averiage", jsonArray);
		jsonObject.put("averiageType", PriceTrendUtils.getPriceType(priceTrend, null));
		if (priceTrend.getPricePerDays().size() > 0) {
			PricePerDay pricePerDay = PriceTrendUtils.getPrePrice(priceTrend, priceTrend.getPricePerDays().get(priceTrend.getPricePerDays().size() - 1));
			jsonObject.put("changePrice", pricePerDay);
			jsonObject.put("nowPrice", priceTrend.getPricePerDays().get(priceTrend.getPricePerDays().size() - 1));
		}
		return jsonObject;
	}

	@Override
	@Cache
	public JSONArray getAllSourcePriceHistory(String docid, int range, List<String> removeSources) {
		Map<String, String> docidSourceMapping = getDocidSourceMapping(docid, removeSources, true);
		List<PriceTrend> priceHistoryDTOs = PriceTrendUtils.getPriceHistory(range, false, docidSourceMapping.keySet().toArray(new String[] {}));
		priceHistoryDTOs = PriceTrendUtils.dealWithPriceHistoryDTOs(priceHistoryDTOs, docidSourceMapping, range);
		if (priceHistoryDTOs.isEmpty())
			return null;
		JSONArray array = new JSONArray();
		for (PriceTrend priceHistoryDTO : priceHistoryDTOs) {
			array.add(PriceTrendUtils.convert(priceHistoryDTO, docidSourceMapping.get(priceHistoryDTO.getDocId())));
		}
		return array;
	}

	private Map<String, String> getDocidSourceMapping(String docid, List<String> limitSource) {
		return getDocidSourceMapping(docid, limitSource, false);
	}

	private Map<String, String> getDocidSourceMapping(String docid, List<String> removeSources, boolean isRemove) {
		List<Map<String, String>> resources = null;
		try {
			// 获得商家比价信息
			resources = doPriceHistorySearch(docid);
		} catch (Exception e) {
			resources = CollectionTools.newListWithSize(0);
		}
		// 建立docid到商家的link
		Map<String, String> docidSourceMapping = new HashMap<String, String>();
		for (Map<String, String> resource : resources) {
			String itemSource = resource.get(Sf1Constants.FIELD_APP_SOURCE);
			if (!removeSources.isEmpty()) {
				if (isRemove && removeSources.contains(itemSource)) {
					continue;
				}
				if (!isRemove && !removeSources.contains(itemSource)) {
					continue;
				}
			}
			String itemDocid = resource.get("DOCID");
			docidSourceMapping.put(itemDocid, itemSource);
		}
		if (isRemove && docidSourceMapping.isEmpty()) {
			for (Map<String, String> resource : resources) {
				String itemSource = resource.get(Sf1Constants.FIELD_APP_SOURCE);
				String itemDocid = resource.get("DOCID");
				docidSourceMapping.put(itemDocid, itemSource);
			}
		}
		return docidSourceMapping;
	}

	// 获得比价商品
	private List<Map<String, String>> doPriceHistorySearch(String docid) {
		SF1SearchBean sf1rBean = new SF1SearchBean();
		// 初始化sf1r搜索bean
		sf1rBean.setKeywords("*");
		sf1rBean.getGroupBeans().clear();
		sf1rBean.setLimit(4000);
		sf1rBean.setCollection(Sf1Helper.CONTEXT_COLLECTION);
		sf1rBean.getSelectList().clear();
		sf1rBean.addSearchIn("Title");
		sf1rBean.setGetAttr(new GetAttr(false, 20));
		sf1rBean.setNeedSearchMode(false);
		// 接口问题 返回的都是大写的
		sf1rBean.addSelect("DOCID", "DOCID");
		sf1rBean.addSelect("Source", "Source");
		// 设置数据集和搜索条件
		sf1rBean.addCondition("uuid", "=", docid);
		SearchDTO result = sf1Query.doSearch(sf1rBean);
		return SUIAppTools.getGoodsCurrentResultList(result.getResourcesList());
	}

}
