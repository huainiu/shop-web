package com.b5m.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.Lang;
import com.b5m.base.common.utils.StringTools;
import com.b5m.base.common.utils.UrlTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.dto.OntimeDetailPriceBean;
import com.b5m.bean.dto.Recharge;
import com.b5m.bean.dto.exchange.AddressDto;
import com.b5m.bean.dto.exchange.DaigouOrder;
import com.b5m.bean.dto.exchange.ExchangeDto;
import com.b5m.bean.dto.exchange.GoodsDto;
import com.b5m.client.ontimeprice.OntimeClient;
import com.b5m.common.utils.DecodeUtils;
import com.b5m.common.utils.LoginHelper;
import com.b5m.common.utils.PWCode;
import com.b5m.common.utils.TradeHttpUtil;
import com.b5m.service.exchange.IExchangeService;
import com.b5m.service.goodsdetail.GoodsDetailService;
import com.b5m.service.ontimeprice.OntimeService;
import com.b5m.sf1.helper.Sf1Helper;
import com.b5m.web.controller.base.AbstractBaseController;

@Controller
@RequestMapping("/exchange")
public class ExchangeController extends AbstractBaseController {
	private static final Log log = LogFactory.getLog(ExchangeController.class);

	@Autowired
	private IExchangeService exchangeService;

	@Autowired
	private OntimeClient ontimeClient;

	@Autowired
	private GoodsDetailService goodsDetailService;

	@Autowired
	private OntimeService ontimeService;

	@Resource(name = "sysConfig")
	private Properties properties;

	JSONObject error_user = JSON.parseObject("{\"ok\": false, \"data\": \"没有用户身份, 请先登录\"}");
	JSONObject error_good = JSON.parseObject("{\"ok\": false, \"data\": \"商品信息异常,请联系客服了解详情\"}");

	@RequestMapping(value = "/item")
	public String pageItem(@RequestParam(required = true) String docId, String c, ModelMap model, HttpServletRequest request) {
		if (StringUtils.isEmpty(c))
			c = Sf1Helper.CONTEXT_COLLECTION;
		setUserInfo(request, model);
		Map<String, Object> newProduce = new HashMap<String, Object>();
		Map<String, String> produce = goodsDetailService.getProduceBySf1r(docId, c);
		if (!validate(produce))
			return "exchange_error";
		for (String key : produce.keySet()) {
			newProduce.put(key, produce.get(key));
		}
		newProduce.put("Picture", newProduce.get("OriginalPicture"));
		newProduce.put("encodeUrl", UrlTools.urlEncode(newProduce.get("Url").toString()));
		model.put("produce", newProduce);
		return "exchange";
	}

	@RequestMapping(value = "/item/e")
	public String pageItem(ExchangeDto dto, String id, @RequestParam(defaultValue = "") String refer, ModelMap model, HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		if (id != null && "hot".equals(refer)) {
			boolean b = exchangeService.setHotInfo(id, map);
			if (!b) {
				return "exchange_error";
			}
		} else {
			if (!validate(dto))
				return "exchange_error";
			map.put("Price", dto.getPrice());
			map.put("Picture", dto.getPicture());
			map.put("Title", dto.getTitle());
			map.put("Source", dto.getSource());
			map.put("Url", dto.getUrl());
			map.put("ref", dto.getRef());
			if (!StringUtils.isEmpty(dto.getRef())){
				map.put("DocId", StringTools.MD5(dto.getUrl()));
			}
			map.put("encodeUrl", UrlTools.urlEncode(map.get("Url").toString()));
		}
		setUserInfo(request, model);
		model.put("produce", map);
		model.put("refer", refer);
		return "exchange";
	}

