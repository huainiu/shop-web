package com.b5m.service.goodsdetail.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.CollectionTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.base.common.utils.ThreadTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.dto.goodsdetail.GoodsDetailDataSetDto;
import com.b5m.bean.dto.goodsdetail.GoodsDetailSearchCndDto;
import com.b5m.bean.dto.goodsdetail.ShopInfoDto;
import com.b5m.bean.dto.shoplist.DocResourceDto;
import com.b5m.bean.entity.ShopInfo;
import com.b5m.bean.entity.Suppliser;
import com.b5m.common.env.Sf1Constants;
import com.b5m.common.utils.FileUtil;
import com.b5m.common.utils.ImageUtils;
import com.b5m.common.utils.goodsdetail.SUIAppTools;
import com.b5m.dao.Dao;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.ISuppliserService;
import com.b5m.service.goodsdetail.GoodsDetailService;
import com.b5m.service.goodsdetail.GoodsUtils;
import com.b5m.service.hbase.HbaseDataService;
import com.b5m.service.hbase.bean.CommentType;
import com.b5m.service.pricetrend.PriceTrend;
import com.b5m.service.pricetrend.PriceTrendService;
import com.b5m.service.pricetrend.PriceTrendUtils;
import com.b5m.service.snapshot.SnapshotTools;
import com.b5m.service.www.SuppliserService;
import com.b5m.sf1.Sf1Query;
import com.b5m.sf1.dto.req.CondSearchBean;
import com.b5m.sf1.dto.req.GetAttr;
import com.b5m.sf1.dto.req.SF1SearchBean;
import com.b5m.sf1.dto.res.SearchDTO;
import com.b5m.sf1.helper.Sf1DataHelper;
import com.b5m.sf1.helper.Sf1Helper;

/**
 * description
 * 
 * @Company b5m
 * @author echo
 * @since 2013-5-31
 */
@Service
public class GoodsDetailServiceImpl implements GoodsDetailService {
	private static final Log LOG = LogFactory.getLog(GoodsDetailServiceImpl.class);

	@Resource(name = "suppliserCachedService")
	private ISuppliserService supplierService;
	@Resource(name = "newSuppliserCacheService")
	private SuppliserService newSuppliserService;
	@Resource(name = "sf1Query")
	private Sf1Query sf1Query;
	@Resource(name = "searchThreadPool")
	private ExecutorService threadPool;
	@Resource
	private HbaseDataService hbaseDataService;
	@Autowired
	private PriceTrendService priceTrendService;
	@Autowired
	private Dao dao;
	private int index = 2;
	@Resource(name = "sysConfig")
	private Properties properties;

