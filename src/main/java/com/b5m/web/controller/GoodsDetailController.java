package com.b5m.web.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.Lang;
import com.b5m.base.common.utils.StringTools;
import com.b5m.bean.dto.Msg;
import com.b5m.bean.dto.goodsdetail.GoodsDetailDataSetDto;
import com.b5m.bean.dto.goodsdetail.GoodsDetailSearchCndDto;
import com.b5m.bean.dto.goodsdetail.ShopInfoDto;
import com.b5m.client.php.PHPClient;
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.utils.goodsdetail.GoodsDetailHelper;
import com.b5m.dao.domain.page.PageView;
import com.b5m.goods.promotions.dto.SuppliserDto;
import com.b5m.goods.promotions.service.ISuppliserService;
import com.b5m.service.daigou.DaigouService;
import com.b5m.service.exchange.IExchangeService;
import com.b5m.service.goodsdetail.GoodsDetailService;
import com.b5m.service.goodsdetail.GoodsUtils;
import com.b5m.service.hbase.HbaseDataService;
import com.b5m.service.hbase.bean.CommentType;
import com.b5m.service.hbase.bean.HComment;
import com.b5m.service.ontimeprice.OntimeService;
import com.b5m.service.pricetrend.PriceTrendService;
import com.b5m.sf1.Sf1Query;
import com.b5m.sf1.helper.Sf1Helper;
import com.b5m.sf1.impl.SF1NewQueryService;
import com.b5m.web.controller.base.AbstractBaseController;
import com.google.common.base.Charsets;
import com.mchange.v1.util.StringTokenizerUtils;

/**
 * 商品详细 goodDetail.htm 获取商品详细信息 goodDetail/shopInfo.htm 取得所有的商家信息
 */
@Controller
public class GoodsDetailController extends AbstractBaseController {
	private static final Log log = LogFactory.getLog(GoodsDetailController.class);
	@Autowired
	private GoodsDetailService goodsDetailService;
	@Resource
	private SF1NewQueryService sF1NewQueryService;
	@Resource
	private HbaseDataService hbaseDataService;
	@Resource(name = "properties")
	private Properties properties;
	@Resource
	@Qualifier("suppliserCachedService")
	private ISuppliserService suppliserCachedTemplateService;
	@Autowired
	private PriceTrendService priceTrendService;
	@Autowired
	private OntimeService ontimePriceService;
	@Autowired
	private DaigouService daigouService;
	@Autowired
	private IExchangeService exchangeServiceImpl;
	@Autowired
	private Sf1Query sf1Query;
	@Resource
	private PHPClient phpClient;
	@Resource(name = "sysConfig")
	private Properties sysConfig;

	private final String FLAG_EXCHANGE = "exchange";

	@RequestMapping("/goodsDetail/compare")
	public String getGoodsDetailCompare(ModelMap model, String keyword, String docId, String tabNum, @RequestParam(required = false, defaultValue = "20") Integer num, HttpServletRequest req, HttpServletResponse res) {
		GoodsDetailDataSetDto dataSet = goodsDetailService.getGoodsDetailInfo(docId);
		if (dataSet.getGoodsDetailInfoDto() == null || StringUtils.isEmpty(StringTools.toStrNotNull(dataSet.getGoodsDetailInfoDto().get("Source")))) {
			try {
				res.sendRedirect("/404.html");
			} catch (IOException e) {
				log.error(getClass(), e);
			}
		}
		model.put("dataSet", dataSet);
		if (StringTools.isEmpty(tabNum)) {
			tabNum = "5";
		}

		model.put("tabNum", tabNum);
		String category = setCategory(model, dataSet.getCategoryValue());
		// 比价页面/详情页tab的链接
		setTabLinks(model, "compare", docId);

		setTitle(Lang.toInt(tabNum), dataSet, category, model);

		// 相关最低价商品
		GoodsDetailSearchCndDto searchCndDto = new GoodsDetailSearchCndDto();
		searchCndDto.setDocId(docId);
		searchCndDto.setSortField("Price");
		searchCndDto.setSortType(GoodsDetailSearchCndDto.ASC);

		model.addAttribute("productDetail", getDetailInfo(docIds(dataSet.getGoodsDetailInfoDto())));
		List<Map<String, String>> resources = goodsDetailService.getSF1RProduceLimit(req, docId, searchCndDto, num);
		model.addAttribute("recommendList", resources);
		// boolean isDaigou =
		// daigouService.canDaigou(dataSet.getGoodsDetailInfoDto(), true, "");
		// model.addAttribute("isDaigou", isDaigou);
		return "shop-compare";
	}

