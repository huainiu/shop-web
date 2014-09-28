package com.b5m.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.b5m.bean.dto.OntimePriceBean;
import com.b5m.bean.dto.SkuRequest;
import com.b5m.common.utils.MemCachedUtils;
import com.b5m.common.utils.PWCode;
import com.b5m.service.daigou.DaigouLogUtils;
import com.b5m.service.ontimeprice.OntimeService;
import com.b5m.service.ontimeprice.SkuBean;
import com.b5m.web.controller.base.AbstractBaseController;

/**
 * 时时价格
 * 
 * @author echo
 * @time 2014年5月12日
 * @mail wuming@b5m.com
 */
@Controller
@RequestMapping
public class OntimeController extends AbstractBaseController {

	@Autowired
	private OntimeService ontimeService;

	@RequestMapping(value = "/ontimeprice/single")
	@ResponseBody
	public void ontimePrice(String docId, String url) throws Exception {
		OntimePriceBean ontimePriceBean = new OntimePriceBean();
		ontimePriceBean.addDocId(docId, url);
		JSONArray jsonArray = ontimeService.queryPrices(ontimePriceBean);
		if (jsonArray == null || jsonArray.size() < 1) {
			output(-1, "error get price", 0);
		} else {
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			output(0, "success", jsonArray.get(0));
			if (!"-1.0".equals(jsonObject.getString("price"))) {
				DaigouLogUtils.info("into memcache price[" + jsonObject.getString("price") + "] for docid[" + docId + "]");
				// 将价格存入缓存中
				MemCachedUtils.setCache(docId + "_ontimeprice", jsonObject.getString("price"), 86400);
			}  
		}
	}

	@RequestMapping(value = "/ontimesku/single")
	@ResponseBody
	public void ontimeSku(String url, String docId, String isDetail, String nosku, HttpServletRequest req) throws Exception {
		if (StringUtils.isEmpty(url)) {
			output(new SkuBean());
			return;
		}
		if (StringUtils.isEmpty(docId) || docId.length() != 32) {
			docId = PWCode.getMD5String(url);
		}
		SkuBean result = ontimeService.querySkuProp(new SkuRequest(docId, url), docId, req);
		output(result);
	}

}