	@Override
	public ShopInfo getShopInfo(String name) {
		ShopInfo shopInfo = dao.getByName(ShopInfo.class, name);
		if (shopInfo != null) {
			String des = shopInfo.getDesHigherRate();
			String serv = shopInfo.getServScHigherScore();
			String cons = shopInfo.getConsSpeedRate();
			String score = shopInfo.getScoreHigherRate();
			String servsc = shopInfo.getServScHigherScore();
			String time = shopInfo.getTimeScHigherRate();
			if (StringUtils.isEmpty(new StringBuilder().append(des).append(serv).append(cons).append(score).append(servsc).append(time).toString()))
				return null;
			if (!StringUtils.isEmpty(des)) {
				shopInfo.getMap().put("des", des.substring(0, index));
				shopInfo.setDesHigherRate(des.substring(index, des.length()));
			}
			if (!StringUtils.isEmpty(serv)) {
				shopInfo.getMap().put("serv", serv.substring(0, index));
				shopInfo.setServScHigherScore(serv.substring(index, serv.length()));
			}
			if (!StringUtils.isEmpty(cons)) {
				shopInfo.getMap().put("cons", cons.substring(0, index));
				shopInfo.setConsSpeedRate(cons.substring(index, cons.length()));
			}
			if (!StringUtils.isEmpty(score)) {
				shopInfo.getMap().put("score", score.substring(0, index));
				shopInfo.setConsSpeedRate(score.substring(index, score.length()));
			}
			if (!StringUtils.isEmpty(servsc)) {
				shopInfo.getMap().put("servsc", servsc.substring(0, index));
				shopInfo.setConsSpeedRate(servsc.substring(index, servsc.length()));
			}
			if (!StringUtils.isEmpty(time)) {
				shopInfo.getMap().put("time", time.substring(0, index));
				shopInfo.setConsSpeedRate(time.substring(index, time.length()));
			}
		}
		return shopInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoods(HttpServletRequest req, String docId, String col, Double scope) {
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsDetailDataSetDto dataSet = getGoodsDetailInfoByDocId(req, docId, col);
		String source = (String) dataSet.getGoodsDetailInfoDto().get("Source");
		if (dataSet.getGoodsDetailInfoDto() == null || StringUtils.isEmpty(source)) {
			return null;
		}
		String uuid = null;
		uuid = (String) dataSet.getGoodsDetailInfoDto().get("uuid");
		if (uuid != null && !uuid.equals(docId)) {
			GoodsDetailSearchCndDto searchCndDto = new GoodsDetailSearchCndDto();
			searchCndDto.setSortField("Price");
			searchCndDto.setSortType(GoodsDetailSearchCndDto.DESC);
			List<Map<String, String>> subDocs = SearchUUID(req, uuid, searchCndDto);
			if (!CollectionUtils.isEmpty(subDocs)) {
				int length = subDocs.size();
				for (int i = 0; i < length; i++) {
					String s = subDocs.get(i).get("DOCID");
					if (docId.equals(s)) {
						subDocs.remove(i);
						break;
					}
				}
				map.put("subDocs", subDocs);
			}
			if (!CollectionUtils.isEmpty(subDocs))
				map.put("highPrice", subDocs.get(0).get("Price"));
		}
		if (map.get("highPrice") == null)
			map.put("highPrice", dataSet.getGoodsDetailInfoDto().get("Price"));
		if (map.get("subDocs") != null) {
			double highP = Double.parseDouble(dataSet.getGoodsDetailInfoDto().get("Price").toString()) * (1 + scope);
			double lowP = Double.parseDouble(dataSet.getGoodsDetailInfoDto().get("Price").toString()) * (1 - scope);
			List<Map<String, String>> subDocs = (List<Map<String, String>>) map.get("subDocs");
			for (int i = 0; i < subDocs.size();) {
				String sourceName = subDocs.get(i).get("Source");
				if (StringUtils.isEmpty(sourceName) || sourceName.equals("淘宝网")) {
					subDocs.remove(i);
					continue;
				}
				double price = Double.parseDouble(subDocs.get(i).get("Price"));
				if (price > highP || price < lowP) {
					subDocs.remove(i);
					continue;
				}
				i++;
			}
			if (!subDocs.isEmpty()) {
				map.put("subDocs", subDocs);
			}
		}
		map.put("dataSet", dataSet);
		return map;
	}

	@Override
	public GoodsDetailDataSetDto getGoodsDetailInfo(String docId) {
		GoodsDetailDataSetDto dataSet = new GoodsDetailDataSetDto();
		Map<String, String> produce = null;
		try {
			produce = getProduceBySf1r(docId);
			if (produce == null)
				return dataSet;
		} catch (Exception e) {
			LOG.error("获取商品信息失败, docid=" + docId + " , 失败原因 : " + e.getMessage());
			return dataSet;
		}
		if (!StringTools.isEmpty(produce.get("isSnap"))) {
			dataSet.setSnap(true);
		}
		dataSet.setDocId(docId);
		ImageUtils.replaceImgUrl(produce);
		// 产品属性设置
		dataSet.setAttributes(getProduceAttr(produce));
		// 设置CategoryValue
		dataSet.setCategoryValue(produce.get(Sf1Constants.FIELD_APP_CATEGORY));
		// 设置图片信息，只要十张图片
		List<Map<String, String>> imageList = getProduceImgs(docId, null);
		int index = imageList.size() > 10 ? 10 : imageList.size();
		dataSet.setImageInfos(imageList.subList(0, index));
		// 设置
		dataSet.setGoodsDetailInfoDto(new HashMap<String, Object>(produce));
		return dataSet;
	}

	@Override
	public GoodsDetailDataSetDto getGoodsDetailInfoByDocId(HttpServletRequest request, String docId) {
		if (request.getAttribute("c") != null) {
			String c = request.getAttribute("c") == null ? Sf1Helper.CONTEXT_COLLECTION : request.getAttribute("c").toString();
			return getGoodsDetailInfoByDocId(request, docId, c);
		}
		return getGoodsDetailInfoByDocId(request, docId, Sf1Helper.CONTEXT_COLLECTION);
	}

	@Override
	public GoodsDetailDataSetDto getGoodsDetailInfoByDocId(HttpServletRequest request, String docId, String collection) {
		GoodsDetailDataSetDto dataSet = new GoodsDetailDataSetDto();
		Map<String, String> produce = null;
		try {
			produce = getProduceBySf1r(docId, collection);
			if (produce == null)
				return dataSet;
		} catch (Exception e) {
			LOG.error("获取商品信息失败, docid=" + docId + " , 失败原因 : " + e.getMessage());
			return dataSet;
		}
		if (!StringTools.isEmpty(produce.get("isSnap"))) {
			dataSet.setSnap(true);
		}
		produce.put("originUrl", produce.get("Url"));
		produce.put("Url", GoodsUtils.redirectUrl(request.getCookies(), docId, produce.get("Url")));
		ImageUtils.replaceImgUrl(produce);
		dataSet.setDocId(docId);
		// 产品属性设置
		dataSet.setAttributes(getProduceAttrByShop(produce));
		// 设置CategoryValue
		dataSet.setCategoryValue(produce.get(Sf1Constants.FIELD_APP_CATEGORY));
		// 设置商品信息
		dataSet.setGoodsDetailInfoDto(new HashMap<String, Object>(produce));
		return dataSet;
	}

	// 设置商家数量
	private void setSourceCount(Map<String, String> produce) {
		String itemCount = produce.get("itemCount");
		if (!StringUtils.isEmpty(itemCount)) {
			int itemInt = Integer.valueOf(itemCount);
			if (itemInt > 1) {
				String source = produce.get("Source");
				produce.put("SourceCount", String.valueOf(source.split(",").length));
			}
		}
	}

	public List<Map<String, String>> SearchUUID(HttpServletRequest request, String docId, GoodsDetailSearchCndDto cnd) {
		List<Map<String, String>> resources = doSearch(docId, cnd);
		if (resources.isEmpty())
			return null;
		for (Map<String, String> resource : resources) {
			resource.put("OrigUrl", resource.get("Url"));
			resource.put("Url", GoodsUtils.redirectUrl(request.getCookies(), docId, resource.get("Url")));
		}
		ImageUtils.replaceImgUrl(resources);
		return resources;
	}

	@Override
	public GoodsDetailDataSetDto getSF1RProduce(HttpServletRequest request, String docId, GoodsDetailSearchCndDto cnd) {
		GoodsDetailDataSetDto dataSet = new GoodsDetailDataSetDto();
		Map<String, SuppliserDto> suppliserMap = getSuppliserMap();
		// 获取相关商品信息
		List<Map<String, String>> resources = SearchUUID(request, docId, cnd);
		if (resources.isEmpty())
			return dataSet;
		Map<String, String> needRemove = null;
		Map<String, String> lowerIndex = resources.get(0);
		for (Map<String, String> resource : resources) {
			resource.put("DocId", resource.get("DOCID"));
			String priceStr1 = resource.get("Price");
			if (priceStr1.indexOf("-") > 0 || resource.get("DOCID").equals(docId)) {
				needRemove = resource;
				continue;
			}
			String lowerStr1 = lowerIndex.get("Price");
			if (lowerStr1.indexOf("-") > 0) {
				lowerStr1 = "";
			}
			if (StringUtils.isEmpty(priceStr1))
				continue;
			if (StringUtils.isEmpty(lowerStr1)) {
				lowerIndex = resource;
				continue;
			}
			if (new BigDecimal(priceStr1).compareTo(new BigDecimal(lowerStr1)) < 0) {
				lowerIndex = resource;
			}
		}
		if (needRemove != null) {
			resources.remove(needRemove);
		}
		dataSet.setLowestSource(lowerIndex);
		// 按商家分组数据
		List<ShopInfoDto> shopInfoList = getProductGroupBySource2(request, resources);
		// 获得商家的详细信息 price
		setShopInfo(request, shopInfoList, suppliserMap);
		dataSet.setShopInfoList(shopInfoList);
		return dataSet;
	}

	@Override
	public List<Map<String, String>> getSF1RProduceLimit(HttpServletRequest request, String docId, final GoodsDetailSearchCndDto cnd, Integer num) {
		// 获取相关商品信息
		List<Map<String, String>> resources = doSearch(docId, cnd);
		if (resources.isEmpty())
			return new ArrayList<Map<String, String>>();
		if (resources.size() > num) {
			resources = resources.subList(0, num);
		}
		for (Map<String, String> resource : resources) {
			resource.put("Url", GoodsUtils.redirectUrl(request.getCookies(), docId, resource.get("Url")));
		}
		ImageUtils.replaceImgUrl(resources);
		return resources;
	}

	public List<Map<String, String>> getSF1RProduceSingleSource(HttpServletRequest request, String docId, GoodsDetailSearchCndDto cnd, String source) {
		SF1SearchBean sf1rBean = setSearchBeanByDto(docId, cnd);
		if (!StringUtils.isEmpty(source)) {
			sf1rBean.addCondition("Source", "=", source);
		}
		SearchDTO result = sf1Query.doSearch(sf1rBean);
		List<Map<String, String>> resources = SUIAppTools.getGoodsCurrentResultList(result.getResourcesList());
		for (Map<String, String> resource : resources) {
			resource.put("OrigUrl", resource.get("Url"));
			resource.put("Url", GoodsUtils.redirectUrl(request.getCookies(), docId, resource.get("Url")));
		}
		ImageUtils.replaceImgUrl(resources);
		return resources;
	}

	@Override
	public List<ShopInfoDto> getRecommandProduces(HttpServletRequest req, String docid, String keyword, Integer pageSize, String attr) {
		List<Map<String, String>> result = recommandProduces(docid, keyword, pageSize, attr);
		List<ShopInfoDto> list = getProductGroupBySource(req, result, attr);
		Map<String, SuppliserDto> suppliserMap = getSuppliserMap();
		setShopInfo(req, list, suppliserMap);
		String filter = req.getParameter("filter");
		if (!StringUtils.isEmpty(filter) && "true".equals(filter)) {
			filterRecommandShopList(list);
		}
		return list;
	}

	@Override
	public List<Map<String, String>> recommandProduces(String docid, String keyword, Integer pageSize, String attr) {
		List<Map<String, String>> result = recommandProduces(docid, keyword, pageSize, attr, "0.7");
		if (result.size() < pageSize) {
			List<Map<String, String>> result2 = recommandProduces(docid, keyword, pageSize, attr, "0.1");
			for (Map<String, String> map : result2) {
				if (!result.contains(map)) {
					result.add(map);
				}
			}
		}
		if (result.size() > pageSize)
			result.subList(0, pageSize);
		return result;
	}

	@Override
	public List<Map<String, String>> recommandProduces(String docid, String keyword, Integer pageSize, String attr, String fuzzy) {
		SF1SearchBean sf1rBean = null;
		if (attr == null) {
			sf1rBean = createRecommandProduceSF1SearchBean(keyword, pageSize, fuzzy);
		} else {
			sf1rBean = createRecommandExchangeP(keyword, pageSize, fuzzy);
		}
		List<Map<String, String>> result = sf1Query.doSearch(sf1rBean).getResourcesList();
		if (attr == null) {
			removeSameProduce(docid, result);
		}
		removeSource(result);
		ImageUtils.replaceImgUrl(result);
		return result;
	}

	private void removeSource(List<Map<String, String>> result) {
		for (int i = 0; i < result.size();) {
			Map<String, String> map = result.get(i);
			if ("淘宝网".equals(map.get("Source"))) {
				result.remove(map);
				continue;
			}
			i++;
		}
	}

	/**
	 * 相关商品的每一个商家只要一个商品
	 * 
	 * @param list
	 */
	private static void filterRecommandShopList(List<ShopInfoDto> list) {
		for (ShopInfoDto shopInfoListDto : list) {
			List<Map<String, String>> shopList = shopInfoListDto.getShopList();
			if (shopList.size() > 1) {
				shopInfoListDto.setShopList(shopList.subList(0, 1));
			}
		}
	}

	private SF1SearchBean createRecommandProduceSF1SearchBean(String keyword, Integer pageSize, String fuzzy) {
		SF1SearchBean sf1rBean = new SF1SearchBean();
		if (StringUtils.isBlank(keyword)) {
			keyword = "*";
		}
		sf1rBean.setKeywords(keyword);
		sf1rBean.setLimit(20);
		sf1rBean.setOffset(0);
		sf1rBean.getGroupBeans().clear();
		sf1rBean.setCollection("b5mo");
		sf1rBean.addSearchIn("Title");
		sf1rBean.setGetAttr(new GetAttr(false, 20));
		sf1rBean.setRequireRelated(false);
		Map<String, Object> modeMap = new HashMap<String, Object>();
		modeMap.put("mode", "suffix");
		modeMap.put("lucky", 1000);
		modeMap.put("use_fuzzy", true);
		modeMap.put("use_fuzzyThreshold", true);
		modeMap.put("fuzzy_threshold", fuzzy);
		modeMap.put("tokens_threshold", "1");
		sf1rBean.setSearchMode(modeMap);
		return sf1rBean;
	}

	private SF1SearchBean createRecommandExchangeP(String keyword, Integer pageSize, String fuzzy) {
		SF1SearchBean sf1rBean = new SF1SearchBean();
		if (StringUtils.isBlank(keyword)) {
			keyword = "*";
		}
		sf1rBean.addCondition("itemcount", "<=", "1");
		sf1rBean.addCondition("Price", ">=", "10");
		sf1rBean.setKeywords(keyword);
		sf1rBean.setLimit(pageSize);
		sf1rBean.setOffset(0);
		sf1rBean.getGroupBeans().clear();
		sf1rBean.setCollection("b5mo");
		sf1rBean.addSearchIn("Title");
		sf1rBean.setGetAttr(new GetAttr(false, 20));
		sf1rBean.setRequireRelated(false);
		Map<String, Object> modeMap = new HashMap<String, Object>();
		modeMap.put("mode", "suffix");
		modeMap.put("lucky", 1000);
		modeMap.put("use_fuzzy", true);
		modeMap.put("use_fuzzyThreshold", true);
		modeMap.put("fuzzy_threshold", fuzzy);
		modeMap.put("tokens_threshold", "1");
		sf1rBean.setSearchMode(modeMap);
		return sf1rBean;
	}

	// 移除与大图相同的产品,如果不存在与大图相同的产品，移除最后一个产品
	private void removeSameProduce(String docid, List<Map<String, String>> result) {
		if (StringTools.isEmpty(docid))
			return;
		int resultLength = result.size();
		for (int i = 0; i < resultLength; i++) {
			String resultDocid = result.get(i).get("DocId");
			if (docid.equals(resultDocid) || (i == result.size() - 1)) {
				result.remove(i);
				return;
			}
		}
	}

	@Override
	public Map<String, String> getProduceBySf1r(String docid, String collection) {
		// 保存快照
		SF1SearchBean sf1rBean = new SF1SearchBean();
		sf1rBean.setCollection(collection);
		sf1rBean.setCondLst(CollectionTools.newList(new CondSearchBean("DOCID", "=", docid)));
		SearchDTO produceSearchDTO = null;
		try {
			produceSearchDTO = sf1Query.doGet(sf1rBean);
			if (!isCompare(produceSearchDTO)) {// 保存非比价的商品
				SnapshotTools.saveSnapshot(docid, collection);
			}
		} catch (Exception e) {
			produceSearchDTO = new SearchDTO();
			LOG.error("failed to get document, season ==> " + e.getMessage());
		}
		String isSnap = "";
		if (produceSearchDTO.getResourcesList().isEmpty()) {
			produceSearchDTO = findBySnap(docid, collection);
			if (isCompare(produceSearchDTO)) {// 如果是比价的 则返回为空
				produceSearchDTO = new SearchDTO();
			}
			isSnap = "true";
		}
		if (produceSearchDTO.getResourcesList().isEmpty())
			return null;
		Map<String, String> produce = SUIAppTools.getGoodsCurrentResultList(produceSearchDTO.getResourcesList()).get(0);
		setSourceCount(produce);
		setKeyword(produce);
		produce.put("isSnap", isSnap);
		return produce;
	}

	private boolean isCompare(SearchDTO produceSearchDTO) {
		if (produceSearchDTO == null)
			return true;
		if (produceSearchDTO.getDocResourceDtos().isEmpty())
			return true;
		DocResourceDto docResourceDto = produceSearchDTO.getDocResourceDtos().get(0);
		String price = docResourceDto.getRes().get("Price");
		return price.indexOf("-") > 0;
	}

	@Override
	public Map<String, String> getProduceBySf1r(String docid) {
		return getProduceBySf1r(docid, Sf1Helper.CONTEXT_COLLECTION);
	}

	private Map<String, String> getProduceAttr(Map<String, String> produce) {
		Map<String, String> attrsMap = new HashMap<String, String>();
		String attrStr = produce.get("Attribute");
		if (!StringUtils.isEmpty(attrStr)) {
			String[] attrs = attrStr.split(",|，");
			for (String attr : attrs) {
				String[] a = attr.split(":");
				if (a.length < 2) {
					continue;
				}
				attrsMap.put(a[0], a[1]);
			}
		}
		return attrsMap;
	}

	private Map<String, String> getProduceAttrByShop(Map<String, String> produce) {
		Map<String, String> attrsMap = new HashMap<String, String>();// 品牌:Apple/苹果,MacBook
																		// Air,型号:MC505CH
		String attrStr = produce.get("Attribute");
		if (!StringUtils.isEmpty(attrStr)) {
			String[] attrs = attrStr.split(",|，");
			for (String attr : attrs) {
				String[] a = attr.split(":");
				if (a.length < 2) {
					continue;
				}
				attrsMap.put(a[0], a[1]);
			}
		}
		return attrsMap;
	}

	// 设置查询关键字
	private void setKeyword(Map<String, String> produce) {
		// 关键字当存在分类时，设置为分类
		String keyword = produce.get("Category");
		if (StringUtils.isNotBlank(keyword)) {
			setKeyword(produce, keyword);
		} else {// 当分类为空时，关键字设置为商家名
			keyword = produce.get("Source");
			if (StringUtils.isNotBlank(keyword)) {
				setKeyword(produce, keyword.split(",")[0]);
			} else {
				setKeyword(produce, produce.get("Title"));
			}
		}
	}

	private void setKeyword(Map<String, String> produce, String keyword) {
		produce.put("Keyword", keyword);
	}

	@Override
	public List<Map<String, String>> getProduceImgs(String docId, GoodsDetailSearchCndDto cnd) {
		SF1SearchBean sf1rBean = setSearchBeanByDto(docId, cnd);
		sf1rBean.setLimit(100);
		sf1rBean.getSelectList().clear();
		sf1rBean.addSelect("Picture", "Picture");
		sf1rBean.addSelect("Source", "Source");
		SearchDTO result = sf1Query.doSearch(sf1rBean);
		List<Map<String, String>> list = SUIAppTools.getGoodsCurrentResultList(result.getResourcesList());
		list = dealRange(list);
		return list;
	}

	protected List<Map<String, String>> dealRange(List<Map<String, String>> list) {
		List<Map<String, String>> newList = new ArrayList<Map<String, String>>(list.size());
		int length = list.size();
		for (int index = 0; index < length;) {
			Map<String, String> map = list.get(index);
			if ("淘宝网".equals(map.get(Sf1Constants.FIELD_APP_SOURCE)) || "天猫".equals(map.get(Sf1Constants.FIELD_APP_SOURCE))) {
				newList.add(map);
				list.remove(index);
				length--;
			} else {
				index++;
			}
		}
		list.addAll(newList);
		return list;
	}

	private SF1SearchBean setSearchBeanByDto(String docId, GoodsDetailSearchCndDto cnd) {
		return setSearchBeanByDto(docId, 4000, cnd);
	}

	private SF1SearchBean setSearchBeanByDto(String docId, int limit, GoodsDetailSearchCndDto cnd) {
		SF1SearchBean sf1rBean = new SF1SearchBean();
		// 初始化sf1r搜索bean
		sf1rBean.setKeywords("*");
		sf1rBean.getGroupBeans().clear();
		sf1rBean.setLimit(limit);
		sf1rBean.setCollection(Sf1Helper.CONTEXT_COLLECTION);
		sf1rBean.addSearchIn("Title");
		sf1rBean.setGetAttr(new GetAttr(false, 20));
		sf1rBean.setRequireRelated(false);
		sf1rBean.setNeedSearchMode(false);
		if (cnd != null) {
			if (StringUtils.isNotBlank(cnd.getSortField()) && StringUtils.isNotBlank(cnd.getSortType())) {
				sf1rBean.addSort(cnd.getSortField(), cnd.getSortType());
			}
		}
		// 设置数据集和搜索条件
		sf1rBean.addCondition("uuid", "=", docId);
		return sf1rBean;
	}

	// 获得比价商品
	private List<Map<String, String>> doSearch(String docId, GoodsDetailSearchCndDto cnd) {
		SF1SearchBean sf1rBean = setSearchBeanByDto(docId, cnd);
		SearchDTO result = sf1Query.doSearch(sf1rBean);
		return SUIAppTools.getGoodsCurrentResultList(result.getResourcesList());
	}

	private Map<String, SuppliserDto> getSuppliserMap() {
		List<SuppliserDto> suppliserDtos = supplierService.findAllSuppliser();
		Map<String, SuppliserDto> map = new HashMap<String, SuppliserDto>();
		for (SuppliserDto suppliserDto : suppliserDtos) {
			map.put(suppliserDto.getName(), suppliserDto);
		}
		return map;
	}

	/**
	 * 查询商家商品 重新定义输出的数据格式
	 * 
	 * @param resources
	 * @return
	 */
	private List<ShopInfoDto> getProductGroupBySource2(HttpServletRequest req, List<Map<String, String>> resources) {
		List<ShopInfoDto> shopInfoList = new ArrayList<ShopInfoDto>();
		ShopInfoDto dto = null;
		for (Map<String, String> map : resources) {
			String source = map.get("Source");
			// 如果来源已经存在，在来源中添加商品，否则添加新的来源
			int index = isContainShop(source, shopInfoList);
			if (index != -1) {
				dto = shopInfoList.get(index);
				dto.setShopListSize(dto.getShopListSize() + 1);
				if (source.equals("淘宝网") && dto.getShopListSize() < 10)
					dto.getShopList().add(map);
			} else {
				dto = new ShopInfoDto();
				dto.setShopName(source);
				dto.getShopList().add(map);
				shopInfoList.add(dto);
				dto.setShopListSize(1);
			}
			replaceUrlWithCps(req, map, source);
		}
		return shopInfoList;
	}

	@SuppressWarnings({ "unchecked" })
	private Map<String, List<ShopInfoDto>> getProductGroupByNorms(List<Map<String, Object>> mapList) {
		Map<String, List<ShopInfoDto>> result = new HashMap<String, List<ShopInfoDto>>();
		for (int i = 0; i < mapList.size(); i++) {
			Map<String, Object> normItem = mapList.get(i);
			String norm = normItem.get("name").toString();
			List<Map<String, String>> resources = (List<Map<String, String>>) normItem.get("docs");
			ShopInfoDto dto = null;
			List<ShopInfoDto> shopInfoList = new ArrayList<ShopInfoDto>();
			for (Map<String, String> resource : resources) {
				String name = resource.get("Source");
				dto = new ShopInfoDto();
				dto.setShopName(name);
				dto.getShopList().add(resource);
				shopInfoList.add(dto);
			}
			result.put(norm, shopInfoList);
		}
		return result;
	}

	/**
	 * 查询商家商品 重新定义输出的数据格式
	 * 
	 * @param resources
	 * @return
	 */
	@Override
	public List<ShopInfoDto> getProductGroupBySource(HttpServletRequest req, List<Map<String, String>> resources, String attr) {
		List<ShopInfoDto> shopInfoList = new ArrayList<ShopInfoDto>();
		ShopInfoDto dto = null;
		for (Map<String, String> map : resources) {
			String source = map.get("Source");
			if (attr != null)
				continue;
			// 如果来源已经存在，在来源中添加商品，否则添加新的来源
			if (!exists(shopInfoList, source)) {
				dto = new ShopInfoDto();
				List<Map<String, String>> newProductList = new ArrayList<Map<String, String>>();
				dto.setShopName(source);
				dto.setShopList(newProductList);
				shopInfoList.add(dto);
			}
			// dto.setDocId(map.get(ComConstants.FIELD_APP_DOCID));
			dto.getShopList().add(map);
			replaceUrlWithCps(req, map, source);
		}
		return shopInfoList;
	}

	private boolean exists(List<ShopInfoDto> shopInfoList, String source) {
		for (ShopInfoDto shopInfoDto : shopInfoList) {
			if (source.equals(shopInfoDto.getShopName()))
				return true;
		}
		return false;
	}

	/**
	 * description replace with cps
	 * 
	 * @param map
	 * @param shopName
	 * @author echo weng
	 * @since 2013-6-8
	 * @mail echo.weng@b5m.com
	 */
	private void replaceUrlWithCps(HttpServletRequest req, Map<String, String> map, String shopName) {
		String shopUrl = map.get("Url");
		if (!StringUtils.isEmpty(shopUrl)) {
			map.put("OrigUrl", map.get("Url"));
			map.put("Url", GoodsUtils.redirectUrl(req.getCookies(), map.get("DOCID"), map.get("Url")));
		}
	}

	/**
	 * 判断是否包含商家
	 * 
	 * @param name
	 * @param shopInfoList
	 * @return
	 */
	private static int isContainShop(String name, List<ShopInfoDto> shopInfoList) {
		int length = shopInfoList.size();
		for (int index = 0; index < length; index++) {
			if (name.equals(shopInfoList.get(index).getShopName())) {
				return index;
			}
		}
		return -1;
	}

	/**
	 * 设置商家的图片url，cps等信息，将韩国馆的信息设置排序
	 * 
	 * @param shopInfoList
	 * @param suppliserMap
	 */
	private void setShopInfo(HttpServletRequest request, List<ShopInfoDto> shopInfoList, Map<String, SuppliserDto> suppliserMap) {
		List<ShopInfoDto> chinalist = new ArrayList<ShopInfoDto>();
		Map<String, Suppliser> supplisersMapKeyIsName = getSupplierMapKeyIsName();
		Map<String, Suppliser> supplisersMapKeyIsUrl = getSupplierMapKeyIsUrl();

		for (ShopInfoDto shopInfoListDto : shopInfoList) {
			SuppliserDto splDto = suppliserMap.get(shopInfoListDto.getShopName());

			if (splDto != null) {
				// 设置商家活动
				shopInfoListDto.setShopTag(splDto.getSpecialService());
				shopInfoListDto.setShopId(String.valueOf(splDto.getId()));
				shopInfoListDto.setLogoImgUrl(splDto.getLogo());
				shopInfoListDto.setUrl(GoodsUtils.redirectUrl(request.getCookies(), "", splDto.getUrl()));
				Suppliser suppliser = supplisersMapKeyIsName.get(shopInfoListDto.getShopName());
				if (suppliser == null) {
					suppliser = supplisersMapKeyIsUrl.get(shopInfoListDto.getUrl());
				}
				if (suppliser != null) {
					shopInfoListDto.setSourceName(suppliser.getName());
				}
			} else {
				// 当商家的url为空时 就从商品的url进行截取
				fillShopUrlIsEmpty(shopInfoListDto);
			}
			chinalist.add(shopInfoListDto);
		}
		// 商家地址url替换
		shopInfoList.clear();
		shopInfoList.addAll(chinalist);
	}

	private Map<String, Suppliser> getSupplierMapKeyIsName() {
		List<Suppliser> supplisers = newSuppliserService.listSuppliser();
		Map<String, Suppliser> map = new HashMap<String, Suppliser>();
		for (Suppliser suppliser : supplisers) {
			map.put(suppliser.getName(), suppliser);
		}
		return map;
	}

	private Map<String, Suppliser> getSupplierMapKeyIsUrl() {
		List<Suppliser> supplisers = newSuppliserService.listSuppliser();
		Map<String, Suppliser> map = new HashMap<String, Suppliser>();
		for (Suppliser suppliser : supplisers) {
			map.put(suppliser.getUrl(), suppliser);
		}
		return map;
	}

	/**
	 * description 当商家的url为空时 就从商品的url进行截取
	 * 
	 * @param shopInfoListDto
	 * @author echo weng
	 * @since 2013-6-9
	 * @mail echo.weng@b5m.com
	 */
	private void fillShopUrlIsEmpty(ShopInfoDto shopInfoListDto) {
		if (StringUtils.isEmpty(shopInfoListDto.getUrl())) {
			List<Map<String, String>> goodsList = shopInfoListDto.getShopList();
			if (goodsList.isEmpty())
				return;
			Map<String, String> goods = goodsList.get(0);
			String goodsUrl = goods.get("Url");
			if (StringUtils.isEmpty(goodsUrl))
				return;
			// http://old.b5m.com/suiProductSender.htm?method=goToCps&splName=%E8%8B%8F%E5%AE%81%E6%98%93%E8%B4%AD&url=http://www.suning.com/emall/prd_10052_10051_-7_1214113_.html
			int indexOfUrl = goodsUrl.indexOf("url=");
			if (indexOfUrl < 0)
				return;
			goodsUrl = goodsUrl.substring(indexOfUrl + "url=".length());
			if (goodsUrl.indexOf("//") > 0) {// http://的长度是7 所以从第8开始截取
				shopInfoListDto.setUrl(goodsUrl.substring(0, goodsUrl.indexOf("/", 8)));
			}
		}
	}

	@Override
	public List<Map<String, String>> getGoodsDetailByDocId(String[] docIds) {
		SF1SearchBean searchBean = new SF1SearchBean();
		searchBean.addCondition("DOCID", "in", docIds);
		searchBean.setCollection(Sf1Helper.CONTEXT_COLLECTION);
		try {
			return sf1Query.doSearch(searchBean).getResourcesList();
		} catch (Exception e) {
			LOG.info("根据浏览记录没有找到相关的商品");
		}
		return null;
	}

	// 如果有比价的，设置比价的链接，同时设置keyword
	private static void setCompareUrl(List<Map<String, String>> resultList, String keyword) {
		for (Map<String, String> map : resultList) {
			String str = map.get("Source");
			if (StringUtils.isNotBlank(str) && str.split(",").length > 1) {
				String docIdStr = map.get("DocId");
				map.put("compareUrl", "item/" + docIdStr + ".html");
				map.put("keyword", keyword);
			}
		}
	}

	/**
	 * 根据关键字搜索
	 * 
	 * @param keyword
	 * @param num
	 * @return
	 */
	private List<Map<String, String>> getProductByKeyword(String keyword, int num) {
		SF1SearchBean searchBean = new SF1SearchBean();
		searchBean.setKeywords(keyword);
		searchBean.setLimit(num);
		searchBean.getGroupBeans().clear();
		searchBean.setCollection(Sf1Helper.CONTEXT_COLLECTION);
		searchBean.addSearchIn("Title");
		searchBean.setGetAttr(new GetAttr(false, 20));
		searchBean.setRequireRelated(false);
		return sf1Query.doSearch(searchBean).getResourcesList();
	}

	public List<Map<String, String>> getRelateProduces(List<String> docIdArray, List<String> keywordArray, int count) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		int num = 12;
		switch (keywordArray.size()) {
		case 2:
			num = 6;
			break;
		case 3:
			num = 4;
			break;
		case 4:
			num = 3;
			break;
		default:
			break;
		}
		for (String keyword : keywordArray) {
			List<Map<String, String>> list = getProductByKeyword(keyword, num);
			setCompareUrl(list, keyword);
			resultList.addAll(list);
		}
		for (String docId : docIdArray) {
			removeSameProduce(docId, resultList);
		}
		// 如果数量不够，则根据最热词汇查找
		if (resultList.size() < count) {
			List<Map<String, String>> hotKeywordList = new ArrayList<Map<String, String>>();
			String hotword = "iphone";
			hotKeywordList.addAll(getProductByKeyword(hotword, 16));
			// 过滤相同数据
			for (String docId : docIdArray) {
				removeSameProduce(docId, hotKeywordList);
			}
			int needCount = count - resultList.size();
			int endIndex = needCount > hotKeywordList.size() ? hotKeywordList.size() : needCount;
			hotKeywordList = hotKeywordList.subList(0, endIndex);
			setCompareUrl(hotKeywordList, hotword);
			resultList.addAll(hotKeywordList);
		} else {
			return resultList.subList(0, count);
		}

		return resultList;
	}