	@RequestMapping(value = "/item/t")
	public String pageItem2(String details, ModelMap model, HttpServletRequest request) {
		String decodeStr = null;
		try {
			decodeStr = DecodeUtils.decodeStr(URLDecoder.decode(details, "utf-8"));
		} catch (Exception e) {
			log.error("兑换解析错误:  " + e.getMessage());
			return "exchange_error";
		}
		if (decodeStr == null) {
			return "exchange_error";
		}
		String[] split = decodeStr.split("&");
		setUserInfo(request, model);
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("Title", URLDecoder.decode(split[0], "utf-8"));
			map.put("Url", URLDecoder.decode(split[4], "utf-8"));
			map.put("Picture", URLDecoder.decode(split[2], "utf-8"));
		} catch (Exception e) {
			return "exchange_error";
		}
		map.put("Price", split[1]);
		map.put("Source", split[3]);
		map.put("encodeUrl", split[4]);
		model.put("produce", map);
		model.put("refer", "b5t");
		return "exchange";
	}

	@RequestMapping(value = "/address/query")
	public String queryAddress(ModelMap model, HttpServletRequest request) throws Exception {
		String userId = getUserId(request);
		if (userId == null) {
			return "snippet/daigou_address";
		}
		JSONObject json = exchangeService.getUserAddress(userId);
		model.put("result", json);
		return "snippet/daigou_address";
	}

	@RequestMapping(value = "/address/add")
	@ResponseBody
	public void addAddress(AddressDto dto, HttpServletRequest request) throws Exception {
		String userId = getUserId(request);
		if (userId == null) {
			output(error_user);
			return;
		}
		dto.setUserId(userId);
		JSONObject json = exchangeService.updateOrAddAddress(dto);
		output(json);
	}

	@RequestMapping(value = "/address/update")
	@ResponseBody
	public void updateAddress(AddressDto dto, HttpServletRequest request) throws Exception {
		String userId = getUserId(request);
		if (userId == null) {
			output(error_user);
			return;
		}
		dto.setUserId(userId);
		JSONObject json = exchangeService.updateOrAddAddress(dto);
		output(json);
	}

	@RequestMapping(value = "/address/delete")
	@ResponseBody
	public void deleteAddress(AddressDto dto, HttpServletRequest request) throws Exception {
		String userId = getUserId(request);
		if (userId == null) {
			output(error_user);
			return;
		}
		dto.setUserId(userId);
		JSONObject json = exchangeService.deleteAddress(dto);
		output(json);
	}

	@ResponseBody
	@RequestMapping(value = "/ontimePrice")
	public void ontimePrice(String url) throws Exception {
		String price = getPrice(url, null);
		if (StringUtils.isEmpty(price)) {
			log.warn("兑换商品, 查找不到商品价格");
			outputError(error_good);
			return;
		}
		output(price);
	}

	@RequestMapping(value = "/order")
	@ResponseBody
	public void order(GoodsDto dto, HttpServletRequest request) throws Exception {
		String userId = getUserId(request);
		if (userId == null) {
			output(error_user);
			return;
		}
		dto.setUserId(userId);
		if ("hot".equals(dto.getRefer()) && dto.getDocId() != null) {
			boolean b = exchangeService.setGoodsInfo(dto);
			if (!b) {
				output(error_good);
				return;
			}
		} else {
			String price = getPrice(dto.getGoodsUrl(), dto.getSku());
			if (!StringUtils.isEmpty(price)) {
				BigDecimal b1 = new BigDecimal(price);
				BigDecimal b2 = new BigDecimal(100);
				dto.setGoodsPrice(price);
				dto.setBdNum(b1.multiply(b2).longValue() + "");
			}
		}
		JSONObject json = exchangeService.order(dto);
		output(json);
	}

	public String getUserId(HttpServletRequest request) {
		return LoginHelper.getUserId(request);
	}

	public void setUserInf(DaigouOrder daigouOrder, HttpServletRequest request) {
		String userId = getUserId(request);
		daigouOrder.setUserId(userId);
		Cookie[] cks = request.getCookies();
		if (null == cks || cks.length <= 0)
			return;
		String nickname = WebTools.getCooKieValue("nickname", cks);
		String email = WebTools.getCooKieValue("email", cks);
		String avatar = WebTools.getCooKieValue("avatar", cks);
		daigouOrder.setNickname(nickname);
		daigouOrder.setEmail(email);
		daigouOrder.setAvatar(avatar);
	}

	private String getPrice(String url, String sku) {
		String price = null;
		if (sku != null)
			price = ontimeService.getPriceFromSku(PWCode.getMD5String(url), sku);
		if (price == null) {
			OntimeDetailPriceBean ontimeDetailPriceBean = new OntimeDetailPriceBean();
			ontimeDetailPriceBean.setUrl(url);
			JSONObject rs = new JSONObject();
			try {
				rs = ontimeClient.queryDetailPrice(ontimeDetailPriceBean.toJsonString());
				price = rs.getString("Price");
			} catch (Exception e) {
				log.error("实时价格: " + e.getMessage());
			}
		}
		if (!StringUtils.isEmpty(price) && Float.parseFloat(price) > 0) {
			return new BigDecimal(price).setScale(2, RoundingMode.HALF_DOWN).toString();
		} else {
			return null;
		}
	}

	private void setUserInfo(HttpServletRequest request, ModelMap model) {
		String userId = getUserId(request);
		if (userId == null) {
			model.put("userBeans", 0);
		} else {
			Recharge obj = new Recharge();
			obj.setUserId(userId);
			JSONObject result = TradeHttpUtil.post2Msg(properties.getProperty("trade.url.insert.b5mBean.accountQuery"), obj);
			if (result == null || !result.getBooleanValue("ok")) {
				model.put("userBeans", 0);
			} else {
				model.put("userBeans", result.getJSONObject("val").getJSONObject("data").getInteger("balance"));
			}
			JSONObject json = exchangeService.getUserAddress(userId);
			model.put("address", json);
		}
	}

	private boolean validate(ExchangeDto dto) {
		if (Lang.isEmpty(dto.getPrice()) || Lang.isEmpty(dto.getPicture()) || Lang.isEmpty(dto.getTitle()) || Lang.isEmpty(dto.getSource()) || Lang.isEmpty(dto.getUrl()))
			return false;
		return true;
	}

	private boolean validate(Map<String, String> map) {
		if (Lang.isEmpty(map.get("Price")) || Lang.isEmpty(map.get("Picture")) || Lang.isEmpty(map.get("Title")) || Lang.isEmpty(map.get("Source")) || Lang.isEmpty(map.get("Url")))
			return false;
		return true;
	}
}
