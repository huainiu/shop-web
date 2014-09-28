package com.b5m.web.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.dto.Message;
import com.b5m.bean.dto.Msg;
import com.b5m.bean.dto.SkuRequest;
import com.b5m.bean.dto.goodsdetail.GoodsDetailSearchCndDto;
import com.b5m.bean.dto.goodsdetail.ShopInfoDto;
import com.b5m.bean.entity.Comment;
import com.b5m.bean.entity.HotKeywords;
import com.b5m.bean.entity.Impress;
import com.b5m.bean.entity.MallBrandInfo;
import com.b5m.bean.entity.Suppliser;
import com.b5m.common.env.GlobalInfo;
import com.b5m.common.env.GlobalWebCfg;
import com.b5m.common.log.LogUtils;
import com.b5m.common.utils.ConfigPropUtils;
import com.b5m.common.utils.LoginHelper;
import com.b5m.common.utils.PWCode;
import com.b5m.common.utils.TradeHttpUtil;
import com.b5m.common.utils.UcMemCachedUtils;
import com.b5m.dao.domain.page.PageView;
import com.b5m.frame.pojo.UserCenter;
import com.b5m.service.goodsdetail.GoodsDetailService;
import com.b5m.service.hbase.HbaseDataService;
import com.b5m.service.hbase.bean.CommentType;
import com.b5m.service.ontimeprice.OntimeService;
import com.b5m.service.ontimeprice.SkuBean;
import com.b5m.service.pricetrend.PricePerDay;
import com.b5m.service.pricetrend.PriceTrend;
import com.b5m.service.pricetrend.PriceTrendService;
import com.b5m.service.pricetrend.PriceTrendUtils;
import com.b5m.service.www.CommentService;
import com.b5m.service.www.HotKeywordsService;
import com.b5m.service.www.ImpressService;
import com.b5m.service.www.MallBrandInfoService;
import com.b5m.service.www.SuppliserService;
import com.b5m.sf1.helper.Sf1Helper;
import com.b5m.web.controller.base.AbstractBaseController;
import com.b5m.web.filter.KeywordFilter;

/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-7-26
 * @email echo.weng@b5m.com
 */
@Controller
@RequestMapping("/api")
public class ApiController extends AbstractBaseController {
	public final String PREFIX_LAST_COMMENT = "last_comment";
	public final String PREFIX_CLICK_IMPRESS = "last_click_impress";
	public final String PREFIX_IMPRESS = "last_impress";
	public final String DEFAULT_AVATAR = "";
	private ExecutorService executorService = Executors.newFixedThreadPool(100);

	@Autowired
	@Qualifier("pCommentService")
	private CommentService commentService;
	@Autowired
	@Qualifier("newSuppliserCacheService")
	private SuppliserService suppliserService;
	@Autowired
	@Qualifier("pImpressService")
	private ImpressService impressService;
	@Autowired
	private GoodsDetailService goodsDetailService;
	@Resource
	private PriceTrendService priceTrendService;
	@Resource
	private MallBrandInfoService mallBrandInfoService;
	@Resource
	private HbaseDataService hbaseDataService;
	@Autowired
	private OntimeService ontimeService;
	@Resource
	private HotKeywordsService hotKeywordsService;

	private Logger log = Logger.getLogger(getClass());