	public SearchDTO findBySnap(String docId, String collection) {
		if (StringUtils.isEmpty(collection)) {
			collection = Sf1Helper.CONTEXT_COLLECTION;
		}
		String filePath = path(docId, collection);
		try {
			String result = WebTools.executeGetMethod(filePath);
			if (StringTools.isEmpty(result)) {
				return null;
			}
			JSONObject jsonObject = JSON.parseObject(result);
			SearchDTO searchDTO = new SearchDTO();
			searchDTO.setSnapshot(true);
			Sf1DataHelper.setResources(searchDTO, null, jsonObject);
			return searchDTO;
		} catch (Exception e) {
			LOG.warn("获取商品快照信息不存在, docid=" + docId);
			return null;
		}
	}

	protected String path(String docid, String collection) {
		docid = new String(docid.getBytes());
		StringBuilder sb = new StringBuilder(FileUtil.getSearchDetailPath());
		sb.append(collection).append("/");
		sb.append(randomPath(docid)).append("/");
		if (!StringUtils.isEmpty(docid)) {
			sb.append(docid);
		}
		return sb.toString();
	}

	protected String randomPath(String docId) {
		int hashCode = HashCodeBuilder.reflectionHashCode(docId);
		int result = hashCode % 10;
		if (result < 0)
			result = -result;
		return String.valueOf(result);
	}

	public Object[] queryDetailInfo(final String docId) {
		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		tasks.add(new Callable<Object>() {
			@Override
			public Object call() {
				try {
					long size = hbaseDataService.getCommentSize(docId, CommentType.ALL);
					return size;
				} catch (Exception e) {
					return 0;
				}
			}
		});
		tasks.add(new Callable<Object>() {
			@Override
			public Object call() {
				try {
					PriceTrend priceTrend = priceTrendService.singlePriceTrend(90, docId, false, null);
					int type = PriceTrendUtils.getPriceType(priceTrend, null);
					return type;
				} catch (Exception e) {
					return 0;
				}
			}
		});
		tasks.add(new Callable<Object>() {
			@Override
			public Object call() {
				try {
					return hbaseDataService.getTag(docId);
				} catch (Exception e) {
					return new ArrayList<String>();
				}
			}
		});
		return ThreadTools.executor(tasks, Object.class, threadPool, 20000l);
	}

}
