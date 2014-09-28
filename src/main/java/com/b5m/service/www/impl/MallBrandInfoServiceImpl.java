package com.b5m.service.www.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.WeakHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.b5m.base.common.utils.StringTools;
import com.b5m.bean.entity.MallBrandInfo;
import com.b5m.bean.entity.RecommendProduct;
import com.b5m.common.spring.aop.Cache;
import com.b5m.dao.Dao;
import com.b5m.dao.domain.cnd.Cnd;
import com.b5m.dao.domain.cnd.Op;
import com.b5m.dao.domain.page.PageCnd;
import com.b5m.dao.domain.page.PageView;
import com.b5m.service.www.MallBrandInfoService;

@Service("mallBrandInfoService")
public class MallBrandInfoServiceImpl implements MallBrandInfoService {

	@Autowired
	private Dao dao;

	private Map<String, List<RecommendProduct>> recommendMap = new WeakHashMap<String, List<RecommendProduct>>();

	private Random random = new Random();

	@Override
	@Cache(timeout = 72000)
	public MallBrandInfo queryById(Long id) {
		return dao.get(MallBrandInfo.class, id);
	}

	@Override
	@Cache(timeout = 72000)
	public PageView<MallBrandInfo> queryPage(Integer currentPage, Integer pageSize, Integer type, String word) {
		List<Object> params = new ArrayList<Object>();
		params.add(type);
		StringBuilder sql = new StringBuilder(" select name, url from t_mall_brand_info where type = ? ");
		if (!StringTools.isEmpty(word)) {
			params.add(word);
			sql.append(" and first_up_char = ? ");
		}
		PageView<MallBrandInfo> pageView = dao.queryPageBySql(sql.toString(), params.toArray(), PageCnd.newInstance(currentPage, pageSize), MallBrandInfo.class);
		return pageView;
	}

	@Override
	@Cache(timeout = 72000)
	public PageView<MallBrandInfo> queryAllDataPage(Integer currentPage, Integer pageSize, Integer type, String word) {
		return dao.queryPage(MallBrandInfo.class, Cnd.where("type", Op.EQ, type).add("firstUpChar", Op.EQ, word), PageCnd.newInstance(currentPage, pageSize));
	}

	@Override
	public String[] queryWords() {
		String[] array = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		return array;
	}

	@Override
	@Cache(timeout = 72000)
	public List<MallBrandInfo> randonPage(Integer pageSize) {
		Integer maxId = dao.queryUniqueBySql("select max(id) from t_mall_brand_info", new Object[] {}, Integer.class);
		List<Integer> randonIds = new ArrayList<Integer>();
		addRandonId(maxId, randonIds, pageSize);
		List<MallBrandInfo> mallBrandInfos = dao.query(MallBrandInfo.class, Cnd.whereIn("id", randonIds.toArray()).desc("id"));
		int length = mallBrandInfos.size();
		for (int index = 0; index < length;) {
			MallBrandInfo mallBrandInfo = mallBrandInfos.get(index);
			if (0 == mallBrandInfo.getStatus()) {
				mallBrandInfos.remove(index);
			} else {
				index++;
			}
		}
		return mallBrandInfos;
	}

	@Override
	public List<RecommendProduct> randomProduct(String collection, Integer count) {
		List<RecommendProduct> list = recommendMap.get(collection);
		if (CollectionUtils.isEmpty(list)) {
			list = dao.query(RecommendProduct.class, Cnd.where("collection", Op.EQ, collection));
			recommendMap.put(collection, list);
		}
		if (!CollectionUtils.isEmpty(list) && list.size() > count) {
			List<Integer> ids = new ArrayList<Integer>(count);
			addRandonId(list.size(), ids, count);
			List<RecommendProduct> rs = new ArrayList<RecommendProduct>(count);
			for (int i = 0; i < ids.size(); i++) {
				rs.add(list.get(ids.get(i)));
			}
			return rs;
		}
		return list;
	}

	private void addRandonId(Integer maxId, List<Integer> randonIds, Integer pageSize) {
		while (randonIds.size() < pageSize) {
			int num = random.nextInt(maxId);
			if (!randonIds.contains(num)) {
				randonIds.add(num);
			}
		}
	}

}