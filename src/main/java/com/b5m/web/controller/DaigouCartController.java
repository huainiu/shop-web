package com.b5m.web.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.b5m.base.common.utils.StringTools;
import com.b5m.bean.dto.DaigouCart;
import com.b5m.client.daigoucart.DaigouCartClient;
import com.b5m.client.daigoucart.DaigouCartLogUtils;
import com.b5m.service.daigou.CenterService;
import com.b5m.web.controller.base.AbstractBaseController;

@Controller
@RequestMapping("/daigoucart")
public class DaigouCartController extends AbstractBaseController {
	@Autowired
	private DaigouCartClient daigouCartClient;
	
	@Autowired
	private CenterService centerService;

	@RequestMapping("/center")
	public String daigouCenter(HttpServletRequest request, String docId, String priceAvg, @RequestParam(value = "price", required = false) String newPrice, String ref, String url, Integer maxNum, String origin, String col, @RequestParam(value = "title", required = false) String title, @RequestParam(value = "imgUrl", required = false) String imgUrl) {
		if (StringUtils.isEmpty(docId) && StringUtils.isEmpty(url)) {
			return "cart-center";
		}
		
		JSONObject product = centerService.getCacheProduct(docId, ref, url, null, col);
		if (product == null) {
			product = new JSONObject(); 
			product.put("Title", title);
			product.put("Picture", imgUrl);
			int remain = 1;
			BigDecimal postPrice = new BigDecimal(newPrice);
			BigDecimal _pri = postPrice.subtract(new BigDecimal(1));
			product.put("afterRemainPrice", _pri.floatValue());
			product.put("Price", newPrice);

			request.setAttribute("priceAvg", newPrice);
			request.setAttribute("product", product);
			request.setAttribute("memKey", StringTools.MD5(product.getString("Url")));
			request.setAttribute("ref", ref);
			request.setAttribute("paramurl", url);
			request.setAttribute("origin", origin);
			request.setAttribute("remainPrice", remain);
			request.setAttribute("b5tPrice", newPrice);
			return "cart-center";
		}
		
		centerService.setShopInfo(request, product, newPrice, priceAvg, ref, docId, url, maxNum);		
		request.setAttribute("origin", origin);
		return "cart-center";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public void addCart(HttpServletRequest request, HttpServletResponse resp, String key, String docId, String priceAvg, String goodsSpec, Integer count, String channel, String ref, String bk, String url, String origin, Integer direct_buy, String col, String skuId,Integer act, String b5tPrice, String title, String imgPath) throws Exception {
		DaigouCart cart = centerService.createCart(request, key, docId, priceAvg, goodsSpec, count, channel, ref, bk, url, origin, col, skuId, act, b5tPrice, title, imgPath);
		if (cart == null) {
			output(-1, "对不起，此商品已下架", "");
			return;
		}
		long start = System.currentTimeMillis();
		if (cart.getStatus() == 0) {
			output(-1, "对不起，此商品已下架", "");
			return;
		}
		try {
			if (direct_buy != null && direct_buy == 1) {
				cart.setDirect_buy(direct_buy);
			}
			JSONObject jsonObject = daigouCartClient.addDaigouCart(cart);
			int code = jsonObject.getIntValue("code");
			if (code != 100) {
				String msg = jsonObject.getString("msg");
				int c = -1;
				if (code == 103) {
					c = -2;
					msg = "对不起，特价商品每人限购一件";
				} else if (code == 104) {
					msg = "对不起，特价商品每人限购一件";
				}
				output(c, msg, "");
				return;
			}
			String val = "";
			if (direct_buy != null && direct_buy == 1) {
				val = jsonObject.getString("url");
			}
			output(1, "添加成功", val);
		} catch (Exception e) {
			DaigouCartLogUtils.error("daigou cart save error", e);
			output(-1, "对不起，添加购物车失败", "");
		}
		DaigouCartLogUtils.info("----------------------save cart success for time is[" + (System.currentTimeMillis() - start) + "ms]----------------------");
	}
	
	/*
	 * 商品详情
	 */
	@RequestMapping("/shopDetail")
	@ResponseBody
	public String shopDetail(HttpServletRequest request, String url) {	
		return centerService.getshopDetail(url);
	}
	
}