	/**
	 * <font style="font-weight:bold">Description: </font> <br/>
	 * 按照 京东>亚马逊>一号店>苏宁易购>天猫 顺序返回商家信息
	 * 
	 * @author echo
	 * @email wuming@b5m.cn
	 * @since 2014年6月12日 下午4:54:52
	 * 
	 * @param resources
	 * @return
	 */
	private String[] docIds(Map<String, Object> sources) {
		String shops = properties.getProperty("display.detail.shops");
		String[] shopArray = StringTools.split(shops, ",");
		String[] docIds = new String[shopArray.length];
		Object subDocsStr = sources.get("SubDocs");
		if (subDocsStr == null)
			return docIds;

		JSONArray jsonArray = JSONObject.parseArray(subDocsStr.toString());
		if (jsonArray == null)
			return docIds;

		int length = jsonArray.size();
		// 京东商城,卓越亚马逊,当当网,1号店官网,淘宝网,天猫
		for (int l = 0; l < length; l++) {
			JSONObject jsonObject = jsonArray.getJSONObject(l);
			String source = jsonObject.getString("Source");
			String docId = jsonObject.getString("DOCID");
			for (int index = 0; index < shopArray.length; index++) {
				if (shopArray[index].equals(source)) {
					docIds[index] = docId;
				}
			}
		}
		return docIds;
	}