	@RequestMapping(value = "/impress/list", method = RequestMethod.GET)
	@ResponseBody
	public void impressList(HttpServletRequest request, Integer size, Long supplierId, String source) throws Exception {
		List<Impress> list = new ArrayList<Impress>();
		if (supplierId != null) {
			list = impressService.queryImpressLessLength(size, supplierId);
		} else if (!StringUtils.isEmpty(source)) {
			Suppliser suppliser = suppliserService.getSuppliserByName(source);
			list = impressService.queryImpressLessLength(size, suppliser.getId());
		}
		JSONArray result = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			Impress impress = list.get(i);
			JSONObject res = new JSONObject();
			res.put("id", impress.getId());
			res.put("content", impress.getContent());
			res.put("impressCount", impress.getImpressCount());
			result.add(res);
		}
		output(0, "success", result);
	}

	@RequestMapping(value = "/impress/click", method = RequestMethod.GET)
	@ResponseBody
	public void clickImpress(HttpServletRequest request, Long id) throws Exception {
		int code = 0;
		String message = "success";
		Map<String, Long> map = new HashMap<String, Long>();
		try {
			if (id == null) {
				code = -1;
				message = "id[" + id + "] no exist";
				output(code, message, null);
				return;
			}
			impressService.clickImpress(id);
			Impress impress = impressService.getImpress(id);
			if (impress == null) {
				code = -1;
				message = "id[" + id + "] no exist";
				output(code, message, null);
				return;
			}
			map.put("impressCount", impress.getImpressCount());
		} catch (Exception e) {
			LogUtils.error(this.getClass(), e);
			code = -1;
			message = e.getMessage();
		}
		output(code, message, map);
	}

	@RequestMapping("/comments/goodCount")
	@ResponseBody
	private void commentCount(Long supplierId, String source) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (supplierId == null) {
			Suppliser suppliser = suppliserService.getSuppliserByName(source);
			if (suppliser != null) {
				supplierId = suppliser.getId();
			}
		}
		if (supplierId != null) {
			int totalNum = commentService.queryCommentCount(supplierId, null);
			int goodNum = commentService.queryCommentCount(supplierId, 0);
			map.put("totalNum", totalNum);
			map.put("goodNum", goodNum);
		}
		if (supplierId == null) {
			output(1005, "supplier no found", false);
			return;
		}
		output(0, "success", map);
	}

	@RequestMapping("/goodsDetail/history/json")
	public void getSourcePriceHistoryJSON(ModelMap model, String docId, String url, Boolean isAll, String siteList, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.logInfoMsg(WebTools.getIpAddr(request) + "------------" + System.currentTimeMillis());
		if (docId == null) {
			if (url == null) {
				output(-1, "url is null", null);
				return;
			}
			docId = PWCode.getMD5String(url);
		}
		if (isAll == null) {
			isAll = false;
		}
		List<String> limitSource = null;
		if (!StringUtils.isEmpty(siteList)) {
			limitSource = Arrays.asList(siteList.split("\\|"));
		} else {
			limitSource = new ArrayList<String>();
		}
		JSONArray json = priceTrendService.getSourcePriceHistoryJSON(docId, 90, isAll, limitSource);
		output(0, "success", json);
	}

	@RequestMapping(value = "/comments/add")
	@ResponseBody
	public void addComment(ModelMap model, HttpServletRequest request, Long supplierId, String source, String content, int type, String userName) throws Exception {
		String ip = WebTools.getIpAddr(request);
		try {
			if (ip == null || "".equals(ip)) {
				output(1002, "未知IP", false);
				return;
			}
			if (supplierId == null) {
				Suppliser suppliser = suppliserService.getSuppliserByName(source);
				if (suppliser != null) {
					supplierId = suppliser.getId();
				}
			}
			if (supplierId == null) {
				output(1005, "supplier no found", false);
				return;
			}
			if (UcMemCachedUtils.getCache(PREFIX_LAST_COMMENT + userName) != null) {
				output(1003, "您添加评论太快", false);
				return;
			}
			boolean b = KeywordFilter.isContentKeyWords(content);
			if (b) {
				output(1004, "评论内容不通过", false);
				return;
			}
			Comment comment = new Comment();
			comment.setSupplierId(supplierId);
			comment.setContent(content);
			comment.setType(type);
			UserCenter user = LoginHelper.getUserCenter(request);
			if (user != null) {
				comment.setAvatar(user.getAvatarTransient());
				comment.setBakStr1(user.getUserId());
			}
			comment.setUser(ip);
			comment.setCreateTime(DateTools.now());
			comment.setUpdateTime(DateTools.now());
			comment.setOper(GlobalInfo.OPER_PASS);
			commentService.addComment(comment);
			UcMemCachedUtils.setCache(PREFIX_LAST_COMMENT + userName, System.currentTimeMillis(), GlobalInfo.getCommentLimitTime());
		} catch (Exception e) {
			output(1000, "server exception", false);
		}
		output(0, "", true);
	}

	@RequestMapping(value = "/shop/item")
	@ResponseBody
	public void shopDetail(String docId) throws Exception {
		Map<String, String> product = goodsDetailService.getProduceBySf1r(docId);
		output(0, "success", product);
	}

	@RequestMapping("/singlePriceTrend")
	@ResponseBody
	public void singlePriceTrendForGoodsDetail(String docId, String source, Boolean fill, Integer day, String price) throws Exception {
		if (fill == null)
			fill = false;
		if (day == null)
			day = 90;
		Map<String, String> docidSourceMapping = new HashMap<String, String>();
		docidSourceMapping.put(docId, source);
		PriceTrend priceTrend = priceTrendService.singlePriceTrend(day, docId, fill, price);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(PriceTrendUtils.convert(priceTrend, source));
		jsonObject.put("averiage", jsonArray);
		jsonObject.put("averiageType", PriceTrendUtils.getPriceType(priceTrend, price));
		if (priceTrend == null || priceTrend.getPricePerDays().isEmpty()) {
			output(0, "success", jsonObject);
			return;
		}
		PricePerDay pricePerDay = PriceTrendUtils.getPrePrice(priceTrend, priceTrend.getPricePerDays().get(priceTrend.getPricePerDays().size() - 1));
		jsonObject.put("changePrice", pricePerDay);
		jsonObject.put("nowPrice", priceTrend.getPricePerDays().get(priceTrend.getPricePerDays().size() - 1));
		output(0, "success", jsonObject);
	}

	@RequestMapping(value = "/shop/recommandProduces")
	@ResponseBody
	public void getRecommandProduces(String docId, @RequestParam(value = "title", defaultValue = "") String title, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, HttpServletRequest request) throws Exception {
		List<ShopInfoDto> shopInfoDtos = goodsDetailService.getRecommandProduces(request, docId, title, pageSize, null);
		output(0, "success", shopInfoDtos);
	}

	@RequestMapping(value = "/msg/depreciate")
	@ResponseBody
	public void depreciateMsg(final HttpServletRequest request, HttpServletResponse response, final Message message) throws Exception {
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				log.debug("发送消息的IP :" + WebTools.getIpAddr(request) + " ,   数据信息 :" + JSON.toJSONString(message));
				message.setToken(ConfigPropUtils.getSysValue("ucenter.msg.token"));
				message.setStatus(Message.UNREAD);
				message.setType(Message.NOTICE);
				message.setTime(new Date());
				message.setTitle("降价提醒<a href='" + GlobalWebCfg.getUcenterHttpUrl(request) + "?method=/user/account/favorites/index'>查看明细</a>");
				JSONObject result = TradeHttpUtil.postMsg(ConfigPropUtils.getUcenterUrl("ucenter.msg.url"), message);
				try {
					output(0, result.getString("message"), "");
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		});
	}

	@RequestMapping("/tag/brands")
	@ResponseBody
	public PageView<MallBrandInfo> tagBrands(Integer currentPage, Integer pageSize, String word) {
		if (currentPage == null || currentPage < 1)
			currentPage = 1;
		if (pageSize == null || pageSize < 1)
			pageSize = 10;
		return mallBrandInfoService.queryAllDataPage(currentPage, pageSize, 1, word);
	}

	@RequestMapping("/tag/malls")
	@ResponseBody
	public PageView<MallBrandInfo> tagMalls(Integer currentPage, Integer pageSize, String word) {
		if (currentPage == null || currentPage < 1)
			currentPage = 1;
		if (pageSize == null || pageSize < 1)
			pageSize = 10;
		return mallBrandInfoService.queryAllDataPage(currentPage, pageSize, 0, word);
	}

	@RequestMapping("/tag/keywords")
	@ResponseBody
	public PageView<MallBrandInfo> tagKeywords(Integer currentPage, Integer pageSize, String word) {
		if (currentPage == null || currentPage < 1)
			currentPage = 1;
		if (pageSize == null || pageSize < 1)
			pageSize = 10;
		return mallBrandInfoService.queryAllDataPage(currentPage, pageSize, 2, word);
	}

	// 商品详情信息
	@RequestMapping("/shop/detail")
	@ResponseBody
	public Msg queryShopDetail(String docId) {
		try {
			String detail = hbaseDataService.getProductDetail(docId);
			return Msg.newSuccInstance(detail);
		} catch (Exception e) {
			return Msg.newFailedInstance(e.getMessage());
		}
	}

	@RequestMapping("/hot/keywords")
	@ResponseBody
	public void queryHotKeywords(String channel, int size) throws Exception {
		List<HotKeywords> hotKeywords = hotKeywordsService.queryAllHotKeywords(channel);
		List<HotKeywords> newHotKeywords = hotKeywordsService.randomKeywords(hotKeywords, channel, size);
		output(newHotKeywords);
	}

	@RequestMapping("/log")
	@ResponseBody
	public void showLog(String name, HttpServletRequest request, HttpServletResponse response) {
		String filePath = getLogPath(name, request);
		try {
			InputStream inputStream = new FileInputStream(filePath);
			String logs = IOUtils.toString(inputStream);
			logs = StringUtils.replace(logs, "\n", "<br/>");
			response.getOutputStream().write(logs.getBytes());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
		}
	}

	@RequestMapping("/shop/comment")
	@ResponseBody
	public Msg queryComment(String docId, String commentType, Integer pageSize, Integer currPageNo) {
		CommentType type = CommentType.ALL;
		if (!StringUtils.isEmpty(commentType)) {
			try {
				type = CommentType.valueOf(commentType);
			} catch (RuntimeException e) {
			}
		}
		if (pageSize == null)
			pageSize = 10;
		if (currPageNo == null)
			currPageNo = 1;
		try {
			return Msg.newSuccInstance(hbaseDataService.queryCommentPage(docId, type, pageSize, currPageNo));
		} catch (Exception e) {
			return Msg.newFailedInstance(e.getMessage());
		}
	}

	@RequestMapping("/shop/allshop")
	@ResponseBody
	public Msg queryAllShop(HttpServletRequest req, String docId, String source) {
		List<Map<String, String>> resources = goodsDetailService.getSF1RProduceSingleSource(req, docId, new GoodsDetailSearchCndDto(), source);
		return Msg.newSuccInstance(goodsDetailService.getProductGroupBySource(req, resources, null));
	}

	@RequestMapping("/recommend/product")
	@ResponseBody
	public void cmsRecommend(@RequestParam(defaultValue = Sf1Helper.CONTEXT_COLLECTION) String col, @RequestParam(defaultValue = "10") Integer count, HttpServletRequest request, HttpServletResponse response) throws Exception {
		output(mallBrandInfoService.randomProduct(col, count));
	}

	@RequestMapping(value = "/ontimesku/single")
	@ResponseBody
	public Msg ontimeSku(String url, String docId, HttpServletRequest req) throws Exception {
		if (StringUtils.isEmpty(url)) {
			return Msg.newFailedInstance(new SkuBean());
		}
		if (StringUtils.isEmpty(docId) || docId.length() != 32)
			docId = PWCode.getMD5String(url);
		SkuBean result = ontimeService.querySkuProp(new SkuRequest(docId, url), docId, req);
		return Msg.newSuccInstance(result);
	}

	private String getLogPath(String name, HttpServletRequest request) {
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		if ("catalina".equals(name)) {
			return contextPath.substring(0, contextPath.indexOf("webapps")) + "logs/catalina.out";
		}
		return contextPath + "WEB-INF/logs/" + name + ".log";
	}
}
