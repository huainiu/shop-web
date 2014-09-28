package com.b5m.service.exchange.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.DateTools;
import com.b5m.base.common.utils.StringTools;
import com.b5m.base.common.utils.UrlTools;
import com.b5m.base.common.utils.WebTools;
import com.b5m.bean.dto.exchange.AddressDto;
import com.b5m.bean.dto.exchange.DaigouOrder;
import com.b5m.bean.dto.exchange.GoodsDto;
import com.b5m.client.php.PHPClient;
import com.b5m.common.utils.ConfigPropUtils;
import com.b5m.common.utils.HttpUtil;
import com.b5m.common.utils.MemCachedUtils;
import com.b5m.common.utils.PWCode;
import com.b5m.service.daigou.DaigouLogUtils;
import com.b5m.service.exchange.IExchangeService;
import com.b5m.service.ontimeprice.OntimeService;

@Service
public class ExchangeServiceImpl implements IExchangeService {

	private static final Log log = LogFactory.getLog(ExchangeServiceImpl.class);

	@Autowired
	private OntimeService ontimeService;

	@Resource
	private PHPClient phpClient;

	@Override
	public JSONObject getUserAddress(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("appType", "22");
		map.put("identifier", userId);
		map.put("encryCode", PWCode.getPassWordCode("null" + userId));
		String url = ConfigPropUtils.getUcenterUrl("address.find");
		String s = WebTools.executeGetMethod(url, map, "utf-8");
		log.info("query address from ucenter, result is[" + s + "], url[" + url + "]");
		JSONObject res = JSON.parseObject(s);
		return res;
	}

	@Override
	public JSONObject deleteAddress(AddressDto dto) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deliveryId", dto.getId());
		map.put("userId", dto.getUserId());
		map.put("appType", "22");
		map.put("identifier", dto.getUserId());
		map.put("encryCode", PWCode.getPassWordCode("null" + dto.getUserId()));
		String url = ConfigPropUtils.getUcenterUrl("address.delete");
		String s = WebTools.executeGetMethod(url, map, "utf-8");
		log.info("delete address from ucenter, result is[" + s + "], url[" + url + "]");
		JSONObject res = JSON.parseObject(s);
		return res;
	}

	@Override
	public JSONObject updateOrAddAddress(AddressDto dto) {
		Map<String, String> map = new HashMap<String, String>();
		getFieldsFiltered(map, dto);
		map.put("appType", "22");
		map.put("identifier", dto.getUserId());
		map.put("encryCode", PWCode.getPassWordCode("null" + dto.getUserId()));
		String url = ConfigPropUtils.getUcenterUrl("address.add");
		if (!StringUtils.isEmpty(dto.getId()))
			url = ConfigPropUtils.getUcenterUrl("address.update");
		String s = HttpUtil.postRequest(url, getFieldsFilteredString(map), "application/x-www-form-urlencoded");
		log.info("update or add address to ucenter, result is[" + s + "], url[" + url + "]");
		JSONObject res = JSON.parseObject(s);
		return res;
	}

	@Override
	public JSONObject order(GoodsDto dto) {
		Map<String, String> map = new HashMap<String, String>();
		log.info(dto.getGoodsName() + dto.getNumber() + dto.getBdNum() + "QW");
		log.info("加密md5 : " + PWCode.getMD5String(dto.getGoodsName() + dto.getNumber() + dto.getBdNum() + "QW"));
		dto.setSignature(PWCode.getMD5String(dto.getGoodsName() + dto.getNumber() + dto.getBdNum() + "QW"));
		getFieldsFiltered(map, dto);
		map.put("appType", "22");
		map.put("identifier", dto.getUserId());
		map.put("encryCode", PWCode.getPassWordCode("null" + dto.getUserId()));
		try {
			String url = ConfigPropUtils.getUcenterUrl("address.order");
			String s = HttpUtil.postRequest(url, getFieldsFilteredString(map), "application/x-www-form-urlencoded");
			log.info("save order to ucenter, result is[" + s + "], url[" + url + "]");
			if (StringUtils.isEmpty(s))
				throw new Exception("没有返回信息");
			return JSON.parseObject(s);
		} catch (Exception e) {
			log.error("save order to ucenter error, errorMsg is[" + e.getMessage() + "]");
			JSONObject json = new JSONObject();
			json.put("ok", false);
			json.put("data", "订单保存异常");
			return json;
		}
	}

	@Override
	public boolean setHotInfo(String id, Map<String, String> map) {
		JSONObject rs = getHotProduct(id);
		if (rs == null || rs.getInteger("stock") <= 0 || rs.getInteger("status") != 1)
			return false;
		if ("元".equals(rs.getString("exchange_price_unit"))) {
			map.put("Price", rs.getString("exchange_price"));
		} else {
			map.put("Price", rs.getBigDecimal("exchange_price").divide(b2).doubleValue() + "");
		}
		map.put("Picture", rs.getString("image_url"));
		map.put("Title", rs.getString("title"));
		map.put("Url", rs.getString("origin_link"));
		map.put("encodeUrl", UrlTools.urlEncode(map.get("Url")));
		map.put("docId", id);
		map.put("referRule", rs.getString("category_id"));
		return true;
	}

	private BigDecimal b2 = new BigDecimal(100);

	@Override
	public boolean setGoodsInfo(GoodsDto dto) {
		JSONObject rs = getHotProduct(dto.getDocId());
		if (rs == null || rs.getInteger("stock") <= 0 || rs.getInteger("status") != 1)
			return false;
		if ("元".equals(rs.getString("exchange_price_unit"))) {
			dto.setGoodsPrice(rs.getString("exchange_price"));
			dto.setBdNum(rs.getBigDecimal("exchange_price").multiply(b2).longValue() + "");
		} else {
			dto.setGoodsPrice(rs.getBigDecimal("exchange_price").divide(b2).doubleValue() + "");
			dto.setBdNum(rs.getBigDecimal("exchange_price").longValue() + "");
		}
		if (rs.getString("category_id").equals("1")) {
			dto.setNumber("1");
		} else {
			if (rs.getInteger("stock") < Integer.parseInt(dto.getNumber())) {
				dto.setNumber(rs.getString("stock"));
			}
		}
		return true;
	}

	@Override
	public JSONObject getHotProduct(String id) {
		JSONObject rs = phpClient.getHotInfo(id);
		if (rs == null || !"0".equals(rs.getString("code")) || rs.getJSONObject("data") == null)
			return null;
		return rs.getJSONObject("data");
	}

	protected static void getFieldsFiltered(Map<String, String> map, Object classNameObj) {
		try {
			Class<? extends Object> clz = classNameObj.getClass();
			Method[] methods = clz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().startsWith("get")) {
					Object value = methods[i].invoke(classNameObj);
					if (value != null)
						map.put(firstZM2LowerCase(methods[i].getName().replace("get", "")), value.toString());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	protected static String MD5(String origin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return byteArrayToHexString(md.digest(origin.getBytes("UTF-8")));
		} catch (Exception ex) {
			return origin;
		}
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	protected static String getFieldsFilteredString(Map<String, String> map) {
		if (CollectionUtils.isEmpty(map))
			return "";
		StringBuilder sb = new StringBuilder();
		for (String s : map.keySet()) {
			String value = map.get(s);
			sb.append(s).append("=").append(UrlTools.urlEncode(value)).append("&");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	private static String firstZM2LowerCase(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static void main(String[] args) {
		String S = PWCode.getMD5String("禾诗（Heth）HS-08A 肩颈捶打按摩披肩带 颈椎按摩器颈部腰部肩部肩膀19900.0QW");
		System.out.println(S);
	}

}