	private String getDetailInfo(String[] docIds) {
		try {
			for (String docId : docIds) {
				String detail = hbaseDataService.getProductDetail(docId);
				if (!StringUtils.isEmpty(detail)) {
					return detail;
				}
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	@RequestMapping("/goodsDetail/more/data")
	@ResponseBody
	public void getGoodsMore(ModelMap model, String docId, String c, HttpServletResponse res, HttpServletRequest req) throws Exception {
		req.setAttribute("c", c);
		getGoodsDetailItem(model, docId, 0.3d, null, res, req);
		res.setStatus(200);
		if (model.get("subDocs") != null) {
			output(200, "同款比价", model.get("subDocs"));
		} else {
			output(200, "同类推荐", model.get("recommand"));
		}
	}

	@RequestMapping("/goodsDetail/item/data")
	@ResponseBody
	public void getGoodsItem(ModelMap model, String docId, String c, String refer, HttpServletResponse res, HttpServletRequest req) throws Exception {
		if (refer == null) {
			req.setAttribute("c", c);
			getGoodsDetailItem(model, docId, 0.3d, null, res, req);
			output(model.get("dataSet"));
			return;
		} else if ("hot".equals(refer)) {
			output(exchangeServiceImpl.getHotProduct(docId));
			return;
		}
		outputError("无效查询, 请检查请求参数");
	}

	@RequestMapping("/goodsDetail/item")
	public String getGoodsDetailItem(ModelMap model, String docId, @RequestParam(defaultValue = "0.3") Double scope, String col, HttpServletResponse res, HttpServletRequest req) throws Exception {
		String host = req.getAttribute("c") == null ? Sf1Helper.CONTEXT_COLLECTION : req.getAttribute("c").toString();
		col = StringUtils.isEmpty(col) ? sysConfig.getProperty(host) : col;
		if (StringUtils.isEmpty(col)) {
			col = host;
			host = Sf1Helper.CONTEXT_COLLECTION;
		}
		Map<String, Object> map = goodsDetailService.getGoods(req, docId, col, scope);
		if (CollectionUtils.isEmpty(map)) {
			return "commpage/404page";
		}
		model.addAllAttributes(map);
		GoodsDetailDataSetDto dataSet = (GoodsDetailDataSetDto) map.get("dataSet");
		docId = dataSet.getGoodsDetailInfoDto().get("DOCID").toString();
		model.put("c", host);
		model.put("col", col);
		model.put("mps", sysConfig.getProperty(host + ".mps"));
		String category = setCategory(model, dataSet.getCategoryValue());
		setTitle(0, dataSet, category, model);
		if (dataSet.isSnap()) {
			return "snap";
		} 
		try {
			model.addAttribute("productDetail", hbaseDataService.getProductDetail(docId));
		} catch (Exception e) {
			logger.logError("/item detailHtml error  ------  " + e.getMessage());
		}
		return "item";
	}

	@RequestMapping("/goodsDetail/cpc/item")
	public String getCpcGoodsDetailItem(ModelMap model, String keyword, String docId, HttpServletResponse res, HttpServletRequest req) throws Exception {
		req.setAttribute("c", "cpc");
		String page = getGoodsDetailItem(model, docId, 0.3, null, res, req);
		if (page.equals("commpage/404page")) {
			req.setAttribute("c", "cpcpromote");
			page = getGoodsDetailItem(model, docId, 0.3, null, res, req);
		}
		return page;
	}

	@RequestMapping("/goodsDetail/item/{c}")
	public String getItemByCollection(@PathVariable("c") String c, String keyword, String docId, HttpServletRequest req) {
		req.setAttribute("c", c);
		return "forward:/goodsDetail/item.htm";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/goodsDetail/detailInfo")
	@ResponseBody
	public void queryGoodsDetail(ModelMap model, HttpServletRequest req, String docId, String col, @RequestParam(defaultValue = "0.3") Double scope) throws Exception {
		Map<String, Object> newProduce = new HashMap<String, Object>();
		if (StringUtils.isEmpty(col))
			col = Sf1Helper.CONTEXT_COLLECTION;
		Map<String, Object> map = goodsDetailService.getGoods(req, docId, col, scope);
		if (CollectionUtils.isEmpty(map)) {
			outputError("");
			return;
		}
		GoodsDetailDataSetDto dataSet = (GoodsDetailDataSetDto) map.get("dataSet");
		docId = dataSet.getGoodsDetailInfoDto().get("DOCID").toString();
		newProduce.put("daigouSource", dataSet.getGoodsDetailInfoDto());
		newProduce.put("highPrice", map.get("highPrice"));
		newProduce.put("subDocs", map.get("subDocs"));
		daigouService.canDaigou(newProduce, col);
		newProduce.put("clickUrl", GoodsUtils.redirectUrl(req.getCookies(), docId, newProduce.get("originUrl").toString()));
		Object[] results = goodsDetailService.queryDetailInfo(docId);
		Long size = (Long) results[0];
		if (size == null)
			size = 0l;
		newProduce.put("CommentSize", size);
		getCarriage(newProduce);
		Integer type = (Integer) results[1];
		List<String> tagList = (List<String>) results[2];
		newProduce.put("trend", type);
		newProduce.put("tagList", tagList);
		newProduce.put("col", col);
		output(0, "success", newProduce);
	}

	private void getCarriage(Map<String, Object> newProduce) {
		List<SuppliserDto> list = suppliserCachedTemplateService.findAllSuppliser();
		if (!CollectionUtils.isEmpty(list)) {
			for (SuppliserDto suppliserDto : list) {
				if (suppliserDto.getName().equals(newProduce.get("Source"))) {
					newProduce.put("carriage", suppliserDto.getCarriage());
					break;
				}
			}
		}
	}

	// 将比价页面的图片组合起来
	private String consistImages(List<Map<String, String>> images) {
		if (images == null || images.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		for (Map<String, String> image : images) {
			sb.append(image.get("Picture")).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	@RequestMapping("/goods")
	@ResponseBody
	public GoodsDetailDataSetDto getAll(String docId) {
		return goodsDetailService.getGoodsDetailInfo(docId);
	}

	/**
	 * 取得所有的商家信息
	 * 
	 * @param docId
	 * @param sortField
	 * @param sortType
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsDetail/shopInfo")
	@ResponseBody
	public void getAllShopInfo(String docId, @RequestParam(defaultValue = "Price") String sortField, @RequestParam(defaultValue = GoodsDetailSearchCndDto.ASC) String sortType, String isDaigou, String isNorms, HttpServletResponse response, HttpServletRequest req) throws Exception {
		GoodsDetailSearchCndDto searchCndDto = new GoodsDetailSearchCndDto();
		searchCndDto.setDocId(docId);
		searchCndDto.setSortField(sortField);
		searchCndDto.setSortType(sortType);
		outputVal(goodsDetailService.getSF1RProduce(req, docId, searchCndDto));
	}

	/**
	 * 取得相关商品的最低20个商品
	 * 
	 * @param docId
	 * @param sortField
	 * @param sortType
	 */
	@RequestMapping(value = "/goodsDetail/shopInfoLimit", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getAllShopInfo(String docId, @RequestParam(required = false, defaultValue = "20") Integer num, HttpServletResponse response, HttpServletRequest req) {
		GoodsDetailSearchCndDto searchCndDto = new GoodsDetailSearchCndDto();
		searchCndDto.setDocId(docId);
		searchCndDto.setSortField("Price");
		searchCndDto.setSortType(GoodsDetailSearchCndDto.ASC);
		List<Map<String, String>> resources = goodsDetailService.getSF1RProduceLimit(req, docId, searchCndDto, num);
		return resources;
	}

	@RequestMapping(value = "/goodsDetail/shopInfo/singleSource")
	@ResponseBody
	public void getSingleShopInfo(String docId, @RequestParam(defaultValue = "Price") String sortField, @RequestParam(defaultValue = GoodsDetailSearchCndDto.ASC) String sortType, String source, HttpServletRequest req) throws Exception {
		GoodsDetailSearchCndDto searchCndDto = new GoodsDetailSearchCndDto();
		searchCndDto.setDocId(docId);
		searchCndDto.setSortField(sortField);
		searchCndDto.setSortType(sortType);
		List<Map<String, String>> shopList = goodsDetailService.getSF1RProduceSingleSource(req, docId, searchCndDto, source);
		outputVal(shopList);
	}

	@RequestMapping(value = "/goodsDetail/detailsComments")
	@ResponseBody
	public void getDetailsComments(HttpServletRequest request, HttpServletResponse response, String docId, @RequestParam(defaultValue = "1") int currPageNo, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "ALL") String commentTypeStr) throws Exception {
		CommentType type = null;
		try {
			type = CommentType.valueOf(commentTypeStr);
		} catch (RuntimeException e) {
			type = CommentType.ALL;
		}
		PageView<HComment> pageView = hbaseDataService.queryCommentPage(docId, type, pageSize, currPageNo);
		outputVal(pageView);
	}

	/**
	 * description
	 * 
	 * @return
	 * @author echo weng
	 * @throws Exception
	 * @since 2013-6-5
	 * @mail echo.weng@b5m.com
	 */
	@RequestMapping(value = "/goodsDetail/recommandProduces")
	@ResponseBody
	public void getRecommandProduces(String docId, @RequestParam(value = "title", defaultValue = "") String title, @RequestParam(defaultValue = "5") Integer pageSize, HttpServletRequest req) throws Exception {
		outputVal(goodsDetailService.getRecommandProduces(req, docId, title, pageSize, null));
	}

	@RequestMapping(value = "/goodsDetail/recommandProduces/exchange")
	@ResponseBody
	public List<ShopInfoDto> getRecommandExchangeProduces(@RequestParam(value = "title", defaultValue = "") String title, @RequestParam(defaultValue = "20") Integer pageSize, HttpServletRequest req) {
		return goodsDetailService.getRecommandProduces(req, null, title, pageSize, FLAG_EXCHANGE);
	}

	@RequestMapping(value = "/php/comments")
	@ResponseBody
	public void getPHPComments(@RequestParam(required = true) String docId, @RequestParam(defaultValue = "10") Integer num) throws Exception {
		outputVal(phpClient.getComments(docId, num));
	}

	/**
	 * 得到相关商品信息
	 */
	@RequestMapping(value = "/relateGoods", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, String>> getRelateGoods(String docIds) {
		Map<String, String> map = processDocId(docIds, null);
		Set<String> docIdSet = map.keySet();
		List<String> docIdArray = new ArrayList<String>();
		List<String> keywordArray = new ArrayList<String>();

		Iterator<String> iterator = docIdSet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = map.get(key);
			docIdArray.add(key);
			if (StringUtils.isNotBlank(value) && !keywordArray.contains(value)) {
				keywordArray.add(value);
			}
		}

		List<Map<String, String>> resultList = goodsDetailService.getRelateProduces(docIdArray, keywordArray, GlobalInfo.VIEW_HISTORY_NUM);
		return resultList;
	}

	@RequestMapping(value = "/goods/detail", method = RequestMethod.GET)
	@ResponseBody
	public String getProductDetail(String docId) {
		try {
			return hbaseDataService.getProductDetail(docId);
		} catch (Exception e) {
			return "";
		}
	}

	@RequestMapping(value = "/comment/query")
	@ResponseBody
	public Msg queryCommentPage(String docId, Integer type, Integer pageSize, Integer pageCode) {
		CommentType commentType = getType(type);
		try {
			PageView<HComment> pageView = hbaseDataService.queryCommentPage(docId, commentType, pageSize, pageCode);
			return Msg.newSuccInstance(pageView);
		} catch (Exception e) {
			return Msg.newFailedInstance(null);
		}
	}

	public CommentType getType(Integer type) {
		if (type == null)
			return CommentType.ALL;
		if (type == 1)
			return CommentType.GOOD;
		if (type == 0)
			return CommentType.NORMAL;
		if (type == -1)
			return CommentType.BAD;
		return CommentType.ALL;
	}

	private Map<String, String> processDocId(String docIds, List<String> returnDocIdList) {
		String[] strArray = docIds.split(",");
		Map<String, String> map = new HashMap<String, String>();

		for (String str : strArray) {
			int index = str.indexOf("-");
			if (index != -1) {
				String docIdStr = str.substring(0, index);
				String keywordStr = str.substring(index + 1);
				map.put(docIdStr, keywordStr);
				if (returnDocIdList != null && !returnDocIdList.contains(docIdStr)) {
					returnDocIdList.add(docIdStr);
				}
			}

		}
		return map;
	}

	private String setCategory(ModelMap model, String category) {
		if (StringUtils.isEmpty(category))
			return category;
		String[] strs = StringTokenizerUtils.tokenizeToArray(category, ">");
		if (strs.length <= 0)
			return category;
		model.put("category", strs[strs.length - 1]);
		return strs[strs.length - 1];
	}

	/**
	 * 添加比价页面/详情页tab的链接
	 * 
	 * 0:allShop 1:param 2:comment 3:tabPriceTrend
	 */
	private void setTabLinks(ModelMap model, String type, String docId) {
		StringBuilder sb = new StringBuilder();
		sb.append("/").append(type).append("/");
		sb.append(docId);
		String basePath = sb.toString();
		model.put("allShopLink", basePath + "_0.html");
		model.put("paramLink", basePath + "_1.html");
		model.put("commentLink", basePath + "_2.html");
		model.put("tabPriceTrendLink", basePath + "_3.html");
		model.put("lowLink", basePath + "_4.html");
		model.put("detailLink", basePath + ".html");
	}

	private void setTitle(Integer tabNum, GoodsDetailDataSetDto dataSet, String category, ModelMap model) {
		Map<String, Object> produce = dataSet.getGoodsDetailInfoDto();
		String produceTitle = StringTools.toStrNotNull(produce.get("Title"));
		Map<String, String> attributes = dataSet.getAttributes();
		String brand = attributes.get("品牌");
		String brandModel = attributes.get(brand + "型号");
		String bm = brand + (brandModel == null ? "" : brandModel);
		String _category = (StringTools.isEmpty(category) ? "" : category + "-");
		if (tabNum == null || tabNum == 0) {
			GoodsDetailHelper.setAllShopTitle(category, _category, model, brand, brandModel, bm, produceTitle);
		} else if (tabNum == 1) {
			GoodsDetailHelper.setParamTitle(category, _category, model, brand, brandModel, bm, produceTitle);
		} else if (tabNum == 2) {
			GoodsDetailHelper.setCommentTitle(category, _category, model, brand, brandModel, bm, produceTitle);
		} else if (tabNum == 3) {
			GoodsDetailHelper.setPriceTrendTitle(category, _category, model, brand, brandModel, bm, produceTitle);
		} else if (tabNum == 4) {
			GoodsDetailHelper.setLowestPriceTitle(category, _category, model, brand, brandModel, bm, produceTitle);
		} else if (tabNum == 5) {
			GoodsDetailHelper.setDetailTitle(category, _category, model, brand, brandModel, bm, produceTitle);
		}
	}

	public static void main(String[] args) {

	}

}
